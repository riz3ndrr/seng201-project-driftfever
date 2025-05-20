package seng201.team0.models;

public class RaceParticipant {
    // Properties
    private Car car;
    private String driverName;
    private int entryNumber;
    private RaceRoute route;
    private boolean isPlayer;
    private double distanceTraveledKilometers = 0.0;
    private double finishTimeSeconds = 0.0;
    private boolean isBrokenDown = false;
    private boolean canRepairBreakdown = false;
    private double repairCost;
    private double secondsPaused = 0.0;


    // Constructor
    public RaceParticipant(Car car, String driverName, int entryNumber, boolean isPlayer) {
        this.car = car;
        this.driverName = driverName;
        this.entryNumber = entryNumber;
        this.route = RaceRoute.getRandomRoute();
        this.isPlayer = isPlayer;
    }


    // Getters and setters
    public Car getCar() { return car; }
    public String getDriverName() { return driverName; }
    public int getEntryNumber() { return entryNumber; }
    public RaceRoute getRoute() { return route; }
    public boolean getIsPlayer() { return isPlayer; }
    public double getDistanceTraveledKilometers() { return distanceTraveledKilometers; }
    public void setDistanceTraveledKilometers(double distanceTraveledKilometers) { this.distanceTraveledKilometers = distanceTraveledKilometers; }
    public double getFinishTimeSeconds() { return finishTimeSeconds; }
    public void setFinishTimeSeconds(double finishTimeSeconds) { this.finishTimeSeconds = finishTimeSeconds; }
    public boolean getIsBrokenDown() { return isBrokenDown; }
    public void setIsBrokenDown(boolean isBrokenDown) { this.isBrokenDown = isBrokenDown; }
    public boolean getCanRepairBreakdown() { return canRepairBreakdown; }
    public void setCanRepairBreakdown(boolean canRepairBreakdown) { this.canRepairBreakdown = canRepairBreakdown; }
    public double getRepairCost() { return repairCost; }
    public void setRepairCost(double repairCost) { this.repairCost = repairCost; }
    public double getSecondsPaused() { return secondsPaused; }
    public void setSecondsPaused(double secondsPaused) { this.secondsPaused = secondsPaused; }
}