package seng201.team0.models;

public class Car extends Item {
    // CHECK IF STATIC IS APPROPRIATE HERE
    private final String name;
    private int speed;          // Changed to int
    private int handling;       // Changed to int
    private int reliability;    // Changed to int
    private int fuelEconomy;    // Changed to int
    private final String desc;

    public Car(int buyingPrice, int sellingPrice, boolean isAvailableToBuy, String name, int speed, int handling, int reliability, int fuelEconomy, String desc) {
        super(buyingPrice, sellingPrice, isAvailableToBuy);
        this.name = name;
        this.speed = speed;
        this.handling = handling;
        this.reliability = reliability;
        this.fuelEconomy = fuelEconomy;
        this.desc = desc;
    }

    public String getName() {
        return name;
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
