package seng201.team0.models;

public class Item {
    private final String name;
    private final float buyingPrice;
    private final float sellingPrice;
    private boolean availableToBuy;
    private boolean isPurchased;


    Item(String name, int buyingPrice, int sellingPrice, boolean availableToBuy) {
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

    // determines if will be shown in shop if not already purchased
    public boolean isAvailableToBuy() {
        return availableToBuy;
    }

    public boolean isPurchased() { return isPurchased;}

    public void setPurchased(boolean isPurchased) { this.isPurchased = isPurchased;}

}

