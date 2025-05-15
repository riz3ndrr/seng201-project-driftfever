package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
import seng201.team0.models.RaceParticipant;
import seng201.team0.services.SimulatorService;
import seng201.team0.models.GameTimer;

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
    private TextArea commentaryTextArea;


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
        player = new RaceParticipant(gameDB.getSelectedCar(), gameDB.getUserName(), 1);
        selectedParticipant = player;
        simulatorService.prepareRace(race, player);
        remainingRaceTimeSeconds = race.getTimeLimitHours() * 60 * 60;
        addGasStopIconsAndLines();
        createRaceArea();
        positionCars();
        displayRaceStats();
        displayParticipantStats(player);
        timer = new GameTimer(600.0, e -> progressSimulation());
        timer.start();
    }

    private void createRaceArea() {
        for (RaceParticipant participant : race.getParticipants()) {
            Pane raceLine = createRaceLineUI(participant);
            raceAreaVBox.getChildren().add(raceLine);
        }
    }

    private Pane createRaceLineUI(RaceParticipant participant) {
        double height = 40.0;
        Pane pane = new Pane();
        pane.setPrefHeight(height);
        pane.setMinHeight(Region.USE_COMPUTED_SIZE);
        pane.setMaxHeight(Region.USE_COMPUTED_SIZE);
        pane.setStyle("-fx-border-color: transparent transparent black transparent; -fx-border-width: 0 0 1 0;");

        String carIcon = "car" + (participant.getCar().getItemID() + 1) + ".png";
        String iconFolder = "file:src/main/resources/designs/car-icon/";
        Image image = new Image(iconFolder + carIcon);

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height / 2);
        imageView.setFitWidth(height);
        imageView.setY(height / 4);
        imageView.setOnMouseClicked(event -> {
            selectedParticipant = participant;
            displayParticipantStats(participant);
        });

        pane.getChildren().add(imageView);
        return pane;
    }

    private void displayRaceStats() {
        raceNameLabel.setText("Name: " + race.getName());
        raceDistanceLabel.setText("Distance: " + race.getDistanceKilometers() + " km");
        raceTimeLimitLabel.setText("Time: " + race.getTimeLimitHours() + " hours");
        racePrizePoolLabel.setText("Prize pool: $" + race.getPrizeMoney());
    }

    private void displayParticipantStats(RaceParticipant participant) {
        Car car = participant.getCar();
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

    private void positionCars() {
        double paneWidth = raceAreaVBox.getWidth();
        double carWidth = 40;
        double areaWidth = paneWidth - carWidth;
        double pixelsPerKilometre = areaWidth / race.getDistanceKilometers();
        for (int i = 0; i < race.getParticipants().size(); i++) {
            RaceParticipant participant = race.getParticipants().get(i);
            Pane raceLinePane = (Pane) raceAreaVBox.getChildren().get(i);
            ImageView carImageView = (ImageView) raceLinePane.getChildren().getFirst();
            double pixelPositionX = participant.getDistanceTraveledKilometers() * pixelsPerKilometre;
            carImageView.setX(pixelPositionX);
        }
    }

    private void addGasStopIconsAndLines() {
        double paneWidth = headerPane.getWidth();
        double carWidth = 40;
        double areaWidth = paneWidth - carWidth;
        double gasStopWidth = 10;
        double gasStopHeight = 20;
        double pixelsPerKilometre = areaWidth / race.getDistanceKilometers();
        Image gasStopIcon = new Image("file:src/main/resources/designs/fuelStopIcon.png");

        for (int i = 0; i < race.getGasStopDistances().size(); i++) {
            int gasStopPositionKM = race.getGasStopDistances().get(i);
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
        double secondsSinceLastTick = timer.getElapsedSecondsInGameTime();
        for (RaceParticipant participant : race.getParticipants()) {
            participant.progressSimulationByTime(secondsSinceLastTick);
        }
        remainingRaceTimeSeconds -= secondsSinceLastTick;
        remainingTimeLabel.setText("Time left: " + GameTimer.totalSecondsToString(remainingRaceTimeSeconds));
        positionCars();
        displayParticipantStats(selectedParticipant);
    }

    private void addCommentary(RaceParticipant participant, String commentary) {
          commentaryTextArea.appendText(String.format("%s\n") );
    }
}