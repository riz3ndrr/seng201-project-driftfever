package seng201.team0.models;

import java.util.ArrayList;

public class RaceParticipant {
    // Properties
    Car car;
    String driverName;
    int entryNumber;
    double distanceTraveledKilometers = 0.0;
    boolean isBrokenDown = false;
    boolean isPaused = false;


    // Constructor
    public RaceParticipant(Car car, String driverName, int entryNumber) {
        this.car = car;
        this.driverName = driverName;
        this.entryNumber = entryNumber;
    }


    // Getters and setters
    public Car getCar() { return car; }
    public String getDriverName() { return driverName; }
    public int getEntryNumber() { return entryNumber; }
    public double getDistanceTraveledKilometers() { return distanceTraveledKilometers; }
    public void setDistanceTraveledKilometers(double distanceTraveledKilometers) { this.distanceTraveledKilometers = distanceTraveledKilometers; }
    public boolean isBrokenDown() { return isBrokenDown; }
    public void setBrokenDown(boolean brokenDown) { isBrokenDown = brokenDown; }
    public boolean isPaused() { return isPaused; }
    public void setPaused(boolean paused) { isPaused = paused; }


    // Logic
    public void progressSimulationByTime(double elapsedGameTimeSeconds, double raceLength, ArrayList<String> commentary) {
        // Check if no fuel or past finish line
        if (car.getFuelInTank() <= 0.0) {
            return;
        }
        if (distanceTraveledKilometers >= raceLength) {
            return;
        }

        // Calculate extra distance
        double speedKilometresPerSecond = car.calculateSpeed() / (60 * 60);
        double distanceInElapsedTime = speedKilometresPerSecond * elapsedGameTimeSeconds;
        distanceTraveledKilometers += distanceInElapsedTime;
        if (distanceTraveledKilometers > raceLength) {
            distanceTraveledKilometers = raceLength;
            commentary.add(String.format("#%d %s has finished the race!", entryNumber, driverName));
        }

        // Calculate remaining fuel in tank
        double fuelConsumptionLitresPerKilometer = car.calculateFuelConsumption();
        double newFuelLitres = car.getFuelInTank() - fuelConsumptionLitresPerKilometer * distanceInElapsedTime;
        if (newFuelLitres < 0.0) {
            newFuelLitres = 0.0;
            commentary.add(String.format("#%d %s has run out of fuel and is out of the race.", entryNumber, driverName));
        }
        car.setFuelInTank(newFuelLitres);
    }

}
