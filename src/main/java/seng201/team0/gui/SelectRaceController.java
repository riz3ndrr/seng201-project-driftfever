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
import seng201.team0.services.SimulatorService;

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

    // Properties
    private Stage stage;
    private Scene scene;
    private Parent root;
    GameStats gameDB = GameManager.getGameStats();
    Car selectedCar = gameDB.selectedCar;
    private Race selectedRace;
    private RaceRoute selectedRoute;


    // Logic
    public void initialize(Stage stage) {
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText(String.format("Balance: $%,.2f", gameDB.getBal()));
        racesLeftLabel.setText(String.format("Races left: %d", gameDB.getRaceCount() - gameDB.getRacesDone()));
        addRacesAndRoutesToTree();
        displaySelectedCar();
    }

    private void addRacesAndRoutesToTree() {
        // Adds race nodes to the TreeView, and routes to each race node
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

    private void treeItemSelected(int raceIndex, int routeIndex) {
        selectedRace = GameManager.getRaces().get(raceIndex);
        selectedRoute = RaceRoute.values()[routeIndex];
        displaySelectedRace();
    }

    public void displaySelectedRace() {
        String gasStopCaption = String.format("gas %s", selectedRace.getGasStopDistances().size() == 1 ? "stop" : "stops");
        raceNameLabel.setText(String.format("%s: %s", selectedRace.getName(), selectedRoute.getName()));
        raceDescLabel.setText(selectedRace.getDesc() + "\n" + selectedRoute.getDescription());
        racePrizeLabel.setText(String.format("Prize for 1st: $%,.2f", selectedRace.getPrizeMoney()));
        raceDistanceLabel.setText(String.format("Distance:  %.2f km with %d %s", selectedRace.getDistanceKilometers(), selectedRace.getGasStopDistances().size(), gasStopCaption));
        raceCurvinessLabel.setText(String.format("Curviness: %.0f%%", 100.0 * selectedRace.getCurviness()));
        raceTimeLimitLabel.setText(String.format("Time Limit: %s", GameTimer.totalSecondsToStringHourMinSec(selectedRace.getTimeLimitHours() * 60.0 * 60.0)));
        beginRaceLabel.setVisible(true);
    }

    private void displaySelectedCar() {
        selectedCarImg.setImage(selectedCar.getIcon());
        carNameLabel.setText(selectedCar.getName());
        carSpeedLabel.setText(String.format("Top speed: %.0f km/h", selectedCar.calculateSpeed(0.0)));
        carHandlingLabel.setText(String.format("Handling: %.0f%%", 100.0 * selectedCar.calculateHandling()));
        carReliabilityLabel.setText(String.format("Reliability: %.2f%%", 100.0 * selectedCar.calculateReliability()));
        carFuelConsumptionLabel.setText(String.format("Fuel efficiency: %.0f L/100kms", 100.0 * selectedCar.calculateFuelConsumption()));
        fuelMeterLabel.setText(String.format("Fuel level: %.0f%% of %.0f L", selectedCar.calculateFuelPercentage(), selectedCar.calculateFuelTankCapacity()));
        fuelMeterLabel.setStyle("");
    }

    public void beginRaceButtonClick(javafx.event.ActionEvent event) throws IOException {
        boolean isFuelLow = selectedCar.getFuelInTank() < 0.5 * selectedCar.calculateFuelTankCapacity();
        if (isFuelLow) {
            fuelMeterLabel.setStyle("-fx-text-fill: red;");
        } else {
            gameDB.setSelectedRace(selectedRace);
            gameDB.setSelectedRoute(selectedRoute);
            System.out.println("GOING TO RACE ON " + selectedRace.getName() + " USING " + selectedCar.getName());
            switchToSimulatorScene(event);
        }
    }
}
