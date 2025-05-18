package seng201.team0.models;

import seng201.team0.GameManager;

import java.util.List;

public class RaceParticipant {
    // Properties
    private Car car;
    private String driverName;
    private int entryNumber;
    private boolean isPlayer;
    private double distanceTraveledKilometers = 0.0;
    private boolean isBrokenDown = false;
    private boolean canRepairBreakdown = false;
    private double secondsPaused = 0.0;
    private GameStats gameDB = GameManager.getGameStats();


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
    public void progressSimulationByTime(double elapsedGameTimeSeconds, double raceLength, List<RaceComment> commentary) {
        // If paused reduce the timer and wait.
        if (secondsPaused > 0.0) {
            secondsPaused -= elapsedGameTimeSeconds;
            if (secondsPaused <= 0.0) {
                secondsPaused = 0.0;
                if (isBrokenDown && canRepairBreakdown) {
                    isBrokenDown = false;
                    commentary.add(new RaceComment(this, "Successfully repaired their car."));
                }
            }
            return;
        }
        // If out of fuel, broken down, or finished, do nothing.
        if (car.getFuelInTank() <= 0.0 || isBrokenDown || distanceTraveledKilometers >= raceLength) {
            return;
        }

        // Calculate extra distance
        double speedKilometresPerSecond = car.calculateSpeed() / (60 * 60);
        double distanceInElapsedTime = speedKilometresPerSecond * elapsedGameTimeSeconds;
        distanceTraveledKilometers += distanceInElapsedTime;
        if (distanceTraveledKilometers > raceLength) {
            distanceTraveledKilometers = raceLength;
            commentary.add(new RaceComment(this, "Finished the race!"));
        }

        // Calculate remaining fuel in tank
        double fuelConsumptionLitresPerKilometer = car.calculateFuelConsumption();
        double newFuelLitres = car.getFuelInTank() - fuelConsumptionLitresPerKilometer * distanceInElapsedTime;
        if (newFuelLitres < 0.0) {
            newFuelLitres = 0.0;
            commentary.add(new RaceComment(this,"Ran out of fuel and is out of the race."));
        }
        car.setFuelInTank(newFuelLitres);

        // Calculate breakdowns
        double chanceOfBreakdown = (1.0 - car.getReliability()) * distanceInElapsedTime;
        boolean didBreakdown = Math.random() < chanceOfBreakdown;
        if (didBreakdown) {
            isBrokenDown = true;
            canRepairBreakdown = Math.random() < gameDB.getOpponentRepairProbability();
            if (canRepairBreakdown) {
                secondsPaused = gameDB.getMinRepairTimeSeconds() + Math.random() * (gameDB.getMaxRepairTimeSeconds() - gameDB.getMinRepairTimeSeconds()); // Random repair time between 15 and 30 minutes
                commentary.add(new RaceComment(this, String.format("Car has broken down and will take %s to fix.", GameTimer.totalSecondsToStringMinSec(secondsPaused))));
            } else {
                commentary.add(new RaceComment(this, "Car has permanently broken down and is out of the race!"));
            }
        }
    }
}