package seng201.team0.services;

import seng201.team0.GameManager;
import seng201.team0.gui.ShopController;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Item;
import seng201.team0.models.Upgrade;

public class ShopService {
    // Player/Game Database
    GameStats gameDB = GameManager.getGameStats();



    public enum purchaseResult {
        SUCCESS,
        ALREADY_OWNED,
        INSUFFICIENT_FUNDS,
    }

    public enum sellResult {
        SUCCESS,
        ITEM_NOT_OWNED
    }

    public purchaseResult buyItem(Item selectedItem, String showCarOrUpgrade) {

        if (selectedItem.isPurchased() && showCarOrUpgrade.equals("Car")) {
            return purchaseResult.ALREADY_OWNED;
        }

        if(gameDB.getBal() >= selectedItem.getBuyingPrice()) {
            gameDB.setBal(gameDB.getBal() - selectedItem.getBuyingPrice());


            if (showCarOrUpgrade.equals("Car")) {
                // If buying item is a car
                gameDB.addItem((Car) selectedItem);
                gameDB.setSelectedCar((Car) selectedItem);
            }
            else {
                // If buying item is an upgrade
                if (!selectedItem.isPurchased()) {
                    gameDB.addItem((Upgrade) selectedItem);
                }
                ((Upgrade) selectedItem).incrementNumPurchased();
            }

            selectedItem.setPurchased(true);
            return purchaseResult.SUCCESS;

        }
        else {
            return purchaseResult.INSUFFICIENT_FUNDS;
        }


    }


    public sellResult sellItem(Item selectedItem, String showCarOrUpgrade) {
        if(selectedItem.isPurchased()) {

            gameDB.setBal(gameDB.getBal() + selectedItem.getSellingPrice());



            if (showCarOrUpgrade.equals("Car")) {
                // if selling a car
                selectedItem.setPurchased(false);
                gameDB.removeItem((Car) selectedItem);
            }
            else {
                // if selling an upgrade
                ((Upgrade) selectedItem).decrementNumPurchased();

                if (((Upgrade) selectedItem).getNumPurchased() == 0) {
                    selectedItem.setPurchased(false);
                    gameDB.removeItem((Upgrade) selectedItem);
                }
            }
            return sellResult.SUCCESS;
        }
        else {
            return sellResult.ITEM_NOT_OWNED;

        }
    }



}


