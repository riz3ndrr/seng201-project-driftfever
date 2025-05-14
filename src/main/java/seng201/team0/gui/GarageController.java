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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Upgrade;
import seng201.team0.services.GarageService;

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
    GameStats gameDB = GameManager.getGameStats();

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
    private Label fuelConsumptionLabel;

    @FXML
    private Label fuelMeterLabel;

    @FXML
    private Label fillTankLabel;


    @FXML
    private Pane upgradesLayer;

    @FXML
    private HBox carsLayer;


    @FXML
    private ImageView upgr0;
    @FXML
    private ImageView upgr1;
    @FXML
    private ImageView upgr2;
    @FXML
    private ImageView upgr3;


    @FXML
    private Label upgradeSpeedLabel;
    @FXML
    private Label upgradeHandlingLabel;
    @FXML
    private Label upgradeReliabilityLabel;
    @FXML
    private Label upgradeFuelEcoLabel;

    @FXML
    private Label currentlySelectedLabel;

    @FXML
    private Label equipUpgrade;

    @FXML
    private Label unequipUpgrade;

    @FXML
    private Label switchUpgradesLabel;

    @FXML
    private Label upgradesHeaderLabel;

    @FXML
    private Label selectedCarTitle;


    @FXML
    private GridPane upgradesGridPane;

    @FXML
    private Label resultEquipMessage;

    GarageService garageService = new GarageService();


    public void initialize(Stage stage) {
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText("Balance: $" + String.format("%.2f", gameDB.getBal()));
        racesLeftLabel.setText("Races left: " + Integer.toString(gameDB.getRaceCount()));
        displaySelectedCar(true);
        displayAvailableUpgrades();
    }

    private int selectedCarIndex = 0;
    private Car selectedCar;

    public void displayCarStats(Car car) {
        String buttonCaption = String.format("Fill tank for $%.2f", garageService.payableCostToFillTank(selectedCar));
        double fuelPercentage = car.calculateFuelPercentage();
        if (fuelPercentage >= 100.0) { buttonCaption = "Tank full"; }

        carNameLabel.setText(car.getName());
        fuelMeterLabel.setText(String.format("Fuel level: %.0f%%", fuelPercentage));
        fillTankLabel.setText(buttonCaption);
        speedLabel.setText(String.format("Top speed: %.0f km/h", car.calculateSpeed()));
        handlingLabel.setText(String.format("Handling: %.0f%%", 100.0 * car.calculateHandling()));
        reliabilityLabel.setText(String.format("Reliability: %.0f%%", 100.0 * car.calculateReliability()));
        fuelConsumptionLabel.setText(String.format("Fuel efficiency: %.0f L/100kms", 100.0 * car.calculateFuelConsumption()));
        //TODO add a label "Tank Capcacity: " for car.calculateFuelTankCapacity() (same as in ShopController)
    }

    public void fillTank() {
        garageService.fillTank(selectedCar);
        balLabel.setText(String.format("Balance: $%.2f", gameDB.getBal()));
        displayCarStats(selectedCar);
    }

    @FXML
    private ImageView carImg;
    @FXML
    private Label selectCarLabel;

    public void displaySelectedCar(boolean displayImg) {
        
        String selectedItemImgDirectory = "";
        selectedCar = gameDB.searchCarAtIndex(selectedCarIndex);

        selectedItemImgDirectory = "file:src/main/resources/designs/car-icon/car" + (selectedCar.getItemID() + 1) + ".png" ;
        Image newItemImg = new Image(selectedItemImgDirectory);
        carImg.setImage(newItemImg);

        if ((gameDB.getSelectedCar().getName()).equals(selectedCar.getName())) {
            selectedCarTitle.setVisible(true);
            selectCarLabel.setVisible(false);
        }
        else {
            selectedCarTitle.setVisible(false);
            selectCarLabel.setVisible(true);
        }
        displayCarStats(selectedCar);
    }

    public void moveRight(MouseEvent event) {
        // get rid of unnecessary text
        resultEquipMessage.setVisible(false);


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

        if (showEquippedItems) {
            //update the list of equipped items as it will change from car to car
            displayAvailableUpgrades();
        }
    }

    public void moveLeft(MouseEvent event) {
        // get rid of unnecessary text
        resultEquipMessage.setVisible(false);

        displaySelectedCar(false);
        if ((selectedCarIndex) == 0) {
            selectedCarIndex = gameDB.getCarCollectionSize() - 1;
        }
        else {
            selectedCarIndex--;
        }
        displaySelectedCar(true);

        if (showEquippedItems) {
            System.out.println("AHHH");
            //update the list of equipped items as it will change from car to car
            displayAvailableUpgrades();
        }
    }

    public void switchUpgrades(MouseEvent event) {
        if (showEquippedItems) {
            // going to show available items
            showEquippedItems = false;
            switchUpgradesLabel.setText("Show Equipped Items");
            upgradesHeaderLabel.setText("Available Upgrades:");
            equipUpgrade.setVisible(true);
            unequipUpgrade.setVisible(false);
        }
        else {
            // going to show equipped items
            showEquippedItems = true;
            switchUpgradesLabel.setText("Show Unequipped Items");
            upgradesHeaderLabel.setText("Equipped Upgrades:");
            equipUpgrade.setVisible(false);
            unequipUpgrade.setVisible(true);
        }
        displayAvailableUpgrades();
    }

    public void unequipUpgrade() {
        GarageService.UnequipResult result = garageService.unequipUpgrade(selectedUpgrade, selectedCar);

        switch (result) {
            case SUCCESS:
                resultEquipMessage.setText("Unequipped " + selectedUpgrade.getName() + " successfully!");
                resultEquipMessage.setVisible(true);
                displayCarStats(selectedCar);
                displayAvailableUpgrades();

                break;
            case UPGRADE_NOT_SELECTED:
                resultEquipMessage.setText("No upgrade is selected");
                resultEquipMessage.setStyle("-fx-text-fill: red");
                resultEquipMessage.setVisible(true);
                break;
        }
    }

    public void equipUpgrade() {
        GarageService.EquipResult result = garageService.equipUpgrade(selectedUpgrade, selectedCar);

        switch (result) {
            case UPGRADE_NOT_SELECTED:
                resultEquipMessage.setText("No upgrade is selected");
                resultEquipMessage.setStyle("-fx-text-fill: red");
                resultEquipMessage.setVisible(true);
                break;

            case UPGRADE_ALREADY_EQUIPPED:
                resultEquipMessage.setText("Upgrade already equipped");
                resultEquipMessage.setStyle("-fx-text-fill: red");
                resultEquipMessage.setVisible(true);
                break;

            case SUCCESS:
                resultEquipMessage.setStyle("-fx-text-fill: green");
                resultEquipMessage.setText("Equipped " + selectedUpgrade.getName() + " successfully!");
                resultEquipMessage.setVisible(true);
                displayCarStats(selectedCar);
                selectedUpgrade = null;
        }
    }

    public void updateSelectedCar() {
        garageService.updateSelectedCar(selectedCar);
        selectedCarTitle.setVisible(true);
        selectCarLabel.setVisible(false);
    }

    public void displaySelectedUpgrade(Upgrade selectedUpgrade) {
        if (showEquippedItems) {
            resultEquipMessage.setText("Selecting " + selectedUpgrade.getName());
        }
        else {
            resultEquipMessage.setText(String.format("Selecting %s, quantity: x%d", selectedUpgrade.getName(), selectedUpgrade.getNumPurchased()));
        }

        resultEquipMessage.setStyle("-fx-text-fill: grey");
        resultEquipMessage.setVisible(true);

        //TODO set to blank because the car stats now have the cumulative effect of upgrades,
        // remove these labels from the ui later as they're no longer needed
        upgradeSpeedLabel.setText("");
        upgradeHandlingLabel.setText("");
        upgradeReliabilityLabel.setText("");
        upgradeFuelEcoLabel.setText("");
        //currentlySelectedLabel.setText(selectedUpgrade.getName());
    }

    private Upgrade selectedUpgrade;

    public void selectUpgrade(MouseEvent event) {

        ImageView clickedUpgrade = (ImageView) event.getSource();

        String upgradeID = clickedUpgrade.getId();

        switch (upgradeID) {
            case "upgr0":
                selectedUpgrade = GameManager.getUpgradeWithID(0);
                break;

            case "upgr1":
                selectedUpgrade = GameManager.getUpgradeWithID(1);
                break;

            case "upgr2":
                selectedUpgrade = GameManager.getUpgradeWithID(2);
                break;

            case "upgr3":
                selectedUpgrade = GameManager.getUpgradeWithID(3);
                break;

            default:
                System.out.println("Not selected upgrade");
        }
        displaySelectedUpgrade(selectedUpgrade);
    }

    private boolean showEquippedItems = false;

    public void displayAvailableUpgrades() {
        List<Upgrade> availableUpgrades;

        if (showEquippedItems) {
            availableUpgrades = selectedCar.getEquippedUpgrades();

        }
        else {
            availableUpgrades = gameDB.getUpgradeCollection();
        }

        for (Upgrade upr : availableUpgrades) {
            System.out.println(upr);
        }


        List<ImageView> upgradeImageList = Arrays.asList(upgr0, upgr1, upgr2, upgr3);

        // maybe optimise this later, used when upgrade is no longer available
        for (ImageView image : upgradeImageList) {
            image.setVisible(false);
        }

        int rowIndex = 0;
        int colIndex = 0;


        for (Upgrade u : availableUpgrades) {
            if (colIndex == 2) {

                colIndex = 0;
                rowIndex++;
            }

            ImageView currUpgradeImage = upgradeImageList.get(u.getItemID());
            upgradesGridPane.setColumnIndex(currUpgradeImage, colIndex);
            upgradesGridPane.setRowIndex(currUpgradeImage, rowIndex);
            currUpgradeImage.setVisible(true);
            colIndex++;
        }
    }

    public void switchToSelectRaceScene(MouseEvent event) throws IOException {
        // Upload all the input (name, difficulty and season length) onto the GameStats "DB"
        // Proceed to the next scene

        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/selectRace.fxml"));
        Parent root = baseLoader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        SelectRaceController baseController = baseLoader.getController();
        baseController.initialize(stage);
    }

    public void switchToShopScene(MouseEvent event) throws IOException {
        // Update the selected car in the GameStats DB for the race
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