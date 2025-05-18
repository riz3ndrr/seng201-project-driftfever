package seng201.team0.unittests.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.GameManager;
import seng201.team0.gui.ShopController;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Purchasable;
import seng201.team0.models.Upgrade;
import seng201.team0.services.ShopService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {
    GameStats gameDB;
    ShopService shopService;
    List<Car> Cars = GameManager.getCars();
    List<Upgrade> Upgrades = GameManager.getUpgrades();
    @BeforeEach
    void init() {
        gameDB = GameManager.getGameStats();
        shopService = new ShopService();
        Cars = GameManager.getCars();
        gameDB.clearCarCollection();
        gameDB.clearUpgradeCollection();
        gameDB.setBal(2000);
    }
    @AfterEach
    void clear() {
        for (Upgrade u : Upgrades) {
            u.setNumPurchased(0);
            u.setPurchased(false);

        }
        gameDB.clearUpgradeCollection();
    }

    @Test
    void sellingACarWhenItsTheLastCarYouOwn() {
        // 1300 750
        System.out.println(gameDB.getCarCollectionSize());
        gameDB.setBal(3000);
        assertEquals(3000, gameDB.getBal());

        // buying one car

        Purchasable selectedCar = Cars.get(4);
        assertEquals("Azure", selectedCar.getName());
        assertEquals(1300, selectedCar.getBuyingPrice());
        assertFalse(selectedCar.isPurchased());
        assertFalse(gameDB.selectedItemInCollection((Car) selectedCar));
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(1700, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection((Car) selectedCar));

        // selling portion
        assertEquals(750, selectedCar.getSellingPrice());
        assertTrue(gameDB.selectedItemInCollection((Car) selectedCar));
        assertTrue(selectedCar.isPurchased());

        ShopService.SellResult result3 = shopService.sellItem(selectedCar);
        assertEquals(ShopService.SellResult.LAST_CAR_OWNED, result3);
        assertEquals(1700, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection((Car) selectedCar));



    }

    @Test
    void sellingACarYouDontOwn() {
        // 1300 750
        // 1400 700
        System.out.println(gameDB.getCarCollectionSize());
        gameDB.setBal(3000);
        assertEquals(3000, gameDB.getBal());

        // buying one car

        Purchasable selectedCar = Cars.get(4);
        assertEquals("Azure", selectedCar.getName());
        assertEquals(1300, selectedCar.getBuyingPrice());
        assertFalse(selectedCar.isPurchased());
        assertFalse(gameDB.selectedItemInCollection((Car) selectedCar));
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(1700, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection((Car) selectedCar));

        // buying another

        selectedCar = Cars.get(3);
        assertEquals("Yellow Car", selectedCar.getName());
        assertEquals(1400, selectedCar.getBuyingPrice());
        assertFalse(selectedCar.isPurchased());
        assertFalse(gameDB.selectedItemInCollection((Car) selectedCar));
        ShopService.PurchaseResult result2 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result2);
        assertEquals(300, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection((Car) selectedCar));



        // trying to sell lime wheels portion
        selectedCar = Cars.get(2);
        assertEquals(850, selectedCar.getSellingPrice());
        assertFalse(gameDB.selectedItemInCollection((Car) selectedCar));
        assertFalse(selectedCar.isPurchased());

        ShopService.SellResult result3 = shopService.sellItem(selectedCar);
        assertEquals(ShopService.SellResult.ITEM_NOT_OWNED, result3);
        assertEquals(300, gameDB.getBal());
        assertFalse(gameDB.selectedItemInCollection((Car) selectedCar));


    }

    @Test
    void sellCarWhenYouOwnIt() {
        // 1300 750
        // 1400 700
        System.out.println(gameDB.getCarCollectionSize());
        gameDB.setBal(3000);
        assertEquals(3000, gameDB.getBal());

        // buying one car

        Purchasable selectedCar = Cars.get(4);
        assertEquals("Azure", selectedCar.getName());
        assertEquals(1300, selectedCar.getBuyingPrice());
        assertFalse(selectedCar.isPurchased());
        assertFalse(gameDB.selectedItemInCollection((Car) selectedCar));
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(1700, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection((Car) selectedCar));

        // buying another

        selectedCar = Cars.get(3);
        assertEquals("Yellow Car", selectedCar.getName());
        assertEquals(1400, selectedCar.getBuyingPrice());
        assertFalse(selectedCar.isPurchased());
        assertFalse(gameDB.selectedItemInCollection((Car) selectedCar));
        ShopService.PurchaseResult result2 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result2);
        assertEquals(300, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection((Car) selectedCar));



        // selling yellow car portion
        assertEquals(700, selectedCar.getSellingPrice());
        ShopService.SellResult result3 = shopService.sellItem(selectedCar);
        assertEquals(ShopService.SellResult.SUCCESS, result3);
        assertEquals(1000, gameDB.getBal());
        assertFalse(gameDB.selectedItemInCollection((Car) selectedCar));

        selectedCar = Cars.get(4);
        assertTrue(gameDB.selectedItemInCollection((Car) selectedCar));



    }


    @Test
    void purchaseUpgradeWhenHaveExactAmount() {
        gameDB.setBal(150);
        assertEquals(150, gameDB.getBal());
        Purchasable selectedUpgrade = (Purchasable) Upgrades.get(0);
        assertEquals("Rocket Fuel", selectedUpgrade.getName());
        assertEquals(150, selectedUpgrade.getBuyingPrice());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedUpgrade);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(0, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection((Upgrade) selectedUpgrade));
    }


