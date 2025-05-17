package seng201.team0.services;

import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Purchasable;
import seng201.team0.models.Upgrade;

import java.util.ArrayList;
import java.util.List;

public class ShopService {
    // Enums
    // make it private / capital
    public enum PurchaseResult {
        SUCCESS,
        ALREADY_OWNED,
        INSUFFICIENT_FUNDS,
    }
    public enum SellResult {
        SUCCESS,
        ITEM_NOT_OWNED
    }


    // Properties
    GameStats gameDB = GameManager.getGameStats();


    // Logic

    /**
     * Attempt to purchase an item and return a result to the shop's controller class depending on if
     * the user has sufficient funds, the item can be purchased or is already owned.
     * @param selectedItem refers to the currently displayed item that the user wishes to buy
     * @return the result of the purchase attempt which will affect how the shop controller class operates
     */
    public PurchaseResult buyItem(Purchasable selectedItem) {
        boolean isCar = selectedItem instanceof Car;
        boolean canPay = gameDB.getBal() >= selectedItem.getBuyingPrice();
        if (isCar && selectedItem.isPurchased()) {
            return PurchaseResult.ALREADY_OWNED;
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
            return PurchaseResult.SUCCESS;
        }
        return PurchaseResult.INSUFFICIENT_FUNDS;
    }


    /**
     * Attempt to sell an item and return a result to the shop's controller class depending on if
     * the user has purchased the item or not.
     * @param selectedItem refers to the currently displayed item that the user wishes to buy
     * @return the result of the sell attempt which will affect how the shop controller class operates
     */
    public SellResult sellItem(Purchasable selectedItem) {
        boolean isCar = selectedItem instanceof Car;
        boolean canSell = selectedItem.isPurchased();
        if (canSell) {
            gameDB.setBal(gameDB.getBal() + selectedItem.getSellingPrice());
            if (isCar) {
                Car car = (Car) selectedItem;

                // Unequips any upgrades from the car
                for (Upgrade upgrade : car.getEquippedUpgrades()) {
                    if (upgrade.getNumPurchased() == 0) {
                        gameDB.addItem(upgrade);
                    }
                    upgrade.setNumPurchased(upgrade.getNumPurchased() + 1);

                }
                car.clearUpgradeCollection();

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
            return SellResult.SUCCESS;
        }
        return SellResult.ITEM_NOT_OWNED;
    }
}