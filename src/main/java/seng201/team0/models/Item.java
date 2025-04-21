package seng201.team0.models;

public class Item {
    private final String name;
    private final float buyingPrice;
    private final float sellingPrice;
    private boolean availableToBuy;
    private boolean isPurchased;

    private int speed;
    private int handling;
    private int reliability;
    private int fuelEconomy;
    private String desc;
    private int upgradeID;


    Item(String name, int buyingPrice, int sellingPrice, boolean availableToBuy, int speed, int handling, int reliability, int fuelEconomy, String desc) {
        this.name = name;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.availableToBuy = availableToBuy;
        this.isPurchased = false;
    }
    public String getName() {
        return name;
    }
    public float getBuyingPrice() {
        return buyingPrice;
    }
    public float getSellingPrice() {
        return sellingPrice;
    }

    public int getCarID() {
        return upgradeID;
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


    // determines if will be shown in shop if not already purchased
    public boolean isAvailableToBuy() {
        return availableToBuy;
    }

    public boolean isPurchased() { return isPurchased;}

    public void setPurchased(boolean isPurchased) { this.isPurchased = isPurchased;}

}

