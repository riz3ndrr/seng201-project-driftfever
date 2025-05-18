package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Race {
    // Properties
    private String name;
    private String desc;
    private float distanceKilometers;
    private double curvinessScaleFactor; // 0 = straight, 1 = max curves
    private List<Double> gasStopDistances;
    private float prizeMoney;
    private int timeLimitHours;
    private List<RaceParticipant> participants;
    private RaceCommentary commentary = new RaceCommentary();


    // Constructor
    public Race(String name, String desc, float distance, double curviness, int numGasStops, float prizeMoney, int timeLimit) {
        this.name = name;
        this.desc = desc;
        this.distanceKilometers = distance;
        this.curvinessScaleFactor = curviness;
        this.gasStopDistances = getListOfFuelStopDistances(numGasStops);
        this.prizeMoney = prizeMoney;
        this.timeLimitHours = timeLimit;
        this.participants = new ArrayList<>();
    }


    // Getters and setters
    public float getDistanceKilometers() { return distanceKilometers; }
    public double getCurviness() { return curvinessScaleFactor; }
    public List<Double> getGasStopDistances() { return gasStopDistances; }
    public String getDesc() { return desc; }
    @Override
    public String toString() { return this.name; }
    public float getPrizeMoney() { return prizeMoney; }
    public int getTimeLimitHours() { return timeLimitHours; }
    public String getName() { return name; }
    public List<RaceParticipant> getParticipants() { return participants; }
    public RaceCommentary getCommentary() { return commentary; }


    // Logic
    /**
     * Calculate and obtain a list of distances from the starting point for fuel stops.
     * @param numStops, the number of pit stops a race has.
     * @return a list (which is the size of the number of race stops) and each index will correspond to that
     * stop's distance from the start e.g. [0, 50, 100] so at index 1,
     * it says that the 2nd stop will be 50km away from the start point give or take a random offset.
     */
    public List<Double> getListOfFuelStopDistances(int numStops) {
        List<Double> result = new ArrayList<>();
        double distanceBetweenStops = distanceKilometers / (numStops + 1);
        for (int i = 0; i < numStops; i++) {
            double offsetFactor = 0.2;
            double randomOffset = (Math.random() - 0.5) * distanceBetweenStops * offsetFactor;
            result.add((distanceBetweenStops + randomOffset) * (i + 1));
        }
        return result;
    }

    public boolean isGasStopWithinBounds(double startDistance, double endDistance) {
        for (Double gasStop : gasStopDistances) {
            if (gasStop >= startDistance && gasStop < endDistance) {
                return true;
            }
        }
        return false;
    }


    /**
     * Add a participant to the total list of race competitors
     * @param participant is one of the participants of the race
     */
    public void addParticipant(RaceParticipant participant) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(participants.size() + 1);
        participants.add(randomIndex, participant);
    }

    /**
     * Clear the total list of participants
     */
    public void clearParticipants() {
        participants.clear();
    }
}
