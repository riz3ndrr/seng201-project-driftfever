package seng201.team0.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Car extends Purchasable {
    // Properties
    private double speedKilometresPerHour;
    private double fuelConsumptionLitresPerKilometer;
    private double fuelTankCapacityLitres;
    private double fuelInTankLitres;
    private double handlingScaleFactor;
    private double reliabilityScaleFactor;
    private ArrayList<Upgrade> equippedUpgrades = new ArrayList<>();


    // Constructor
    public Car(int itemID, String name, String desc, float buyingPrice, float sellingPrice, boolean isAvailableToBuy,
               double speedKilometresPerHour, double fuelConsumption, double fuelTankCapacity, double handling, double reliability) {
        super(itemID, name, desc, buyingPrice, sellingPrice, isAvailableToBuy);
        this.speedKilometresPerHour = speedKilometresPerHour;
        this.fuelConsumptionLitresPerKilometer = fuelConsumption;
        this.fuelTankCapacityLitres = fuelTankCapacity;
        this.fuelInTankLitres = 0.0;
        this.handlingScaleFactor = handling;
        this.reliabilityScaleFactor = reliability;
    }


    // Getters and setters
    public double getFuelInTank() { return fuelInTankLitres; }
    public void setFuelInTank(double fuelInTankLitres) { this.fuelInTankLitres = fuelInTankLitres; }
    public ArrayList<Upgrade> getEquippedUpgrades() {
        return equippedUpgrades;
    }


    // Logic
    public String upgradesToString() {
        ArrayList<String> items = new ArrayList<>();
        for (Upgrade upgrade : equippedUpgrades) {
            items.add(upgrade.getName());
        }
        return String.join(", ", items);
    }

    public Car makeCopy() {
        Car copy = new Car(getItemID(), getName(), getDesc(), getBuyingPrice(), getSellingPrice(), isAvailableToBuy(),
        speedKilometresPerHour, fuelConsumptionLitresPerKilometer, fuelTankCapacityLitres, handlingScaleFactor, reliabilityScaleFactor);
        return copy;
    }

    public double calculateSpeed() {
        double result = speedKilometresPerHour;
        for (Upgrade upgrade : equippedUpgrades) {
            result = result * upgrade.getSpeedMultiplier();
        }
        return result;
    }

    public double calculateFuelTankCapacity() {
        double result = fuelTankCapacityLitres;
        for (Upgrade upgrade : equippedUpgrades) {
            result = result * upgrade.getFuelTankCapacityMultiplier();
        }


        return result;
    }

    public double calculateHandling() {
        double result = handlingScaleFactor;
        for (Upgrade upgrade : equippedUpgrades) {
            result = result * upgrade.getHandlingMultiplier();
        }
        return result;
    }

    public double calculateReliability() {
        double result = reliabilityScaleFactor;
        for (Upgrade upgrade : equippedUpgrades) {
            result = result * upgrade.getReliabilityMultiplier();
        }
        return result;
    }

    public double calculateFuelConsumption() {
        double result = fuelConsumptionLitresPerKilometer;
        for (Upgrade upgrade : equippedUpgrades) {
            result = result * upgrade.getFuelConsumptionMultiplier();
        }
        return result;
    }

    public double calculateFuelPercentage() {
        double result = 100.0 * fuelInTankLitres / calculateFuelTankCapacity();
        if (result > 100) {
            setFuelInTank(calculateFuelTankCapacity());
            return 100.0;
        }
        return result;
    }

    public void addUpgrade(Upgrade upgrade) {
        if (!checkIfUpgradeEquipped(upgrade)) {
            equippedUpgrades.add(upgrade);
        }
    }

    public void removeUpgrade(Upgrade upgrade) {
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
        for (Upgrade upgrade : equippedUpgrades) {
            System.out.println(upgrade.getName());
        }
    }

    public double costToFillTank(double costPerLitre) {
        double litresNeeded = calculateFuelTankCapacity() - fuelInTankLitres;
        if (litresNeeded < 0.0) {
            litresNeeded = 0.0;
        }
        return litresNeeded * costPerLitre;
    }
}
