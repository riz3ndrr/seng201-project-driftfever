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
    private static double fuelCostPerLitre = 1.5;
    private static double minimumSecondsForGasStop = 3.0 * 60.0; // Time for driver to get out, pay, etc
    private static double secondsToPumpLitreOfGas = 10.0; // Time for a single litre of fuel to be pumped

    private static int numOpponents = 8;
    private static double opponentUpgradeProbability = 0.15;
    private static double opponentRefuelProbability = 0.8;
    private static double opponentRepairProbability = 0.75; // Chance that an opponent breaking down can be repaired
    private static double opponentPickUpHitchhikerProbability = 0.5; // Chance that if a hitchhiker is available the opponent will stop and pick them up
    private static double chanceOfHitchhikerPerKilometre = 0.005; // Chance that in any given kilometre a hitchhiker is available for pickup
    private static double hitchhikerPickUpTimeSeconds = 5.0 * 60.0; // If stopping this is how long it takes to pick up a hitchhiker.
    private static double minHitchhikerReward = 50.0;
    private static double maxHitchhikerReward = 300.0;
    private static double minRepairTimeSeconds = 10.0 * 60.0; // Repairs will take at least 10 minutes
    private static double maxRepairTimeSeconds = 20.0 * 60.0; // Repairs will take at most 20 minutes
    private static double minRepairCost = 200.0;
    private static double maxRepairCost = 800.0;
    private static double chanceOfRaceRouteBlockage = 0.000037; // Chance that in any given second a route is blocked, around 50/50 chance of happening per 2 hours



    // Constructor
    private static List<Race> createRaces() {
        return new ArrayList<>(Arrays.asList(
                new Race("Serpent's Spiral",
                        "This race has an insane amount of twists and turns!",
                        200,
                        0.7,
                        4,
                        4000,
                        3.5
                        ),
                new Race("Sunset Sprint",
                        "A long, mostly straight race through open countryside.",
                        275,
                        0.2,
                        5,
                        2000,
                        3.75),
                new Race("Turbo Loop",
                        "A short, moderately curvy track perfect for quick sprints.",
                        100,
                        0.5,
                        1,
                        1700,
                        1.25),
                new Race("The Iron Road",
                        "A challenging endurance race with frequent turns and pit stops.",
                        325,
                        0.7,
                        8,
                        4000,
                        5.5),
                new Race("Featherline Cruise",
                        "A smooth ride with almost no curves and limited gas stops.",
                        180,
                        0.1,
                        1,
                        2300,
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
                        180,
                        0.6,
                        55,
                        0.8,
                        0.9931),
                new Car(1,
                        "Red Rover",
                        "A great car perfect for a rookie racer",
                        1550,
                        850,
                        195,
                        0.72,
                        60,
                        0.9,
                        0.9927),
                new Car(2,
                        "Lime Wheels",
                        "A versatile car with equal balance between speed and handling.",
                        1500,
                        850,
                        170,
                        0.54,
                        50,
                        0.75,
                        0.9964),
                new Car(3,
                        "Yellow Car",
                        "Light and agile, perfect for quick turns and smooth drifting.",
                        1400,
                        700,
                        160,
                        0.48,
                        45,
                        0.95,
                        0.9943),
                new Car(4,
                        "Azure",
                        "Durable with solid control and good handling on various surfaces.",
                        1300,
                        750,
                        135,
                        0.66,
                        70,
                        0.7,
                        0.9992),
                new Car(5,
                        "Crosswind",
                        "High-performance with fast acceleration and responsive handling.",
                        3600,
                        1600,
                        185,
                        0.84,
                        50,
                        0.95,
                        0.9928),
                new Car(6,
                        "Thunder McKing",
                        "Was used to win 7 Piston Cups. Kablow!",
                        5200,
                        4800,
                        220,
                        1.1,
                        70,
                        0.9,
                        0.9963),
                new Car(7,
                        "Icarus' Wings",
                        "The world's fastest car. Although not renowned for its reliability.",
                        4600,
                        1400,
                        240,
                        1.25,
                        70,
                        0.5,
                        0.9821),
                new Car(8,
                        "Bumblebee",
                        "Legend says this car has a mind of its own",
                        5400,
                        4300,
                        195,
                        0.42,
                        55,
                        0.3,
                        0.9975)
        ));
    }


    private static List<Upgrade> createUpgrades() {
        return new ArrayList<>(Arrays.asList(
                new Upgrade(0,
                        "Rocket Fuel",
                        "Fuel to make your car go ZOOOOM!",
                        150,
                        120,
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
                        1.2,
                        1.2,
                        1.0,
                        1.0,
                        1.0),
                new Upgrade(
                        4,
                        "Attachable Rocket",
                        "Apparently stolen from NASA; use it to break the sound barrier.",
                        2000,
                        1500,
                        1.5,
                        0.8,
                        0.9,
                        0.8,
                        1.0),
                new Upgrade(3,
                        "Jumbo Fuel Tank",
                        "Extra capacity for extra distance.",
                        600,
                        540,
                        0.95,
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
                        1.0,
                        1.3,
                        1.0,
                        1.0,
                        1.0),
                new Upgrade(
                        7,
                        "AI System",
                        "Smart driving assistant that drives your car with finesse.",
                        700,
                        630,
                        1.0,
                        1.5,
                        1.2,
                        1.0,
                        1.0),
                new Upgrade(
                        6,
                        "Better Brakes",
                        "Upgraded brakes for shorter stopping distance and better control.",
                        500,
                        300,
                        1.0,
                        1.15,
                        1.0,
                        1.0,
                        1.0)
        ));
    }



    // Getters and setters
    public static double getFuelCostPerLitre() { return fuelCostPerLitre; }
    public static double getMinimumSecondsForGasStop() { return minimumSecondsForGasStop; }
    public static double getSecondsToPumpLitreOfGas() { return secondsToPumpLitreOfGas; }
    public static int getNumOpponents() { return numOpponents; }
    public static void setNumOpponents(int value) { numOpponents = value; }
    public static double getOpponentUpgradeProbability() { return opponentUpgradeProbability; }
    public static void setOpponentUpgradeProbability(double value) { opponentUpgradeProbability = value; }
    public static double getOpponentRefuelProbability() { return opponentRefuelProbability; }
    public static double getOpponentRepairProbability() { return opponentRepairProbability; }
    public static double getOpponentPickUpHitchhikerProbability() { return opponentPickUpHitchhikerProbability; }
    public static double getHitchhikerPickUpTimeSeconds() { return hitchhikerPickUpTimeSeconds; }
    public static double getChanceOfHitchhikerPerKilometre() { return chanceOfHitchhikerPerKilometre; }
    public static double getChanceOfRaceRouteBlockage() { return chanceOfRaceRouteBlockage; }

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
    public static Upgrade getUpgradeWithID(int findUpgradeID) {
        for (Upgrade upgrade : upgradeArray) {
            if (upgrade.getItemID() == findUpgradeID) {
                return upgrade;
            }
        }
        return null;
    }


    // Logic
    /**
     * Calculate the cost of fixing a broken vehicle which is a random value.
     * @return the repair cost
     */
    public static double calculateRandomRepairCost() {
        return minRepairCost + Math.random() * (maxRepairCost - minRepairCost);
    }

    /**
     * Calculate the time it takes to repair a broken vehicle which is random.
     * @return the time in seconds to repair.
     */
    public static double calculateRandomRepairTime() {
        return minRepairTimeSeconds + Math.random() * (maxRepairTimeSeconds - minRepairTimeSeconds);
    }
    /**
     *
     * Create a random value which will be used as the amount of money a person gets for
     * picking up a hitchhiker.
     * @return the money reward
     */
    public static double calculateRandomHitchhikerReward() {
        return minHitchhikerReward + Math.random() * (maxHitchhikerReward - minHitchhikerReward);
    }


}

