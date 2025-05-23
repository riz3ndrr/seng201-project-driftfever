package seng201.team0.gui;

import javafx.event.Event;
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
import seng201.team0.models.RaceRoute;
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
    private Label routeNameLabel;
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
    /**
     * Initializes the simulator UI and game logic.
     * @param stage the primary stage on which the simulator scene is set
     */
    public void initialize(Stage stage) {
        player = new RaceParticipant(gameDB.getSelectedCar(), gameDB.getUserName(), 1, true);
        player.setRoute(gameDB.getSelectedRoute());
        selectedParticipant = null;
        simulatorService.prepareRace(race, player);
        remainingRaceTimeSeconds = race.getTimeLimitHours() * 60.0 * 60.0;
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

    /**
     * Creates icons and dotted lines to mark gas stop locations.
     */
    private void createGasStopIconsAndLines() {
        double paneWidth = headerPane.getWidth();
        double carWidth = 50;
        double areaWidth = paneWidth - carWidth;
        double gasStopWidth = 45;
        double gasStopHeight = 45;
        double pixelsPerKilometre = areaWidth / race.getDistanceKilometers();
        Image gasStopIcon = new Image(getClass().getResourceAsStream("/designs/fuelStopIcon.png"));

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

    /**
     * Creates UI lines for each participant in the race and adds them to the race area container.
     */
    private void createRaceArea() {
        for (RaceParticipant participant : race.getParticipants()) {
            Pane raceLine = createRaceLineUI(participant);
            raceAreaVBox.getChildren().add(raceLine);
        }
    }

    /**
     * Creates a single race line UI element for a given participant.
     * @param participant the race participant for whom the UI line is created
     * @return a Pane representing the participant’s race line, including their name label and car icon
     */
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

    /**
     * Handles user clicks on a car icon.
     * Selects the clicked participant, updates the stats display, and filters commentary.
     * @param participant the race participant whose car was clicked
     */
    private void carWasClicked(RaceParticipant participant) {
        selectedParticipant = participant;
        displayParticipantStats(participant);
        filterCommentary();
    }

    /**
     * Handles user clicks on the race area outside of any car.
     * Deselects any participant, updates the stats display, and filters commentary.
     */
    public void raceAreaWasClicked() {
        selectedParticipant = null;
        displayParticipantStats(null);
        filterCommentary();
    }

    // Logic - Update UI
    /**
     * Updates the remaining time and player's balance labels in the header.
     */
    private void displayTimerAndBalance() {
        remainingTimeLabel.setText("Time left: " + GameTimer.totalSecondsToStringHourMinSec(remainingRaceTimeSeconds));
        balanceLabel.setText(String.format("$%,.2f", gameDB.getBal()));
    }

    /**
     * Updates the race statistics labels (name, distance, time limit, prize pool) in the UI.
     */
    private void displayRaceStats() {
        raceNameLabel.setText(String.format("Name: %s", race.getName()));
        raceDistanceLabel.setText(String.format("Distance: %.0f km", race.getDistanceKilometers()));
        raceTimeLimitLabel.setText(String.format("Time: %.2f hours", race.getTimeLimitHours()));
        racePrizePoolLabel.setText(String.format("Prize pool: $%,.2f", race.getPrizeMoney()));
    }

    /**
     * Updates the participant statistics labels in the UI.
     * If the given participant is null, resets the display to prompt the user to click a car.
     * @param participant the race participant whose stats should be displayed, or null to clear
     */
    private void displayParticipantStats(RaceParticipant participant) {
        if (participant == null) {
            driverNameLabel.setStyle("-fx-text-fill: orange;");
            driverNameLabel.setText("Click a car for info");
            carEntryNumberLabel.setText("");
            routeNameLabel.setText("");
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
            routeNameLabel.setText(String.format("Route: %s", participant.getRoute().getName()));
            carModelLabel.setText("Model: " + car.getName());
            carSpeedLabel.setText(String.format("Top speed: %.0f km/h", car.calculateSpeed(race.getCurviness())));
            carFuelCurrentLabel.setText(String.format("Fuel: %.0f L of %.0f L tank", car.getFuelInTank(), car.calculateFuelTankCapacity()));
            carFuelConsumptionLabel.setText(String.format("Fuel efficiency: %.0f L/100kms", 100.0 * car.calculateFuelConsumption()));
            carHandlingLabel.setText(String.format("Handling: %.0f%%", 100.0 * car.calculateHandling()));
            carReliabilityLabel.setText(String.format("Reliability: %.2f%%", 100.0 * car.calculateReliability()));
            carUpgradesLabel.setText("Upgrades: " + car.upgradesToString());
        }
    }

    /**
     * Positions each car icon along its race line based on the distance traveled.
     */
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
    /**
     * Adds a new comment to the race commentary list.
     * @param comment the race comment to add and display
     */
    private void addAndDisplayComment(RaceComment comment) {
        race.getCommentary().add(comment);
        if (selectedParticipant == null || comment.getParticipant() == selectedParticipant) {
            displayComment(comment);
        }
    }

    /**
     * Creates and displays a single commentary row in the commentary box.
     * @param comment the race comment to display
     */
    private void displayComment(RaceComment comment) {
        HBox row = comment.createUI();
        commentaryVBox.getChildren().add(row);
    }

    /**
     * Clears and repopulates the commentary box with comments only from the selected participant.
     */
    private void filterCommentary() {
        commentaryVBox.getChildren().clear();
        List<RaceComment> comments = race.getCommentary().getCommentsForParticipant(selectedParticipant);
        for (RaceComment comment : comments) {
            displayComment(comment);
        }
    }

    /**
     * Locks the commentary scroll pane to keep the newest comment visible at the bottom.
     * Adds a listener to the commentary container’s height property to auto-scroll to the bottom on content changes.
     */
    private void lockCommentaryScrollToBottom() {
        commentaryVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            commentaryScrollPane.setVvalue(1.0); // Scroll to bottom when the vbox height changes.
        });
    }

    //Simulation
    /**
     * Advances the simulation by seconds since the last timer tick: updates each participant’s state,
     * checks for race end conditions, and refreshes the UI accordingly.
     */
    private void progressSimulation() {
        double secondsSinceLastTick = timer.getElapsedSecondsInGameTime();
        boolean isRaceRouteBlocked = Math.random() < GameManager.getChanceOfRaceRouteBlockage() * secondsSinceLastTick;
        RaceRoute blockedRoute = isRaceRouteBlocked ? RaceRoute.getRandomRoute() : null;
        for (RaceParticipant participant : race.getParticipants()) {
            progressSimulationForParticipant(participant, secondsSinceLastTick, blockedRoute);
        }
        remainingRaceTimeSeconds -= secondsSinceLastTick;
        if (remainingRaceTimeSeconds <= 0.0) {
            timer.pause();
            promptForUserInteraction(player, Race.RaceInteractionType.RACE_TIMEOUT);
        }
        displayTimerAndBalance();
        positionCars();
        displayParticipantStats(selectedParticipant);
    }

    /**
     * Advances the simulation for a single participant by the given elapsed game time.
     * Handles pausing, finishing, fuel consumption, breakdowns, hitchhikers, gas stops, and blocked routes.
     * @param participant the race participant to update
     * @param elapsedGameTimeSeconds the amount of simulated time elapsed in seconds
     * @param blockedRoute the route that is currently blocked, or null if none
     */
    public void progressSimulationForParticipant(RaceParticipant participant, double elapsedGameTimeSeconds, RaceRoute blockedRoute) {
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
            participant.setFinishTimeSeconds(race.getTimeLimitHours() * 60.0 * 60.0 - remainingRaceTimeSeconds);
            addAndDisplayComment(new RaceComment(participant, String.format("Finished the race in %s!", GameTimer.totalSecondsToStringHourMinSec(participant.getFinishTimeSeconds()))));
        }
        consumeFuel(participant, distanceInElapsedTime);
        checkForBreakdown(participant, distanceInElapsedTime);
        checkForHitchhiker(participant, distanceInElapsedTime);
        checkForGasStop(participant, distanceInElapsedTime);
        checkForBlockedRoute(participant, blockedRoute);
    }

    /**
     * Decrements the participant’s paused time and returns true if they have just finished pausing.
     * @param participant the race participant whose paused time is to be updated
     * @param elapsedGameTimeSeconds the amount of simulated time elapsed in seconds
     * @return true if the participant’s pause period has ended this tick, false otherwise
     */
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

    /**
     * Consumes fuel for the participant based on the distance traveled in elapsed seconds.
     * If fuel runs out, the participant is out of the race.
     * @param participant the race participant whose fuel to consume
     * @param distanceInElapsedTime the distance traveled in this tick, in kilometers
     */
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

    /**
     * Checks if a hitchhiker is waiting, initiates a pick-up interaction if so.
     * @param participant the race participant potentially encountering a hitchhiker
     * @param distanceInElapsedTime the distance traveled in this tick, in kilometers
     */
    private void checkForHitchhiker(RaceParticipant participant, double distanceInElapsedTime) {
        boolean isHitchhikerWaiting = Math.random() < GameManager.getChanceOfHitchhikerPerKilometre() * distanceInElapsedTime;
        if (isHitchhikerWaiting) {
            if (participant.getIsPlayer()) {
                promptForUserInteraction(participant, Race.RaceInteractionType.PASSING_HITCHHIKER);
            } else {
                boolean didPickUpHitchhiker = Math.random() < GameManager.getOpponentPickUpHitchhikerProbability();
                if (didPickUpHitchhiker) {
                    pickupHitchhiker(participant);
                }
            }
        }
    }

    /**
     * Pauses the participant to pick up a hitchhiker, awards the player if applicable, and logs a commentary message.
     * @param participant the race participant picking up the hitchhiker
     */
    private void pickupHitchhiker(RaceParticipant participant) {
        double delay = GameManager.getHitchhikerPickUpTimeSeconds();
        participant.setSecondsPaused(participant.getSecondsPaused() + delay);
        addAndDisplayComment(new RaceComment(participant, String.format("Spent %s picking up a hitchhiker.", GameTimer.totalSecondsToStringMinSec(delay))));
        if (participant.getIsPlayer()) {
            double randomReward = GameManager.calculateRandomHitchhikerReward();
            gameDB.setBal(gameDB.getBal() + randomReward);
        }
    }

    /**
     * Checks if the participant has passed a gas stop within the bounds of where they were previously compared to now.
     * initiates a refueling interaction if so.
     * @param participant the race participant potentially passing a gas stop
     * @param distanceInElapsedTime the distance traveled in this tick, in kilometers
     */
    private void checkForGasStop(RaceParticipant participant, double distanceInElapsedTime) {
        double toDistance = participant.getDistanceTraveledKilometers();
        double fromDistance = toDistance - distanceInElapsedTime;
        boolean didPassGasStop = race.isGasStopWithinBounds(fromDistance, toDistance);
        if (didPassGasStop) {
            if (participant.getIsPlayer()) {
                promptForUserInteraction(participant, Race.RaceInteractionType.PASSING_GAS_STOP);
            } else {
                double chanceOfRefueling = GameManager.getOpponentRefuelProbability();
                if (Math.random() <= chanceOfRefueling) {
                    stopForGas(participant);
                }
            }
        }
    }

    /**
     * Pauses the participant for refueling, refills their tank, deducts cost, and logs a commentary message.
     * @param participant the race participant stopping for gas
     */
    private void stopForGas(RaceParticipant participant) {
        Car car = participant.getCar();
        double fuelRequiredLitres = car.calculateFuelTankCapacity() - car.getFuelInTank();
        double secondsForGasStop = GameManager.getMinimumSecondsForGasStop() + GameManager.getSecondsToPumpLitreOfGas() * fuelRequiredLitres;
        participant.setSecondsPaused(secondsForGasStop);
        car.setFuelInTank(car.calculateFuelTankCapacity());
        String message = String.format("Stopping for %s to refuel %.0f litres.",
                GameTimer.totalSecondsToStringMinSec(secondsForGasStop), fuelRequiredLitres);
        addAndDisplayComment(new RaceComment(participant, message));
        if (participant.getIsPlayer()) {
            double cost = fuelRequiredLitres * GameManager.getFuelCostPerLitre();
            gameDB.setBal(gameDB.getBal() - cost);
        }
    }

    /**
     * Checks if the participant’s car breaks down.
     * Initiates repair or retirement logic if a breakdown occurs.
     * @param participant the race participant potentially breaking down
     * @param distanceInElapsedTime the distance traveled in this tick, in kilometers
     */
    private void checkForBreakdown(RaceParticipant participant, double distanceInElapsedTime) {
        double chanceOfBreakdown = (1.0 - participant.getCar().getReliability()) * distanceInElapsedTime;
        boolean didBreakdown = Math.random() < chanceOfBreakdown;
        if (didBreakdown) {
            participant.setIsBrokenDown(true);
            if (participant.getIsPlayer()) {
                promptForUserInteraction(participant, Race.RaceInteractionType.BROKEN_DOWN);
            } else {
                participant.setCanRepairBreakdown(Math.random() < GameManager.getOpponentRepairProbability());
                repairBreakdownOrRetire(participant);
            }
        }
    }

    /**
     * Handles repair or permanent retirement for a broken-down car, including pause time and cost deduction.
     * @param participant the race participant requiring repair or retirement
     */
    private void repairBreakdownOrRetire(RaceParticipant participant) {
        if (participant.getCanRepairBreakdown()) {
            double randomRepairTime = GameManager.calculateRandomRepairTime();
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

    /**
     * Checks if the participant’s assigned route is currently blocked. If so, marks them as DNF and
     * logs a commentary message. Prompts the player if applicable.
     * @param participant the race participant whose route may be blocked
     * @param blockedRoute the currently blocked route, or null if none
     */
    private void checkForBlockedRoute(RaceParticipant participant, RaceRoute blockedRoute) {
        if (blockedRoute != null) {
            boolean routeMatchesParticipant = participant.getRoute() == blockedRoute;

            if (routeMatchesParticipant) {
                boolean participantAlreadyFinished = participant.getDistanceTraveledKilometers() >= race.getDistanceKilometers();

                if (!participantAlreadyFinished) {
                    participant.setIsBrokenDown(true);
                    participant.setCanRepairBreakdown(false);
                    addAndDisplayComment(new RaceComment(participant, blockedRoute.getMessage() + " DNF"));

                    if (participant.getIsPlayer()) {
                        promptForUserInteraction(participant, Race.RaceInteractionType.ROUTE_BLOCKED);
                    }
                }
            }
        }
    }

    /**
     * Pauses the simulation and displays a popup ui for interactions such as
     * picking up hitchhikers, refueling, breakdown repairs, race timeout, or blocked routes.
     * @param participant the race participant requiring user interaction
     * @param type the type of interaction to prompt
     */
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
                        participant.getCar().costToFillTank(GameManager.getFuelCostPerLitre()));
                yesCaption = "Yes, fill 'er up";
                noCaption = "No, I like walking";
                break;
            case BROKEN_DOWN:
                participant.setRepairCost(GameManager.calculateRandomRepairCost());
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
                    gameDB.selectedCar.setBrokenDown(true);
                    yesCaption = null;
                    noCaption = "OK";
                }
                break;
            case RACE_TIMEOUT:
                title = "Time's Up!";
                question = "Congratulations all drivers, the race is over. Please proceed to the award ceremony.";
                yesCaption = "Leaderboard";
                noCaption = null;
                break;
            case ROUTE_BLOCKED:
                title = "Your Route Has Been Blocked!";
                question = participant.getRoute().getMessage() + "\n\nUnfortunately, you cannot complete the race.";
                yesCaption = null;
                noCaption = "Retire";
                break;
            default:
                return;
        }
        // Display popup ui
        Image carIcon = participant.getCar().getIcon();
        UserPromptPane popup = new UserPromptPane(carIcon, title, question, yesCaption, noCaption);
        StackPane pane = popup.show();
        GridPane.setColumnSpan(pane, 3);
        GridPane.setRowSpan(pane, 3);
        simulatorGridPane.getChildren().add(pane);

        // Add handlers for yes and no options
        popup.setYesHandler(event -> {
            simulatorGridPane.getChildren().remove(pane);
            userInteractionResponse(event, participant, type, true);
        } );
        popup.setNoHandler(event -> {
            simulatorGridPane.getChildren().remove(pane);
            userInteractionResponse(event, participant, type, false);
        } );
    }

    /**
     * Handles the user’s response to a popup ui, executes the appropriate action
     * (e.g., pick up hitchhiker, refuel, repair, retire, or proceed to leaderboard).
     * @param event the event triggered by the user’s choice
     * @param participant the race participant for whom the interaction was prompted
     * @param type the type of interaction that was prompted
     * @param didChooseYes true if the user selected the affirmative option, false otherwise
     */
    private void userInteractionResponse(Event event, RaceParticipant participant, Race.RaceInteractionType type, boolean didChooseYes) {
        switch (type) {
            case PASSING_HITCHHIKER:
                if (didChooseYes) {
                    pickupHitchhiker(participant);
                }
                else {
                    addAndDisplayComment(new RaceComment(participant, "Opted to abandon hitchhiker."));
                }
                timer.resume();
                break;
            case PASSING_GAS_STOP:
                if (didChooseYes) {
                    stopForGas(participant);
                } else {
                    addAndDisplayComment(new RaceComment(participant, "Skipped fuel stop."));
                }
                timer.resume();
                break;
            case BROKEN_DOWN:
                if (didChooseYes) {

                    repairBreakdownOrRetire(participant);
                } else {
                    gameDB.selectedCar.setBrokenDown(true);
                    addAndDisplayComment(new RaceComment(participant, "Broke down and opted not to repair, retired from race."));
                }
                timer.resume();
                break;
            case RACE_TIMEOUT:
                try {
                    gameDB.setRacesDone(gameDB.getRacesDone() + 1);
                    switchToLeaderboardScene(getStage(), race);
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
                break;
            case ROUTE_BLOCKED:
                timer.resume();
                break;
        }
    }
}