package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Upgrade;
import seng201.team0.services.GarageService;

import java.util.Arrays;
import java.util.List;

public class GarageController extends ParentController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label balLabel;
    @FXML
    private Label racesLeftLabel;
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
    private Label fuelTankCapacityLabel;
    @FXML
    private Label fuelMeterLabel;
    @FXML
    private Label fillTankLabel;
    @FXML
    private ImageView upgr0;
    @FXML
    private ImageView upgr1;
    @FXML
    private ImageView upgr2;
    @FXML
    private ImageView upgr3;
    @FXML
    private ImageView upgr4;
    @FXML
    private ImageView upgr5;
    @FXML
    private ImageView upgr6;
    @FXML
    private ImageView upgr7;
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
    @FXML
    private Label unequipOrEquipUpgradeLabel;
    @FXML
    private ImageView carImg;
    @FXML
    private Label selectCarLabel;


    // Properties
    GameStats gameDB = GameManager.getGameStats();
    GarageService garageService = new GarageService();
    private int selectedCarIndex = 0;
    private Car selectedCar;
    private Upgrade selectedUpgrade;
    private boolean showEquippedItems = false;


    // Logic
    public void initialize(Stage stage) {
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText(String.format("Balance: $%,.2f", gameDB.getBal()));
        racesLeftLabel.setText(String.format("Races left: %d", gameDB.getRaceCount() - gameDB.getRacesDone()));
        displaySelectedCar();
        displayAvailableUpgrades();
    }

    public void displayCarStats(Car car) {
        String buttonCaption = String.format("Fill tank for $%.2f", garageService.payableCostToFillTank(selectedCar));
        double fuelPercentage = car.calculateFuelPercentage();
        if (fuelPercentage >= 100.0) { buttonCaption = "Tank full"; }

        carNameLabel.setText(car.getName());
        fuelMeterLabel.setText(String.format("Fuel level: %.0f%%", fuelPercentage));
        fillTankLabel.setText(buttonCaption);
        speedLabel.setText(String.format("Top speed: %.0f km/h", car.calculateSpeed(0.0)));
        handlingLabel.setText(String.format("Handling: %.0f%%", 100.0 * car.calculateHandling()));
        reliabilityLabel.setText(String.format("Reliability: %.2f%%", 100.0 * car.calculateReliability()));
        fuelConsumptionLabel.setText(String.format("Fuel efficiency: %.0f L/100kms", 100.0 * car.calculateFuelConsumption()));
        fuelTankCapacityLabel.setText(String.format("Fuel tank: %.0f L", car.calculateFuelTankCapacity()));
    }

    public void displaySelectedCar() {
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

    public void fillTank() {
        garageService.fillTank(selectedCar);
        balLabel.setText(String.format("Balance: $%,.2f", gameDB.getBal()));
        displayCarStats(selectedCar);
    }

    public void moveRight() {
        resultEquipMessage.setVisible(false);
        if ((selectedCarIndex + 1) == gameDB.getCarCollectionSize()) {
            selectedCarIndex = 0;
        }
        else {
            selectedCarIndex++;
        }
        displaySelectedCar();

        if (showEquippedItems) {
            //update the list of equipped items as it will change from car to car
            displayAvailableUpgrades();
        }
    }

    public void moveLeft() {
        resultEquipMessage.setVisible(false);
        if ((selectedCarIndex) == 0) {
            selectedCarIndex = gameDB.getCarCollectionSize() - 1;
        }
        else {
            selectedCarIndex--;
        }
        displaySelectedCar();

        if (showEquippedItems) {
            //update the list of equipped items as it will change from car to car
            displayAvailableUpgrades();
        }
    }

    public void switchUpgrades() {
        if (showEquippedItems) {
            // going to show available items
            showEquippedItems = false;
            switchUpgradesLabel.setText("Show Equipped Items");
            unequipOrEquipUpgradeLabel.setText("Equip Upgrade");
            upgradesHeaderLabel.setText("Available Upgrades:");
        }
        else {
            // going to show equipped items
            showEquippedItems = true;
            switchUpgradesLabel.setText("Show Unequipped Items");
            unequipOrEquipUpgradeLabel.setText("Unequip Upgrade");
            upgradesHeaderLabel.setText("Equipped Upgrades:");
        }
        displayAvailableUpgrades();
    }

    public void unequipOrEquipUpgrade() {
        if (showEquippedItems) {
            unequipUpgrade();
        } else {
            equipUpgrade();
        }
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
                displayAvailableUpgrades();
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
        } else {
            resultEquipMessage.setText(String.format("Selecting %s, quantity: x%d", selectedUpgrade.getName(), selectedUpgrade.getNumPurchased()));
        }
        resultEquipMessage.setStyle("-fx-text-fill: grey");
        resultEquipMessage.setVisible(true);
    }

    public void selectUpgrade(MouseEvent event) {
        ImageView clickedUpgrade = (ImageView) event.getSource();
        String upgradeID = clickedUpgrade.getId();

        int id = Integer.parseInt(upgradeID.substring(4));
        selectedUpgrade = GameManager.getUpgradeWithID(id);
        displaySelectedUpgrade(selectedUpgrade);
    }

    public void displayAvailableUpgrades() {
        List<Upgrade> availableUpgrades;
        if (showEquippedItems) {
            availableUpgrades = selectedCar.getEquippedUpgrades();
        } else {
            availableUpgrades = gameDB.getUpgradeCollection();
        }

        List<ImageView> upgradeImageList = Arrays.asList(upgr0, upgr1, upgr2, upgr3, upgr4, upgr5, upgr6, upgr7);
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
}