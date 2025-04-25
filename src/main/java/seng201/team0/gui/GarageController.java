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
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Upgrade;

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
    GameStats gameDB = GameStats.getInstance();

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
    private Label fuelEcoLabel;

    @FXML
    private Pane upgradesLayer;

    @FXML
    private HBox carsLayer;





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
        carNameLabel.setText(car.getName());
        speedLabel.setText(String.format("Speed: %d", car.getSpeed()));
        handlingLabel.setText(String.format("Handling: %d", car.getHandling()));
        reliabilityLabel.setText(String.format("Reliability: %d", car.getReliability()));
        fuelEcoLabel.setText(String.format("Fuel Economy: %d", car.getFuelEconomy()));
    }

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

        selectedCar = gameDB.searchCarAtIndex(selectedCarIndex);
        ImageView selectedCarImg = carImageList.get(selectedCar.getItemID());
        selectedCarImg.setVisible(displayImg);

        displayCarStats(selectedCar);
    }


    public void moveRight(MouseEvent event) {
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

    @FXML
    private ImageView upgr0;

    @FXML
    private ImageView upgr1;
    @FXML
    private ImageView upgr2;


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
    private Label switchUpgradesLabel;

    @FXML
    private Label upgradesHeaderLabel;


    @FXML
    private GridPane upgradesGridPane;

    public void switchUpgrades(MouseEvent event) {
        if (showEquippedItems) {
            showEquippedItems = false;
            switchUpgradesLabel.setText("Show Equipped Items");
            upgradesHeaderLabel.setText("Available Upgrades:");
        }
        else {
            showEquippedItems = true;
            switchUpgradesLabel.setText("Show Unequipped Items");
            upgradesHeaderLabel.setText("Equipped Upgrades:");
        }
        displayAvailableUpgrades();
    }

    public void unequipUpgrade(MouseEvent event) {
        //filler
        System.out.println("Unequipped upgrade");
    }

    public void equipUpgrade(MouseEvent event) {
        if (selectedCar.checkIfUpgradeEquipped(selectedUpgrade)) {
            // update later
            System.out.println("Upgrade already equipped");
        }
        else {
            selectedCar.addEquippedUpgrade(selectedUpgrade);
            selectedUpgrade.decrementNumPurchased();

            if (selectedUpgrade.getNumPurchased() == 0) {

                gameDB.removeItem(selectedUpgrade);
                displayAvailableUpgrades();

                upgradeSpeedLabel.setText("");
                upgradeHandlingLabel.setText("");
                upgradeReliabilityLabel.setText("");
                upgradeFuelEcoLabel.setText("");

            }

            selectedCar.changeSpeed(selectedUpgrade.getSpeed());
            selectedCar.changeHandling(selectedUpgrade.getHandling());
            selectedCar.changeReliability(selectedUpgrade.getReliability());
            selectedCar.changeFuelEconomy(selectedUpgrade.getFuelEconomy());

            displayCarStats(selectedCar);

            System.out.println("How much is left: " + selectedUpgrade.getNumPurchased());
            selectedCar.printEquippedUpgrades();
            System.out.println("AAAHH  " + selectedUpgrade.getNumPurchased());
            gameDB.printUpgrades();

        }




    }

    public void displaySelectedUpgrade(Upgrade selectedUpgrade) {
        upgradeSpeedLabel.setText(String.format("(%d)", selectedUpgrade.getSpeed()));
        upgradeHandlingLabel.setText(String.format("(%d)", selectedUpgrade.getHandling()));
        upgradeReliabilityLabel.setText(String.format("(%d)", selectedUpgrade.getReliability()));
        upgradeFuelEcoLabel.setText(String.format("(%d)", selectedUpgrade.getFuelEconomy()));
        //currentlySelectedLabel.setText(selectedUpgrade.getName());

        Car selectedCar = gameDB.searchCarAtIndex(selectedCarIndex);
        //System.out.println("CURRENTLY SELECTING " + selectedCar.getName());
        System.out.println("CURRENTLY SELECTING " + selectedUpgrade.getName());

    }

    private Upgrade selectedUpgrade;
    public void selectUpgrade(MouseEvent event) {

        ImageView clickedUpgrade = (ImageView) event.getSource();
        String upgradeID = clickedUpgrade.getId();

        switch (upgradeID) {
            case "upgr0":
                selectedUpgrade = Upgrade.getUpgradeAtIndex(0);
                displaySelectedUpgrade(selectedUpgrade);
                break;

            case "upgr1":
                selectedUpgrade = Upgrade.getUpgradeAtIndex(1);
                displaySelectedUpgrade(selectedUpgrade);
                break;

            case "upgr2":
                selectedUpgrade = Upgrade.getUpgradeAtIndex(2);
                displaySelectedUpgrade(selectedUpgrade);
                break;
        }





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


        List<ImageView> upgradeImageList = Arrays.asList(upgr0, upgr1, upgr2);

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

    public void switchToShopScene(MouseEvent event) throws IOException {
        // Upload all the input (name, difficulty and season length) onto the GameStats "DB"
        // Proceed to the next scene

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
