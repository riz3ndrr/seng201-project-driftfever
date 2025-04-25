package seng201.team0.models;

import java.util.Arrays;
import java.util.List;

public class Upgrade extends Item {
    // CHECK IF STATIC IS APPROPRIATE HERE

    private int numPurchased;

    private static List<Upgrade> upgradeArray =
            Arrays.asList(
                    // buyingPrice changed for testing purposes
                    new Upgrade("Rocket Fuel", 5, 70, true, 10, -2, -2, -3, "Fuel to make your car go ZOOOOM!", 0),
                    new Upgrade("Grippy Tyres", 5, 300, true, 0, 8, 0, 0, "Improved traction for tighter turns and better control at high speeds.", 1),
                    new Upgrade("Carbon Fibre Plating", 5, 800, true, 2, 0, 7, 7, "Lightweight yet durable—improves speed without sacrificing reliability.", 2)
            );

    public static Upgrade getUpgradeAtIndex(int index) {
        return upgradeArray.get(index);
    }

    public static List<Upgrade> getUpgrades() {
        return upgradeArray;
    }

    public void incrementNumPurchased() {
        numPurchased++;
    }

    public void decrementNumPurchased() {

        numPurchased--;
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
