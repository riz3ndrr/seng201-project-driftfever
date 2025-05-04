package seng201.team0;

import seng201.team0.gui.MainWindow;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Race;
import seng201.team0.models.Upgrade;

import java.util.Arrays;
import java.util.List;

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
            new Race(50, 5, 4, "This race has an insane amount of twists and turns!"),
            new Race(80, 2, 2, "A long, mostly straight race through open countryside."),
            new Race(30, 3, 1, "A short, moderately curvy track perfect for quick sprints."),
            new Race(100, 4, 3, "A challenging endurance race with frequent turns and pit stops."),
            new Race(60, 1, 0, "A smooth ride with almost no curves and no gas stops.")
    );


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

    public static List<Car> getCars() {
        return carsArray;
    }


}
