package seng201.team0.unittests.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.GameManager;
import seng201.team0.models.*;
import seng201.team0.services.SimulatorService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import seng201.team0.models.Race;
import seng201.team0.models.RaceParticipant;


public class SimulatorServiceTest {

    private final GameStats gameDB = GameManager.getGameStats();
    private final SimulatorService simulator = new SimulatorService();
    private final List<Car> cars = GameManager.getCars();
    private final List<Upgrade> upgrades = GameManager.getUpgrades();

    @BeforeEach
    void setUp() {
        GameManager.setNumOpponents(3);
        GameManager.setOpponentUpgradeProbability(0.0);
    }

    @AfterEach
    void tearDown() {
        for (Upgrade u : upgrades) {
            u.setNumPurchased(0);
            u.setPurchased(false);
        }
    }

    /**
     * prepareRace should generate exactly numOpponents opponents,
     * then add the player, for a total of numOpponents + 1 participants.
     */
    @Test
    void prepareRace() {
        Race race = new Race("Test", "desc", 50f, 0.2, 2, 5000f, 2.0);
        RaceParticipant player = new RaceParticipant(cars.get(0), "You", 1, true);
        simulator.prepareRace(race, player);

        assertEquals(4, race.getParticipants().size(), "Should have  numOpponents + 1 participants");
        boolean foundPlayer = false;
        for (RaceParticipant p : race.getParticipants()) {
            if (p.getIsPlayer()) {
                foundPlayer = true;
                break;
            }
        }
        assertTrue(foundPlayer, "The player's RaceParticipant should be in the list");
    }

    /**
     * generateOpponents should clear existing participants
     * and then add exactly the requested number of CPU opponents.
     */
    @Test
    void generateOpponents() {
        Race race = new Race("Test", "desc", 50f, 0.2, 2, 5000f, 2.0);
        race.addParticipant(new RaceParticipant(cars.getFirst(), "Test", 1, true));
        assertFalse(race.getParticipants().isEmpty());

        simulator.generateOpponents(race, 5);

        // Old participants cleared, now 5 new ones
        assertEquals(5, race.getParticipants().size(), "After generateOpponents, should have exactly N opponents");

        // None of these should be the player
        for (RaceParticipant p : race.getParticipants()) {
            assertFalse(p.getIsPlayer(), "Generated opponents must not be marked as player.");
        }
    }

    /**
     * generateRandomCar should return a fresh Car copy whose fuel tank
     * is filled to capacity and (with default 0% upgrade chance) has no upgrades.
     */
    @Test
    void generateRandomCarZeroUpgrades() {
        // Ensure we have at least one
        assertFalse(cars.isEmpty(), "GameManager.getCars() should provide some cars");

        Car randomCar = simulator.generateRandomCar();
        double capacity = randomCar.calculateFuelTankCapacity();

        // Fuel should be set to full capacity
        assertEquals(capacity, randomCar.getFuelInTank(), "generated car must start with full tank");
        // No upgrades should be equipped by default
        assertTrue(randomCar.getEquippedUpgrades().isEmpty(), "No upgrades should be applied");
    }

    /**
     * If opponentUpgradeProbability is set to 100%, every available upgrade
     * should be equipped on each generated car.
     */
    @Test
    void generateRandomCarMaxUpgrades() {
        GameManager.setOpponentUpgradeProbability(1.0);

        Car randomCar = simulator.generateRandomCar();

        assertEquals(upgrades.size(), randomCar.getEquippedUpgrades().size(), "100% probability, all upgrades should be added");
        //
    }

}


















