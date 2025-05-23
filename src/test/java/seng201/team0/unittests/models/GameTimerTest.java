package seng201.team0.unittests.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team0.models.GameTimer;

import static org.junit.jupiter.api.Assertions.*;

public class GameTimerTest {
    private GameTimer timer;

    /**
     * Sets up a fresh GameTimer before each test, with a handler
     * that flips a flag when invoked.
     */
    @BeforeEach
    void setUp() {
        timer = new GameTimer(1.0, event -> onTimer());
    }

    private void onTimer() {
    }

    /**
     * Verifies that negative inputs to the hour-minute second formatter
     * return "None".
     */
    @Test
    void totalSecondsToStringHourMinSecNegativeTest() {
        String result = GameTimer.totalSecondsToStringHourMinSec(-10.0);
        assertEquals("None", result, "Negative input should yield None");
    }

    /**
     * Verifies correct formatting of a positive total second input
     * into "H:MM:SS" form.
     */
    @Test
    void totalSecondsToStringHourMinSec() {
        String result = GameTimer.totalSecondsToStringHourMinSec(3661.0);
        assertEquals("1:01:01", result);
    }

    /**
     * Verifies that negative inputs to the minute second formatter
     * return "None".
     */
    @Test
    void totalSecondsToStringMinSecNegativeTest() {
        String result = GameTimer.totalSecondsToStringMinSec(-1.0);
        assertEquals("None", result);
    }

    /**
     * Verifies correct formatting of a positive total-second input
     * into "MMm SSs" form.
     */
    @Test
    void totalSecondsToStringMinSec() {
        String result = GameTimer.totalSecondsToStringMinSec(125.0);
        assertEquals("02m 05s", result);
    }

    /**
     * Ensures pause and resume calls do not throw exceptions
     * even if the timer hasn’t been started.
     */
    @Test
    void pauseResume() {
        assertDoesNotThrow(timer::pause);
        assertDoesNotThrow(timer::resume);
    }
}