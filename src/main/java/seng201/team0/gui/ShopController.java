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
    private Label itemFuelTankCapacityLabel;

    @FXML
    private Label buyItem;
    @FXML
    private Label sellItem;

    @FXML
    private Label shopSubtitle;

    private Stage stage;
    private Scene scene;


    @FXML
    private ImageView itemImg;

    @FXML
    private Label viewItemLabel;

    @FXML
    private Label currentlyOwnLabel;


    // Properties
    GameStats gameDB = GameManager.getGameStats();
    ShopService shopService = new ShopService();
    private List<Car> availableCars = GameManager.getCars();
    private List<Upgrade> availableUpgrades = GameManager.getUpgrades();
    private String showCarOrUpgrade = "Car"; // determines which section of the shop to display
    private int selectedItemIndex = 0;
    private Purchasable selectedItem = availableCars.get(selectedItemIndex);


    // Logic

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
            case EXCEEDED_CAR_OWNED_LIMIT:
                shopSubtitle.setText("The garage can only hold up to 5 cars");
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
     *
     * @param event
     * @throws IOException
     */
    public void trySwitchToSelectRaceScene(MouseEvent event) throws IOException {
              if (gameDB.getCarCollectionSize() < 1) {
                  shopSubtitle.setText("You must first own one car");
              }
              else {
                  switchToSelectRaceScene(event);
              }

          }


    public void end(MouseEvent event) throws IOException {
        // Upload all the input (name, difficulty and season length) onto the GameStats "DB"
        // Proceed to the next scene
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/leaderboard.fxml"));
        Parent root = baseLoader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        LeaderboardController baseController = baseLoader.getController();
        baseController.initialize(null);
    }

    /**When the "show garage" button is clicked, it will switch to the "garage" scene
     * if the user has selected 3 cars as that is the minimum number of cars a user will need
     * to purchase before proceeding with the rest of the game.
     *
     * @param event
     * @throws IOException if the garage fxml is unable to be accessed.
     */


     public void trySwitchToGarageScene(MouseEvent event) throws IOException {
              // Upload all the input (name, difficulty and season length) onto the GameStats "DB"
              // Proceed to the next scene
              if (gameDB.getCarCollectionSize() < 1) {
                  shopSubtitle.setText("You must first own one car");
              }
              else {
                  switchToGarageScene(event);
              }
          }

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

    public void debug(MouseEvent mouseEvent) {
        System.out.println(selectedItem.getName());
        System.out.println(selectedItem instanceof Car);
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
            selectedItemImgDirectory = "/designs/car-icon/car" + (selectedItem.getItemID() + 1) + ".png" ;
            imgWidth = 400;
            imgHeight = 200;
        } else {
            selectedItemImgDirectory = "/designs/upgrade-icons/upgrade" + (selectedItem.getItemID()+ 1) + ".png" ;
            Upgrade upgrade = (Upgrade) selectedItem;
            currentlyOwnLabel.setText(String.format("You currently own %d", upgrade.getNumPurchased()));
            imgWidth = 200;
            imgHeight = 200;
        }
        Image newItemImg = new Image(getClass().getResourceAsStream(selectedItemImgDirectory));
        itemImg.setFitWidth(imgWidth);
        itemImg.setFitHeight(imgHeight);
        itemImg.setImage(newItemImg);
        //selectedItemImg.setVisible(displayImg);
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


    /**
     * Toggle between displaying cars and upgrades on the UI.
     * If cars were previously shown, then it will display the first upgrade in the list and reflect such changes
     * in the UI by showing upgrade-related information. Likewise if upgrades were previously shown.
     */

    public void switchDisplayedItemType() {
        if (showCarOrUpgrade.equals("Car")) {
            showCarOrUpgrade = "Upgrade";
            selectedItemIndex = 0;
            selectedItem = availableUpgrades.get(selectedItemIndex);

            shopSubtitle.setText("Purchase car parts which can be equipped to your car to modify its stats");
            itemStatsLabel.setText("Upgrade Stats:");
            currentlyOwnLabel.setVisible(true);
            viewItemLabel.setText("View cars");

        }
        else {
            showCarOrUpgrade = "Car";
            selectedItem = availableCars.get(selectedItemIndex);

            shopSubtitle.setText("Find a new vehicle to drive you to victory");
            itemStatsLabel.setText("Car Stats:");
            viewItemLabel.setText("View upgrades");
        }
        displaySelectedItem();

    }

    /**
     * Show the user's name, the amount of races they have left to compete and their current balance.
     * Proceed to display the default selected item.
     * @param stage
     */
    public void initialize(Stage stage) {
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText(String.format("Balance: $%,.2f", gameDB.getBal()));
        racesLeftLabel.setText(String.format("Races left: %d", gameDB.getRaceCount() - gameDB.getRacesDone()));
        if (gameDB.getRacesDone() >= 1 && !gameDB.areAllCarsBrokenDown()) {
            viewItemLabel.setVisible(true);
        }
        else {
            viewItemLabel.setVisible(false);
        }
        displaySelectedItem();
    }



}
