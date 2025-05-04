package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Race;

import java.io.IOException;
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
    private Label beginRaceLabel;

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

    Race selectedRace;

    public void displaySelectedRace() {
        raceNameLabel.setText(selectedRace.getName());
        raceDescLabel.setText(selectedRace.getRaceDesc());
        racePrizeLabel.setText("Prize for 1st: " + String.format("$%.2f", selectedRace.getPrizeMoney()));
        raceDistanceLabel.setText("Distance: " + String.format("%.2f", selectedRace.getDistance()) + "km");
        raceTimeLimitLabel.setText("Time Limit: " + String.format("%d", selectedRace.getTimeLimit()) + "s");
    }

    public void selectRace(MouseEvent event) {
        Label clickedRace = (Label) event.getSource();
        String raceID = clickedRace.getId();

        switch (raceID) {
            case "race0":
                selectedRace = GameManager.getRaceAtIndex(0);
                break;
            case "race1":
                selectedRace = GameManager.getRaceAtIndex(1);
                break;
            case "race2":
                selectedRace = GameManager.getRaceAtIndex(2);
                break;
            case "race3":
                selectedRace = GameManager.getRaceAtIndex(3);
                break;
            case "race4":
                selectedRace = GameManager.getRaceAtIndex(4);
                break;
        }

        displaySelectedRace();
        beginRaceLabel.setVisible(true);

    }

    public void beginRace() {
        gameDB.setSelectedRace(selectedRace);
        System.out.println("GOING TO RACE ON " + selectedRace.getName() + " USING " + selectedCar.getName());
    }

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
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
        racesLeftLabel.setText("Races left: " + Integer.toString(gameDB.getRaceCount()));
        System.out.println(selectedCar.getName());
    }



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
