package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.GameManager;
import seng201.team0.gui.GarageController;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Item;
import seng201.team0.models.Upgrade;
import seng201.team0.services.GarageService;
import seng201.team0.services.ShopService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GarageTest {
    GameStats gameDB;
    ShopService shopService;
    List<Car> Cars;
    List<Upgrade> Upgrades;
    List<Car> availableCars;
    GarageService garageService = new GarageService();
    GameManager gameManager = new GameManager();
    Car selectedCar;
    Item selectedItem;
    //  select car test

    @BeforeEach
    void init() {
        // init issue, garage controller -> garage service parejong panksiyon
        gameDB = GameManager.getGameStats();
        shopService = new ShopService();
        Cars = GameManager.getCars();
        Upgrades = GameManager.getUpgrades();

        gameDB.clearCarCollection();
        gameDB.clearUpgradeCollection();

        gameDB.setBal(10000);

        shopService.buyItem(Cars.get(0), "Car");
        shopService.buyItem(Cars.get(1), "Car");
        shopService.buyItem(Cars.get(2), "Car");

        //selectedItem = Upgrades.get(0);

        shopService.buyItem(Upgrades.get(0), "Upgrade");
        shopService.buyItem(Upgrades.get(0), "Upgrade");
        shopService.buyItem(Upgrades.get(1), "Upgrade");

        System.out.println();
        gameDB.printUpgrades();
        System.out.println();

        // Selected Car is Purple Car
        gameDB.setSelectedCar(gameDB.searchCarAtIndex(0));
    }

    @Test
    void selectCar() {
        //gameDB.printCars();
        assertEquals(gameDB.getSelectedCar().getName(), "Purple Car");
        garageService.updateSelectedCar(gameDB.searchCarAtIndex(1));
        assertEquals(gameDB.getSelectedCar().getName(), "Lime Wheels");
    }

    @Test
    void fillTankWhenHaveSufficientMoney() {
        gameDB.setBal(200);
        selectedCar = gameDB.getSelectedCar();
        selectedCar.setFuel(40);
        garageService.fillTank(selectedCar);

        assertEquals(selectedCar.getFuel(), 100);
        assertEquals(gameDB.getBal(), 20);
    }

    @Test
    void fillTankWhenYouDontHaveEnoughMoneyToFullyFill() {
        gameDB.setBal(60);
        selectedCar = gameDB.getSelectedCar();
        selectedCar.setFuel(40);
        garageService.fillTank(selectedCar);
        assertEquals(selectedCar.getFuel(), 60);
        assertEquals(gameDB.getBal(), 0);
    }

    @Test
    void equipUpgradeWhenPurchasedMultipleOfSameUpgrade() {
        // Selecting rocket fuel
        selectedCar = gameDB.getSelectedCar();
        Upgrade selectedUpgrade = gameManager.getUpgradeAtIndex(0);
        GarageService.equipResult result = garageService.equipUpgrade(selectedUpgrade, selectedCar);
        assertEquals(result, GarageService.equipResult.SUCCESS);
        assertTrue(selectedCar.getEquippedUpgrades().contains(selectedUpgrade));
        assertEquals(selectedUpgrade.getNumPurchased(), 1);
        assertTrue(gameManager.getAvailableUpgrades().contains(selectedUpgrade));
    }

    @Test
    void equipUpgradeWhenOnlyOwnOneInstanceOfAnUpgrade() {
        selectedCar = gameDB.getSelectedCar();
        Upgrade selectedUpgrade = gameManager.getUpgradeAtIndex(1);
        GarageService.equipResult result = garageService.equipUpgrade(selectedUpgrade, selectedCar);


        assertEquals(result, GarageService.equipResult.SUCCESS);
        assertEquals(selectedUpgrade.getNumPurchased(), 0);

        assertTrue(selectedCar.getEquippedUpgrades().contains(selectedUpgrade));
        assertFalse(gameDB.getUpgradeCollection().contains(selectedUpgrade));

    }

    @Test
    void equipUpgradeWhenNoUpgradeSelected() {
        selectedCar = gameDB.getSelectedCar();
        Upgrade selectedUpgrade = null;
        GarageService.equipResult result = garageService.equipUpgrade(selectedUpgrade, selectedCar);
        assertEquals(result, GarageService.equipResult.UPGRADE_NOT_SELECTED);

    }

    @Test
    void unequipUpgrade() {
        // equipping rocket fuel
        selectedCar = gameDB.getSelectedCar();
        Upgrade selectedUpgrade = gameManager.getUpgradeAtIndex(0);
        GarageService.equipResult equipResult = garageService.equipUpgrade(selectedUpgrade, selectedCar);
        assertEquals(equipResult, GarageService.equipResult.SUCCESS);
        assertTrue(selectedCar.getEquippedUpgrades().contains(selectedUpgrade));
        assertEquals(selectedUpgrade.getNumPurchased(), 1);

        // unequipping it

        GarageService.unequipResult unequipResult = garageService.unequipUpgrade(selectedUpgrade, selectedCar);
        assertEquals(unequipResult, GarageService.unequipResult.SUCCESS);
        assertFalse(selectedCar.getEquippedUpgrades().contains(selectedUpgrade));
        assertEquals(selectedUpgrade.getNumPurchased(), 2);

        // kakaibang resulta pag na simula ng test + ilan test ba na kailangan namin
    }

}
