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
import seng201.team0.services.ShopService;

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


    private ArrayList<Car> availableCars = GameManager.getAvailableCars();
    private ArrayList<Upgrade> availableUpgrades = GameManager.getAvailableUpgrades();

    public Item selectedItem;

    // determines which section of the shop to display
    private String showCarOrUpgrade = "Car";

    // Player/Game Database
    GameStats gameDB = GameManager.getGameStats();

    ShopService shopService = new ShopService();


    /**
     * Buy a selected item and updates the GUI depending on if the
     * item is successfully purchased or not.
     */
    public void buyItem() {
        // revise this import
        ShopService.purchaseResult result = shopService.buyItem(selectedItem, showCarOrUpgrade);

        switch (result) {
            case SUCCESS:
                balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
                shopSubtitle.setText("Purchased " + selectedItem.getName() + " Successfully!");
                if (showCarOrUpgrade.equals("Upgrade")) {
                    currentlyOwnLabel.setText("You currently own: x" + ((Upgrade) selectedItem).getNumPurchased());
                }
                break;
            case ALREADY_OWNED:
                shopSubtitle.setText("You already own this item");
                break;
            case INSUFFICIENT_FUNDS:
                shopSubtitle.setText("Insufficient funds");
                break;
        }
    }




    /**
     * Tries to sell the selected item and updates the GUI depending on if the
     * item is able to sold or not.
     */

    public void sellItem() {
        // update later
        ShopService.sellResult result = shopService.sellItem(selectedItem, showCarOrUpgrade);
        switch (result) {
            case SUCCESS:
                balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
                shopSubtitle.setText("Sold " + selectedItem.getName() + " Successfully!");

                if (showCarOrUpgrade.equals("Upgrade")) {
                    currentlyOwnLabel.setText("You currently own: x" + ((Upgrade) selectedItem).getNumPurchased());
                }

                break;
            case ITEM_NOT_OWNED:
                shopSubtitle.setText("You do not own this item");
        }



    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label viewGarage;

    /**When the "select race" button is clicked, it will switch to the "select race" scene
     * if the user has selected 3 cars as that is the minimum number of cars a user will need
     * to purchase before proceeding with the rest of the game.
     *
     * @param event
     * @throws IOException
     */

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

    public void end(MouseEvent event) throws IOException {
        // Upload all the input (name, difficulty and season length) onto the GameStats "DB"
        // Proceed to the next scene
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/endScreen.fxml"));
        Parent root = baseLoader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        EndScreenController baseController = baseLoader.getController();
        baseController.initialize(stage);






    }

    /**When the "show garage" button is clicked, it will switch to the "garage" scene
     * if the user has selected 3 cars as that is the minimum number of cars a user will need
     * to purchase before proceeding with the rest of the game.
     *
     * @param event
     * @throws IOException
     */
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

    /**
     * When the right arrow is clicked, it will update the index of the selected item by 1 (i.e., move
     * to the next item) and display its corresponding image and attributes. When the final item is reached and the
     * user calls this function, the index will reset and the first item will be displayed.
     */

    public void moveRight() {
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

        displaySelectedItem();
    }

    /**
     * When the left arrow is clicked, it will update the index of the selected item by -1 (i.e., move
     * to the previous item) and display its corresponding image and attributes. If the GUI is currently
     * displaying the first item and the
     * user calls this function, the index will be set such that the final item in the list of available item is shown.
     */
    public void moveLeft(MouseEvent event) {


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
        displaySelectedItem();
    }

    public void debug(MouseEvent mouseEvent) {
        System.out.println("Available cars:");
        for (Car car: availableCars) {
            System.out.println(car.getName());
        }
    }

    /**
     * When this function is called, it will first determine if the shown item is of type car or upgrade, and proceed
     * to set the displayed Image to the corresponding selected item along with changing the image's dimensions to better
     * display the image. Additionally, this function will update the
     * displayed attributes such as the item's name, speed, etc.
     */
    public void displaySelectedItem() {
        String selectedItemImgDirectory = "";
        int imgWidth;
        int imgHeight;

        if (showCarOrUpgrade.equals("Car")) {
            selectedItem = availableCars.get(selectedItemIndex);

            selectedItemImgDirectory = "file:src/main/resources/designs/car-icon/car" + (selectedItem.getItemID() + 1) + ".png" ;
            imgWidth = 200;
            imgHeight = 100;

        }
        else {
            selectedItem = availableUpgrades.get(selectedItemIndex);
            selectedItemImgDirectory = "file:src/main/resources/designs/upgrade-icons/upgrade" + (selectedItem.getItemID()+ 1) + ".png" ;
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
        selectedItemIndex = 0;
        showCarOrUpgrade = "Upgrade";
        shopSubtitle.setText("Purchase car parts which can be equipped to your car to modify its stats");
        viewCarsLabel.setVisible(true);
        viewUpgradesLabel.setVisible(false);
        itemStatsLabel.setText("Upgrade Stats:");
        displaySelectedItem();
        currentlyOwnLabel.setVisible(true);

    }

    public void viewCars() {
        selectedItemIndex = 0;
        showCarOrUpgrade = "Car";
        shopSubtitle.setText("FInd a new vehicle to drive you to victory");
        viewCarsLabel.setVisible(false);
        viewUpgradesLabel.setVisible(true);
        itemStatsLabel.setText("Car Stats:");
        displaySelectedItem();
        currentlyOwnLabel.setVisible(false);
    }




    public void initialize(Stage stage) {
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
        racesLeftLabel.setText("Races left: " + gameDB.getRaceCount());


        displaySelectedItem();





    }



}
