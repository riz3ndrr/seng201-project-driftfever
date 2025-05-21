package seng201.team0.unittests.models;

import seng201.team0.GameManager;
import seng201.team0.models.Upgrade;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpgradeTest {
    List<Upgrade> Upgrades = GameManager.getUpgrades();
    // Testing with Rocket Fuel which has a speed multiplier
    // of 1.2, a fuel consumption multiplier of 0.9 and a
    // reliability multiplier of 1.0
    Upgrade rocketFuel = Upgrades.getFirst();
    double positiveMultiplier = rocketFuel.getSpeedMultiplier();
    double negativeMultiplier = rocketFuel.getFuelEfficiencyMultiplier();
    double neutralMultiplier = rocketFuel.getReliabilityMultiplier();


    /**
     * Test to see if rocket fuel, for which it increases the car's speed,
     * is displayed with a + on the shop
     */
    @Test
    public void testPositiveMultiplierIsBeingDisplayedCorrectly() {
        String displayedUpgradeEffect = rocketFuel.displayForMultiplier(positiveMultiplier);
        assertEquals('+', displayedUpgradeEffect.charAt(0));
    }

    /**
     * Test to see if rocket fuel, for which it has no effect on the car's reliablity,
     * is displayed with a + on the shop
     */
    @Test
    public void testNeutralMultiplierIsBeingDisplayedCorrectly() {
        String displayedUpgradeEffect = rocketFuel.displayForMultiplier(neutralMultiplier);
        assertEquals('+', displayedUpgradeEffect.charAt(0));
    }

    /**
     * Test to see if rocket fuel, for which it decreases the car's fuel consumption,
     * is displayed with a - on the shop
     */
    @Test
    public void testNegativeMultiplierIsBeingDisplayedCorrectly() {
        String displayedUpgradeEffect = rocketFuel.displayForMultiplier(negativeMultiplier);
        assertEquals('-', displayedUpgradeEffect.charAt(0));
    }
}
