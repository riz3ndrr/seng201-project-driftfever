package seng201.team0.models;

public class Upgrade extends Item {
    // CHECK IF STATIC IS APPROPRIATE HERE

    private boolean isEquipped;
    private int upgradeID;

    public Upgrade(String name, int buyingPrice, int sellingPrice, boolean isAvailableToBuy, int speed, int handling, int reliability, int fuelEconomy, String desc, int upgradeID) {
        super(name, buyingPrice, sellingPrice, isAvailableToBuy, speed, handling, reliability, fuelEconomy, desc);
        this.isEquipped = false;
        this.upgradeID = upgradeID;
    }

    //implement parts later
    // private ArrayList<Part> upgrades
}
