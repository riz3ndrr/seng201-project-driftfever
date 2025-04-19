package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Item;

import java.io.IOException;
import java.util.ArrayList;

public class ShopController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label balLabel;
    @FXML
    private Label racesLeftLabel;

    @FXML
    private Label shopItem;
    @FXML
    private Label shopCar;

    @FXML
    private Label carNameLabel;
    @FXML
    private Label carDescLabel;
    @FXML
    private Label carSpeedLabel;
    @FXML
    private Label carHandlingLabel;
    @FXML
    private Label carReliabilityLabel;
    @FXML
    private Label carFuelEcoLabel;

    @FXML
    private Label buyItem;
    @FXML
    private Label sellItem;

    @FXML
    private Label purchaseConfirmationLabel;

    @FXML
    private GridPane shopGridPane;

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
    private Button bruh;

    private ArrayList<Car> Cars = new ArrayList<>();

    private Item selectedItem;

    // Player/Game Database
    GameStats gameDB = GameStats.getInstance();


    public void buyItem(MouseEvent mouseEvent) {
        // check if need to use get method
        if (gameDB.selectedCarInCollection( (Car) selectedItem) ) {
            purchaseConfirmationLabel.setText("You already own this car");
        }
        else {

            if (gameDB.getBal() >= selectedItem.getBuyingPrice()) {
                gameDB.setBal(gameDB.getBal() - selectedItem.getBuyingPrice());
                balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
                gameDB.addCar((Car) selectedItem);

                purchaseConfirmationLabel.setText("Purchase Successful!");
            }
            else {
                purchaseConfirmationLabel.setText("Insufficient funds!");
            }
        }

        gameDB.printCars();

    }

    public void sellItem(MouseEvent mouseEvent) {
        // update later

        if (gameDB.selectedCarInCollection( (Car) selectedItem) ) {
            gameDB.setBal(gameDB.getBal() + selectedItem.getSellingPrice());
            balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
            purchaseConfirmationLabel.setText("Sold successfully!");
            gameDB.removeCar((Car) selectedItem);
            gameDB.printCars();
        }
        else {
            purchaseConfirmationLabel.setText("You do not own this car");
        }


    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label viewGarage;



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

    public void createCars() {

        Car car1 = new Car(1300, 600, true, "The Stallion", 5, 4, 6, 5, "A balanced car with smooth acceleration and steady handling.", 0);
        Car car2 = new Car(2000, 1000, true, "Silverline", 4, 4, 5, 5, "A smooth ride with good stability and moderate handling.", 1);
        Car car3 = new Car(3000, 1500, true, "Nightfall", 5, 5, 4, 4, "A versatile car with equal balance between speed and handling.", 2);
        Car car4 = new Car(4000, 2000, true, "Driftwood", 4, 5, 5, 4, "Light and agile, perfect for quick turns and smooth drifting.", 3);
        Car car5 = new Car(2500, 1200, true, "Ironclad", 5, 4, 6, 5, "Durable with solid control and good handling on various surfaces.", 4);
        Car car6 = new Car(3600, 1600, false, "Crosswind", 8, 9, 9, 7, "High-performance with fast acceleration and responsive handling.", 5);
        Car car7 = new Car(4200, 1800, false, "Phantom", 8, 9, 7, 8, "Fast and agile, designed for quick maneuvers and high speeds.", 6);
        Car car8 = new Car(4600, 1400, false, "Redstone", 9, 8, 8, 7, "A powerhouse car with aggressive handling and speed.", 7);
        Car car9 = new Car(5000, 1300, false, "Bumblebee", 10, 10, 9, 9, "Legend says this car has a mind of its own", 8);


        Cars.add(car1);
        Cars.add(car2);
        Cars.add(car3);
        Cars.add(car4);
        Cars.add(car5);
        Cars.add(car6);
        Cars.add(car7);
        Cars.add(car8);
        Cars.add(car9);
    }



    public void selectCar(MouseEvent event) {
        ImageView clickedCar = (ImageView) event.getSource();
        String carID = clickedCar.getId();

        switch (carID) {
            case "car1Img":
                selectedItem = Cars.get(0);
                displayCarAttributes(Cars.get(0));
                break;

            case "car2Img":
                selectedItem = Cars.get(1);
                displayCarAttributes(Cars.get(1));
                break;

            case "car3Img":
                selectedItem = Cars.get(2);
                displayCarAttributes(Cars.get(2));
                break;

            case "car4Img":
                selectedItem = Cars.get(3);
                displayCarAttributes(Cars.get(3));
                break;

            case "car5Img":
                selectedItem = Cars.get(4);
                displayCarAttributes(Cars.get(4));
                break;

            case "car6Img":
                selectedItem = Cars.get(5);
                displayCarAttributes(Cars.get(5));
                break;

            case "car7Img":
                selectedItem = Cars.get(6);
                displayCarAttributes(Cars.get(6));
                break;

            case "car8Img":
                selectedItem = Cars.get(7);
                displayCarAttributes(Cars.get(7));
                break;

            case "car9Img":
                selectedItem = Cars.get(8);
                displayCarAttributes(Cars.get(8));
                break;

            default:
                System.out.println("No car selected.");
        }

    }

    public void displayCarAttributes(Car car) {
        carNameLabel.setText(car.getName());
        carDescLabel.setText(car.getDesc());
        carSpeedLabel.setText("Speed: " + Integer.toString(car.getSpeed()));
        carHandlingLabel.setText("Handling: " + Integer.toString(car.getHandling()));
        carReliabilityLabel.setText("Reliability: " + Integer.toString(car.getReliability()));
        carFuelEcoLabel.setText("Fuel Economy: " + Integer.toString(car.getFuelEconomy()));
        buyItem.setText("Buy item for $" + car.getBuyingPrice());
        sellItem.setText("Sell item for $" + car.getSellingPrice() + " ");

    }

    public void initialize(Stage stage) {
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
        racesLeftLabel.setText("Races left: " + Integer.toString(gameDB.getRaceCount()));

        createCars();

        gameDB.printCars();

    }



}
