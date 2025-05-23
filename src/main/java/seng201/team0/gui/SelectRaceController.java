package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.GameTimer;
import seng201.team0.models.Race;
import seng201.team0.models.RaceRoute;

import java.io.IOException;

public class SelectRaceController extends ParentController {
    @FXML
    private TreeView<String> raceListView;
    @FXML
    private ImageView selectedCarImg;
    @FXML
    private Label carSpeedLabel;
    @FXML
    private Label carHandlingLabel;
    @FXML
    private Label carReliabilityLabel;
    @FXML
    private Label carFuelConsumptionLabel;
    @FXML
    private Label carNameLabel;
    @FXML
    private Label fuelMeterLabel;
    @FXML
    private Label raceNameLabel;
    @FXML
    private Label raceDescLabel;
    @FXML
    private Label racePrizeLabel;
    @FXML
    private Label raceDistanceLabel;
    @FXML
    private Label raceCurvinessLabel;
    @FXML
    private Label raceTimeLimitLabel;
    @FXML
    private Button beginRaceLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label balLabel;
    @FXML
    private Label racesLeftLabel;

    @FXML
    private Label brokenDownLabel;

    // Properties
    GameStats gameDB = GameManager.getGameStats();
    Car selectedCar = gameDB.selectedCar;
    private Race selectedRace;
    private RaceRoute selectedRoute;


    // Logic

    /**
     * Display the user's name, their balance and the amount of races left to complete.
     * Additionally, also display the user's selected car for racing and add the list of races and their routes.
     * @param stage
     */
    public void initialize(Stage stage) {
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText(String.format("Balance: $%,.2f", gameDB.getBal()));
        racesLeftLabel.setText(String.format("Races left: %d", gameDB.getRaceCount() - gameDB.getRacesDone()));
        addRacesAndRoutesToTree();
        displaySelectedCar();
    }

    /**
     * Add race nodes to a TreeView element and also their respective routes to each node.
     */
    private void addRacesAndRoutesToTree() {
        TreeItem<String> root = new TreeItem<>("Races");
        raceListView.setRoot(root);
        for (Race race : GameManager.getRaces()) {
            TreeItem<String> raceNode = new TreeItem<>(race.getName());
            root.getChildren().add(raceNode);
            for (RaceRoute route : RaceRoute.values()) {
                TreeItem<String> routeNode = new TreeItem<>(route.getName());
                raceNode.getChildren().add(routeNode);
            }
        }
    }

    /**
     * TODO // complete JavaDoc
     */
    public void treeViewMouseClicked() {
        TreeItem<String> selectedItem = raceListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            boolean isRace = selectedItem.getParent() == raceListView.getRoot();
            if (isRace) {
                selectedItem.setExpanded(!selectedItem.isExpanded());
            } else {
                TreeItem routeNode = selectedItem;
                TreeItem raceNode = routeNode.getParent();
                int routeIndex = raceNode.getChildren().indexOf(routeNode);
                int raceIndex = raceNode.getParent().getChildren().indexOf(raceNode);
                treeItemSelected(raceIndex, routeIndex);
            }
        }
    }

    /**
     * Update the race the game will take place on and a particular route on the race that the player
     * will traverse through.
     * @param raceIndex which is the index of the chosen race the user wishes to compete on.
     * @param routeIndex which is the index of the chosen route the user will take on the race.
     */
    private void treeItemSelected(int raceIndex, int routeIndex) {
        selectedRace = GameManager.getRaces().get(raceIndex);
        selectedRoute = RaceRoute.values()[routeIndex];
        displaySelectedRace();
    }

    /**
     * Updates the race details section of the UI with information about the currently selected race and route.
     * <p>
     * Displays details about the race include:
     * <ul>
     *   <li>Race name and associated route</li>
     *   <li>Description of the race and route</li>
     *   <li>Prize money</li>
     *   <li>The total distance of the race and number of gas stops</li>
     *   <li>How curvy the race is as a percentage</li>
     *   <li>Time limit formatted as hours, minutes, and seconds</li>
     * </ul>
     * Also make the "Begin Race" label visible to allow the player to start the race.
     */

    public void displaySelectedRace() {
        String gasStopCaption = String.format("gas %s", selectedRace.getGasStopDistances().size() == 1 ? "stop" : "stops");
        raceNameLabel.setText(String.format("%s: %s", selectedRace.getName(), selectedRoute.getName()));
        raceDescLabel.setText(selectedRace.getDesc() + "\n" + selectedRoute.getDescription());
        racePrizeLabel.setText(String.format("Prize pool: $%,.2f", selectedRace.getPrizeMoney()));
        raceDistanceLabel.setText(String.format("Distance:  %.2f km with %d %s", selectedRace.getDistanceKilometers(), selectedRace.getGasStopDistances().size(), gasStopCaption));
        raceCurvinessLabel.setText(String.format("Curviness: %.0f%%", 100.0 * selectedRace.getCurviness()));
        raceTimeLimitLabel.setText(String.format("Time Limit: %s", GameTimer.totalSecondsToStringHourMinSec(selectedRace.getTimeLimitHours() * 60.0 * 60.0)));
        if (selectedCar.isBrokenDown()) {
            beginRaceLabel.setVisible(false);
        }
        else {
            beginRaceLabel.setVisible(true);
        }

    }

    /**
     * Display the details of the car chosen for the race by the player along with an image of it.
     *<p>
     * Displayed details about the car include:
     * <ul>
     *     <li>The car's name</li>
     *     <li>Its speed</li>
     *     <li>Its handling</li>
     *     <li>Its reliability</li>
     *     <li>Its fuel efficiency</li>
     *     <li>How much of the tank is full</li>
     * </ul>
     */

    private void displaySelectedCar() {
        selectedCarImg.setImage(selectedCar.getIcon());
        carNameLabel.setText(selectedCar.getName());
        carSpeedLabel.setText(String.format("Top speed: %.0f km/h", selectedCar.calculateSpeed(0.0)));
        carHandlingLabel.setText(String.format("Handling: %.0f%%", 100.0 * selectedCar.calculateHandling()));
        carReliabilityLabel.setText(String.format("Reliability: %.2f%%", 100.0 * selectedCar.calculateReliability()));
        carFuelConsumptionLabel.setText(String.format("Fuel efficiency: %.0f L/100kms", 100.0 * selectedCar.calculateFuelConsumption()));
        fuelMeterLabel.setText(String.format("Fuel level: %.0f%% of %.0f L", selectedCar.calculateFuelPercentage(), selectedCar.calculateFuelTankCapacity()));
        fuelMeterLabel.setStyle("");

        if (selectedCar.isBrokenDown()) {
            brokenDownLabel.setVisible(true);
        }
        else {
            brokenDownLabel.setVisible(false);
        }
    }

    /**
     * When clicked, if the fuel of the selected car is below 50%, the user will not be able to start the game.
     * Otherwise, the selected race and route will be updated, and the racing will proceed.
     * @param event
     * @throws IOException if the fxml file is not able to be loaded or accessed.
     */

    public void beginRaceButtonClick(javafx.event.ActionEvent event) throws IOException {
        if (!selectedCar.isBrokenDown()) {
            gameDB.setSelectedRace(selectedRace);
            gameDB.setSelectedRoute(selectedRoute);
            switchToSimulatorScene(event);
        }

        }

}
