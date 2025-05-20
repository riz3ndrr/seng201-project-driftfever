package seng201.team0.models;

public class Purchasable {
    // Properties
    private int itemID;
    private final String name;
    private String desc;
    private final double buyingPrice;
    private final double sellingPrice;
    private boolean availableToBuy; // Determines whether available in the shop (ie. if not already purchased)
    private boolean isPurchased;


    // Constructor
    Purchasable(int itemID, String name, String desc, double buyingPrice, double sellingPrice) {
        this.itemID = itemID;
        this.name = name;
        this.desc = desc;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.isPurchased = false;
    }


    // Getters and setters
    public int getItemID() { return itemID; }
    public String getName() { return name; }
    public String getDesc() { return desc; }
    public double getBuyingPrice(double costMultiplier) { return buyingPrice * costMultiplier; }
    public double getSellingPrice() { return sellingPrice; }
    public void setPurchased(boolean isPurchased) { this.isPurchased = isPurchased;}
    public boolean isPurchased() { return isPurchased;}
}

