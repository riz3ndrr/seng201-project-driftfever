package seng201.team0.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seng201.team0.GameManager;
import seng201.team0.gui.SimulatorController;
import seng201.team0.models.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;


public class SimulatorService {
    // Properties
    GameStats gameDB = GameManager.getGameStats();


    // Logic

    /**
     * When called, generate a randomised list of opponents and add the main player to that list.
     * @param race which is the race selected by the user
     * @param player which refers to the main player
     */
    public void prepareRace(Race race, RaceParticipant player) {
        generateOpponents(race, GameManager.getNumOpponents());
        race.addParticipant(player);
    }

    /**
     * Generate a random entry number for a racer
     * @return int which is the entry number of a racer.
     */

    private int generateEntryNumberForOpponent() {
        // Numbers between 2 and 99 as the player is always number 1
        Random rand = new Random();
        return rand.nextInt(97) + 2;
    }

    /**
     * Randomly generate a full name for the CPU racers
     * @return a String composing of a random combination of 2 names
     */

    private String generateOpponentName() {
        String[] firstNames = { "Jake", "Emily", "Liam", "Chloe", "Max", "Sophia", "Luke", "Olivia",
                "Ava", "Noah", "Leo", "Mia", "Ella", "Ethan", "Zoe", "Jack", "Grace",
                "Hunter", "Scarlett", "Mason", "Blaze", "Rocket", "Clutch", "Turbo", "Crash" };
        String[] lastNames = { "Walker", "Stone", "Blake", "Foster", "Morgan", "Turner", "Bennett", "Reed",
                "Porter", "Hayes", "Brooks", "Carter", "Watts", "Flynn", "West", "Cole",
                "Racer", "Fastlane", "Wheelman", "Burner", "Quickshift", "McSpeed", "Nitro", "O'Clutch", "Zoomer" };
        Random rand = new Random();
        return firstNames[rand.nextInt(firstNames.length)] + " " + lastNames[rand.nextInt(lastNames.length)];
    }

    /**
     * Clear the current list of opponents and generate new ones.
     * @param race refers to the selected race
     * @param numOpponents refers to the number of opponents in that particular race
     */

    public void generateOpponents(Race race, int numOpponents) {
        race.clearParticipants();
        for (int i = 0; i < numOpponents; i++) {
            Car car = generateRandomCar();
            RaceParticipant participant = new RaceParticipant(car, generateOpponentName(), generateEntryNumberForOpponent(), false);
            race.addParticipant(participant);
        }
    }

    /**
     * Generate a random car with randomly equipped upgrades and a full tank.
     * @return the car of type Car of a particular opponent.
     */

    public Car generateRandomCar() {
        List<Car> cars = GameManager.getCars();
        Random rand = new Random();
        int randomIndex = rand.nextInt(cars.size());
        Car randomCar = cars.get(randomIndex).makeCopy();
        double chanceOfUpgrade = GameManager.getOpponentUpgradeProbability();
        for (Upgrade upgrade : GameManager.getUpgrades()) {
            boolean awarded = Math.random() <= chanceOfUpgrade;
            if (awarded) {
                randomCar.addUpgrade(upgrade);
            }
        }
        randomCar.setFuelInTank(randomCar.calculateFuelTankCapacity());
        return randomCar;
    }


}
