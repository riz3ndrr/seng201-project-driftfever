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
    private int itemID;

    Item(String name, int buyingPrice, int sellingPrice, boolean availableToBuy, int speed, int handling, int reliability, int fuelEconomy, String desc, int itemID) {
        this.name = name;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.availableToBuy = availableToBuy;
        this.isPurchased = false;
        this.speed = speed;
        this.handling = handling;
        this.reliability = reliability;
        this.fuelEconomy = fuelEconomy;
        this.desc = desc;
        this.itemID = itemID;

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

    public int getItemID() {
        return itemID;
    }
}

