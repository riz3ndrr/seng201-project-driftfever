package seng201.team0;

import seng201.team0.gui.MainWindow;
import seng201.team0.models.*;
import seng201.team0.services.ShopService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameManager {
    // Properties
    private static GameStats gameDB = new GameStats();
    private static List<Race> raceArray = createRaces();
    private static List<Car> carsArray = createCars();
    private static List<Upgrade> upgradeArray = createUpgrades();


    // Constructor
    private static List<Race> createRaces() {
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


    private static List<Car> createCars() {
        return new ArrayList<>(Arrays.asList(
                new Car(0,
                        "Purple Car",
                        "A balanced car with smooth acceleration and steady handling.",
                        1600,
                        1400,
                        true,
                        180,
                        0.6,
                        55555,//TODO PUT BACK TO 55 once refueling is implemented, this value is for testing
                        0.8,
                        0.7),
                new Car(1,
                        "Lightning McQueen",
                        "Kachow!",
                        1550,
                        850,
                        true,
                        195,
                        0.72,
                        60,
                        0.9,
                        0.6),
                new Car(2,
                        "Lime Wheels",
                        "A versatile car with equal balance between speed and handling.",
                        1500,
                        850,
                        true,
                        170,
                        0.54,
                        50,
                        0.75,
                        0.85),
                new Car(3,
                        "Yellow Car",
                        "Light and agile, perfect for quick turns and smooth drifting.",
                        1400,
                        700,
                        true,
                        160,
                        0.48,
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
                        0.66,
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
                        0.84,
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
                        1.1,
                        70,
                        0.9,
                        0.4),
                new Car(7,
                        "Icarus' Wings",
                        "The world's fastest car. Although not renowned for its stability.",
                        4600,
                        1400,
                        false,
                        240,
                        1.25,
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
                        0.42,
                        55,
                        0.3,
                        0.7)
        ));
    }

    private static List<Upgrade> createUpgrades() {
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
                        1.7),

                new Upgrade(
                        4,
                        "Attachable Rocket",
                        "Apparently stolen from NASA; use it to break the sound barrier.",
                        2000,
                        1500,
                        true,
                        1.5,
                        1.0,
                        0.9,
                        1.2,
                        1.0
                ),
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

                new Upgrade(
                        5,
                        "Glue Spray",
                        "Spray glue on tires to boost its grip",
                        300,
                        200,
                        true,
                        1.0,
                        1.3,
                        1.0,
                        1.0,
                        1.0
                ),

                new Upgrade(
                        7,
                        "AI System",
                        "Smart driving assistant that drives your car with finesse.",
                        700,
                        630,
                        true,
                        1.0,
                        1.5,
                        1.2,
                        1.0,
                        1.0
                ),

                new Upgrade(
                        6,
                        "Better Brakes",
                        "Upgraded brakes for shorter stopping distance and better control.",
                        500,
                        300,
                        true,
                        1.0,
                        1.15,
                        1.0,
                        1.0,
                        1.0
                )




        ));
    }


    // Getters and setters
    public static GameStats getGameStats() { return gameDB; }
    public static List<Race> getRaces() {return raceArray; }
    public static Race getRaceAtIndex(int index) {
        return raceArray.get(index);
    }
    public static List<Car> getCars() {
        return carsArray;
    }
    public static List<Upgrade> getUpgrades() {
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

    public static List<Car> getAvailableCars() {
        List<Car> result = new ArrayList<>();
        for (Car car : carsArray) {
            if (!car.isPurchased() && car.isAvailableToBuy()) {
                result.add(car);
            }
        }
        return result;
    }

    public static List<Upgrade> getAvailableUpgrades() {
        List<Upgrade> result = new ArrayList<>();
        for (Upgrade upgrade : upgradeArray) {
            if (!upgrade.isPurchased() && upgrade.isAvailableToBuy()) {
                result.add(upgrade);
            }
        }
        return result;
    }

}

