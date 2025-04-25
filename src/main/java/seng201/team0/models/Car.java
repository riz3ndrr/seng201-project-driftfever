package seng201.team0.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Car extends Item {
    // CHECK IF STATIC IS APPROPRIATE HERE
    private static List<Car> carsArray =
            Arrays.asList( new Car("Purple Car", 1600, 800, true, 5, 4, 6, 5, "A balanced car with smooth acceleration and steady handling.", 0),
                        new Car("Lightning McQueen", 1550, 850, true, 4, 4, 5, 5, "A smooth ride with good stability and moderate handling.", 1),
                        new Car("Lime Wheels", 1500, 850, true, 5, 5, 4, 4, "A versatile car with equal balance between speed and handling.", 2),
                        new Car("Yellow Car", 1400, 700, true, 4, 5, 5, 4, "Light and agile, perfect for quick turns and smooth drifting.", 3),
                        new Car("Azure", 1300, 750, true, 5, 4, 6, 5, "Durable with solid control and good handling on various surfaces.", 4),
                        new Car("Crosswind", 3600, 1600, false, 8, 9, 9, 7, "High-performance with fast acceleration and responsive handling.", 5),
                        new Car("Phantom", 4200, 1800, false, 8, 9, 7, 8, "Fast and agile, designed for quick maneuvers and high speeds.", 6),
                        new Car("Icarus' Wings", 4600, 1400, false, 15, 3, 4, 3, "The world's fastest car. Although not renowned for its stability.", 7),
                        new Car("Bumblebee", 5000, 1300, false, 10, 10, 9, 9, "Legend says this car has a mind of its own", 8)
);

    private ArrayList<Upgrade> equippedUpgrades = new ArrayList<>();

    public ArrayList<Upgrade> getEquippedUpgrades() {
        return equippedUpgrades;
    }

    public void addEquippedUpgrade(Upgrade upgrade) {
        equippedUpgrades.add(upgrade);
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

    public static List<Car> getCars() {
        return carsArray;
    }

    private int fuelMeter = 100;

    public void setFuel (int fuel) {
        this.fuelMeter = fuel;
    }

    public int getFuel () {
        return fuelMeter;
    }

    public Car(String name, int buyingPrice, int sellingPrice, boolean isAvailableToBuy, int speed, int handling, int reliability, int fuelEconomy, String desc, int itemID) {
        super(name, buyingPrice, sellingPrice, isAvailableToBuy, speed, handling, reliability, fuelEconomy, desc, itemID);
    }



    //implement parts later
    // private ArrayList<Part> upgrades
}
