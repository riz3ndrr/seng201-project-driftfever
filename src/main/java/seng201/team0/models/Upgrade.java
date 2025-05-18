package seng201.team0.models;

public class Upgrade extends Purchasable {
    // Properties
    private double speedMultiplier;
    private double handlingMultiplier;
    private double reliabilityMultiplier;
    private double fuelConsumptionMultiplier;
    private double fuelTankCapacityMultiplier;
    private int numPurchased;


    // Constructor
    public Upgrade(int upgradeID, String name, String desc, int buyingPrice, int sellingPrice,
                   double speedMultiplier, double handlingMultiplier, double reliabilityMultiplier, double fuelConsumptionMultiplier, double fuelTankCapacityMultiplier) {
        super(upgradeID, name, desc, buyingPrice, sellingPrice);
        this.speedMultiplier = speedMultiplier;
        this.handlingMultiplier = handlingMultiplier;
        this.reliabilityMultiplier = reliabilityMultiplier;
        this.fuelConsumptionMultiplier = fuelConsumptionMultiplier;
        this.fuelTankCapacityMultiplier = fuelTankCapacityMultiplier;
        this.numPurchased = 0;
    }


    // Getters and setters
    public double getSpeedMultiplier() { return speedMultiplier; }
    public double getHandlingMultiplier() { return handlingMultiplier; }
    public double getReliabilityMultiplier() { return reliabilityMultiplier; }
    public double getFuelConsumptionMultiplier() { return fuelConsumptionMultiplier; }
    public double getFuelTankCapacityMultiplier() { return fuelTankCapacityMultiplier; }
    public int getNumPurchased() { return numPurchased; }
    public void setNumPurchased(int numPurchased) { this.numPurchased = numPurchased; }


    // Logic

    /**
     * @param multiplier is the stat multiplier that a upgrade has for a particular stat
     * @return a string so that we can display the effect an upgrade has for a particular stat
     */
    public String displayForMultiplier(double multiplier) {
        if (multiplier >= 1.0) {
            return String.format("+%.0f%%", 100.0 * (multiplier - 1.0));
        } else {
            return String.format("-%.0f%%", 100.0 * (1.0 - multiplier));
        }
    }

    // Commented out as upgrades can only be purchased once
    /*
    public void incrementNumPurchased() { numPurchased++; }
    public void decrementNumPurchased() { numPurchased--; }
    public void resetNumPurchased() { numPurchased = 0; }
    public int getNumPurchased() { return numPurchased; }
    */


    //implement parts later
    // private ArrayList<Part> upgrades
}
