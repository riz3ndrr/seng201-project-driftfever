package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private Label shopSubtitle;

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

    private ArrayList<Car> Cars = new ArrayList<>();
    private ArrayList<Car> availableCars = new ArrayList<>();

    private Item selectedItem;

    // Player/Game Database
    GameStats gameDB = GameStats.getInstance();


    public void buyItem(MouseEvent mouseEvent) {
        // check if need to use get method
        if (gameDB.selectedCarInCollection( (Car) selectedItem) ) {
            shopSubtitle.setText("You already own this car");
        }
        else {
            if (gameDB.getBal() >= selectedItem.getBuyingPrice()) {
                gameDB.setBal(gameDB.getBal() - selectedItem.getBuyingPrice());
                balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));

                selectedItem.setPurchased(true);
                gameDB.addCar((Car) selectedItem);
                shopSubtitle.setText("Purchased " + selectedItem.getName() + " Successfully!");
                //availableCars.remove((Car) selectedItem);
                //moveRight();




            }
            else {
                shopSubtitle.setText("Insufficient funds!");
            }
        }

        gameDB.printCars();

    }

    public void sellItem(MouseEvent mouseEvent) {
        // update later

        if (gameDB.selectedCarInCollection( (Car) selectedItem) ) {
            gameDB.setBal(gameDB.getBal() + selectedItem.getSellingPrice());
            balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
            shopSubtitle.setText("Sold successfully!");
            gameDB.removeCar((Car) selectedItem);
            gameDB.printCars();
        }
        else {
            shopSubtitle.setText("You do not own this car");
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
        if (gameDB.getCarCollectionSize() < 3) {
            shopSubtitle.setText("You must select 3 cars first");
        }
        else {
            FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/garage.fxml"));
            Parent root = baseLoader.load();

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            GarageController baseController = baseLoader.getController();
            baseController.initialize(stage);
        }





    }
    public void moveRight() {
        // hide the current car
        displaySelectedCar(false);
        if ((selectedCarIndex + 1) == availableCars.size()) {
            selectedCarIndex = 0;
        }
        else {
            selectedCarIndex++;
        }
        // then display the new car
        displaySelectedCar(true);
    }
    public void moveRight(MouseEvent event) {
        // hide the current car
        displaySelectedCar(false);
        if ((selectedCarIndex + 1) == availableCars.size()) {
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
            selectedCarIndex = availableCars.size() - 1;
        }
        else {
            selectedCarIndex--;
        }
        displaySelectedCar(true);
    }


    public void createCars() {

        Car car1 = new Car("Purple Car", 1600, 800, true, 5, 4, 6, 5, "A balanced car with smooth acceleration and steady handling.", 0);
        Car car2 = new Car("Lightning McQueen", 1550, 850, true, 4, 4, 5, 5, "A smooth ride with good stability and moderate handling.", 1);
        Car car3 = new Car("Lime Wheels", 1500, 850, true, 5, 5, 4, 4, "A versatile car with equal balance between speed and handling.", 2);
        Car car4 = new Car("Yellow Car", 1400, 700, true, 4, 5, 5, 4, "Light and agile, perfect for quick turns and smooth drifting.", 3);
        Car car5 = new Car("Azure", 1300, 750, true, 5, 4, 6, 5, "Durable with solid control and good handling on various surfaces.", 4);
        Car car6 = new Car("Crosswind", 3600, 1600, false, 8, 9, 9, 7, "High-performance with fast acceleration and responsive handling.", 5);
        Car car7 = new Car("Phantom", 4200, 1800, false, 8, 9, 7, 8, "Fast and agile, designed for quick maneuvers and high speeds.", 6);
        Car car8 = new Car("Icarus' Wings", 4600, 1400, false, 15, 3, 4, 3, "The world's fastest car. Although not renowned for its stability.", 7);
        Car car9 = new Car("Bumblebee", 5000, 1300, false, 10, 10, 9, 9, "Legend says this car has a mind of its own", 8);


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
        selectedItem = availableCars.get(selectedCarIndex);
        Car selectedCar = availableCars.get(selectedCarIndex);
        ImageView selectedCarImg = carImageList.get(selectedCar.getCarID());
        selectedCarImg.setVisible(displayImg);

        carNameLabel.setText(selectedCar.getName());
        carSpeedLabel.setText(String.format("Speed: %d", selectedCar.getSpeed()));
        carHandlingLabel.setText(String.format("Handling: %d", selectedCar.getHandling()));
        carReliabilityLabel.setText(String.format("Reliability: %d", selectedCar.getReliability()));
        carFuelEcoLabel.setText(String.format("Fuel Economy: %d", selectedCar.getFuelEconomy()));
        carDescLabel.setText(selectedCar.getDesc());
        buyItem.setText(String.format("Buy Item for $%.2f", selectedCar.getBuyingPrice()));
        sellItem.setText(String.format("Sell Item for $%.2f", selectedCar.getSellingPrice()));
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

    public void createListOfAvailableCars() {
        for (Car car : Cars) {
            if (car.isAvailableToBuy() && !car.isPurchased()) {
                availableCars.add(car);
            }
        }
    }

    public void initialize(Stage stage) {
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
        racesLeftLabel.setText("Races left: " + Integer.toString(gameDB.getRaceCount()));

        createCars();
        createListOfAvailableCars();
        displaySelectedCar(true);


    }



}
