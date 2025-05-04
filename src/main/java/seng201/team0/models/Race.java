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

    public Race(float distance, int curviness, int num_gas_stops, String raceDesc) {
        this.distance = distance;
        this.curviness = curviness;
        this.num_gas_stops = num_gas_stops;
        this.raceDesc = raceDesc;
        gas_stop_distances = initialise_gas_stops();
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
