package seng201.team0.models;

import java.util.ArrayList;

public class Race {
    // distance in km
    private float distance;
    // curviness ranges from 1 to 5
    private int curviness;
    private int num_gas_stops;
    private ArrayList<Integer> gas_stop_distances;
    private String raceDesc;
    private float prizeMoney;
    private int timeLimit;
    private String name;

    public Race(float distance, int curviness, int num_gas_stops, String raceDesc, float prizeMoney, int timeLimit, String name) {
        this.distance = distance;
        this.curviness = curviness;
        this.num_gas_stops = num_gas_stops;
        this.raceDesc = raceDesc;
        this.prizeMoney = prizeMoney;
        this.timeLimit = timeLimit;
        this.name = name;
        gas_stop_distances = initialise_gas_stops();

    }

    public float getDistance() {
        return distance;
    }

    public int getCurviness() {
        return curviness;
    }

    public int getNumGasStops() {
        return num_gas_stops;
    }

    public String getRaceDesc() {
        return raceDesc;
    }

    public float getPrizeMoney() {
        return prizeMoney;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> initialise_gas_stops() {
        ArrayList<Integer> result = new ArrayList<>();
        int distance_between_stops = (int) (distance / (num_gas_stops + 1));
        for (int i = 0; i < num_gas_stops; i++) {
            result.add(distance_between_stops * (i + 1));
        }

        return result;
    }
}
