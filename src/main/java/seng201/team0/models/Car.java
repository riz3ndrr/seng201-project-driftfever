package seng201.team0.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Car extends Item {
    // CHECK IF STATIC IS APPROPRIATE HERE


    private final int carID;

    public Car(String name, int buyingPrice, int sellingPrice, boolean isAvailableToBuy, int speed, int handling, int reliability, int fuelEconomy, String desc, int carID) {
        super(name, buyingPrice, sellingPrice, isAvailableToBuy, speed, handling, reliability, fuelEconomy, desc);
        this.carID = carID;
    }


    public int getCarID() {
        return carID;
    }


    //implement parts later
    // private ArrayList<Part> upgrades
}
