package seng201.team0.models;


import java.util.ArrayList;
import java.util.List;

public class GameStats {
    // Enums
    public enum Difficulty {
        EASY(5500F, 0.8),
        REGULAR(500000F, 1.0), //changed bal for testing!!
        HARD(4500F, 1.2);

        private final double startingBalance;
        private final double winningsMultiplier;

        Difficulty(double startingBalance, double winningsMultiplier) {
            this.startingBalance = startingBalance;
            this.winningsMultiplier = winningsMultiplier;
        }
        public double getStartingBalance() {
            return startingBalance;
        }
        public double getWinningsMultiplier() {
            return winningsMultiplier;
        }
    }


    // Properties
    private Race selectedRace;
    private int raceCount;
    private int racesDone = 0;
    private Difficulty raceDifficulty = Difficulty.REGULAR;

    private String userName;
    private double bal = raceDifficulty.getStartingBalance();
    private double prizeMoneyWon = 0;
    private double fuelCostPerLitre = 2.5;
    private int numOpponenents = 8;
    private double oppenentUpgradeProbability = 0.15;

    public Car selectedCar;
    private ArrayList<Car> carCollection = new ArrayList<>();
    private ArrayList<Upgrade> upgradeCollection = new ArrayList<>();


    // Constructor
    public GameStats() {
    }


    // Getters and setters
    public Race getSelectedRace() { return selectedRace; }
    public void setSelectedRace(Race selectedRace) { this.selectedRace = selectedRace; }
    public int getRaceCount() { return raceCount; }
    public void setRaceCount(int raceCount) { this.raceCount = raceCount; }
    public int getRacesDone() { return racesDone; }
    public Difficulty getRaceDifficulty() {
        return raceDifficulty;
    }
    public void setRaceDifficulty(Difficulty difficulty) {
        this.raceDifficulty = difficulty;
        this.bal = difficulty.getStartingBalance();
    }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public double getBal() { return bal; }
    public void setBal(double bal) { this.bal = bal; }
    public double getPrizeMoneyWon() {
        return prizeMoneyWon;
    }
    public double getFuelCostPerLitre() { return fuelCostPerLitre; }
    public int getNumOpponenents() { return numOpponenents; }
    public double getOpponentUpgradeProbability() { return oppenentUpgradeProbability; }

    public List<Upgrade> getUpgradeCollection() { return upgradeCollection; }
    public int getCarCollectionSize() { return carCollection.size(); }
    public void setSelectedCar(Car car) { this.selectedCar = car; }
    public Car getSelectedCar() { return selectedCar; }


    // Logic
    public double calculateAdjustedWinnings(double baseCost) {
        return baseCost * raceDifficulty.getWinningsMultiplier();
    }

    public void clearCarCollection() {
        for (Car car : carCollection) {
            car.setPurchased(false);
        }
        carCollection.clear();
    }

    public void clearUpgradeCollection() {
        for (Upgrade upgrade : upgradeCollection) {
            upgrade.setPurchased(false);
            //upgrade.resetNumPurchased();
        }
        upgradeCollection.clear();
    }

    public boolean selectedItemInCollection(Upgrade upgrade) {
        for (Upgrade u : upgradeCollection) {
            if (u.getName().equals(u.getName())) {
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

    public void addItem(Purchasable item) {
        if (item instanceof Car) {
            carCollection.add((Car) item);
        } else {
            upgradeCollection.add((Upgrade) item);
        }
    }

    public void removeItem(Purchasable item) {
        if (item instanceof Car) {
            carCollection.remove((Car) item);
        } else {
            upgradeCollection.remove((Upgrade) item);
        }
        /*int i = 0;
        if (item instanceof Car) {
            while (i < carCollection.size()) {
                if (carCollection.get(i).getName().equals(item.getName())) {
                    carCollection.remove(i);
                    break;
                }
                i++;
            }
        } else {
            while (i < upgradeCollection.size()) {
                if (upgradeCollection.get(i).getName().equals(item.getName())) {
                    upgradeCollection.remove(i);
                    break;
                }
                i++;
            }
        }*/
    }

    /*

    public void removeItem(Car car) {
        boolean removed = false;
        int i = 0;
        while (i < carCollection.size() || !removed) {
            if (carCollection.get(i).getName().equals(car.getName())) {
                carCollection.remove(i);
                removed = true;
            }
            i++;
        }
    }

    public void removeItem(Upgrade upgrade) {
        boolean removed = false;
        int i = 0;
        while (i < upgradeCollection.size() || !removed) {
            if (upgradeCollection.get(i).getName().equals(upgrade.getName())) {
                upgradeCollection.remove(i);
                removed = true;
            }
            i++;
        }
    }*/

    public Car searchCarAtIndex(int i) {
        return carCollection.get(i);
    }

    public void printCars() {
        System.out.println("You have these cars in your collection");
        for (Car car : carCollection) {
            System.out.println(car.getName() + " is purchased: " + car.isPurchased());
        }
    }

    public void printUpgrades() {
        System.out.println("You have purchased these upgrades");
        for (Upgrade upgrade : upgradeCollection) {
            System.out.println(String.format("%s quantity: x%d", upgrade.getName(), upgrade.getNumPurchased()));
        }
    }
}
