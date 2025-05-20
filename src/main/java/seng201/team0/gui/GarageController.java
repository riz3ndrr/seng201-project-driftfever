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

    /**
     * Upon initialization, will display the player's name, their balance and amount of races left to complete on the
     * left. It will also display the currently selected car for racing along with the upgrades available to be used.
     * @param stage
     */
    public void initialize(Stage stage) {
        nameLabel.setText("Name: " + gameDB.getUserName());
        balLabel.setText(String.format("Balance: $%,.2f", gameDB.getBal()));
        racesLeftLabel.setText(String.format("Races left: %d", gameDB.getRaceCount() - gameDB.getRacesDone()));
        displaySelectedCar();
        displayAvailableUpgrades();
    }

    /**
     * Display the car's stats along with the cost of filling up the tank (if it's not already full)
     * @param car which is the currently displayed car.
     */

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

    /**
     * Display the car's respective image.
     * If the car is currently being selected for racing, it will display that fact and likewise if it isn't
     */
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


    /**
     * Call the fillTank() method in the GarageService class and update the balance label depending on the result of the
     * method along with its fuel level.
     */
    public void fillTank() {
        garageService.fillTank(selectedCar);
        balLabel.setText(String.format("Balance: $%,.2f", gameDB.getBal()));
        displayCarStats(selectedCar);
    }

    /**
     * Move to the next car in the collection.
     * If the car is the last item in the list, the UI will then display the first item in the list. Hides the text
     * showing if an upgrade has been equipped
     * or not when function is called along with refreshing the list of equipped upgrades if that is what has been shown
     * previously.
     */
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

    /**
     * Move to the previous car in the collection.
     * If the car is the first item in the list, the UI will then display the last item in the list. Hides the text
     * showing if an upgrade has been equipped
     * or not when function is called along with refreshing the list of equipped upgrades if that is what has been shown
     * previously.
     */
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

    /**
     * Toggles the display between equipped and available upgrades in the UI to reflect the new state and refreshes
     * the list of upgrades accordingly.
     * If currently showing equipped items, the method switches to show available upgrades.
     * Likewise if we are currently showing the available items.
     */
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

    /**
     * Call equipUpgrade() or equipUpgrade() depending on if we are displaying the list of upgrades able to be equipped
     * or the list of upgrades already equipped.
     */
    public void unequipOrEquipUpgrade() {
        if (showEquippedItems) {
            unequipUpgrade();
        } else {
            equipUpgrade();
        }
    }

    /**
     * Call the unequipUpgrade() method in the GarageService class which passes through the selected car and
     * upgrade as a parameter and receives the result of it as an Enum. The UI will reflect
     * the result of unequipping the upgrade depending on if it was successful
     * or unsuccessful due to no upgrade being selected.
     */

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

    /**
     * Call the equipUpgrade() method in the GarageService class which passes through the selected car and
     * upgrade as a parameter and receives the result of it as an Enum.
     * The UI will reflect the result of equipping the upgrade depending on if it was successful
     * or unsuccessful due to no upgrade being selected or that particular upgrade already being equiped.
     */
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

    /**
     * Update the selected car used for racing and reflect that change through changes in the UI
     */
    public void updateSelectedCar() {
        garageService.updateSelectedCar(selectedCar);
        selectedCarTitle.setVisible(true);
        selectCarLabel.setVisible(false);
    }

    /**
     * Show the name of the upgrade (and it's quantity if we are showing the list of upgrades available to be equipped)
     * @param selectedUpgrade which is the upgrade the player is currently selecting
     */

    public void displaySelectedUpgrade(Upgrade selectedUpgrade) {
        if (showEquippedItems) {
            resultEquipMessage.setText("Selecting " + selectedUpgrade.getName());
        } else {
            resultEquipMessage.setText(String.format("Selecting %s, quantity: x%d", selectedUpgrade.getName(), selectedUpgrade.getNumPurchased()));
        }
        resultEquipMessage.setStyle("-fx-text-fill: grey");
        resultEquipMessage.setVisible(true);
    }

    /**
     * When the icon of an upgrade is clicked, we obtain its itemID and call the displaySelectedUpgrade() method
     * to change UI elements.
     * @param event
     */
    public void selectUpgrade(MouseEvent event) {
        ImageView clickedUpgrade = (ImageView) event.getSource();
        String upgradeID = clickedUpgrade.getId();

        int id = Integer.parseInt(upgradeID.substring(4));
        selectedUpgrade = GameManager.getUpgradeWithID(id);
        displaySelectedUpgrade(selectedUpgrade);
    }

    /**
     * Displays a list of either equipped or available upgrades in the UI grid,
     * depending on what the player wants to be displayed.
     * <p>
     * If the UI is set to show equipped items, it displays the upgrades
     * currently equipped on the selected car. Otherwise, it will show all upgrades available to be equipped.
     * <p>
     * This method refreshes whenever a change is made (like when an upgrade is equipped
     * or the currently displayed car changers), placing the
     * relevant upgrades into a grid layout (2 columns per row) based on their IDs.
     */

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