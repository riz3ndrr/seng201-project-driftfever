package seng201.team0.models;

import javafx.scene.image.Image;
import seng201.team0.GameManager;

import java.util.ArrayList;
import java.util.List;

public class Car extends Purchasable {
    // Properties
    private double speedKilometresPerHour;
    private double fuelConsumptionLitresPerKilometer;
    private double fuelTankCapacityLitres;
    private double fuelInTankLitres;
    private double handlingScaleFactor;
    private double reliability; // Chance of not breaking down every kilometre (eg: 0.99 = 1% chance of breakdown per kilometre)
    private List<Upgrade> equippedUpgrades = new ArrayList<>();
    private GameStats gameDB = GameManager.getGameStats();


    // Constructor
    public Car(int itemID, String name, String desc, double buyingPrice, double sellingPrice,
               double speedKilometresPerHour, double fuelConsumption, double fuelTankCapacity, double handling, double reliability) {
        super(itemID, name, desc, buyingPrice, sellingPrice);
        this.speedKilometresPerHour = speedKilometresPerHour;
        this.fuelConsumptionLitresPerKilometer = fuelConsumption;
        this.fuelTankCapacityLitres = fuelTankCapacity;
        this.fuelInTankLitres = 0.0;
        this.handlingScaleFactor = handling;
        this.reliability = reliability;
    }


    // Getters and setters
    public double getFuelInTank() { return fuelInTankLitres; }
    public void setFuelInTank(double fuelInTankLitres) { this.fuelInTankLitres = fuelInTankLitres; }
    public List<Upgrade> getEquippedUpgrades() {
        return equippedUpgrades;
    }
    public double getSpeed() { return speedKilometresPerHour; }
    public double getFuelConsumption() {return fuelConsumptionLitresPerKilometer;}
    public double getFuelTankCapacity() {return fuelTankCapacityLitres;}
    public double getHandlingScaleFactor() {return handlingScaleFactor;}
    public double getReliability() {return reliability;}



    // Logic

    /**
     * A helper function used by the car class to unequip all of its equipped upgrades.
     * Whenever an upgrade is unequipped, the variable, NumPurchased, which tracks the quantity of how much
     * of that particular upgrade is available to be equipped is changed.
     */
    public void unequipAllUpgrades() {
        for (Upgrade equippedUpgrade : equippedUpgrades) {
            if (equippedUpgrade.getNumPurchased() == 0) {
                gameDB.addItem(equippedUpgrade);
            }
            equippedUpgrade.setNumPurchased(equippedUpgrade.getNumPurchased() + 1);
        }
        equippedUpgrades.clear();
    }


    public String upgradesToString() {
        List<String> items = new ArrayList<>();
        for (Upgrade upgrade : equippedUpgrades) {
            items.add(upgrade.getName());
        }
        return String.join(", ", items);
    }

    /**
     * Create a copy of a car which will be used as the opponent's car in the racing simulator.
     * @return a copy of a certain car.
     */
    public Car makeCopy() {
        Car copy = new Car(getItemID(), getName(), getDesc(), getBuyingPrice(1.0), getSellingPrice(),
        speedKilometresPerHour, fuelConsumptionLitresPerKilometer, fuelTankCapacityLitres, handlingScaleFactor, reliability);
        return copy;
    }

    /**
     * Calculates the car's top speed by applying its upgrades stats onto it
     * @return the speed after its upgrades' stats have been applied to it
     */
    public double calculateSpeed(double trackCurviness) {
        double result = speedKilometresPerHour;
        for (Upgrade upgrade : equippedUpgrades) {
            result = result * upgrade.getSpeedMultiplier();
        }
        // When curviness is 0 there is no impact on the speed and when curviness is 1 then speed is 0.
        // The higher the handling the less this effect applies.
        double handling = calculateHandling();
        double effectOfHandlingAndCurviness = trackCurviness * (1 - (handling * (1 - trackCurviness)));
        result -= result * effectOfHandlingAndCurviness;
        return result;
    }

    /**
     * Calculates the car's fuel tank capacity by applying its upgrades stats onto it
     * @return the car's fuel tank capacity after its upgrades' stats have been applied to it
     */
    public double calculateFuelTankCapacity() {
        double result = fuelTankCapacityLitres;
        for (Upgrade upgrade : equippedUpgrades) {
            result = result * upgrade.getFuelTankCapacityMultiplier();
        }
        return result;
    }

    /**
     * Calculates the car's handling stat by applying its upgrades stats onto it
     * The result is always between 0 and 1
     * @return the car's handling stat after its upgrades' stats have been applied to it
     */
    public double calculateHandling() {
        double result = handlingScaleFactor;
        for (Upgrade upgrade : equippedUpgrades) {
            double remaining = 1.0 - result;
            if (upgrade.getHandlingMultiplier() >= 1.0) {
                result += remaining * (upgrade.getHandlingMultiplier() - 1.0);
            } else {
                result -= remaining * (1.0 - upgrade.getHandlingMultiplier());
            }
        }
        return result;
    }

    /**
     * Calculates the car's reliability stat by applying its upgrades stats onto it
     * The result is always between 0 and 1
     * @return the car's reliability stat capacity after its upgrades' stats have been applied to it
     */
    public double calculateReliability() {
        double result = reliability;
        for (Upgrade upgrade : equippedUpgrades) {
            double remaining = 1.0 - result;
            if (upgrade.getReliabilityMultiplier() >= 1.0) {
                result += remaining * (upgrade.getReliabilityMultiplier() - 1.0);
            } else {
                result -= remaining * (1.0 - upgrade.getReliabilityMultiplier());
            }
        }
        return result;
    }

    /**
     * Calculates the car's fuel consumption stat by applying its upgrades stats onto it
     * @return the car's fuel consumption stat after its upgrades' stats have been applied to it
     */
    public double calculateFuelConsumption() {
        double result = fuelConsumptionLitresPerKilometer;
        for (Upgrade upgrade : equippedUpgrades) {
            result = result * upgrade.getFuelEfficiencyMultiplier();
        }
        return result;
    }

    /**
     * Calculates the car's fuel level as a percentage.
     * If the car's fuel level is over 100 (which can occurs in scenarios like unequipping an upgrade
     * that increases the car's tank capacity and the car's tank is already full prior to unequipping it),
     * then it will set the level to 100%.
     * @return the car's fuel level percentage.
     */
    public double calculateFuelPercentage() {
        double result = 100.0 * fuelInTankLitres / calculateFuelTankCapacity();
//        if (result > 100) {
//            setFuelInTank(calculateFuelTankCapacity());
//            return 100.0;
//        }
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

    /**
     * Calculate the litres needed to fill up the tank, if it is below 0, it will set it to 0.
     * * @param costPerLitre
     * @return the total cost for how many litres it needs to fill up a tank.
     */

    public double costToFillTank(double costPerLitre) {
        double litresNeeded = calculateFuelTankCapacity() - fuelInTankLitres;

        //commented out as no scenario where current fuel in tank will be greater than capacity
//        if (litresNeeded < 0.0) {
//            litresNeeded = 0.0;
//        }
        return litresNeeded * costPerLitre;
    }

    public Image getIcon() {
        String carIcon = "car" + (getItemID() + 1) + ".png";
        String iconFolder = "file:src/main/resources/designs/car-icon/";
        return new Image(iconFolder + carIcon);
    }
}
