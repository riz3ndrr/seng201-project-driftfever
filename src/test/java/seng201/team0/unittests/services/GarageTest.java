package seng201.team0.unittests.services;

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

public class GarageTest {
    GameStats gameDB = GameManager.getGameStats();
    ShopService shopService = new ShopService();;
    List<Car> Cars = GameManager.getCars();;
    List<Upgrade> Upgrades = GameManager.getUpgrades();
    Upgrade selectedUpgrade;
    GarageService garageService = new GarageService();
    Car selectedCar;



    /**
     * Sets up variables before every test. Here, we are purchasing Purple Car, Red Rover and Lime Wheels,
     * in which Lime Wheels is set as the selected car.
     *
     * 2 lots of Rocket Fuel and 1 of Grippy Tyres are also purchased before every test, Lime Wheels'
     * fuel tank is set to half capacity.
     */

    @BeforeEach
    void init() {


        gameDB.setBal(10000);
        shopService.buyItem(Cars.get(0));
        shopService.buyItem(Cars.get(1));
        shopService.buyItem(Cars.get(2));


        shopService.buyItem(Upgrades.get(0));
        shopService.buyItem(Upgrades.get(0));
        shopService.buyItem(Upgrades.get(1));

        gameDB.setSelectedCar(gameDB.searchCarAtIndex(2));
        selectedCar = gameDB.getSelectedCar();
        selectedCar.setFuelInTank(selectedCar.getFuelTankCapacity() * 0.5);

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
     * Test to see if equipping an upgrade that increases fuel tank capacity, filling up the tank,
     * then proceed to unequip that same upgrade will leave that car at 100% fuel i.e., get rid
     * of the excess fuel.
     */

    @Test
    void equipUpgradeThatChangesFuelTankCapacityAndUnequipIt() {
        // Equipping Jumbo Fuel Tank
        selectedUpgrade = Upgrades.get(4);
        double ogFuelCapacity = selectedCar.getFuelTankCapacity();
        GarageService.EquipResult result1 = garageService.equipUpgrade(selectedUpgrade, selectedCar);
        assertEquals(GarageService.EquipResult.SUCCESS, result1);
        assertTrue(selectedCar.checkIfUpgradeEquipped(selectedUpgrade));
        assertNotEquals(ogFuelCapacity, selectedCar.calculateFuelTankCapacity());

        // Fill the tank up
        garageService.fillTank(selectedCar);
        assertEquals(selectedCar.getFuelInTank(), selectedCar.calculateFuelTankCapacity());

        // Unequip the upgrade, which should decrease its fuel tank capacity
        GarageService.UnequipResult result2 = garageService.unequipUpgrade(selectedUpgrade, selectedCar);
        assertEquals(GarageService.UnequipResult.SUCCESS, result2);
        assertFalse(selectedCar.checkIfUpgradeEquipped(selectedUpgrade));

        assertEquals(ogFuelCapacity, selectedCar.calculateFuelTankCapacity());
        System.out.println(selectedCar.getFuelInTank());



    }

    /**
     * Test to see if a car tank will be filled up as much as possible if the player doesn't have
     * sufficient funds to filly up the tank
     */
    @Test
    void fillTankWhenYouDontHaveEnoughMoneyToFullyFill() {
        // Fill tank to 50%
        float ogBal = 50;
        gameDB.setBal(ogBal);
        assertEquals(gameDB.getBal(), 50);

        garageService.fillTank(selectedCar);
        assertEquals(selectedCar.getFuelInTank(), selectedCar.getFuelTankCapacity() * 0.5 + ogBal / gameDB.getFuelCostPerLitre());

        assertEquals(gameDB.getBal(), 0);

    }

    /**
     * Test to see if a car tank will be fully filled up if the player has
     * sufficient funds to do so.
     */

    @Test
    void fillTankWhenHaveSufficientFunds() {
        float ogBal = 1000;
        gameDB.setBal(ogBal);
        assertEquals(gameDB.getBal(), 1000);


        garageService.fillTank(selectedCar);
        assertEquals(selectedCar.getFuelInTank(), selectedCar.getFuelTankCapacity());
        assertEquals(gameDB.getBal(), ogBal - selectedCar.getFuelTankCapacity() * 0.5 * gameDB.getFuelCostPerLitre());

    }

    /**
     * Test to see if the player's selected car is updated.
     */

    @Test
    void updateSelectedCar() {
        selectedCar = gameDB.getSelectedCar();
        String originalCarName = selectedCar.getName();

        Car newSelectedCar = Cars.get(0);
        String newCarName = newSelectedCar.getName();
        garageService.updateSelectedCar(newSelectedCar);

        assertEquals(newCarName, gameDB.getSelectedCar().getName());
        assertNotEquals(newCarName, originalCarName);
    }

    /**
     * Test to attempt to unequip an upgrade when no upgrade has been selected
     */

    @Test
    void unequipUpgradeWhenNoUpgradeSelected() {
        selectedUpgrade = null;
        int ogAmountOfEquippedUpgrades = selectedCar.getEquippedUpgrades().size();
        GarageService.UnequipResult result1 = garageService.unequipUpgrade(selectedUpgrade, selectedCar);
        int newAmountOfEquippedUpgrades = selectedCar.getEquippedUpgrades().size();
        assertEquals(GarageService.UnequipResult.UPGRADE_NOT_SELECTED, result1);
        assertEquals(ogAmountOfEquippedUpgrades, newAmountOfEquippedUpgrades);

    }

    /**
     * Test to attempt to equip an upgrade when no upgrade has been selected
     */
    @Test
    void equippingWhenNoUpgradeSelected() {
        selectedUpgrade = null;
        int originalAmountOfEquippedUpgrades = selectedCar.getEquippedUpgrades().size();
        GarageService.EquipResult result1 = garageService.equipUpgrade(selectedUpgrade, selectedCar);
        assertEquals(GarageService.EquipResult.UPGRADE_NOT_SELECTED, result1);
        assertEquals(originalAmountOfEquippedUpgrades, selectedCar.getEquippedUpgrades().size());
    }


    /**
     * Test to unequip an upgrade (if that upgrade has already been equipped by the car)
     */
    @Test
    void unequipUpgradeSuccessfully() {
        equipUpgradeSuccessfullyWhenOwningOneCopy();
        assertTrue(selectedCar.checkIfUpgradeEquipped(selectedUpgrade));
        assertEquals(selectedUpgrade.getNumPurchased(), 0);
        GarageService.UnequipResult result1 = garageService.unequipUpgrade(selectedUpgrade, selectedCar);
        assertEquals(result1, GarageService.UnequipResult.SUCCESS);
        assertFalse(selectedCar.checkIfUpgradeEquipped(selectedUpgrade));
        assertEquals(selectedUpgrade.getNumPurchased(), 1);
        assertTrue(gameDB.selectedItemInCollection(selectedUpgrade));
    }

    /**
     * Test to attempt to equip an upgrade even if it has already been purchased
     */

    @Test
    void equippingAnUpgradeThatIsAlreadyEquipped() {
        equipUpgradeSuccessfullyWhenOwningMultipleCopies();
        GarageService.EquipResult result2 = garageService.equipUpgrade(selectedUpgrade, selectedCar);
        assertEquals(GarageService.EquipResult.UPGRADE_ALREADY_EQUIPPED, result2);
        assertEquals(selectedUpgrade.getNumPurchased(), 1);

    }


    /**
     * Attempt to equip an upgrade for which the user owns multiple lots of (in this case, 2)
     */

    @Test
    void equipUpgradeSuccessfullyWhenOwningMultipleCopies() {
        // Equipping Rocket Fuel
        selectedUpgrade = Upgrades.getFirst();
        int originalAmountOfEquippedUpgrades = selectedCar.getEquippedUpgrades().size();

        double ogSpeed = selectedCar.calculateSpeed(0.0);
        double ogFuelConsumption = selectedCar.calculateFuelConsumption();
        double ogFuelTankCapacity = selectedCar.calculateFuelTankCapacity();
        double ogHandlingScaleFactor = selectedCar.calculateHandling();
        double ogReliabilityScaleFactor = selectedCar.calculateReliability();

        assertFalse(selectedCar.checkIfUpgradeEquipped(selectedUpgrade));
        assertEquals(selectedUpgrade.getNumPurchased(), 2);
        GarageService.EquipResult result1 = garageService.equipUpgrade(selectedUpgrade, selectedCar);
        assertEquals(GarageService.EquipResult.SUCCESS, result1);
        assertTrue(selectedCar.checkIfUpgradeEquipped(selectedUpgrade));
        assertEquals(selectedUpgrade.getNumPurchased(), 1);
        assertNotEquals(originalAmountOfEquippedUpgrades, selectedCar.getEquippedUpgrades().size());
        assertTrue(gameDB.selectedItemInCollection(selectedUpgrade));

        // check if stats have been changed
        assertEquals(ogSpeed * selectedUpgrade.getSpeedMultiplier(), selectedCar.calculateSpeed(0.0));
        assertEquals(ogFuelConsumption * selectedUpgrade.getFuelEfficiencyMultiplier(), selectedCar.calculateFuelConsumption());
        assertEquals(ogFuelTankCapacity * selectedUpgrade.getFuelTankCapacityMultiplier(), selectedCar.calculateFuelTankCapacity());
        assertEquals(ogHandlingScaleFactor * selectedUpgrade.getHandlingMultiplier(), selectedCar.calculateHandling());
        assertEquals(ogReliabilityScaleFactor * selectedUpgrade.getReliabilityMultiplier(), selectedCar.calculateReliability());;



    }

    /**
     * Test to equip more than one upgrade to a car (in this case, equipping rocket fuel and grippy tyres)
     */
    @Test
    void equippingDifferentUpgrades() {
        assertEquals("",selectedCar.upgradesToString());
        // equipping Rocket Fuel first
        equipUpgradeSuccessfullyWhenOwningOneCopy();
        selectedUpgrade = Upgrades.getFirst();

        // equipping Grippy Tyres
        equipUpgradeSuccessfullyWhenOwningMultipleCopies();

        assertEquals(2, selectedCar.getEquippedUpgrades().size());
        assertEquals("Grippy Tyres, Rocket Fuel",selectedCar.upgradesToString());

    }


    /**
     * Test to equip an upgrade for which the player only owns one lot of
     */
    @Test
    void equipUpgradeSuccessfullyWhenOwningOneCopy() {
        // Equipping Grippy Tyres
        selectedUpgrade = Upgrades.get(1);
        int originalAmountOfEquippedUpgrades = selectedCar.getEquippedUpgrades().size();
        assertFalse(selectedCar.checkIfUpgradeEquipped(selectedUpgrade));
        assertEquals(selectedUpgrade.getNumPurchased(), 1);
        GarageService.EquipResult result1 = garageService.equipUpgrade(selectedUpgrade, selectedCar);
        assertEquals(GarageService.EquipResult.SUCCESS, result1);
        assertTrue(selectedCar.checkIfUpgradeEquipped(selectedUpgrade));
        assertEquals(selectedUpgrade.getNumPurchased(), 0);
        assertNotEquals(originalAmountOfEquippedUpgrades, selectedCar.getEquippedUpgrades().size());
        assertFalse(gameDB.selectedItemInCollection(selectedUpgrade));

    }

}
