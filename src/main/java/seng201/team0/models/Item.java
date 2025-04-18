package seng201.team0.models;

public class Item {
    private final float buyingPrice;
    private final float sellingPrice;
    private boolean availableToBuy;

    Item(int buyingPrice, int sellingPrice, boolean availableToBuy) {
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.availableToBuy = false;
    }

    public float getBuyingPrice() {
        return buyingPrice;
    }
    public float getSellingPrice() {
        return sellingPrice;
    }

    public boolean isAvailableToBuy() {
        return availableToBuy;
    }
}
