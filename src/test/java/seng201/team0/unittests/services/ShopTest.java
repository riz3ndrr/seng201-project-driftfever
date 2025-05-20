package seng201.team0.unittests.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import seng201.team0.GameManager;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Upgrade;
import seng201.team0.services.ShopService;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {
    GameStats gameDB = GameManager.getGameStats();
    ShopService shopService = new ShopService();
    List<Car> Cars = GameManager.getCars();
    List<Upgrade> Upgrades = GameManager.getUpgrades();

    /**
     * After every test, essentially reset the upgrade and cars array so that everything is available to buy and
     * the player owns nothing
     */
    @AfterEach
    void clear() {
        for (Upgrade u : Upgrades) {
            u.setNumPurchased(0);
            u.setPurchased(false);

        }
        gameDB.clearCarCollection();
        gameDB.clearUpgradeCollection();
    }

    /**
     * Test to sell a car if it's the last car you own (the transaction will not go through)
     */

    @Test
    void sellingACarWhenItsTheLastCarYouOwn() {
        // 1300 750
        System.out.println(gameDB.getCarCollectionSize());
        gameDB.setBal(3000);
        assertEquals(3000, gameDB.getBal());

        // buying one car

        Car selectedCar = Cars.get(4);
        assertEquals("Azure", selectedCar.getName());
        assertEquals(1300, selectedCar.getBuyingPrice(1.0));
        assertFalse(selectedCar.isPurchased());
        assertFalse(gameDB.selectedItemInCollection(selectedCar));
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(1700, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection(selectedCar));

        // selling portion
        assertEquals(750, selectedCar.getSellingPrice());
        assertTrue(gameDB.selectedItemInCollection(selectedCar));
        assertTrue(selectedCar.isPurchased());

        ShopService.SellResult result3 = shopService.sellItem(selectedCar);
        assertEquals(ShopService.SellResult.LAST_CAR_OWNED, result3);
        assertEquals(1700, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection(selectedCar));



    }

    /**
     * Test to sell a car if you don't actually own it (obviously will not sell the car)
     */

    @Test
    void sellingACarYouDontOwn() {
        // 1300 750
        // 1400 700
        System.out.println(gameDB.getCarCollectionSize());
        gameDB.setBal(3000);
        assertEquals(3000, gameDB.getBal());

        // buying one car

        Car selectedCar = Cars.get(4);
        assertEquals("Azure", selectedCar.getName());
        assertEquals(1300, selectedCar.getBuyingPrice(1.0));
        assertFalse(selectedCar.isPurchased());
        assertFalse(gameDB.selectedItemInCollection(selectedCar));
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(1700, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection(selectedCar));


        // trying to sell lime wheels
        selectedCar = Cars.get(2);
        assertEquals(850, selectedCar.getSellingPrice());
        assertFalse(gameDB.selectedItemInCollection(selectedCar));
        assertFalse(selectedCar.isPurchased());

        ShopService.SellResult result3 = shopService.sellItem(selectedCar);
        assertEquals(ShopService.SellResult.ITEM_NOT_OWNED, result3);
        assertEquals(1700, gameDB.getBal());
        assertFalse(gameDB.selectedItemInCollection(selectedCar));


    }

    /**
     * Test to sell a car successfully
     */

    @Test
    void sellCarWhenYouOwnIt() {

        System.out.println(gameDB.getCarCollectionSize());
        gameDB.setBal(3000);
        assertEquals(3000, gameDB.getBal());

        // buying one car

        Car selectedCar = Cars.get(4);
        assertEquals("Azure", selectedCar.getName());
        assertEquals(1300, selectedCar.getBuyingPrice(1.0));
        assertFalse(selectedCar.isPurchased());
        assertFalse(gameDB.selectedItemInCollection(selectedCar));
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(1700, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection(selectedCar));

        // buying another

        selectedCar = Cars.get(3);
        assertEquals("Yellow Car", selectedCar.getName());
        assertEquals(1400, selectedCar.getBuyingPrice(1.0));
        assertFalse(selectedCar.isPurchased());
        assertFalse(gameDB.selectedItemInCollection(selectedCar));
        ShopService.PurchaseResult result2 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result2);
        assertEquals(300, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection(selectedCar));



        // selling yellow car portion
        assertEquals(700, selectedCar.getSellingPrice());
        ShopService.SellResult result3 = shopService.sellItem(selectedCar);
        assertEquals(ShopService.SellResult.SUCCESS, result3);
        assertEquals(1000, gameDB.getBal());
        assertFalse(gameDB.selectedItemInCollection(selectedCar));

        selectedCar = Cars.get(4);
        assertTrue(gameDB.selectedItemInCollection(selectedCar));



    }

    /**
     * Purchase an upgrade when your balance is exactly the same as the cost of buying that upgrade
     */


    @Test
    void purchaseUpgradeWhenHaveExactAmount() {
        gameDB.setBal(150);
        assertEquals(150, gameDB.getBal());
        Upgrade selectedUpgrade = Upgrades.get(0);
        assertEquals("Rocket Fuel", selectedUpgrade.getName());
        assertEquals(150, selectedUpgrade.getBuyingPrice(1.0));
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedUpgrade);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(0, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection(selectedUpgrade));
    }



    /**
     * Test to sell an upgrade which you own exactly one of
     */

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
        assertFalse(gameDB.selectedItemInCollection(selectedUpgrade));
        }

    /**
     * Test to sell an upgrade you don't actually own
     */


    @Test
    void sellingAnUpgradeYouDontOwn() {
        purchaseUpgradeWhenHaveMoreThanSufficientBalance();
        assertEquals(850,gameDB.getBal());
        Upgrade selectedUpgrade = Upgrades.get(1);
        assertEquals("Grippy Tyres", selectedUpgrade.getName());
        assertEquals(350, selectedUpgrade.getSellingPrice());
        assertEquals(0, selectedUpgrade.getNumPurchased());
        assertFalse(selectedUpgrade.isPurchased());
        // Selling it
        ShopService.SellResult result2 = shopService.sellItem((selectedUpgrade));
        assertEquals(ShopService.SellResult.ITEM_NOT_OWNED, result2);
        assertEquals(0, selectedUpgrade.getNumPurchased());
        assertEquals(850,gameDB.getBal());
    }


    /**
     * Test to sell an upgrade for which you own multiple of (in this example, 2)
     */
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
        assertEquals(1, selectedUpgrade.getNumPurchased());
        assertEquals(550, gameDB.getBal());
        assertTrue(selectedUpgrade.isPurchased());
        assertTrue(gameDB.selectedItemInCollection(selectedUpgrade));


    }


    /**
     * Test to purchase multiple of the same upgrade successfully
     */


    @Test
    void purchaseMultipleOfSameUpgradeWhenHaveSufficientFunds() {
        gameDB.setBal(1000);
        gameDB.clearUpgradeCollection();
        assertEquals(1000, gameDB.getBal());
        Upgrade selectedUpgrade = Upgrades.get(1);

        assertFalse(gameDB.selectedItemInCollection(selectedUpgrade));
        assertEquals("Grippy Tyres", selectedUpgrade.getName());
        System.out.println(gameDB.getUpgradeCollectionSize());
        assertEquals(0, selectedUpgrade.getNumPurchased());
        assertEquals(400, selectedUpgrade.getBuyingPrice(1.0));
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedUpgrade);

        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(600, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection(selectedUpgrade));

        assertEquals(1, selectedUpgrade.getNumPurchased());
        ShopService.PurchaseResult result2 = shopService.buyItem(selectedUpgrade);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result2);
        assertEquals(200, gameDB.getBal());
        assertEquals(2, selectedUpgrade.getNumPurchased());
    }

    /**
     * Test to purchase an upgrade when you barely don't have enough money.
     * In this case, you're buying Rocket Fuel which costs 150, but you only have 149.
     */

    @Test
    void purchaseUpgradeWhenJustDontHaveEnoughMoney() {
        gameDB.setBal(149);
        assertEquals(149, gameDB.getBal());
        Upgrade selectedUpgrade = Upgrades.get(0);
        assertEquals("Rocket Fuel", selectedUpgrade.getName());
        assertEquals(150, selectedUpgrade.getBuyingPrice(1.0));
        assertFalse(gameDB.selectedItemInCollection(selectedUpgrade));
        assertFalse(selectedUpgrade.isPurchased());
        assertEquals(0, selectedUpgrade.getNumPurchased());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedUpgrade);
        assertEquals(ShopService.PurchaseResult.INSUFFICIENT_FUNDS, result1);
        assertEquals(149, gameDB.getBal());
        assertFalse(gameDB.selectedItemInCollection(selectedUpgrade));
        assertEquals(0, selectedUpgrade.getNumPurchased());


    }

    /**
     * Test to purchase an upgrade when you have more than enough money to purchase one.
     */

    @Test
    void purchaseUpgradeWhenHaveMoreThanSufficientBalance() {
        gameDB.setBal(1000);
        assertEquals(1000, gameDB.getBal());
        Upgrade selectedUpgrade = Upgrades.get(0);
        assertEquals("Rocket Fuel", selectedUpgrade.getName());
        assertEquals(150, selectedUpgrade.getBuyingPrice(1.0));
        assertFalse(gameDB.selectedItemInCollection(selectedUpgrade));
        assertFalse(selectedUpgrade.isPurchased());
        assertEquals(0, selectedUpgrade.getNumPurchased());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedUpgrade);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(850, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection(selectedUpgrade));
        assertEquals(1, selectedUpgrade.getNumPurchased());


    }

    /**
     * Test to purchase a car when you just barely don't have enough to buy it.
     * In this test, we are trying to purchase Yellow Car which has a buying price of $1400,
     * but you only have $1399.
     */

    @Test
    void purchaseCarWhenBarelyShortOfBalance() {
        gameDB.setBal(1399);
        assertEquals(1399, gameDB.getBal());
        Car selectedCar = Cars.get(3);
        assertEquals("Yellow Car", selectedCar.getName());
        assertEquals(1400, selectedCar.getBuyingPrice(1.0));
        assertFalse(selectedCar.isPurchased());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.INSUFFICIENT_FUNDS, result1);
        assertEquals(1399, gameDB.getBal());
        assertFalse(gameDB.selectedItemInCollection(selectedCar));
        assertFalse(selectedCar.isPurchased());
    }

    /**
     * Test to purchase a car when it has already been purchased.
     */

    @Test
    void purchaseCarWhenCarIsAlreadyPurchased() {
        gameDB.setBal(2000);
        assertEquals(2000, gameDB.getBal());
        Car selectedCar = Cars.get(3);
        assertEquals("Yellow Car", selectedCar.getName());
        assertEquals(1400, selectedCar.getBuyingPrice(1.0));
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertTrue(gameDB.selectedItemInCollection(selectedCar));
        assertTrue(selectedCar.isPurchased());
        assertEquals(600, gameDB.getBal());

        ShopService.PurchaseResult result2 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.ALREADY_OWNED, result2);
        assertEquals(600, gameDB.getBal());
        assertTrue(selectedCar.isPurchased());

        //assertFalse(gameDB.selectedItemInCollection((Car) selectedCar));
    }

    /**
     * Test to purchase a car when you have no money at all.
     */

    @Test
    void purchaseCarWhenHaveNoMoney() {
        gameDB.setBal(0);
        assertEquals(0, gameDB.getBal());
        Car selectedCar = Cars.get(3);
        assertEquals("Yellow Car", selectedCar.getName());
        assertEquals(1400, selectedCar.getBuyingPrice(1.0));
        assertFalse(selectedCar.isPurchased());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.INSUFFICIENT_FUNDS, result1);
        assertEquals(0, gameDB.getBal());
        assertFalse(gameDB.selectedItemInCollection(selectedCar));
        assertFalse(selectedCar.isPurchased());
    }



    /**
     * Test to purchase a car when your balance is exactly the same as the cost of buying that car.
     */


    @Test
    void purchaseCarWhenHaveExactAmount() {
        gameDB.setBal(1600);
        assertEquals(1600, gameDB.getBal());
        Car selectedCar = Cars.get(0);
        assertEquals("Purple Car", selectedCar.getName());
        assertEquals(1600, selectedCar.getBuyingPrice(1.0));
        assertFalse(selectedCar.isPurchased());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(0, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection(selectedCar));
        assertTrue(selectedCar.isPurchased());


    }


    /** Test to purchase a car when you have more than enough money to purchase it
     *
     */

    @Test
    void purchaseCarWhenHaveMoreThanSufficientBalance() {
        gameDB.setBal(2000);
        assertEquals(2000, gameDB.getBal());
        Car selectedCar = Cars.get(0);
        assertEquals("Purple Car", selectedCar.getName());
        assertEquals(1600, selectedCar.getBuyingPrice(1.0));
        assertFalse(selectedCar.isPurchased());
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedCar);
        assertEquals(ShopService.PurchaseResult.SUCCESS, result1);
        assertEquals(400, gameDB.getBal());
        assertTrue(gameDB.selectedItemInCollection(selectedCar));
        assertTrue(selectedCar.isPurchased());
    }
}
