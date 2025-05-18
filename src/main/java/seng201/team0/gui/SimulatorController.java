package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Race;
import seng201.team0.models.RaceComment;
import seng201.team0.models.RaceParticipant;
import seng201.team0.services.SimulatorService;
import seng201.team0.models.GameTimer;

import java.util.ArrayList;
import java.util.List;

public class SimulatorController {

    @FXML
    private Label raceNameLabel;
    @FXML
    private Label raceDistanceLabel;
    @FXML
    private Label raceTimeLimitLabel;
    @FXML
    private Label racePrizePoolLabel;
    @FXML
    private Label driverNameLabel;
    @FXML
    private Label carEntryNumberLabel;
    @FXML
    private Label carModelLabel;
    @FXML
    private Label carSpeedLabel;
    @FXML
    private Label carFuelCurrentLabel;
    @FXML
    private Label carFuelConsumptionLabel;
    @FXML
    private Label carHandlingLabel;
    @FXML
    private Label carReliabilityLabel;
    @FXML
    private Label carUpgradesLabel;
    @FXML
    private Pane headerPane;
    @FXML
    private Label remainingTimeLabel;
    @FXML
    private Pane gasStopLines;
    @FXML
    private VBox raceAreaVBox;
    @FXML
    private ScrollPane commentaryScrollPane;
    @FXML
    private VBox commentaryVBox;



    // Properties
    private GameStats gameDB = GameManager.getGameStats();
    private Race race = gameDB.getSelectedRace();
    private RaceParticipant player;
    private RaceParticipant selectedParticipant;
    private GameTimer timer;
    private SimulatorService simulatorService = new SimulatorService();
    double remainingRaceTimeSeconds;


    // Logic
    public void initialize(Stage stage) {
        player = new RaceParticipant(gameDB.getSelectedCar(), gameDB.getUserName(), 1, true);
        selectedParticipant = null;
        simulatorService.prepareRace(race, player);
        remainingRaceTimeSeconds = race.getTimeLimitHours() * 60 * 60;
        addGasStopIconsAndLines();
        lockCommentaryScrollToBottom();
        addAndDisplayComment(new RaceComment(null, "Race Commentary"));
        createRaceArea();
        positionCars();
        displayRaceStats();
        displayParticipantStats(null);
        timer = new GameTimer(300.0, e -> progressSimulation());
        timer.start();
    }

    private void createRaceArea() {
        for (RaceParticipant participant : race.getParticipants()) {
            Pane raceLine = createRaceLineUI(participant);
            raceAreaVBox.getChildren().add(raceLine);
        }
    }

    private Pane createRaceLineUI(RaceParticipant participant) {
        double height = 70.0;
        Pane pane = new Pane();
        pane.setPrefHeight(height);
        pane.setMinHeight(Region.USE_COMPUTED_SIZE);
        pane.setMaxHeight(Region.USE_COMPUTED_SIZE);
        pane.setStyle("-fx-border-color: transparent transparent black transparent; -fx-border-width: 0 0 1 0;");

        Label playerNameLabel = new Label();
        playerNameLabel.setId(participant.getIsPlayer() ? "playerNameLabel" : "opponentNameLabel");
        playerNameLabel.setText(participant.getDriverName());
        playerNameLabel.setLayoutX(10);
        playerNameLabel.setLayoutY(10);
        pane.getChildren().add(playerNameLabel);

        Image carIcon = participant.getCar().getIcon();
        ImageView imageView = new ImageView(carIcon);
        imageView.setFitHeight(height / 2);
        imageView.setFitWidth(height);
        imageView.setY(height / 4);
        imageView.setId("car");
        imageView.setOnMouseClicked(event -> {
            event.consume();
            carWasClicked(participant);
        });

        pane.getChildren().add(imageView);
        return pane;
    }

    private void carWasClicked(RaceParticipant participant) {
        selectedParticipant = participant;
        displayParticipantStats(participant);
        filterCommentary();
    }

    public void raceAreaWasClicked() {
        selectedParticipant = null;
        displayParticipantStats(null);
        filterCommentary();
    }

    private void displayRaceStats() {
        raceNameLabel.setText("Name: " + race.getName());
        raceDistanceLabel.setText("Distance: " + race.getDistanceKilometers() + " km");
        raceTimeLimitLabel.setText("Time: " + race.getTimeLimitHours() + " hours");
        racePrizePoolLabel.setText("Prize pool: $" + race.getPrizeMoney());
    }

    private void displayParticipantStats(RaceParticipant participant) {
        if (participant == null) {
            driverNameLabel.setStyle("-fx-text-fill: orange;");
            driverNameLabel.setText("Click a car for info");
            carEntryNumberLabel.setText("");
            carModelLabel.setText("");
            carSpeedLabel.setText("");
            carFuelCurrentLabel.setText("");
            carFuelConsumptionLabel.setText("");
            carHandlingLabel.setText("");
            carReliabilityLabel.setText("");
            carUpgradesLabel.setText("");
        } else {
            Car car = participant.getCar();
            driverNameLabel.setStyle(null);
            driverNameLabel.setText("Driver: " + participant.getDriverName());
            carEntryNumberLabel.setText(String.format("Entry #: %d", participant.getEntryNumber()));
            carModelLabel.setText("Model: " + car.getName());
            carSpeedLabel.setText(String.format("Top speed: %.0f km/h", car.calculateSpeed()));
            carFuelCurrentLabel.setText(String.format("Fuel: %.0f L of %.0f L tank", car.getFuelInTank(), car.calculateFuelTankCapacity()));
            carFuelConsumptionLabel.setText(String.format("Fuel efficiency: %.0f L/100kms", 100.0 * car.calculateFuelConsumption()));
            carHandlingLabel.setText(String.format("Handling: %.0f%%", 100.0 * car.calculateHandling()));
            carReliabilityLabel.setText(String.format("Reliability: %.0f%%", 100.0 * car.calculateReliability()));
            carUpgradesLabel.setText("Upgrades: " + car.upgradesToString());
        }
    }

