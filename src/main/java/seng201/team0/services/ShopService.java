package seng201.team0.services;

import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Purchasable;
import seng201.team0.models.Upgrade;

public class ShopService {
    // Enums
    public enum purchaseResult {
        SUCCESS,
        ALREADY_OWNED,
        INSUFFICIENT_FUNDS,
    }
    public enum sellResult {
        SUCCESS,
        ITEM_NOT_OWNED
    }


    // Properties
    GameStats gameDB = GameManager.getGameStats();


    // Logic
    public purchaseResult buyItem(Purchasable selectedItem) {
        boolean isCar = selectedItem instanceof Car;
        boolean canPay = gameDB.getBal() >= selectedItem.getBuyingPrice();
        if (isCar && selectedItem.isPurchased()) {
            return purchaseResult.ALREADY_OWNED;
        }
        if (canPay) {
            gameDB.setBal(gameDB.getBal() - selectedItem.getBuyingPrice());
            if (isCar) {
                gameDB.addItem(selectedItem);
                selectedItem.setPurchased(true);
                if (gameDB.getSelectedCar() == null) {
                    // If no car has been previously selected (via the Garage), will set the selected car for racing
                    // to be the first purchased car
                    gameDB.setSelectedCar((Car) selectedItem);
                }
            } else {
                Upgrade upgrade = (Upgrade) selectedItem;
                if (!upgrade.isPurchased()) {
                    gameDB.addItem(upgrade);
                    selectedItem.setPurchased(true);
                }
                upgrade.setNumPurchased(upgrade.getNumPurchased() + 1);
            }
            return purchaseResult.SUCCESS;
        }
        return purchaseResult.INSUFFICIENT_FUNDS;
    }

    public sellResult sellItem(Purchasable selectedItem) {
        boolean isCar = selectedItem instanceof Car;
        boolean canSell = selectedItem.isPurchased();
        if (canSell) {
            gameDB.setBal(gameDB.getBal() + selectedItem.getSellingPrice());
            if (isCar) {
                selectedItem.setPurchased(false);
                gameDB.removeItem(selectedItem);
            } else {
                Upgrade upgrade = (Upgrade) selectedItem;
                if (upgrade.getNumPurchased() > 1) {
                    upgrade.setNumPurchased(upgrade.getNumPurchased() - 1);
                } else {
                    upgrade.setNumPurchased(0);
                    selectedItem.setPurchased(false);
                    gameDB.removeItem(selectedItem);
                }
            }
            return sellResult.SUCCESS;
        }
        return sellResult.ITEM_NOT_OWNED;
    }
}