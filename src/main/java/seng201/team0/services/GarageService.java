package seng201.team0.services;

import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Upgrade;

public class GarageService {
    // Enums
    public enum UnequipResult {
        UPGRADE_NOT_SELECTED,
        SUCCESS
    }

    public enum EquipResult {
        UPGRADE_NOT_SELECTED,
        SUCCESS,
        UPGRADE_ALREADY_EQUIPPED
    }



    // Properties
    GameStats gameDB = GameManager.getGameStats();


    // Logic

    public boolean fixCar(Car car) {
        if (gameDB.getBal() >= 500) {
            gameDB.setBal(gameDB.getBal() - 500);
            car.setBrokenDown(false);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Figure out the cost to fill up a car's tank.
     * If the amount to fully fill up a car's tank is greater than the current
     * balance of the user, then it will return the user's balance (it will use all of the player's
     * money to fill up the tank as much as possible)
     * @param car refers to the currently displayed car on screen
     * @return the cost to fully fill up the car's tank
     */
    public double payableCostToFillTank(Car car) {
        double payable = car.costToFillTank(gameDB.getFuelCostPerLitre());
        if (payable > gameDB.getBal()) {
            payable = gameDB.getBal();
        }
        return payable;
    }

    /**
     * Fill the car's fuel tank as much as possible.
     * @param car refers to the currently displayed car on screen
     */

    public void fillTank(Car car) {
        double fuelCost = payableCostToFillTank(car);
        double litres = fuelCost / gameDB.getFuelCostPerLitre();
        car.setFuelInTank(litres + car.getFuelInTank());
        gameDB.setBal(gameDB.getBal() - fuelCost);
    }

    /** Set the player's car for racing to a particular car.
     * @param car refers to the newly selected car to be used for racing
     */
    public void updateSelectedCar(Car car) {
        gameDB.setSelectedCar(car);
    }

    /**
     * Attempt to equip a selected upgrade onto the currently displayed car
     * @param selectedUpgrade refers to the upgrade the player wishes to equip
     * @param selectedCar refers to the car the player wishes to equip an upgrade on
     * @return the result of trying to equip an upgrade (which will determine how the controller class
     * will operate)
     */
    public EquipResult equipUpgrade (Upgrade selectedUpgrade, Car selectedCar) {
        if (selectedUpgrade == null) {
            return EquipResult.UPGRADE_NOT_SELECTED;
        }
        if (selectedCar.checkIfUpgradeEquipped(selectedUpgrade)) {
            return EquipResult.UPGRADE_ALREADY_EQUIPPED;
        } else {
            selectedUpgrade.setNumPurchased(selectedUpgrade.getNumPurchased() - 1);
            if (selectedUpgrade.getNumPurchased() == 0) {
                gameDB.removeItem(selectedUpgrade);
            }
            selectedCar.addUpgrade(selectedUpgrade);
            return EquipResult.SUCCESS;
        }
    }

    /**
     * Attempt to unequip a selected upgrade onto the currently displayed car
     * @param selectedUpgrade refers to the upgrade the player wishes to unequip
     * @param selectedCar refers to the car the player wishes to unequip a particular upgrade
     * @return the result of trying to unequip an upgrade (which will determine how the controller class
     * will operate)
     */
    public UnequipResult unequipUpgrade(Upgrade selectedUpgrade, Car selectedCar) {
        if (selectedUpgrade == null) {
            return UnequipResult.UPGRADE_NOT_SELECTED;
        }
        selectedCar.removeUpgrade(selectedUpgrade);

        // If the unequipped upgrade increased tank capacity
        if (selectedCar.getFuelInTank() > selectedCar.getFuelTankCapacity()) {
            selectedCar.setFuelInTank(selectedCar.getFuelTankCapacity());
        }

        if (selectedUpgrade.getNumPurchased() == 0) {
            gameDB.addItem(selectedUpgrade);
        }
        selectedUpgrade.setNumPurchased(selectedUpgrade.getNumPurchased() + 1);
        return UnequipResult.SUCCESS;
    }
}
