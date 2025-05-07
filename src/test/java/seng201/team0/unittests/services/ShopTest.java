package seng201.team0.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.GameManager;
import seng201.team0.gui.ShopController;
import seng201.team0.models.Car;
import seng201.team0.models.GameStats;
import seng201.team0.models.Item;
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


        availableCars = Arrays.asList( new Car("Purple Car", 1600, 800, true, 5, 4, 6, 5, "A balanced car with smooth acceleration and steady handling.", 0),
                new Car("Lightning McQueen", 1550, 850, true, 4, 4, 5, 5, "Kachow!", 1),
                new Car("Lime Wheels", 1500, 850, true, 5, 5, 4, 4, "A versatile car with equal balance between speed and handling.", 2),
                new Car("Yellow Car", 1400, 700, true, 4, 5, 5, 4, "Light and agile, perfect for quick turns and smooth drifting.", 3),
                new Car("Azure", 1300, 750, true, 5, 4, 6, 5, "Durable with solid control and good handling on various surfaces.", 4));
    }

    // NOTE: tests work individually but not all at once (idk why)
    @Test
    void sellCar() {
        // currently print the balance
        gameDB.setBal(2000);
        Item selectedItem = (Item) availableCars.get(0);
        ShopService.purchaseResult result1 = shopService.buyItem(selectedItem, "Car");
//        assertEquals(gameDB.selectedItemInCollection((Car) selectedItem), true);
        ShopService.sellResult result2 = shopService.sellItem(selectedItem, "Car");
        assertTrue(gameDB.selectedItemInCollection((Car) selectedItem));
        assertEquals(1200, gameDB.getBal());
    }



    @Test
    void purchaseCar() {
        // currently print the balance
        gameDB.setBal(2000);
        Item selectedItem = (Item) availableCars.get(0);
        ShopService.purchaseResult result = shopService.buyItem(selectedItem, "Car");
        assertEquals(400, gameDB.getBal());
        assertEquals(gameDB.selectedItemInCollection((Car) selectedItem), true);
    }


}
