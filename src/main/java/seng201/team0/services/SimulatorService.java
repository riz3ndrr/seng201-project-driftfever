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
import java.util.ArrayList;
import java.util.Random;


public class SimulatorService {
    // Properties
    GameStats gameDB = GameManager.getGameStats();


    // Logic
    public void prepareRace(Race race, RaceParticipant player) {
        generateOpponents(race, gameDB.getNumOpponenents());
        race.addParticipant(player);
    }

    private int generateEntryNumberForOpponent() {
        // Numbers between 2 and 99 as the player is always number 1
        Random rand = new Random();
        return rand.nextInt(97) + 2;
    }

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

    public void generateOpponents(Race race, int numOpponents) {
        race.clearParticipants();
        for (int i = 0; i < numOpponents; i++) {
            Car car = generateRandomCar();
            RaceParticipant participant = new RaceParticipant(car, generateOpponentName(), generateEntryNumberForOpponent());
            race.addParticipant(participant);
        }
    }

    public Car generateRandomCar() {
        ArrayList<Car> cars = GameManager.getCars();
        Random rand = new Random();
        int randomIndex = rand.nextInt(cars.size());
        Car randomCar = cars.get(randomIndex).makeCopy();
        double chanceOfUpgrade = gameDB.getOpponentUpgradeProbability();
        for (Upgrade upgrade : GameManager.getUpgrades()) {
            boolean awarded = Math.random() <= chanceOfUpgrade;
            if (awarded) {
                randomCar.addUpgrade(upgrade);
            }
        }
        randomCar.setFuelInTank(randomCar.calculateFuelTankCapacity());
        return randomCar;
    }

    public static void switchToSimulatorScene(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SimulatorController.class.getResource("/fxml/simulator.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        SimulatorController controller = loader.getController();
        controller.initialize(stage);
    }
}
