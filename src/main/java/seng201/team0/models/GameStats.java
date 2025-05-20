package seng201.team0.models;


import java.util.ArrayList;
import java.util.List;

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
    private int raceCount;
    private int racesDone = 0;
    private Difficulty difficulty = Difficulty.REGULAR;

    private String userName;
    private final double startingBalance = 5000000; //TODO revert, changed bal for testing!!
    private double bal = startingBalance;
    private double prizeMoneyWon = 0;
    private double fuelCostPerLitre = 2.5;
    private double minimumSecondsForGasStop = 3.0 * 60.0;; // Time for driver to get out, pay, etc
    private double secondsToPumpLitreOfGas = 10.0; // Time for a single litre of fuel to be pumped

    private int numOpponents = 8;
    private double opponentUpgradeProbability = 0.15;
    private double opponentRefuelProbability = 0.8;
    private double opponentRepairProbability = 0.75; // Chance that an opponent breaking down can be repaired
    private double opponentPickUpHitchhikerProbability = 0.5; // Chance that if a hitchhiker is available the opponent will stop and pick them up
    private double chanceOfHitchhikerPerKilometre = 0.005; // Chance that in any given kilometre a hitchhiker is available for pickup
    private double hitchhikerPickUpTimeSeconds = 5.0 * 60.0; // If stopping this is how long it takes to pick up a hitchhiker.
    private double minHitchhikerReward = 50.0;
    private double maxHitchhikerReward = 300.0;
    private double minRepairTimeSeconds = 10.0 * 60.0; // Repairs will take at least 10 minutes
    private double maxRepairTimeSeconds = 20.0 * 60.0; // Repairs will take at most 20 minutes
    private double minRepairCost = 200.0;
    private double maxRepairCost = 800.0;
    private double chanceOfRaceRouteBlockage = 0.000037; // Chance that in any given second a route is blocked, around 50/50 chance of happening per 2 hours

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
    public double getFuelCostPerLitre() { return fuelCostPerLitre; }
    public double getMinimumSecondsForGasStop() { return minimumSecondsForGasStop; }
    public double getSecondsToPumpLitreOfGas() { return secondsToPumpLitreOfGas; }
    public int getNumOpponents() { return numOpponents; }
    public double getOpponentUpgradeProbability() { return opponentUpgradeProbability; }
    public double getOpponentRefuelProbability() { return opponentRefuelProbability; }
    public double getOpponentRepairProbability() { return opponentRepairProbability; }
    public double getOpponentPickUpHitchhikerProbability() { return opponentPickUpHitchhikerProbability; }
    public double getHitchhikerPickUpTimeSeconds() { return hitchhikerPickUpTimeSeconds; }
    public double getChanceOfHitchhikerPerKilometre() { return chanceOfHitchhikerPerKilometre; }
    public double getChanceOfRaceRouteBlockage() { return chanceOfRaceRouteBlockage; }

    public List<Upgrade> getUpgradeCollection() { return upgradeCollection; }
    public List<Car> getCarCollection() { return carCollection;}
    public int getUpgradeCollectionSize() { return upgradeCollection.size();}
    public int getCarCollectionSize() { return carCollection.size(); }
    public void setSelectedCar(Car car) { this.selectedCar = car; }
    public Car getSelectedCar() { return selectedCar; }


    // Logic
    /**
     * Sets every car to not be purchased so it is available in the shop.
     * Then proceed to clear the user's car collection
     */
    public void clearCarCollection() {
        for (Car car : carCollection) {
            car.unequipAllUpgrades();
            car.setPurchased(false);
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

    /**
     * Calculate the cost of fixing a broken vehicle which is a random value.
     * @return the repair cost
     */
    public double calculateRandomRepairCost() {
        return minRepairCost + Math.random() * (maxRepairCost - minRepairCost);
    }

    /**
     * Calculate the time it takes to repair a broken vehicle which is random.
     * @return the time in seconds to repair.
     */
    public double calculateRandomRepairTime() {
        return minRepairTimeSeconds + Math.random() * (maxRepairTimeSeconds - minRepairTimeSeconds);
    }
    /**
     *
     * Create a random value which will be used as the amount of money a person gets for
     * picking up a hitchhiker.
     * @return the money reward
     */
    public double calculateRandomHitchhikerReward() {
        return minHitchhikerReward + Math.random() * (maxHitchhikerReward - minHitchhikerReward);
    }
}
