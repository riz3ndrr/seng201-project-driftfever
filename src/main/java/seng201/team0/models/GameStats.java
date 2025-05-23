package seng201.team0.models;


import seng201.team0.GameManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Base settings for the game
 */
public class GameStats {
    // Enums
    public enum Difficulty {
        EASY(0.8),
        REGULAR(1.0),
        HARD(1.2);

        private final double costMultiplier;

        Difficulty(double costMultiplier) {
            this.costMultiplier = costMultiplier;
        }

        public double getCostMultiplier() {
            return costMultiplier;
        }
    }


    // Properties
    private Race selectedRace;
    private RaceRoute selectedRoute;
    private int raceCount = 3;
    private int racesDone = 0;
    private Difficulty difficulty = Difficulty.REGULAR;

    private String userName;
    private final double startingBalance = 5000;
    private double bal = startingBalance;
    private double prizeMoneyWon = 0;

    public Car selectedCar;
    private List<Car> carCollection = new ArrayList<>();
    private List<Upgrade> upgradeCollection = new ArrayList<>();


    // Constructor
    public GameStats() {
    }


    // Getters and setters
    public Race getSelectedRace() { return selectedRace; }
    public void setSelectedRace(Race selectedRace) { this.selectedRace = selectedRace; }
    public RaceRoute getSelectedRoute() { return selectedRoute; }
    public void setSelectedRoute(RaceRoute selectedRoute) { this.selectedRoute = selectedRoute; }
    public int getRaceCount() { return raceCount; }
    public void setRaceCount(int raceCount) { this.raceCount = raceCount; }
    public int getRacesDone() { return racesDone; }
    public void setRacesDone(int racesDone) { this.racesDone = racesDone; }
    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public double getStartingBalance() { return startingBalance; }
    public double getBal() { return bal; }
    public void setBal(double bal) { this.bal = bal; }
    public double getPrizeMoneyWon() { return prizeMoneyWon; }
    public void setPrizeMoneyWon(double prizeMoneyWon) { this.prizeMoneyWon = prizeMoneyWon; }

    public List<Upgrade> getUpgradeCollection() { return upgradeCollection; }
    public List<Car> getCarCollection() { return carCollection;}
    public int getUpgradeCollectionSize() { return upgradeCollection.size();}
    public int getCarCollectionSize() { return carCollection.size(); }
    public void setSelectedCar(Car car) { this.selectedCar = car; }
    public Car getSelectedCar() { return selectedCar; }


    // Logic
    /**
     * Reset all the variables for a new playthrough
     */
    public void reset() {
        clearCarCollection();
        clearUpgradeCollection();
        setPrizeMoneyWon(0);
        setRacesDone(0);
        setRaceCount(3);
        selectedCar = null;
    }

    /**
     * check if exactly one car is not broken down
     * @return
     */
    public boolean checkIfOneCarNotBrokenDown() {
        int brokenDownCount = 0;
        for (Car car : carCollection) {
            if (car.isBrokenDown()) {
                brokenDownCount++;
            }
        }
        if (getCarCollectionSize() - brokenDownCount == 1) {
            return true;
        }
        return  false;
    }

    /**
     * Check if all cars are broken down.
     * @return true if all the cars are broken down
     */
    public boolean areAllCarsBrokenDown() {
        for (Car car : carCollection) {
            if (!car.isBrokenDown()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the user can continue playing. The game checks this by first checking if all cars are broken
     * down, if not, return true if the user is financially able to fill a car tank..
     * Otherwise, if the user has only one car left, return false if the user does
     * not have the funds to get a functioning car. Otherwise,
     * if the cost of repairing + the cost of refueling is greater than the cars selling price + the user's balance,
     * return true, or else return false.
     * @return true or false depending on if the user is able to continue playing
     */
    public boolean canContinuePlaying() {
        double costToPlay = 500; // TODO undo for testing
        if (areAllCarsBrokenDown()) {
            if (getCarCollection().size() == 1) {
                if (500 - getBal() + selectedCar.costToFillTank(GameManager.getFuelCostPerLitre()) < 0) {
                    return true;
                }
                return false;
            }
            for (Car car : carCollection) {
                costToPlay -= car.getSellingPrice();
                costToPlay += car.costToFillTank(GameManager.getFuelCostPerLitre());
            }
            costToPlay -= getBal();

            if (costToPlay <= 0) {
                return true;
            }
            else {
                return false;
            }
        }
        if (getBal() >= 0) {
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * Sets every car to not be purchased so it is available in the shop.
     * Then proceed to clear the user's car collection
     */
    public void clearCarCollection() {
        for (Car car : carCollection) {
            car.unequipAllUpgrades();
            car.setPurchased(false);
            car.setBrokenDown(false);
        }
        carCollection.clear();
    }

    /**
     * Sets every upgrade to not be purchased.
     * Then proceed to clear the user's upgrade collection
     */
    public void clearUpgradeCollection() {
        for (Upgrade upgrade : upgradeCollection) {
            upgrade.setNumPurchased(0);
            upgrade.setPurchased(false);
        }
        upgradeCollection.clear();
    }

    /**
     * Checks if an item is in the user's collection
     * (used for JUnit testing)
     *
     * @param upgrade
     * @return
     */
    public boolean selectedItemInCollection(Upgrade upgrade) {
        for (Upgrade u : upgradeCollection) {
            if (u.getName().equals(upgrade.getName())) {
                return true;
            }
        }
        return false;
    }




    public boolean selectedItemInCollection(Car car) {
        for (Car c : carCollection) {
            if (c.getName().equals(car.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add an item to the user's respective collection
     * @param item
     */
    public void addItem(Purchasable item) {
        if (item instanceof Car) {
            carCollection.add((Car) item);
        } else {
            upgradeCollection.add((Upgrade) item);
        }
    }

    /**
     * Removes an item from its respective collection
     * @param item
     */
    public void removeItem(Purchasable item) {
        if (item instanceof Car) {
            carCollection.remove((Car) item);
        } else {
            upgradeCollection.remove((Upgrade) item);
        }
    }

    /**
     * Returns the index of a car in the user's array collection
     * @param i which is the index of the list we are curious about
     * @return the car at index i of the list
     */
    public Car searchCarAtIndex(int i) {
        return carCollection.get(i);
    }
}