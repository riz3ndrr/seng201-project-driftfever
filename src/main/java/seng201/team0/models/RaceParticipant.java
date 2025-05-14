package seng201.team0.models;

public class RaceParticipant {
    // Properties
    Car car;
    int entryNumber;
    double distanceTraveledKilometers = 0.0;
    boolean isBrokenDown = false;
    boolean isPaused = false;



    // Constructor
    public RaceParticipant(Car car, int entryNumber) {
        this.car = car;
        this.entryNumber = entryNumber;
    }


    // Getters and setters
    public Car getCar() { return car; }
    public int getEntryNumber() { return entryNumber; }
    public double getDistanceTraveledKilometers() { return distanceTraveledKilometers; }
    public void setDistanceTraveledKilometers(double distanceTraveledKilometers) { this.distanceTraveledKilometers = distanceTraveledKilometers; }
    public boolean isBrokenDown() { return isBrokenDown; }
    public void setBrokenDown(boolean brokenDown) { isBrokenDown = brokenDown; }
    public boolean isPaused() { return isPaused; }
    public void setPaused(boolean paused) { isPaused = paused; }


}
