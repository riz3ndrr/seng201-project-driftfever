package seng201.team0;

import seng201.team0.gui.MainWindow;
import seng201.team0.models.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameManager {
    static GameStats gameDB = new GameStats();

    public static void startGame(String [] args) {
        MainWindow.launchWrapper(args);
    }




    public static GameStats getGameStats() {
        return gameDB;
    }

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

    private static List<Race> raceArray = Arrays.asList(
            new Race(50, 5, 4, "This race has an insane amount of twists and turns!", 1000, 300, "Serpent's Spiral"),
            new Race(80, 2, 2, "A long, mostly straight race through open countryside.", 800, 300, "Sunset Sprint"),
            new Race(30, 3, 1, "A short, moderately curvy track perfect for quick sprints.", 500, 300, "Turbo Loop"),
            new Race(100, 4, 3, "A challenging endurance race with frequent turns and pit stops.", 3000, 300, "The Iron Road"),
            new Race(60, 1, 0, "A smooth ride with almost no curves and no gas stops.", 700, 300, "Featherline Cruise")
    );



    public static Race getRaceAtIndex(int index) {
        return raceArray.get(index);
    }


    private static List<Car> carsArray =
            Arrays.asList( new Car("Purple Car", 1600, 800, true, 5, 4, 6, 5, "A balanced car with smooth acceleration and steady handling.", 0),
                    new Car("Lime Wheels", 1500, 850, true, 5, 5, 4, 4, "A versatile car with equal balance between speed and handling.", 1),
                    new Car("Lightning McQueen", 1550, 850, true, 4, 4, 5, 5, "Kachow!", 2),
                    new Car("Yellow Car", 1400, 700, true, 4, 5, 5, 4, "Light and agile, perfect for quick turns and smooth drifting.", 3),
                    new Car("Azure", 1300, 750, true, 5, 4, 6, 5, "Durable with solid control and good handling on various surfaces.", 4),
                    new Car("Crosswind", 3600, 1600, false, 8, 9, 9, 7, "High-performance with fast acceleration and responsive handling.", 5),
                    new Car("Thunder McKing", 5200, 3200, true, 12, 9, 6, 6, "Was used to win 7 Piston Cups. Kablow!", 6),
                    new Car("Icarus' Wings", 4600, 1400, false, 15, 3, 4, 3, "The world's fastest car. Although not renowned for its stability.", 7),
                    new Car("Bumblebee", 5000, 1300, false, 10, 10, 9, 9, "Legend says this car has a mind of its own", 8)
            );

    public static boolean canBeBought(Item item) {
        return !item.isPurchased() && item.isAvailableToBuy();
    }

    private static ArrayList<Car> availableCarsArray = new ArrayList<>(carsArray.stream().filter(GameManager::canBeBought).collect(Collectors.toList()));

    private static ArrayList<Upgrade> availableUpgradesArray = new ArrayList<>(upgradeArray.stream().filter(GameManager::canBeBought).collect(Collectors.toList()));

    public static ArrayList<Car> getAvailableCars() {
        return availableCarsArray;
    }

    public static ArrayList<Upgrade> getAvailableUpgrades() {
        return availableUpgradesArray;
    }

    public static List<Car> getCars() {
        return carsArray;
    }

    public static List<Race> getRaces() {return raceArray;}


}
