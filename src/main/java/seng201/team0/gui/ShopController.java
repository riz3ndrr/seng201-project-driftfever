package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Purchasable;
import seng201.team0.models.Upgrade;
import seng201.team0.services.ShopService;

import java.io.IOException;
import java.util.List;

public class ShopController extends ParentController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label balLabel;
    @FXML
    private Label racesLeftLabel;

    @FXML
    private Label shopSubtitle;
    @FXML
    private Label buyItem;
    @FXML
    private Label sellItem;
    @FXML
    private Label viewItemLabel;
    @FXML
    private Label currentlyOwnLabel;

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
    private Label itemFuelTankCapacityLabel;
    @FXML
    private ImageView itemImg;


    // Properties
    GameStats gameDB = GameManager.getGameStats();
    private Stage stage;
    private Scene scene;
    ShopService shopService = new ShopService();
    private List<Car> availableCars = GameManager.getCars();
    private List<Upgrade> availableUpgrades = GameManager.getUpgrades();
    private String showCarOrUpgrade = "Car"; // determines which section of the shop to display
    private int selectedItemIndex = 0;
    private Purchasable selectedItem = availableCars.get(selectedItemIndex);


    // Logic
    public void initialize(Stage stage) {
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText(String.format("Balance: $%,.2f", gameDB.getBal()));
        racesLeftLabel.setText(String.format("Races left: %d", gameDB.getRaceCount() - gameDB.getRacesDone()));
        displaySelectedItem();
    }

    /**
     * Buy a selected item and updates the GUI depending on if the
     * item is successfully purchased or not.
     */
    public void buyItem() {
        boolean isCar = selectedItem instanceof Car;
        ShopService.PurchaseResult result = shopService.buyItem(selectedItem);

        switch (result) {
            case SUCCESS:
                balLabel.setText("Balance: $" + String.format("%,.2f", gameDB.getBal()));
                shopSubtitle.setText("Purchased " + selectedItem.getName() + " Successfully!");
                if (!isCar) {
                    Upgrade upgrade = (Upgrade) selectedItem;
                    currentlyOwnLabel.setText(String.format("You currently own %d", upgrade.getNumPurchased()));
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
        boolean isCar = selectedItem instanceof Car;
        ShopService.SellResult result = shopService.sellItem(selectedItem);
        switch (result) {
            case SUCCESS:
                balLabel.setText("Balance: $" + String.format("%,.2f", gameDB.getBal()));
                shopSubtitle.setText("Sold " + selectedItem.getName() + " Successfully!");

                if (!isCar) {
                    Upgrade upgrade = (Upgrade) selectedItem;
                    currentlyOwnLabel.setText(String.format("You currently own %d", upgrade.getNumPurchased()));
                }
                break;
            case ITEM_NOT_OWNED:
                shopSubtitle.setText("You do not own this item");
                break;

            case LAST_CAR_OWNED:
                shopSubtitle.setText("Must own at least 1 car");
                break;
        }
    }


    /**When the "select race" button is clicked, it will switch to the "select race" scene
     * if the user has selected 1 car as that is the minimum number a user will need
     * to purchase before proceeding with the rest of the game.
     * @param event
     * @throws IOException
     */
    public void trySwitchToSelectRaceScene(MouseEvent event) throws IOException {
        if (gameDB.getCarCollectionSize() < 1) {
            shopSubtitle.setText("You must first own one car");
        } else {
            switchToSelectRaceScene(event);
        }
    }

    /**When the "show garage" button is clicked, it will switch to the "garage" scene
     * if the user has selected 3 cars as that is the minimum number of cars a user will need
     * to purchase before proceeding with the rest of the game.
     * @param event
     * @throws IOException
     */
    public void trySwitchToGarageScene(MouseEvent event) throws IOException {
        if (gameDB.getCarCollectionSize() < 1) {
            shopSubtitle.setText("You must first own one car");
        } else {
            switchToGarageScene(event);
        }
    }

    public void moveRight() {
        // change the variables depending on if we're shopping cars or upgrades
        int availableItemsLength;

        if (showCarOrUpgrade.equals("Car")) {
            availableItemsLength = availableCars.size();
        } else {
            availableItemsLength = availableUpgrades.size();
        }

        if ((selectedItemIndex + 1) == availableItemsLength) {
            selectedItemIndex = 0;
        } else {
            selectedItemIndex++;
        }

        if (showCarOrUpgrade.equals("Car")) {
            selectedItem = availableCars.get(selectedItemIndex);
        } else {
            selectedItem = availableUpgrades.get(selectedItemIndex);
        }
        displaySelectedItem();
    }

    /**
     * When the left arrow is clicked, it will update the index of the selected item by -1 (i.e., move
     * to the previous item) and display its corresponding image and attributes. If the GUI is currently
     * displaying the first item and the
     * user calls this function, the index will be set such that the final item in the list of available item is shown.
     */
    public void moveLeft() {
        // change the variables depending on if we're shopping cars or upgrades

        int availableItemsLength;

        if (showCarOrUpgrade.equals("Car")) {
            availableItemsLength = availableCars.size();
        } else {
            availableItemsLength = availableUpgrades.size();
        }

        if ((selectedItemIndex) == 0) {
            selectedItemIndex = availableItemsLength - 1;
        } else {
            selectedItemIndex--;
        }

        if (showCarOrUpgrade.equals("Car")) {
            selectedItem = availableCars.get(selectedItemIndex);
        } else {
            selectedItem = availableUpgrades.get(selectedItemIndex);
        }
        displaySelectedItem();
    }

    /**
     * When this function is called, it will first determine if the shown item is of type car or upgrade, and proceed
     * to set the displayed Image to the corresponding selected item along with changing the image's dimensions to better
     * display the image. Additionally, this function will update the
     * displayed attributes such as the item's name, speed, etc.
     */
    public void displaySelectedItem() {
        boolean isCar = selectedItem instanceof Car;
        String selectedItemImgDirectory = "";
        int imgWidth;
        int imgHeight;
        if (isCar) {
            selectedItemImgDirectory = "file:src/main/resources/designs/car-icon/car" + (selectedItem.getItemID() + 1) + ".png" ;
            imgWidth = 400;
            imgHeight = 200;
        } else {
            selectedItemImgDirectory = "file:src/main/resources/designs/upgrade-icons/upgrade" + (selectedItem.getItemID()+ 1) + ".png" ;
            Upgrade upgrade = (Upgrade) selectedItem;
            currentlyOwnLabel.setText(String.format("You currently own %d", upgrade.getNumPurchased()));
            imgWidth = 200;
            imgHeight = 200;
        }
        Image newItemImg = new Image(selectedItemImgDirectory);
        itemImg.setFitWidth(imgWidth);
        itemImg.setFitHeight(imgHeight);
        itemImg.setImage(newItemImg);
        itemNameLabel.setText(selectedItem.getName());
        itemDescLabel.setText(selectedItem.getDesc());
        buyItem.setText(String.format("Buy Item for $%.2f", selectedItem.getBuyingPrice(gameDB.getDifficulty().getCostMultiplier())));
        sellItem.setText(String.format("Sell Item for $%.2f", selectedItem.getSellingPrice()));
        if (isCar) {
            Car car = (Car) selectedItem;
            itemSpeedLabel.setText(String.format("Top speed: %.0f km/h", car.calculateSpeed(0.0)));
            itemHandlingLabel.setText(String.format("Handling: %.0f%%", 100.0 * car.calculateHandling()));
            itemReliabilityLabel.setText(String.format("Reliability: %.2f%%", 100.0 * car.calculateReliability()));
            itemFuelEcoLabel.setText(String.format("Fuel efficiency: %.0f L/100kms", 100.0 * car.calculateFuelConsumption()));
            itemFuelTankCapacityLabel.setText(String.format("Fuel tank: %.0f L", car.calculateFuelTankCapacity()));
        } else {
            Upgrade upgrade = (Upgrade) selectedItem;
            itemSpeedLabel.setText("Speed: " + upgrade.displayForMultiplier(upgrade.getSpeedMultiplier()));
            itemHandlingLabel.setText("Handling: " + upgrade.displayForMultiplier(upgrade.getHandlingMultiplier()));
            itemReliabilityLabel.setText("Reliability: " + upgrade.displayForMultiplier(upgrade.getReliabilityMultiplier()));
            itemFuelEcoLabel.setText("Fuel efficiency: " + upgrade.displayForMultiplier(upgrade.getFuelEfficiencyMultiplier()));
            itemFuelTankCapacityLabel.setText("Fuel tank: " + upgrade.displayForMultiplier(upgrade.getFuelTankCapacityMultiplier()));
        }
    }

    public void switchDisplayedItemType() {
        if (showCarOrUpgrade.equals("Car")) {
            showCarOrUpgrade = "Upgrade";
            selectedItemIndex = 0;
            selectedItem = availableUpgrades.get(selectedItemIndex);

            shopSubtitle.setText("Purchase car parts which can be equipped to your car to modify its stats");
            itemStatsLabel.setText("Upgrade Stats:");
            currentlyOwnLabel.setVisible(true);
            viewItemLabel.setText("View cars");
        } else {
            showCarOrUpgrade = "Car";
            selectedItem = availableCars.get(selectedItemIndex);

            shopSubtitle.setText("FInd a new vehicle to drive you to victory");
            itemStatsLabel.setText("Car Stats:");
            viewItemLabel.setText("View upgrades");
        }
        displaySelectedItem();
    }
}