    private void positionCars() {
        double paneWidth = raceAreaVBox.getWidth();
        double carWidth = 66;
        double areaWidth = paneWidth - carWidth;
        double pixelsPerKilometre = areaWidth / race.getDistanceKilometers();
        for (int i = 0; i < race.getParticipants().size(); i++) {
            RaceParticipant participant = race.getParticipants().get(i);
            Pane raceLinePane = (Pane) raceAreaVBox.getChildren().get(i);
            ImageView carImageView = (ImageView) raceLinePane.lookup("#car");
            double pixelPositionX = participant.getDistanceTraveledKilometers() * pixelsPerKilometre;
            carImageView.setX(pixelPositionX);
        }
    }

    private void addGasStopIconsAndLines() {
        double paneWidth = headerPane.getWidth();
        double carWidth = 50;
        double areaWidth = paneWidth - carWidth;
        double gasStopWidth = 45;
        double gasStopHeight = 45;
        double pixelsPerKilometre = areaWidth / race.getDistanceKilometers();
        Image gasStopIcon = new Image("file:src/main/resources/designs/fuelStopIcon.png");

        for (int i = 0; i < race.getGasStopDistances().size(); i++) {
            double gasStopPositionKM = race.getGasStopDistances().get(i);
            double pixelPositionX = carWidth + gasStopPositionKM * pixelsPerKilometre;

            ImageView imageView = new ImageView(gasStopIcon);
            imageView.setX(pixelPositionX - gasStopWidth / 2);
            imageView.setY(headerPane.getHeight() - gasStopHeight);
            imageView.setFitWidth(gasStopWidth);
            imageView.setFitHeight(gasStopHeight);
            headerPane.getChildren().add(imageView);

            Line dottedLine = new Line();
            dottedLine.setLayoutX(pixelPositionX);
            dottedLine.setEndY(gasStopLines.getHeight());
            dottedLine.getStrokeDashArray().addAll(7.0, 7.0);
            dottedLine.setStroke(Color.LIGHTGRAY);
            gasStopLines.getChildren().add(dottedLine);
        }
    }

    private void progressSimulation() {
        List<RaceComment> commentary = new ArrayList<>();
        double secondsSinceLastTick = timer.getElapsedSecondsInGameTime();
        for (RaceParticipant participant : race.getParticipants()) {
            double currentDistance = participant.getDistanceTraveledKilometers();
            participant.progressSimulationByTime(secondsSinceLastTick, race.getDistanceKilometers(), commentary);

            boolean didPassGasStop = race.isGasStopWithinBounds(currentDistance, participant.getDistanceTraveledKilometers());
            if (didPassGasStop ) {
                participantGasStopHandler(participant);
            }
        }
        remainingRaceTimeSeconds -= secondsSinceLastTick;
        remainingTimeLabel.setText("Time left: " + GameTimer.totalSecondsToStringHourMinSec(remainingRaceTimeSeconds));
        positionCars();
        displayParticipantStats(selectedParticipant);
        addAndDisplayCommentary(commentary);
    }

    private void participantGasStopHandler(RaceParticipant participant) {
        double chanceOfRefueling = gameDB.getOpponentRefuelProbability();
        if (Math.random() <= chanceOfRefueling) {
            Car car = participant.getCar();
            double fuelRequiredLitres = car.calculateFuelTankCapacity() - car.getFuelInTank();
            double secondsForGasStop = gameDB.getMinimumSecondsForGasStop() + gameDB.getSecondsToPumpLitreOfGas() * fuelRequiredLitres;
            participant.setSecondsPaused(secondsForGasStop);
            car.setFuelInTank(car.calculateFuelTankCapacity());
            String message = String.format("Stopping for %s to refuel %.0f litres.",
                    GameTimer.totalSecondsToStringMinSec(secondsForGasStop), fuelRequiredLitres);
            addAndDisplayComment(new RaceComment(participant, message));
        }
    }

    private void addAndDisplayCommentary(List<RaceComment> commentary) {
        for (RaceComment comment : commentary) {
            addAndDisplayComment(comment);
        }
    }

    private void addAndDisplayComment(RaceComment comment) {
        race.getCommentary().add(comment);
        if (selectedParticipant == null || comment.getParticipant() == selectedParticipant) {
            displayComment(comment);
        }
    }

    private void displayComment(RaceComment comment) {
        HBox row = comment.createUI();
        commentaryVBox.getChildren().add(row);
    }

    private void filterCommentary() {
        commentaryVBox.getChildren().clear();
        List<RaceComment> comments = race.getCommentary().getCommentsForParticipant(selectedParticipant);
        for (RaceComment comment : comments) {
            displayComment(comment);
        }
    }

    //Listens for changes to the height property of the vbox which happens when a commentary row is added/removed.
    //and scroll to the bottom when this happens so that the most recent commentary is scrolled into view.
    private void lockCommentaryScrollToBottom() {
        commentaryVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            commentaryScrollPane.setVvalue(1.0); // Scroll to bottom when the vbox height changes.
        });
    }
}