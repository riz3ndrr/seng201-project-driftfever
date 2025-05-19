package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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

import java.util.List;

public class SimulatorController extends ParentController {
    @FXML
    private GridPane simulatorGridPane;
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
    public Label balanceLabel;
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


    // Logic - Setup Race and Create UI
    public void initialize(Stage stage) {
        player = new RaceParticipant(gameDB.getSelectedCar(), gameDB.getUserName(), 1, true);
        selectedParticipant = null;
        simulatorService.prepareRace(race, player);
        remainingRaceTimeSeconds = race.getTimeLimitHours() * 60 * 60;
        createGasStopIconsAndLines();
        lockCommentaryScrollToBottom();
        addAndDisplayComment(new RaceComment(null, "Race Commentary"));
        createRaceArea();
        positionCars();
        displayRaceStats();
        displayParticipantStats(null);
        timer = new GameTimer(300.0, e -> progressSimulation());
        timer.start();
    }

    private void createGasStopIconsAndLines() {
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

    // Logic - Update UI
    private void displayTimerAndBalance() {
        remainingTimeLabel.setText("Time left: " + GameTimer.totalSecondsToStringHourMinSec(remainingRaceTimeSeconds));
        balanceLabel.setText(String.format("$%,.2f", gameDB.getBal()));
    }

    private void displayRaceStats() {
        raceNameLabel.setText(String.format("Name: %s", race.getName()));
        raceDistanceLabel.setText(String.format("Distance: %.0f km", race.getDistanceKilometers()));
        raceTimeLimitLabel.setText(String.format("Time: %d hours", race.getTimeLimitHours()));
        racePrizePoolLabel.setText(String.format("Prize pool: $%,.2f", race.getPrizeMoney()));
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
            carSpeedLabel.setText(String.format("Top speed: %.0f km/h", car.calculateSpeed(race.getCurviness())));
            carFuelCurrentLabel.setText(String.format("Fuel: %.0f L of %.0f L tank", car.getFuelInTank(), car.calculateFuelTankCapacity()));
            carFuelConsumptionLabel.setText(String.format("Fuel efficiency: %.0f L/100kms", 100.0 * car.calculateFuelConsumption()));
            carHandlingLabel.setText(String.format("Handling: %.0f%%", 100.0 * car.calculateHandling()));
            carReliabilityLabel.setText(String.format("Reliability: %.2f%%", 100.0 * car.calculateReliability()));
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


    // Logic - Display and Filter Commentary
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


    // Logic - Simulation
    private void progressSimulation() {
        double secondsSinceLastTick = timer.getElapsedSecondsInGameTime();
        for (RaceParticipant participant : race.getParticipants()) {
            progressSimulationForParticipant(participant, secondsSinceLastTick);
        }
        remainingRaceTimeSeconds -= secondsSinceLastTick;
        displayTimerAndBalance();
        positionCars();
        displayParticipantStats(selectedParticipant);
    }

    public void progressSimulationForParticipant(RaceParticipant participant, double elapsedGameTimeSeconds) {
        // If paused reduce the timer and wait.
        boolean didFinishPausing = waitIfPaused(participant, elapsedGameTimeSeconds);
        if (didFinishPausing && participant.getIsBrokenDown() && participant.getCanRepairBreakdown()) {
            participant.setIsBrokenDown(false);
            addAndDisplayComment(new RaceComment(participant, "Successfully repaired their car."));
        }
        // If paused, out of fuel, broken down, or finished, do nothing.
        if (participant.getSecondsPaused() > 0.0 ||
                participant.getCar().getFuelInTank() <= 0.0 ||
                participant.getIsBrokenDown() ||
                participant.getDistanceTraveledKilometers() >= race.getDistanceKilometers()) {
            return;
        }
        // Calculate extra distance
        double speedKilometresPerSecond = participant.getCar().calculateSpeed(race.getCurviness()) / (60 * 60);
        double distanceInElapsedTime = speedKilometresPerSecond * elapsedGameTimeSeconds;
        participant.setDistanceTraveledKilometers(participant.getDistanceTraveledKilometers() + distanceInElapsedTime);
        if (participant.getDistanceTraveledKilometers() > race.getDistanceKilometers()) {
            participant.setDistanceTraveledKilometers(race.getDistanceKilometers());
            addAndDisplayComment(new RaceComment(participant, "Finished the race!"));
        }
        consumeFuel(participant, distanceInElapsedTime);
        checkForBreakdown(participant, distanceInElapsedTime);
        checkForHitchhiker(participant, distanceInElapsedTime);
        checkForGasStop(participant, distanceInElapsedTime);
    }

    private boolean waitIfPaused(RaceParticipant participant, double elapsedGameTimeSeconds) {
        if (participant.getSecondsPaused() > 0.0) {
            participant.setSecondsPaused(participant.getSecondsPaused() - elapsedGameTimeSeconds);
            if (participant.getSecondsPaused() <= 0.0) {
                participant.setSecondsPaused(0.0);
                return true;
            }
        }
        return false;
    }

    private void consumeFuel(RaceParticipant participant, double distanceInElapsedTime) {
        // Calculate remaining fuel in tank
        double fuelConsumptionLitresPerKilometer = participant.getCar().calculateFuelConsumption();
        double newFuelLitres = participant.getCar().getFuelInTank() - fuelConsumptionLitresPerKilometer * distanceInElapsedTime;
        if (newFuelLitres < 0.0) {
            newFuelLitres = 0.0;
            addAndDisplayComment(new RaceComment(participant,"Ran out of fuel and is out of the race."));
        }
        participant.getCar().setFuelInTank(newFuelLitres);
    }

    private void checkForHitchhiker(RaceParticipant participant, double distanceInElapsedTime) {
        boolean isHitchhikerWaiting = Math.random() < gameDB.getChanceOfHitchhikerPerKilometre() * distanceInElapsedTime;
        if (isHitchhikerWaiting) {
            if (participant.getIsPlayer()) {
                promptForUserInteraction(participant, Race.RaceInteractionType.PASSING_HITCHHIKER);
            } else {
                boolean didPickUpHitchhiker = Math.random() < gameDB.getOpponentPickUpHitchhikerProbability();
                if (didPickUpHitchhiker) {
                    pickupHitchhiker(participant);
                }
            }
        }
    }

    private void pickupHitchhiker(RaceParticipant participant) {
        double delay = gameDB.getHitchhikerPickUpTimeSeconds();
        participant.setSecondsPaused(participant.getSecondsPaused() + delay);
        addAndDisplayComment(new RaceComment(participant, String.format("Spent %s picking up a hitchhiker.", GameTimer.totalSecondsToStringMinSec(delay))));
        if (participant.getIsPlayer()) {
            double randomReward = gameDB.calculateRandomHitchhikerReward();
            gameDB.setBal(gameDB.getBal() + randomReward);
        }
    }

    private void checkForGasStop(RaceParticipant participant, double distanceInElapsedTime) {
        double toDistance = participant.getDistanceTraveledKilometers();
        double fromDistance = toDistance - distanceInElapsedTime;
        boolean didPassGasStop = race.isGasStopWithinBounds(fromDistance, toDistance);
        if (didPassGasStop) {
            if (participant.getIsPlayer()) {
                promptForUserInteraction(participant, Race.RaceInteractionType.PASSING_GAS_STOP);
            } else {
                double chanceOfRefueling = gameDB.getOpponentRefuelProbability();
                if (Math.random() <= chanceOfRefueling) {
                    stopForGas(participant);
                }
            }
        }
    }

    private void stopForGas(RaceParticipant participant) {
        Car car = participant.getCar();
        double fuelRequiredLitres = car.calculateFuelTankCapacity() - car.getFuelInTank();
        double secondsForGasStop = gameDB.getMinimumSecondsForGasStop() + gameDB.getSecondsToPumpLitreOfGas() * fuelRequiredLitres;
        participant.setSecondsPaused(secondsForGasStop);
        car.setFuelInTank(car.calculateFuelTankCapacity());
        String message = String.format("Stopping for %s to refuel %.0f litres.",
                GameTimer.totalSecondsToStringMinSec(secondsForGasStop), fuelRequiredLitres);
        addAndDisplayComment(new RaceComment(participant, message));
        if (participant.getIsPlayer()) {
            double cost = fuelRequiredLitres * gameDB.getFuelCostPerLitre();
            gameDB.setBal(gameDB.getBal() - cost);
        }
    }

    private void checkForBreakdown(RaceParticipant participant, double distanceInElapsedTime) {
        double chanceOfBreakdown = (1.0 - participant.getCar().getReliability()) * distanceInElapsedTime;
        boolean didBreakdown = Math.random() < chanceOfBreakdown;
        if (didBreakdown) {
            participant.setIsBrokenDown(true);
            if (participant.getIsPlayer()) {
                promptForUserInteraction(participant, Race.RaceInteractionType.BROKEN_DOWN);
            } else {
                participant.setCanRepairBreakdown(Math.random() < gameDB.getOpponentRepairProbability());
                repairBreakdownOrRetire(participant);
            }
        }
    }

    private void repairBreakdownOrRetire(RaceParticipant participant) {
        if (participant.getCanRepairBreakdown()) {
            double randomRepairTime = gameDB.calculateRandomRepairTime();
            participant.setSecondsPaused(randomRepairTime);
            addAndDisplayComment(new RaceComment(participant, String.format("Car has broken down and will take %s to fix.", GameTimer.totalSecondsToStringMinSec(participant.getSecondsPaused()))));
            if (participant.getIsPlayer()) {
                double cost = participant.getRepairCost();
                gameDB.setBal(gameDB.getBal() - cost);
            }
        } else {
            addAndDisplayComment(new RaceComment(participant, "Car has permanently broken down and is out of the race!"));
        }
    }

    private void promptForUserInteraction(RaceParticipant participant, Race.RaceInteractionType type) {
        timer.pause();
        String title;
        String question;
        String yesCaption;
        String noCaption;
        switch (type) {
            case PASSING_HITCHHIKER:
                title = "Choose Carefully";
                question = "A desperate hitchhiker is looking for a lift and might have some money, but this will cost you some precious time.\nStop and pick them up?";
                yesCaption = "Yes, pickup";
                noCaption = "Ditch 'em";
                break;
            case PASSING_GAS_STOP:
                title = "Choose Carefully";
                question = String.format("You're passing a gas stop and you have %.0f/%.0fL in your tank.\nWould you like to refill in exchange for valuable time and $%.2f?",
                        participant.getCar().getFuelInTank(),
                        participant.getCar().calculateFuelTankCapacity(),
                        participant.getCar().costToFillTank(gameDB.getFuelCostPerLitre()));
                yesCaption = "Yes, fill 'er up";
                noCaption = "No, I like walking";
                break;
            case BROKEN_DOWN:
                participant.setRepairCost(gameDB.calculateRandomRepairCost());
                boolean canAffordRepairs = gameDB.getBal() >= participant.getRepairCost();
                participant.setCanRepairBreakdown(canAffordRepairs);
                if (canAffordRepairs) {
                    title = "Choose Carefully";
                    question = String.format("You have broken down and it's going to take a while to repair!\nDo you want to pay $%.2f for repairs?", participant.getRepairCost());
                    yesCaption = "Yes, repair and wait";
                    noCaption = "No, retire from race";
                } else {

                    title = "Oh No!";
                    question = String.format("You have broken down and don't have enough funds to cover the $%.2f repair bill.\nYou're out of the race.", participant.getRepairCost());
                    yesCaption = null;
                    noCaption = "OK";
                }
                break;
            default:
                return;
        }
        Image carIcon = participant.getCar().getIcon();
        UserPromptPane popup = new UserPromptPane(carIcon, title, question, yesCaption, noCaption);
        StackPane pane = popup.show();
        GridPane.setColumnSpan(pane, 3);
        GridPane.setRowSpan(pane, 3);
        simulatorGridPane.getChildren().add(pane);

        // Add handlers for yes and no options
        popup.setYesHandler(e -> {
            simulatorGridPane.getChildren().remove(pane);
            userInteractionResponse(participant, type, true);
        } );
        popup.setNoHandler(e -> {
            simulatorGridPane.getChildren().remove(pane);
            userInteractionResponse(participant, type, false);
        } );
    }

    private void userInteractionResponse(RaceParticipant participant, Race.RaceInteractionType type, boolean didChooseYes) {
        if (didChooseYes) {
            switch (type) {
                case PASSING_HITCHHIKER:
                    pickupHitchhiker(participant);
                    break;
                case PASSING_GAS_STOP:
                    stopForGas(participant);
                    break;
                case BROKEN_DOWN:
                    repairBreakdownOrRetire(participant);
                    break;
            }
        }
        timer.resume();
    }
}