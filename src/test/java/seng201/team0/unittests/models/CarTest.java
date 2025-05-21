package seng201.team0.unittests.models;

import org.junit.jupiter.api.Test;
import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.Upgrade;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarTest {
    /**
     * Test to see if rocket fuel, for which it increases the car's speed,
     * is displayed with a + on the shop
     */
    List<Upgrade> Upgrades = GameManager.getUpgrades();
    List<Car> Cars = GameManager.getCars();
    Car selectedCar = Cars.get(0);


    @Test
    public void testFuelPercentage() {
        // Set car's fuel tank to 70% of full capacity
        selectedCar.setFuelInTank(0.7 * selectedCar.getFuelTankCapacity());
        assertEquals(selectedCar.calculateFuelPercentage(), 70);
    }

    @Test
    public void makeCopy() {
        Car copyCar = selectedCar.makeCopy();
        boolean IsEqualToCopy = selectedCar.equals(copyCar);
        assertEquals(copyCar.getName(), selectedCar.getName());
        assertEquals(copyCar.getSpeed(), selectedCar.getSpeed());
        assertEquals(copyCar.getFuelConsumption(), selectedCar.getFuelConsumption());
        assertEquals(copyCar.getFuelTankCapacity(), selectedCar.getFuelTankCapacity());
        assertEquals(copyCar.getItemID(), selectedCar.getItemID());
        assertEquals(copyCar.getHandlingScaleFactor(), selectedCar.getHandlingScaleFactor());
        assertEquals(copyCar.getReliability(), selectedCar.getReliability());
        assertEquals(copyCar.getEquippedUpgrades(), selectedCar.getEquippedUpgrades());
        assertEquals(copyCar.upgradesToString(), selectedCar.upgradesToString());


    }

}
