package seng201.team0.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Car extends Item {
    // CHECK IF STATIC IS APPROPRIATE HERE

    private int speed;          // Changed to int
    private int handling;       // Changed to int
    private int reliability;    // Changed to int
    private int fuelEconomy;    // Changed to int
    private final String desc;
    private final int carID;

    public Car(String name, int buyingPrice, int sellingPrice, boolean isAvailableToBuy, int speed, int handling, int reliability, int fuelEconomy, String desc, int carID) {
        super(name, buyingPrice, sellingPrice, isAvailableToBuy);
        this.speed = speed;
        this.handling = handling;
        this.reliability = reliability;
        this.fuelEconomy = fuelEconomy;
        this.desc = desc;
        this.carID = carID;
    }

    public int getCarID() {
        return carID;
    }




    public String getDesc() {
        return desc;
    }

    public int getSpeed() {
        return speed;
    }
    public int getHandling() {
        return handling;
    }
    public int getReliability() {
        return reliability;
    }
    public int getFuelEconomy() {
        return fuelEconomy;
    }


    //implement parts later
    // private ArrayList<Part> upgrades
}
