package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.GameManager;
import seng201.team0.gui.ShopController;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Purchasable;
import seng201.team0.services.ShopService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {
    GameStats gameDB;
    ShopService shopService;
    List<Car> Cars;
    List<Car> availableCars;

    @BeforeEach
    void init() {
        gameDB = GameManager.getGameStats();
         shopService = new ShopService();
         Cars = GameManager.getCars();


        availableCars = Arrays.asList( new Car(0, "Purple Car", "A balanced car with smooth acceleration and steady handling.", 1600, 800, true, 5, 4, 6, 5, 0),
                new Car(1,"Lightning McQueen", "Kachow!", 1550, 850, true, 4, 4, 5, 5, 0),
                new Car(2, "Lime Wheels", "A versatile car with equal balance between speed and handling.", 1500, 850, true, 5, 5, 4, 4, 0),
                new Car(3, "Yellow Car", "Light and agile, perfect for quick turns and smooth drifting.", 1400, 700, true, 4, 5, 5, 4, 0),
                new Car(4, "Azure", "Durable with solid control and good handling on various surfaces.", 1300, 750, true, 5, 4, 6, 5, 0));
    }

    // NOTE: tests work individually but not all at once (idk why)
    @Test
    void sellCar() {
        // currently print the balance
        gameDB.setBal(2000);
        Purchasable selectedItem = (Purchasable) availableCars.get(0);
        ShopService.PurchaseResult result1 = shopService.buyItem(selectedItem);
//        assertEquals(gameDB.selectedItemInCollection((Car) selectedItem), true);
        ShopService.SellResult result2 = shopService.sellItem(selectedItem);
        assertTrue(gameDB.selectedItemInCollection((Car) selectedItem));
        assertEquals(1200, gameDB.getBal());
    }



    @Test
    void purchaseCar() {
        // currently print the balance
        gameDB.setBal(2000);
        Purchasable selectedItem = (Purchasable) availableCars.get(0);
        ShopService.PurchaseResult result = shopService.buyItem(selectedItem);
        assertEquals(400, gameDB.getBal());
        assertEquals(gameDB.selectedItemInCollection((Car) selectedItem), true);
    }


}
