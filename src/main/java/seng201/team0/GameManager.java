package seng201.team0;

import seng201.team0.gui.MainWindow;
import seng201.team0.models.*;
import seng201.team0.services.ShopService;

import java.util.ArrayList;
import java.util.Arrays;

public class GameManager {
    // Properties
    private static GameStats gameDB = new GameStats();
    private static ArrayList<Race> raceArray = createRaces();
    private static ArrayList<Car> carsArray = createCars();
    private static ArrayList<Upgrade> upgradeArray = createUpgrades();


    // Constructor
    private static ArrayList<Race> createRaces() {
        return new ArrayList<>(Arrays.asList(
                new Race("Serpent's Spiral",
                        "This race has an insane amount of twists and turns!",
                        200,
                        0.9,
                        4,
                        1000,
                        3
                        ),
                new Race("Sunset Sprint",
                        "A long, mostly straight race through open countryside.",
                        650,
                        0.2,
                        2,
                        800,
                        4),
                new Race("Turbo Loop",
                        "A short, moderately curvy track perfect for quick sprints.",
                        100,
                        0.5,
                        1,
                        500,
                        1),
                new Race("The Iron Road",
                        "A challenging endurance race with frequent turns and pit stops.",
                        550,
                        0.7,
                        3,
                        3000,
                        7),
                new Race("Featherline Cruise",
                        "A smooth ride with almost no curves and limited gas stops.",
                        300,
                        0.1,
                        1,
                        700,
                        2)
        ));
    }

    private static ArrayList<Car> createCars() {
        return new ArrayList<>(Arrays.asList(
                new Car(0,
                        "Purple Car",
                        "A balanced car with smooth acceleration and steady handling.",
                        1600,
                        1400,
                        true,
                        180,
                        0.1,
                        55555,//TODO PUT BACK TO 55 once refueling is implemented, this value is for testing
                        0.8,
                        0.7),
                new Car(2,
                        "Lime Wheels",
                        "A versatile car with equal balance between speed and handling.",
                        1500,
                        850,
                        true,
                        170,
                        0.09,
                        50,
                        0.75,
                        0.85),
                new Car(1,
                        "Lightning McQueen",
                        "Kachow!",
                        1550,
                        850,
                        true,
                        195,
                        0.12,
                        60,
                        0.9,
                        0.6),
                new Car(3,
                        "Yellow Car",
                        "Light and agile, perfect for quick turns and smooth drifting.",
                        1400,
                        700,
                        true,
                        160,
                        0.06,
                        45,
                        0.95,
                        0.8),
                new Car(4,
                        "Azure",
                        "Durable with solid control and good handling on various surfaces.",
                        1300,
                        750,
                        true,
                        135,
                        0.11,
                        70,
                        0.7,
                        0.95),
                new Car(5,
                        "Crosswind",
                        "High-performance with fast acceleration and responsive handling.",
                        3600,
                        1600,
                        false,
                        185,
                        0.14,
                        50,
                        0.95,
                        0.6),
                new Car(6,
                        "Thunder McKing",
                        "Was used to win 7 Piston Cups. Kablow!",
                        5200,
                        4800,
                        true,
                        220,
                        0.2,
                        50,
                        0.9,
                        0.4),
                new Car(7,
                        "Icarus' Wings",
                        "The world's fastest car. Although not renowned for its stability.",
                        4600,
                        1400,
                        false,
                        240,
                        0.25,
                        40,
                        0.5,
                        0.4),
                new Car(8,
                        "Bumblebee",
                        "Legend says this car has a mind of its own",
                        5000,
                        1300,
                        false,
                        195,
                        0.07,
                        55,
                        0.3,
                        0.7)
        ));
    }

    private static ArrayList<Upgrade> createUpgrades() {
        return new ArrayList<>(Arrays.asList(
                new Upgrade(0,
                        "Rocket Fuel",
                        "Fuel to make your car go ZOOOOM!",
                        150,
                        120,
                        true,
                        1.2,
                        1.0,
                        1.0,
                        0.9,
                        1.0),
                new Upgrade(3,
                        "Jumbo Fuel Tank",
                        "Extra capacity for extra distance.",
                        600,
                        540,
                        true,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.7),
                new Upgrade(1,
                        "Grippy Tyres",
                        "Improved traction for tighter turns and better control at high speeds.",
                        400,
                        350,
                        true,
                        1.0,
                        1.5,
                        1.0,
                        1.0,
                        1.0),
                new Upgrade(2,
                        "Carbon Fibre Panels",
                        "Lightweight yet durable — improves speed without sacrificing reliability.",
                        1100,
                        950,
                        true,
                        1.2,
                        1.5,
                        1.0,
                        1.0,
                        1.7)
        ));
    }


    // Getters and setters
    public static GameStats getGameStats() { return gameDB; }
    public static ArrayList<Race> getRaces() {return raceArray; }
    public static Race getRaceAtIndex(int index) {
        return raceArray.get(index);
    }
    public static ArrayList<Car> getCars() {
        return carsArray;
    }
    public static ArrayList<Upgrade> getUpgrades() {
        return upgradeArray;
    }
    public static Upgrade getUpgradeAtIndex(int index) { return upgradeArray.get(index); }
    public static Upgrade getUpgradeWithID(int findUpgradeID) {
        for (Upgrade upgrade : upgradeArray) {
            if (upgrade.getItemID() == findUpgradeID) {
                return upgrade;
            }
        }
        return null;
    }


    // Logic
    public static void startGame(String [] args) {
        //TODO This code pre buys some things for testing, delete later!!
        ShopService shopService = new ShopService();
        shopService.buyItem(carsArray.get(0));
        shopService.buyItem(carsArray.get(1));
        shopService.buyItem(carsArray.get(2));
        shopService.buyItem(upgradeArray.get(0));
        shopService.buyItem(upgradeArray.get(1));
        shopService.buyItem(upgradeArray.get(1));
        shopService.buyItem(upgradeArray.get(2));
        shopService.buyItem(upgradeArray.get(3));
        for (Car car : carsArray) {
            car.setFuelInTank(car.calculateFuelTankCapacity());
        }

        MainWindow.launchWrapper(args);
    }

    public static ArrayList<Car> getAvailableCars() {
        ArrayList<Car> result = new ArrayList<>();
        for (Car car : carsArray) {
            if (!car.isPurchased() && car.isAvailableToBuy()) {
                result.add(car);
            }
        }
        return result;
    }

    public static ArrayList<Upgrade> getAvailableUpgrades() {
        ArrayList<Upgrade> result = new ArrayList<>();
        for (Upgrade upgrade : upgradeArray) {
            if (!upgrade.isPurchased() && upgrade.isAvailableToBuy()) {
                result.add(upgrade);
            }
        }
        return result;
    }

}

