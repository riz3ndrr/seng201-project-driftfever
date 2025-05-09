package seng201.team0.services;

import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Upgrade;

public class GarageService {
    // Player/Game Database
    GameStats gameDB = GameManager.getGameStats();

    public enum unequipResult {
        UPGRADE_NOT_SELECTED,
        SUCCESS
    }

    public enum equipResult {
        UPGRADE_NOT_SELECTED,
        SUCCESS,
        UPGRADE_ALREADY_EQUIPPED
    }

    public enum filLResult {
        SUCCESS,

    }

    public void fillTank(Car selectedCar) {
        float fuelCost = (100 - selectedCar.getFuel()) * 3;
        if (fuelCost > gameDB.getBal()) {
            fuelCost = gameDB.getBal();
            selectedCar.setFuel(selectedCar.getFuel() + fuelCost / 3);
        }
        else {
            selectedCar.setFuel(100);
        }
        gameDB.setBal(gameDB.getBal() - fuelCost);

    }

    public void updateSelectedCar(Car car) {
        gameDB.setSelectedCar(car);
    }

    public equipResult equipUpgrade (Upgrade selectedUpgrade, Car selectedCar) {
        if (selectedUpgrade == null) {
            return equipResult.UPGRADE_NOT_SELECTED;
        }
        if (selectedCar.checkIfUpgradeEquipped(selectedUpgrade)) {
            return equipResult.UPGRADE_ALREADY_EQUIPPED;
        }
        else {
            selectedUpgrade.decrementNumPurchased();

            if (selectedUpgrade.getNumPurchased() == 0) {
                gameDB.removeItem(selectedUpgrade);
            }

            selectedCar.addEquippedUpgrade(selectedUpgrade);
            selectedCar.changeSpeed(selectedUpgrade.getSpeed());
            selectedCar.changeHandling(selectedUpgrade.getHandling());
            selectedCar.changeReliability(selectedUpgrade.getReliability());
            selectedCar.changeFuelEconomy(selectedUpgrade.getFuelEconomy());
            return equipResult.SUCCESS;
        }
    }


    public unequipResult unequipUpgrade(Upgrade selectedUpgrade, Car selectedCar) {
        if (selectedUpgrade == null) {
            return unequipResult.UPGRADE_NOT_SELECTED;
        }

        selectedCar.removeEquippedUpgrade(selectedUpgrade);

        if (selectedUpgrade.getNumPurchased() == 0) {
            gameDB.addItem(selectedUpgrade);
        }
        selectedUpgrade.incrementNumPurchased();
        selectedCar.changeSpeed(-selectedUpgrade.getSpeed());
        selectedCar.changeHandling(-selectedUpgrade.getHandling());
        selectedCar.changeReliability(-selectedUpgrade.getReliability());
        selectedCar.changeFuelEconomy(-selectedUpgrade.getFuelEconomy());

        return unequipResult.SUCCESS;
    }
}