//    }
//    @Test
//    void purchaseMultipleUpgradesWhenHaveNotEnoughForSecondUpgrade() {
//        gameDB.setBal(500);
//        assertEquals(500, gameDB.getBal());
//        Purchasable selectedUpgrade = (Purchasable) Upgrades.get(1);
//        assertEquals("Grippy Tyres", selectedUpgrade.getName());
//        assertEquals(400, selectedUpgrade.getBuyingPrice());
//        assertFalse(gameDB.selectedItemInCollection((Upgrade) selectedUpgrade));
//        assertFalse(selectedUpgrade.isPurchased());
//        assertEquals(0, ((Upgrade) selectedUpgrade).getNumPurchased());
//
//        ShopService.PurchaseResult result1 = shopService.buyItem(selectedUpgrade);
//        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
//        assertEquals(100, gameDB.getBal());
//        assertTrue(gameDB.selectedItemInCollection((Upgrade) selectedUpgrade));
//        assertEquals(1, ((Upgrade) selectedUpgrade).getNumPurchased());
//
//        ShopService.PurchaseResult result2 = shopService.buyItem(selectedUpgrade);
//        assertEquals(ShopService.PurchaseResult.INSUFFICIENT_FUNDS, result2);
//        assertEquals(100, gameDB.getBal());
//        assertTrue(gameDB.selectedItemInCollection((Upgrade) selectedUpgrade));
//        assertEquals(1, ((Upgrade) selectedUpgrade).getNumPurchased());
//
// }


    @Test
    void sellingAnUpgradeYouOwnOneOf() {
        Upgrade selectedUpgrade = Upgrades.get(0);
        purchaseUpgradeWhenHaveExactAmount();
        assertEquals(0, gameDB.getBal());
        assertEquals(1, selectedUpgrade.getNumPurchased());

        ShopService.SellResult result1 = shopService.sellItem(selectedUpgrade);
        assertEquals(ShopService.SellResult.SUCCESS, result1);
        assertEquals(120, gameDB.getBal());
        assertEquals(0, selectedUpgrade.getNumPurchased());
        assertFalse(gameDB.selectedItemInCollection((Upgrade) selectedUpgrade));
        }



    @Test
    void sellingAnUpgradeYouDontOwn() {
        purchaseUpgradeWhenHaveMoreThanSufficientBalance();
        assertEquals(850,gameDB.getBal());
        Upgrade selectedUpgrade = Upgrades.get(1);
        assertEquals("Grippy Tyres", selectedUpgrade.getName());
        assertEquals(350, selectedUpgrade.getSellingPrice());
        assertEquals(0, ((Upgrade) selectedUpgrade).getNumPurchased());
        assertFalse(selectedUpgrade.isPurchased());
        // Selling it
        ShopService.SellResult result2 = shopService.sellItem((selectedUpgrade));
        assertEquals(ShopService.SellResult.ITEM_NOT_OWNED, result2);
        assertEquals(0, ((Upgrade) selectedUpgrade).getNumPurchased());
        assertEquals(850,gameDB.getBal());
    }


    @Test
    void sellingAnUpgradeForWhichYouOwnMultipleOf() {

        // buying 2 sets of Grippy Tyres
        purchaseMultipleOfSameUpgradeWhenHaveSufficientFunds();

        assertEquals(200, gameDB.getBal());
        Upgrade selectedUpgrade = Upgrades.get(1);
        assertEquals("Grippy Tyres", selectedUpgrade.getName());
        // Selling one of them
        assertEquals(350, selectedUpgrade.getSellingPrice());
        ShopService.SellResult result3 = shopService.sellItem((selectedUpgrade));
        assertEquals(ShopService.SellResult.SUCCESS, result3);
        assertEquals(1, ((Upgrade) selectedUpgrade).getNumPurchased());
        assertEquals(550, gameDB.getBal());
        assertTrue(selectedUpgrade.isPurchased());
        assertTrue(gameDB.selectedItemInCollection((Upgrade) selectedUpgrade));


    }



    @Test
    void purchaseMultipleOfSameUpgradeWhenHaveSufficientFunds() {
        gameDB.setBal(1000);
        gameDB.clearUpgradeCollection();
        assertEquals(1000, gameDB.getBal());
        Purchasable selectedUpgrade = Upgrades.get(1);

        assertFalse(gameDB.selectedItemInCollection((Upgrade) selectedUpgrade));
        assertEquals("Grippy Tyres", selectedUpgrade.getName());
        System.out.println(gameDB.getUpgradeCollectionSize());
        assertEquals(0, ((Upgrade) selectedUpgrade).getNumPurchased());
        assertEquals(400, selectedUpgrade.getBuyingPrice());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedUpgrade);

        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(600, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection((Upgrade) selectedUpgrade));

        assertEquals(1, ((Upgrade) selectedUpgrade).getNumPurchased());
        ShopService.PurchaseResult result2 = shopService.buyItem(selectedUpgrade);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result2);
        assertEquals(200, gameDB.getBal());
        assertEquals(2, ((Upgrade) selectedUpgrade).getNumPurchased());
    }

    @Test
    void purchaseUpgradeWhenJustDontHaveEnoughMoney() {
        gameDB.setBal(149);
        assertEquals(149, gameDB.getBal());
        Purchasable selectedUpgrade = (Purchasable) Upgrades.get(0);
        assertEquals("Rocket Fuel", selectedUpgrade.getName());
        assertEquals(150, selectedUpgrade.getBuyingPrice());
        assertFalse(gameDB.selectedItemInCollection((Upgrade) selectedUpgrade));
        assertFalse(selectedUpgrade.isPurchased());
        assertEquals(0, ((Upgrade) selectedUpgrade).getNumPurchased());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedUpgrade);
        assertEquals(ShopService.PurchaseResult.INSUFFICIENT_FUNDS, result1);
        assertEquals(149, gameDB.getBal());
        assertFalse(gameDB.selectedItemInCollection((Upgrade) selectedUpgrade));
        assertEquals(0, ((Upgrade) selectedUpgrade).getNumPurchased());


    }

    @Test
    void purchaseUpgradeWhenHaveMoreThanSufficientBalance() {
        gameDB.setBal(1000);
        assertEquals(1000, gameDB.getBal());
        Purchasable selectedUpgrade = (Purchasable) Upgrades.get(0);
        assertEquals("Rocket Fuel", selectedUpgrade.getName());
        assertEquals(150, selectedUpgrade.getBuyingPrice());
        assertFalse(gameDB.selectedItemInCollection((Upgrade) selectedUpgrade));
        assertFalse(selectedUpgrade.isPurchased());
        assertEquals(0, ((Upgrade) selectedUpgrade).getNumPurchased());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedUpgrade);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(850, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection((Upgrade) selectedUpgrade));
        assertEquals(1, ((Upgrade) selectedUpgrade).getNumPurchased());


    }

    @Test
    void purchaseCarWhenBarelyShortOfBalance() {
        gameDB.setBal(1399);
        assertEquals(1399, gameDB.getBal());
        Purchasable selectedCar = (Purchasable) Cars.get(3);
        assertEquals("Yellow Car", selectedCar.getName());
        assertEquals(1400, selectedCar.getBuyingPrice());
        assertFalse(selectedCar.isPurchased());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.INSUFFICIENT_FUNDS, result1);
        assertEquals(1399, gameDB.getBal());
        assertFalse(gameDB.selectedItemInCollection((Car) selectedCar));
        assertFalse(selectedCar.isPurchased());
    }

    @Test
    void purchaseCarWhenCarIsAlreadyPurchased() {
        gameDB.setBal(2000);
        assertEquals(2000, gameDB.getBal());
        Purchasable selectedCar = (Purchasable) Cars.get(3);
        assertEquals("Yellow Car", selectedCar.getName());
        assertEquals(1400, selectedCar.getBuyingPrice());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertTrue(gameDB.selectedItemInCollection((Car) selectedCar));
        assertTrue(selectedCar.isPurchased());
        assertEquals(600, gameDB.getBal());

        ShopService.PurchaseResult result2 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.ALREADY_OWNED, result2);
        assertEquals(600, gameDB.getBal());
        assertTrue(selectedCar.isPurchased());

        //assertFalse(gameDB.selectedItemInCollection((Car) selectedCar));
    }

    @Test
    void purchaseCarWhenHaveNoMoney() {
        gameDB.setBal(0);
        assertEquals(0, gameDB.getBal());
        Purchasable selectedCar = (Purchasable) Cars.get(3);
        assertEquals("Yellow Car", selectedCar.getName());
        assertEquals(1400, selectedCar.getBuyingPrice());
        assertFalse(selectedCar.isPurchased());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.INSUFFICIENT_FUNDS, result1);
        assertEquals(0, gameDB.getBal());
        assertFalse(gameDB.selectedItemInCollection((Car) selectedCar));
        assertFalse(selectedCar.isPurchased());
    }




    @Test
    void purchaseCarWhenHaveExactAmount() {
        gameDB.setBal(1600);
        assertEquals(1600, gameDB.getBal());
        Purchasable selectedCar = (Purchasable) Cars.get(0);
        assertEquals("Purple Car", selectedCar.getName());
        assertEquals(1600, selectedCar.getBuyingPrice());
        assertFalse(selectedCar.isPurchased());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(0, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection((Car) selectedCar));
        assertTrue(selectedCar.isPurchased());


    }




    @Test
    void purchaseCarWhenHaveMoreThanSufficientBalance() {
        gameDB.setBal(2000);
        assertEquals(2000, gameDB.getBal());
        Purchasable selectedCar = (Purchasable) Cars.get(0);
        assertEquals("Purple Car", selectedCar.getName());
        assertEquals(1600, selectedCar.getBuyingPrice());
        assertFalse(selectedCar.isPurchased());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(400, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection((Car) selectedCar));
        assertTrue(selectedCar.isPurchased());
    }


}
