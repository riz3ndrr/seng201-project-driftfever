package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Item;

import java.awt.event.ActionEvent;
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

    private ArrayList<Car> Cars = new ArrayList<>();

    private Item selectedItem;

    // Player/Game Database
    GameStats gameDB = GameStats.getInstance();

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void buyItem(MouseEvent mouseEvent) {
        // check if need to use get method
        if (gameDB.selectedCarInCollection((Car) selectedItem)) {
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
        if (gameDB.selectedCarInCollection((Car) selectedItem)) {
            gameDB.setBal(gameDB.getBal() + selectedItem.getSellingPrice());
            balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
            purchaseConfirmationLabel.setText("Sold successfully!");
            gameDB.removeCar((Car) selectedItem);
        }
        else {
            purchaseConfirmationLabel.setText("You do not own this car");
        }


    }


    public void bruh() {
        System.out.println("bruh");
    }

    public void createCars() {

        Car car1 = new Car(1300, 600, true, "The Stallion", 5, 4, 6, 5, "A balanced car with smooth acceleration and steady handling.");
        Car car2 = new Car(2000, 1000, true, "Silverline", 4, 4, 5, 5, "A smooth ride with good stability and moderate handling.");
        Car car3 = new Car(3000, 1500, true, "Nightfall", 5, 5, 4, 4, "A versatile car with equal balance between speed and handling.");
        Car car4 = new Car(4000, 2000, true, "Driftwood", 4, 5, 5, 4, "Light and agile, perfect for quick turns and smooth drifting.");
        Car car5 = new Car(2500, 1200, true, "Ironclad", 5, 4, 6, 5, "Durable with solid control and good handling on various surfaces.");
        Car car6 = new Car(3600, 1600, false, "Crosswind", 8, 9, 9, 7, "High-performance with fast acceleration and responsive handling.");
        Car car7 = new Car(4200, 1800, false, "Phantom", 8, 9, 7, 8, "Fast and agile, designed for quick maneuvers and high speeds.");
        Car car8 = new Car(4600, 1400, false, "Redstone", 9, 8, 8, 7, "A powerhouse car with aggressive handling and speed.");
        Car car9 = new Car(5000, 1300, false, "Shadowline", 10, 10, 9, 9, "Top-tier performance, offering unmatched speed and handling.");

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
            case "car1Select":
                selectedItem = Cars.get(0);
                displayCarAttributes(Cars.get(0));
                break;

            case "car2Select":
                selectedItem = Cars.get(1);
                displayCarAttributes(Cars.get(1));
                break;

            case "car3Select":
                selectedItem = Cars.get(2);
                displayCarAttributes(Cars.get(2));
                break;

            case "car4Select":
                selectedItem = Cars.get(3);
                displayCarAttributes(Cars.get(3));
                break;

            case "car5Select":
                selectedItem = Cars.get(4);
                displayCarAttributes(Cars.get(4));
                break;

            case "car6Select":
                selectedItem = Cars.get(5);
                displayCarAttributes(Cars.get(5));
                break;

            case "car7Select":
                selectedItem = Cars.get(6);
                displayCarAttributes(Cars.get(6));
                break;

            case "car8Select":
                selectedItem = Cars.get(7);
                displayCarAttributes(Cars.get(7));
                break;

            case "car9Select":
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

    }


}
