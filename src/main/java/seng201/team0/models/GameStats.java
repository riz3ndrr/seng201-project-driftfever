package seng201.team0.models;


import java.util.ArrayList;
import java.util.List;

public class GameStats {

    public enum Difficulty {
        EASY(5500F, 1.2),
        REGULAR(5000F, 1.0),
        HARD(4500F, 0.8);

        private final float startingBalance;
        private final double winningsMultiplier;

        Difficulty(float startingBalance, double winningsMultiplier) {
            this.startingBalance = startingBalance;
            this.winningsMultiplier = winningsMultiplier;
        }

        public float getStartingBalance() {
            return startingBalance;
        }

        public double getWinningsMultiplier() {
            return winningsMultiplier;
        }
    }
    private static final GameStats instance = new GameStats();

    private int raceCount = 3;
    private String userName;
    private Difficulty raceDifficulty = Difficulty.REGULAR;
    private float bal = raceDifficulty.getStartingBalance();

    private final ArrayList<Car> carCollection = new ArrayList<>();
    private final ArrayList<Upgrade> upgradeCollection = new ArrayList<>();

    public List<Upgrade> getUpgradeCollection() {
        return upgradeCollection;
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


    public int getCarCollectionSize() {
        return carCollection.size();
    }


    public void addItem(Car car) {carCollection.add(car);}
    public void addItem(Upgrade upgrade) {upgradeCollection.add(upgrade);}

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
    }

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
            System.out.println(upgrade.getName() + " quantity: " + String.format("%d", upgrade.getNumPurchased()) );
        }
    }

    public static GameStats getInstance() {
        return instance;
    }

    public void setBal(float bal) {
        this.bal = bal;
    }
    public float getBal() {
        return bal;
    }

        public double getAdjustedWinnings(double baseCost) {
            return baseCost * raceDifficulty.getWinningsMultiplier();
        }

    public void setRaceCount(int raceCount) {
        this.raceCount = raceCount;
    }
    public int getRaceCount() {
        return raceCount;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setRaceDifficulty(Difficulty difficulty) {
        this.raceDifficulty = difficulty;
        this.bal = difficulty.getStartingBalance();
    }

    public Difficulty getRaceDifficulty() {
        return raceDifficulty;
    }

}
