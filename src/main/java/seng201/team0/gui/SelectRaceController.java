package seng201.team0.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Race;
import seng201.team0.services.SimulatorService;

import java.io.IOException;

public class SelectRaceController {
    // Player/Game Database
    GameStats gameDB = GameManager.getGameStats();
    Car selectedCar = gameDB.selectedCar;

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
    private Label raceTimeLimitLabel;

    @FXML
    private Button beginRaceLabel;

    @FXML
    private Label nameLabel;
    @FXML
    private Label balLabel;
    @FXML
    private Label racesLeftLabel;

//    @FXML
//    private Label race0;
//    @FXML
//    private Label race1;
//    @FXML
//    private Label race2;
//    @FXML
//    private Label race3;
//    @FXML
//    private Label race4;
//    List<Label> listOfRaceLabels = Arrays.asList(race0, race1, race2, race3, race4);

    private Race selectedRace;

    public void displaySelectedRace() {
        raceNameLabel.setText(selectedRace.getName());
        raceDescLabel.setText(selectedRace.getDesc());
        racePrizeLabel.setText("Prize for 1st: " + String.format("$%.2f", selectedRace.getPrizeMoney()));
        raceDistanceLabel.setText("Distance: " + String.format("%.2f", selectedRace.getDistanceKilometers()) + "km");
        raceTimeLimitLabel.setText("Time Limit: " + String.format("%d", selectedRace.getTimeLimitHours()) + "s");
    }

    public void beginRace(javafx.event.ActionEvent event) throws IOException {
        gameDB.setSelectedRace(selectedRace);
        System.out.println("GOING TO RACE ON " + selectedRace.getName() + " USING " + selectedCar.getName());
        SimulatorService.switchToSimulatorScene(event);
    }

    public void displaySelectedCar() {
        String selectedCarImgDirectory = "file:src/main/resources/designs/car-icon/car" + (selectedCar.getItemID() + 1) + ".png" ;
        Image carImg = new Image(selectedCarImgDirectory);
        selectedCarImg.setImage(carImg);
        carNameLabel.setText(selectedCar.getName());
        carSpeedLabel.setText(String.format("Top speed: %.0f km/h", selectedCar.calculateSpeed()));
        carHandlingLabel.setText(String.format("Handling: %.0f%%", 100.0 * selectedCar.calculateHandling()));
        carReliabilityLabel.setText(String.format("Reliability: %.0f%%", 100.0 * selectedCar.calculateReliability()));
        carFuelConsumptionLabel.setText(String.format("Fuel efficiency: %.0f L/100kms", 100.0 * selectedCar.calculateFuelConsumption()));
        fuelMeterLabel.setText(String.format("Fuel level: %.0f%% of %.0f L", selectedCar.calculateFuelPercentage(), selectedCar.calculateFuelTankCapacity()));
    }

    public void initialize(Stage stage) {
        displaySelectedCar();
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
        racesLeftLabel.setText("Races left: " + Integer.toString(gameDB.getRaceCount()));
        System.out.println(selectedCar.getName());

        raceListView.setItems(FXCollections.observableArrayList(GameManager.getRaces()));
        raceListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Race>() {

            @Override
            public void changed(ObservableValue<? extends Race> observableValue, Race race, Race t1) {
                selectedRace = raceListView.getSelectionModel().getSelectedItem();
                displaySelectedRace();
                beginRaceLabel.setVisible(true);

            }
        });
    }

    @FXML
    private ListView<Race> raceListView;
    @FXML
    private Label bruhLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToGarageScene(MouseEvent event) throws IOException {
        // Upload all the input (name, difficulty and season length) onto the GameStats "DB"
        // Proceed to the next scene

        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/garage.fxml"));
        Parent root = baseLoader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        GarageController baseController = baseLoader.getController();
        baseController.initialize(stage);
    }

    public void switchToShopScene(MouseEvent event) throws IOException {
        // Upload all the input (name, difficulty and season length) onto the GameStats "DB"
        // Proceed to the next scene

        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/shop.fxml"));
        Parent root = baseLoader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ShopController baseController = baseLoader.getController();
        baseController.initialize(stage);

    }
}
