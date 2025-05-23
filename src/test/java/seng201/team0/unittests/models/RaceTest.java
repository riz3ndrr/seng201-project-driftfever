package seng201.team0.unittests.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.Race;
import seng201.team0.models.RaceParticipant;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RaceTest {
    private Race race;

    @BeforeEach
    void setUp() {
        race = new Race("Test", "Desc", 120f, 0.4, 4, 2000f, 3.5);
    }

    /**
     * Tests that the constructor properly sets all values for a Race object.
     */
    @Test
    void constructorTest() {
        assertEquals("Test", race.getName());
        assertEquals("Desc", race.getDesc());
        assertEquals(120f, race.getDistanceKilometers());
        assertEquals(0.4, race.getCurviness());
        assertEquals(4, race.getGasStopDistances().size());
        assertEquals(2000f, race.getPrizeMoney());
        assertEquals(3.5, race.getTimeLimitHours());
        assertTrue(race.getParticipants().isEmpty());
    }

    /**
     * Verifies that requesting a list of gas stop distances with 0 stops returns an empty list.
     */
    @Test
    void getListOfFuelStopDistances() {
        List<Double> list = race.getListOfFuelStopDistances(0);
        assertTrue(list.isEmpty());
    }

    /**
     * Verifies that the list of gas stop distances generated contains the correct number of stops,
     * the distances are increasing, and they are within the races total distance.
     */
    @Test
    void setListOfFuelStopDistancesWithinBounds() {
        int stops = 5;
        List<Double> list = race.getListOfFuelStopDistances(stops);
        assertEquals(stops, list.size());
        double prev = 0.0;
        for (double d : list) {
            assertTrue(d > prev, "Distances must increase");
            assertTrue(d <= race.getDistanceKilometers(), "Distances must not exceed race length");
            prev = d;
        }
    }

    /**
     * Tests whether the method correctly identifies when a distance is within a gas stop range.
     */
    @Test
    void isGasStopWithinBounds() {
        double stop = race.getGasStopDistances().get(0);
        assertTrue(race.isGasStopWithinBounds(stop - 0.01, stop + 0.01));
        assertFalse(race.isGasStopWithinBounds(race.getDistanceKilometers() + 1, race.getDistanceKilometers() + 10));
    }

    /**
     * Tests the addition and removal of participants in the race.
     */
    @Test
    void modifyParticipants() {
        RaceParticipant p1 = new RaceParticipant(null, "P1", 1, false);
        RaceParticipant p2 = new RaceParticipant(null, "P2", 2, false);

        race.addParticipant(p1);
        assertEquals(1, race.getParticipants().size());
        assertTrue(race.getParticipants().contains(p1));

        race.addParticipant(p2);
        assertEquals(2, race.getParticipants().size());
        assertTrue(race.getParticipants().contains(p2));

        race.clearParticipants();
        assertTrue(race.getParticipants().isEmpty());
    }

    /**
     * Tests sorting logic of race participants by finish time, and by distance traveled when times are equal.
     * Expected order is fastest finisher first, then by furthest distance if unfinished.
     */
    @Test
    void sortParticipantsByFinishTime() {
        RaceParticipant p1 = new RaceParticipant(null, "P1", 1, false);
        p1.setFinishTimeSeconds(30.0);

        RaceParticipant p2 = new RaceParticipant(null, "P2", 2, false);
        p2.setFinishTimeSeconds(20.0);

        RaceParticipant p3 = new RaceParticipant(null, "P3", 3, false);
        p3.setFinishTimeSeconds(0.0);
        p3.setDistanceTraveledKilometers(80.0);

        RaceParticipant p4 = new RaceParticipant(null, "P4", 4, false);
        p4.setFinishTimeSeconds(0.0);
        p4.setDistanceTraveledKilometers(60.0);

        race.clearParticipants();
        race.addParticipant(p3);
        race.addParticipant(p1);
        race.addParticipant(p4);
        race.addParticipant(p2);

        race.sortParticipantsByFinishTime();
        List<RaceParticipant> sorted = race.getParticipants();

        // Expected order: p2 (20s), p1 (30s), p3 (80km), p4 (60km)
        assertEquals("P2", sorted.get(0).getDriverName());
        assertEquals("P1", sorted.get(1).getDriverName());
        assertEquals("P3", sorted.get(2).getDriverName());
        assertEquals("P4", sorted.get(3).getDriverName());
    }
}
