package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;

public class RaceParticipant {
    // Properties
    Car car;
    String driverName;
    int entryNumber;
    boolean isPlayer;
    double distanceTraveledKilometers = 0.0;
    boolean isBrokenDown = false;
    double secondsPaused = 0.0;


    // Constructor
    public RaceParticipant(Car car, String driverName, int entryNumber, boolean isPlayer) {
        this.car = car;
        this.driverName = driverName;
        this.entryNumber = entryNumber;
        this.isPlayer = isPlayer;
    }


    // Getters and setters
    public Car getCar() { return car; }
    public String getDriverName() { return driverName; }
    public int getEntryNumber() { return entryNumber; }
    public boolean getIsPlayer() { return isPlayer; }
    public double getDistanceTraveledKilometers() { return distanceTraveledKilometers; }
    public void setDistanceTraveledKilometers(double distanceTraveledKilometers) { this.distanceTraveledKilometers = distanceTraveledKilometers; }
    public boolean isBrokenDown() { return isBrokenDown; }
    public void setBrokenDown(boolean brokenDown) { isBrokenDown = brokenDown; }
    public void setSecondsPaused(double secondsPaused) { this.secondsPaused = secondsPaused; }


    // Logic
    public void progressSimulationByTime(double elapsedGameTimeSeconds, double raceLength, List<String> commentary) {
        // Check if paused, no fuel, or past finish line
        if (secondsPaused > 0.0) {
            secondsPaused -= elapsedGameTimeSeconds;
            if (secondsPaused <= 0.0) {
                secondsPaused = 0.0;
            }
            return;
        }
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
