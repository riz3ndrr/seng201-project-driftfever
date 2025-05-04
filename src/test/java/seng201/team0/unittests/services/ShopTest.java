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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ShopTest {
    GameStats gameDB;
    ShopService shopService;
    List<Car> Cars;
    ArrayList<Car> availableCars = new ArrayList<>();

    @BeforeEach
    void init() {
        gameDB = GameManager.getGameStats();
         shopService = new ShopService();
         Cars = GameManager.getCars();


        for (Car car : Cars) {
            if (car.isAvailableToBuy() && !car.isPurchased()) {
                availableCars.add(car);
            }
        }
    }

    // NOTE: tests work individually but not all at once (idk why)
    @Test
    void sellCar() {
        // currently print the balance
        gameDB.setBal(2000);
        Item selectedItem = (Item) availableCars.get(0);
        ShopService.purchaseResult result1 = shopService.buyItem(selectedItem, "Car");
        assertEquals(gameDB.selectedItemInCollection((Car) selectedItem), true);
        ShopService.sellResult result2 = shopService.sellItem(selectedItem, "Car");
        assertFalse(gameDB.selectedItemInCollection((Car) selectedItem));
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
