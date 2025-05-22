package seng201.team0.unittests.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Upgrade;
import seng201.team0.services.GarageService;
import seng201.team0.services.ShopService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GameStatsTest {

    GameStats gameDB = GameManager.getGameStats();
    ShopService shopService = new ShopService();;
    List<Car> Cars = GameManager.getCars();;
    List<Upgrade> Upgrades = GameManager.getUpgrades();
    Upgrade selectedUpgrade;
    GarageService garageService = new GarageService();
    Car selectedCar;

    /**
     * Set all cars to be broken down for testing purposes
     */
    void setAllCarsToBeBrokenDown() {
        for (Car car : gameDB.getCarCollection()) {
            car.setBrokenDown(true);
        }
    }

    /**
     * Set all cars to not be broken down for testing purposes
     */
    void setAllCarsToNotBeBrokenDown() {
        for (Car car : gameDB.getCarCollection()) {
            car.setBrokenDown(false);
        }
    }

    /**
     * Start off the tests with 3 cars, all them being initialised to be broken down
     */
    @BeforeEach
    void init() {
        gameDB.setBal(10000);
        shopService.buyItem(Cars.get(0));
        shopService.buyItem(Cars.get(1));
        shopService.buyItem(Cars.get(2));

    }

    /**
     * Clear the player's car collection (and the upgrades the respective car has equipped) along with
     * the player's upgrade collection.
     */
    @AfterEach
    void clear() {
        gameDB.clearCarCollection();
        gameDB.clearUpgradeCollection();
    }


    /**
     * Test to check if this method returns true if all cars are broken down
     */
    @Test
    void checkIfAllCarsBrokenDownWhenAllAreBroken() {
        setAllCarsToBeBrokenDown();
        boolean result = gameDB.areAllCarsBrokenDown();
        assertTrue(result);
    }

    /**
     * Test to check if this method returns false if one car is not broken down
     */
    @Test
    void checkIfAllCarsBrokenDownWhenOneIsNot() {
        setAllCarsToBeBrokenDown();
        Car selectedCar = Cars.get(0);
        selectedCar.setBrokenDown(false);
        boolean result = gameDB.areAllCarsBrokenDown();
        assertFalse(result);
    }

    /**
     * Test to check if this method returns false if all cars are not broken down
     */
    @Test
    void checkIfAllCarsBrokenDownWhenAllAreNot() {
        setAllCarsToNotBeBrokenDown();
        for (Car car : gameDB.getCarCollection()) {
            car.setBrokenDown(false);
        }
        boolean result = gameDB.areAllCarsBrokenDown();
        assertFalse(result);
    }

    /**
     * Check if the player can continue playing if all cars are functional
     */
    @Test
    void checkIfCanContinuePlayingIfAllCarsAreNotBroken() {
        setAllCarsToNotBeBrokenDown();
        boolean result = gameDB.canContinuePlaying();
        assertTrue(result);
    }

    /**
     * Check if the player can continue playing if it has one car that is functional
     */
    @Test
    void checkIfCanContinuePlayingIfOnlyOneCarIsFunctional() {
        setAllCarsToBeBrokenDown();
        Car selectedCar = Cars.get(0);
        selectedCar.setBrokenDown(false);
        boolean result = gameDB.canContinuePlaying();
        assertTrue(result);
    }

    /**
     * Check if the player can continue playing if all cars are broken down after the race,
     * but has sufficient funds to repair/refuel one and continue the game
     */
    @Test
    void checkIfCanContinuePlayingIfAllCarsAreBrokenButHaveSufficientFunds() {
        setAllCarsToBeBrokenDown();
        gameDB.setBal(5000);
        assertEquals(5000, gameDB.getBal());
        boolean result = gameDB.canContinuePlaying();
        assertTrue(result);
    }

    /**
     * Check if the player can continue playing if all cars are broken down after the race, and has
     * insufficent funds to repair the car, but the user is able to sell one of their cars
     */
    @Test
    void checkIfCanContinuePlayingIfAllCarsAreBrokenAndHaveMoreThanOneCar() {
        setAllCarsToBeBrokenDown();
        gameDB.setBal(50);
        assertEquals(50, gameDB.getBal());
        boolean result = gameDB.canContinuePlaying();
        assertTrue(result);
    }

    /**
     * Check if the player can continue playing if the user has only car (which has broken down)
     * and has insufficent funds.
     */
    @Test
    void checkIfCanContinuePlayingIfOnlyOwnOneCarWhichIsBroken() {
        gameDB.clearCarCollection();
        assertEquals(0, gameDB.getCarCollectionSize());
        shopService.buyItem(Cars.get(0));
        gameDB.setBal(50);
        assertEquals(50, gameDB.getBal());
        boolean result = gameDB.canContinuePlaying();
        assertFalse(result);
    }

}
