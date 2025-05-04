package seng201.team0.models;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Car extends Item {
    // CHECK IF STATIC IS APPROPRIATE HERE








    private ArrayList<Upgrade> equippedUpgrades = new ArrayList<>();

    public ArrayList<Upgrade> getEquippedUpgrades() {
        return equippedUpgrades;
    }

    public void addEquippedUpgrade(Upgrade upgrade) {
        equippedUpgrades.add(upgrade);
    }

    public void removeEquippedUpgrade(Upgrade upgrade) {
        equippedUpgrades.remove(upgrade);
    }

    public boolean checkIfUpgradeEquipped(Upgrade selectedUpgrade) {
        for (Upgrade upgrade : equippedUpgrades) {
            if (upgrade.getName().equals(selectedUpgrade.getName())) {
                return true;
            }
        }
        return false;
    }


    public void printEquippedUpgrades() {
        System.out.println("This car has equipped:");
        for (Upgrade u : equippedUpgrades) {
            System.out.println(u.getName());
        }
    }



    private float fuelMeter = 76.78F;

    public void setFuel (float fuel) {
        this.fuelMeter = fuel;
    }

    public float getFuel () {
        return fuelMeter;
    }

    public Car(String name, int buyingPrice, int sellingPrice, boolean isAvailableToBuy, int speed, int handling, int reliability, int fuelEconomy, String desc, int itemID) {
        super(name, buyingPrice, sellingPrice, isAvailableToBuy, speed, handling, reliability, fuelEconomy, desc, itemID);
    }



    //implement parts later
    // private ArrayList<Part> upgrades
}
