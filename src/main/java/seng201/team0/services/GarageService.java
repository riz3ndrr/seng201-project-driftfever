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

    public enum FilLResult {
        SUCCESS,
    }


    // Properties
    GameStats gameDB = GameManager.getGameStats();


    // Logic
    public double payableCostToFillTank(Car car) {
        double payable = car.costToFillTank(gameDB.getFuelCostPerLitre());
        if (payable > gameDB.getBal()) {
            payable = gameDB.getBal();
        }
        return payable;
    }

    public void fillTank(Car car) {
        double fuelCost = payableCostToFillTank(car);
        double litres = fuelCost / gameDB.getFuelCostPerLitre();
        car.setFuelInTank(litres + car.getFuelInTank());
        gameDB.setBal(gameDB.getBal() - fuelCost);
    }

    public void updateSelectedCar(Car car) {
        gameDB.setSelectedCar(car);
    }

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

    public UnequipResult unequipUpgrade(Upgrade selectedUpgrade, Car selectedCar) {
        if (selectedUpgrade == null) {
            return UnequipResult.UPGRADE_NOT_SELECTED;
        }
        selectedCar.removeUpgrade(selectedUpgrade);
        if (selectedUpgrade.getNumPurchased() == 0) {
            gameDB.addItem(selectedUpgrade);
        }
        selectedUpgrade.setNumPurchased(selectedUpgrade.getNumPurchased() + 1);
        return UnequipResult.SUCCESS;
    }
}
