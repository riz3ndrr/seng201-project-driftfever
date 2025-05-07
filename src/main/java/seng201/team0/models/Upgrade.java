package seng201.team0.models;

import java.util.Arrays;
import java.util.List;

public class Upgrade extends Item {
    // CHECK IF STATIC IS APPROPRIATE HERE

    private int numPurchased;


    public void incrementNumPurchased() {
        numPurchased++;
    }

    public void decrementNumPurchased() {

        numPurchased--;
    }

    public void resetNumPurchased() {
        numPurchased = 0;
    }

    public int getNumPurchased() {
        return numPurchased;
    }

    public Upgrade(String name, int buyingPrice, int sellingPrice, boolean isAvailableToBuy, int speed, int handling, int reliability, int fuelEconomy, String desc, int upgradeID) {
        super(name, buyingPrice, sellingPrice, isAvailableToBuy, speed, handling, reliability, fuelEconomy, desc, upgradeID);
        this.numPurchased = 0;
    }

    //implement parts later
    // private ArrayList<Part> upgrades
}
