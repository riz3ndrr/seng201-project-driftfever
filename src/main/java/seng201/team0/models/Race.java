package seng201.team0.models;

import java.util.ArrayList;
import java.util.List;

public class Race {
    // Properties
    private String name;
    private String desc;
    private float distanceKilometers;
    private double curvinessScaleFactor; // 0 = straight, 1 = max curves
    private int numGasStops;
    private List<Integer> gasStopDistances;
    private float prizeMoney;
    private int timeLimitHours;
    private List<RaceParticipant> participants;


    // Constructor
    public Race(String name, String desc, float distance, double curviness, int numGasStops, float prizeMoney, int timeLimit) {
        this.name = name;
        this.desc = desc;
        this.distanceKilometers = distance;
        this.curvinessScaleFactor = curviness;
        this.numGasStops = numGasStops;
        this.gasStopDistances = getListOfFuelStopDistances(numGasStops);
        this.prizeMoney = prizeMoney;
        this.timeLimitHours = timeLimit;
        this.participants = new ArrayList<>();
    }


    // Getters and setters
    public float getDistanceKilometers() {
        return distanceKilometers;
    }
    public double getCurviness() {
        return curvinessScaleFactor;
    }
    public int getNumGasStops() {
        return numGasStops;
    }
    public List<Integer> getGasStopDistances() { return gasStopDistances; }
    public String getDesc() { return desc; }
    @Override
    public String toString() {
        return this.name;
    }
    public float getPrizeMoney() {
        return prizeMoney;
    }
    public int getTimeLimitHours() {
        return timeLimitHours;
    }
    public String getName() {
        return name;
    }
    public List<RaceParticipant> getParticipants() { return participants; }


    // Logic

    /**
     * Calculate and obtain a list of distances from the starting point for fuel stops.
     * @param numStops, the number of pit stops a race has.
     * @return a list (which is the size of the number of race stops) and each index will correspond to that
     * stop's distance from the start e.g. [0, 50, 100] so at index 1,
     * it says that the 2nd stop will be 50km away from the start point
     */
    public List<Integer> getListOfFuelStopDistances(int numStops) {
        //TODO randomise spacing of gas stops
        numGasStops = numStops;
        List<Integer> result = new ArrayList<>();
        int distance_between_stops = (int) (distanceKilometers / (numGasStops + 1));
        for (int i = 0; i < numGasStops; i++) {
            result.add(distance_between_stops * (i + 1));
        }
        return result;
    }


    /**
     * Add a participant to the total list of race competitors
     * @param participant is one of the participants of the race
     */
    public void addParticipant(RaceParticipant participant) {
        participants.add(participant);
    }

    /**
     * Clear the total list of participants
     */
    public void clearParticipants() {
        participants.clear();
    }
}
