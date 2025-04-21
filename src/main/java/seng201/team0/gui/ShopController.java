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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Item;
import seng201.team0.models.Upgrade;

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
    private Label itemStatsLabel;

    @FXML
    private Label itemNameLabel;
    @FXML
    private Label itemDescLabel;
    @FXML
    private Label itemSpeedLabel;
    @FXML
    private Label itemHandlingLabel;
    @FXML
    private Label itemReliabilityLabel;
    @FXML
    private Label itemFuelEcoLabel;

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
    
    @FXML
    private ImageView upgrade1;
    @FXML
    private ImageView upgrade2;
    @FXML
    private ImageView upgrade3;



    @FXML
    private HBox carLayer;

    private ArrayList<Car> Cars = new ArrayList<>();
    private ArrayList<Car> availableCars = new ArrayList<>();
    private ArrayList<Upgrade> Upgrades = new ArrayList<>();
    private ArrayList<Upgrade> availableUpgrades = new ArrayList<Upgrade>();

    private Item selectedItem;

    // determines which section of the shop to display
    private String showCarOrUpgrade = "Car";

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

    int selectedItemIndex = 0;

    public void moveRight(MouseEvent event) {
        // hide the current car
        displaySelectedItem(false);

        // change the variables depending on if we're shopping cars or upgrades

        int availableItemsLength;

        if (showCarOrUpgrade == "Car") {
            availableItemsLength = availableCars.size();
        }
        else {
            availableItemsLength = availableUpgrades.size();
        }


        if ((selectedItemIndex + 1) == availableItemsLength) {
            selectedItemIndex = 0;
        }
        else {
            selectedItemIndex++;
        }

        // then display the new item
        displaySelectedItem(true);
    }

    public void moveLeft(MouseEvent event) {
        // hide the current car
        displaySelectedItem(false);

        // change the variables depending on if we're shopping cars or upgrades

        int availableItemsLength;

        if (showCarOrUpgrade == "Car") {
            availableItemsLength = availableCars.size();
        }
        else {
            availableItemsLength = availableUpgrades.size();
        }


        if ((selectedItemIndex) == 0) {
            selectedItemIndex = availableItemsLength - 1;
        }
        else {
            selectedItemIndex--;
        }

        // then display the new item
        displaySelectedItem(true);
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

    public void createUpgrades() {
        Upgrade rocketFuel = new Upgrade("Rocket Fuel", 100, 70, true, 10, -2, -2, -3, "Fuel to make your car go ZOOOOM!", 0);
        Upgrade grippyTyres = new Upgrade("Grippy Tyres", 500, 300, true, 0, 8, 0, 0, "Improved traction for tighter turns and better control at high speeds.", 1);
        Upgrade carbonFibrePlating = new Upgrade("Carbon Fibre Plating", 1000, 800, true, 2, 0, 7, 7, "Lightweight yet durable—improves speed without sacrificing reliability.", 2);

        Upgrades.add(rocketFuel);
        Upgrades.add(grippyTyres);
        Upgrades.add(carbonFibrePlating);
    }


    public void displaySelectedItem(boolean displayImg) {

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

        List<ImageView> upgradeImageList = Arrays.asList(
                upgrade1,
                upgrade2,
                upgrade3
        );

        if (showCarOrUpgrade == "Car") {
            selectedItem = availableCars.get(selectedItemIndex);

            ImageView selectedCarImg = carImageList.get(selectedItem.getItemID() );
            selectedCarImg.setVisible(displayImg);
        }
        else {
            selectedItem = availableUpgrades.get(selectedItemIndex);

            ImageView selectedUpgradeImg = upgradeImageList.get(selectedItem.getItemID() );
            selectedUpgradeImg.setVisible(displayImg);
        }


        itemNameLabel.setText(selectedItem.getName());
        itemSpeedLabel.setText(String.format("Speed: %d", selectedItem.getSpeed()));
        itemHandlingLabel.setText(String.format("Handling: %d", selectedItem.getHandling()));
        itemReliabilityLabel.setText(String.format("Reliability: %d", selectedItem.getReliability()));
        itemFuelEcoLabel.setText(String.format("Fuel Economy: %d", selectedItem.getFuelEconomy()));
        itemDescLabel.setText(selectedItem.getDesc());
        buyItem.setText(String.format("Buy Item for $%.2f", selectedItem.getBuyingPrice()));
        sellItem.setText(String.format("Sell Item for $%.2f", selectedItem.getSellingPrice()));
    }

    public void viewUpgrades() {
        displaySelectedItem(false);
        selectedItemIndex = 0;
        showCarOrUpgrade = "Upgrade";
        shopSubtitle.setText("Purchase car parts which can be equipped to your car to modify its stats");
        itemStatsLabel.setText("Upgrade Stats:");
        displaySelectedItem(true);

    }

    public void viewCars() {
        displaySelectedItem(false);
        selectedItemIndex = 0;
        showCarOrUpgrade = "Car";
        shopSubtitle.setText("FInd a new vehicle to drive you to victory");
        itemStatsLabel.setText("Car Stats:");
        displaySelectedItem(true);
    }


    public void createListOfAvailableCars() {
        for (Car car : Cars) {
            if (car.isAvailableToBuy() && !car.isPurchased()) {
                availableCars.add(car);
            }
        }
    }
    public void createListOfAvailableUpgrades() {
        for (Upgrade upgrade : Upgrades) {
            if (upgrade.isAvailableToBuy() && !upgrade.isPurchased()) {
                availableUpgrades.add(upgrade);
            }
        }
    }



    public void initialize(Stage stage) {
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
        racesLeftLabel.setText("Races left: " + Integer.toString(gameDB.getRaceCount()));

        createCars();
        createUpgrades();
        createListOfAvailableCars();
        createListOfAvailableUpgrades();
        displaySelectedItem(true);




    }



}
