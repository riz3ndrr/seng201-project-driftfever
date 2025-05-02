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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import seng201.team0.GameManager;
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
    private ImageView itemImg;

    @FXML
    private ImageView upgrade1;
    @FXML
    private ImageView upgrade2;
    @FXML
    private ImageView upgrade3;

    @FXML
    private Label viewCarsLabel;
    @FXML
    private Label viewUpgradesLabel;

    @FXML
    private Label currentlyOwnLabel;



    @FXML
    private HBox carLayer;

    private List<Car> Cars = GameManager.getCars();
    private List<Upgrade> Upgrades = GameManager.getUpgrades();


    private ArrayList<Car> availableCars = new ArrayList<>();
    private ArrayList<Upgrade> availableUpgrades = new ArrayList<Upgrade>();

    private Item selectedItem;

    // determines which section of the shop to display
    private String showCarOrUpgrade = "Car";

    // Player/Game Database
    GameStats gameDB = GameManager.getGameStats();

    public boolean checkItemOwned(Item item) {
        return item.isPurchased();
    }


    public void buyItem(MouseEvent mouseEvent) {
        // check if need to use get method
        if (checkItemOwned(selectedItem) && showCarOrUpgrade.equals("Car")) {
            // You can buy multiple upgrades but only one car
            shopSubtitle.setText("You already own this item");
        }
        else {
            if (gameDB.getBal() >= selectedItem.getBuyingPrice()) {
                // If you have enough mony
                gameDB.setBal(gameDB.getBal() - selectedItem.getBuyingPrice());
                balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));


                shopSubtitle.setText("Purchased " + selectedItem.getName() + " Successfully!");


                if (showCarOrUpgrade.equals("Car")) {
                    // if buying item is a car
                    gameDB.addItem((Car) selectedItem);
                }
                else {
                    // if buying item is an upgrade

                    if (checkItemOwned(selectedItem)) {
                        ((Upgrade) selectedItem).incrementNumPurchased();
                    }
                    else {
                        gameDB.addItem((Upgrade) selectedItem);
                        ((Upgrade) selectedItem).incrementNumPurchased();
                    }
                    currentlyOwnLabel.setText("You currently own: x" + ((Upgrade) selectedItem).getNumPurchased());
                }
                selectedItem.setPurchased(true);


            }
            else {
                shopSubtitle.setText("Insufficient funds!");
            }
        }

        gameDB.printCars();
        System.out.println();
        gameDB.printUpgrades();

    }


    public void sellItem(MouseEvent mouseEvent) {
        // update later

        if (checkItemOwned(selectedItem)) {
            gameDB.setBal(gameDB.getBal() + selectedItem.getSellingPrice());
            balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
            shopSubtitle.setText("Sold successfully!");
            if (showCarOrUpgrade.equals("Car")) {
                gameDB.removeItem((Car) selectedItem);
            }
            else {

                ((Upgrade) selectedItem).decrementNumPurchased();

                if (((Upgrade) selectedItem).getNumPurchased() == 0) {
                    selectedItem.setPurchased(false);
                    gameDB.removeItem((Upgrade) selectedItem);
                }
                currentlyOwnLabel.setText("You currently own: x" + ((Upgrade) selectedItem).getNumPurchased());
            }
            gameDB.printCars();
            System.out.println();
            gameDB.printUpgrades();
        }
        else {
            shopSubtitle.setText("You do not own this item");
        }


    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label viewGarage;

    public void switchToSelectRaceScene(MouseEvent event) throws IOException {
        if (gameDB.getCarCollectionSize() < 3) {
            shopSubtitle.setText("You must select 3 cars first");
        }
        else {
            FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/selectRace.fxml"));
            Parent root = baseLoader.load();

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            SelectRaceController baseController = baseLoader.getController();
            baseController.initialize(stage);
        }

    }

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

        if (showCarOrUpgrade.equals("Car")) {
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

        if (showCarOrUpgrade.equals("Car")) {
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



    public void displaySelectedItem(boolean displayImg) {
        String selectedItemImgDirectory = "";
        int imgWidth;
        int imgHeight;

        if (showCarOrUpgrade.equals("Car")) {
            selectedItem = availableCars.get(selectedItemIndex);

            selectedItemImgDirectory = "file:src/main/resources/designs/car-icon/car" + (selectedItemIndex + 1) + ".png" ;
            imgWidth = 200;
            imgHeight = 100;

        }
        else {
            selectedItem = availableUpgrades.get(selectedItemIndex);
            selectedItemImgDirectory = "file:src/main/resources/designs/upgrade-icons/upgrade" + (selectedItemIndex + 1) + ".png" ;
            currentlyOwnLabel.setText("You currently own: x" + ((Upgrade) selectedItem).getNumPurchased());
            imgWidth = 100;
            imgHeight = 100;

        }


        Image newItemImg = new Image(selectedItemImgDirectory);
        itemImg.setFitWidth(imgWidth);
        itemImg.setFitHeight(imgHeight);
        itemImg.setImage(newItemImg);


        //selectedItemImg.setVisible(displayImg);
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
        viewCarsLabel.setVisible(true);
        viewUpgradesLabel.setVisible(false);
        itemStatsLabel.setText("Upgrade Stats:");
        displaySelectedItem(true);
        currentlyOwnLabel.setVisible(true);

    }

    public void viewCars() {
        displaySelectedItem(false);
        selectedItemIndex = 0;
        showCarOrUpgrade = "Car";
        shopSubtitle.setText("FInd a new vehicle to drive you to victory");
        viewCarsLabel.setVisible(false);
        viewUpgradesLabel.setVisible(true);
        itemStatsLabel.setText("Car Stats:");
        displaySelectedItem(true);
        currentlyOwnLabel.setVisible(false);
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
            if (upgrade.isAvailableToBuy()) {
                availableUpgrades.add(upgrade);
            }
        }
    }





    public void initialize(Stage stage) {
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
        racesLeftLabel.setText("Races left: " + gameDB.getRaceCount());

        createListOfAvailableCars();
        createListOfAvailableUpgrades();
        displaySelectedItem(true);





    }



}
