package seng201.team0.models;

import java.util.ArrayList;

public class Race {
    // Properties
    private String name;
    private String desc;
    private float distanceKilometers;
    private double curvinessScaleFactor; // 0 = straight, 1 = max curves
    private int numGasStops;
    private ArrayList<Integer> gasStopDistances;
    private float prizeMoney;
    private int timeLimitHours;
    private ArrayList<RaceParticipant> participants;


    // Constructor
    public Race(String name, String desc, float distance, double curviness, int numGasStops, float prizeMoney, int timeLimit) {
        this.name = name;
        this.desc = desc;
        this.distanceKilometers = distance;
        this.curvinessScaleFactor = curviness;
        this.numGasStops = numGasStops;
        this.gasStopDistances = setNumGasStops(numGasStops);
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
    public ArrayList<RaceParticipant> getParticipants() { return participants; }


    // Logic
    public ArrayList<Integer> setNumGasStops(int numStops) {
        //TODO randomise spacing of gas stops
        numGasStops = numStops;
        ArrayList<Integer> result = new ArrayList<>();
        int distance_between_stops = (int) (distanceKilometers / (numGasStops + 1));
        for (int i = 0; i < numGasStops; i++) {
            result.add(distance_between_stops * (i + 1));
        }
        return result;
    }

    public void addParticipant(RaceParticipant participant) {
        participants.add(participant);
    }

    public void clearParticipants() {
        participants.clear();
    }
}
