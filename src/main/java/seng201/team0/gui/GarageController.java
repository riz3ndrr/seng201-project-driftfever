package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GarageController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label balLabel;
    @FXML
    private Label racesLeftLabel;

    @FXML
    private Label viewShop;
    // Player/Game Database
    GameStats gameDB = GameStats.getInstance();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView car1Img;

    @FXML
    private ImageView car2Img;

    @FXML
    private ImageView car3Img;

    @FXML
    private ImageView car4Img;

    @FXML
    private ImageView car5Img;

    @FXML
    private ImageView car6Img;

    @FXML
    private ImageView car7Img;

    @FXML
    private ImageView car8Img;

    @FXML
    private ImageView car9Img;

    @FXML
    private Label carNameLabel;

    @FXML
    private Label speedLabel;

    @FXML
    private Label handlingLabel;

    @FXML
    private Label reliabilityLabel;

    @FXML
    private Label fuelEcoLabel;





    public void initialize(Stage stage) {
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
        racesLeftLabel.setText("Races left: " + Integer.toString(gameDB.getRaceCount()));
        displaySelectedCar(true);

    }

    private int selectedCarIndex = 0;

    public void displaySelectedCar(boolean displayImg) {

        List<ImageView> carImageList = Arrays.asList(
                car1Img,
                car2Img,
                car3Img,
                car4Img,
                car5Img,
                car6Img,
                car7Img,
                car8Img,
                car9Img
        );

        Car selectedCar = gameDB.searchCarAtIndex(selectedCarIndex);
        ImageView selectedCarImg = carImageList.get(selectedCar.getCarID());
        selectedCarImg.setVisible(displayImg);

        carNameLabel.setText(selectedCar.getName());
        speedLabel.setText(String.format("Speed: %d", selectedCar.getSpeed()));
        handlingLabel.setText(String.format("Handling: %d", selectedCar.getHandling()));
        reliabilityLabel.setText(String.format("Reliability: %d", selectedCar.getReliability()));
        fuelEcoLabel.setText(String.format("Fuel Economy: %d", selectedCar.getFuelEconomy()));
    }


    public void moveRight(MouseEvent event) {
        // hide the current car
        displaySelectedCar(false);
        if ((selectedCarIndex + 1) == gameDB.getCarCollectionSize()) {
            selectedCarIndex = 0;
        }
        else {
            selectedCarIndex++;
        }
        // then display the new car
        displaySelectedCar(true);
    }

    public void moveLeft(MouseEvent event) {
        displaySelectedCar(false);
        if ((selectedCarIndex) == 0) {
            selectedCarIndex = gameDB.getCarCollectionSize() - 1;
        }
        else {
            selectedCarIndex--;
        }
        displaySelectedCar(true);
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
