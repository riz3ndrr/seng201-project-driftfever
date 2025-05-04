package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;

import java.util.Arrays;
import java.util.List;

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
    private Label carFuelEcoLabel;
    @FXML
    private Label carNameLabel;
    @FXML
    private Label fuelMeterLabel;


    public void displaySelectedCar() {
        String selectedCarImgDirectory = "file:src/main/resources/designs/car-icon/car" + (selectedCar.getItemID() + 1) + ".png" ;
        Image carImg = new Image(selectedCarImgDirectory);
        selectedCarImg.setImage(carImg);
        carSpeedLabel.setText(String.format("Speed: %d", selectedCar.getSpeed()));
        carHandlingLabel.setText(String.format("Handling: %d", selectedCar.getHandling()));
        carReliabilityLabel.setText(String.format("Reliability: %d", selectedCar.getReliability()));
        carFuelEcoLabel.setText(String.format("Fuel Economy: %d", selectedCar.getFuelEconomy()));
        carNameLabel.setText(selectedCar.getName());
        fuelMeterLabel.setText("Fuel Tank: " + String.format("%.2f", selectedCar.getFuel()) + "%");

    }

    public void initialize(Stage stage) {
        displaySelectedCar();
        System.out.println(selectedCar.getName());
    }
}
