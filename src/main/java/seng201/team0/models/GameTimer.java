package seng201.team0.models;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * GameTimer is a class that runs at given framerate and maps
 * real-world time to game time. Game time runs much faster.
 * The class calls a handler many times per second and passes
 * the number of seconds that have elapsed since the last frame.
 */
public class GameTimer {
    // Properties

    // timeSpeedFactor maps real world time to in game time, (eg: 1 actual second is equivalent to 1 minute in the game,
    // so a 10-hour race would take 10 minutes to run. In order to get that timeSpeedFactor should be set to 60.)
    private double timeSpeedFactor;
    private EventHandler<Event> handler;
    private long timeAtLastTick;
    private double secondsElapsed;
    private Timeline timeline;
    private int framerate = 40;


    // Constructor
    /**
     * Create a timer that runs faster than real time based on a scale factor
     * @param timeSpeedFactor Controls how much faster the game timer is compared to real time
     * @param handler This is the code that is called whenever the timer ticks
     */
    public GameTimer(double timeSpeedFactor, EventHandler<Event> handler) {
        this.timeSpeedFactor = timeSpeedFactor;
        this.handler = handler;
    }


    // Getters and setters
    public double getElapsedSecondsInGameTime() { return secondsElapsed; }


    // Logic
    /**
     * Start the timer
     */
    public void start() {
        timeAtLastTick = System.currentTimeMillis();
        Duration interval = Duration.millis(1000.0 / framerate);
        KeyFrame keyFrame = new KeyFrame(interval, event -> onTick());
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Called when the timer fires, at a rate determined by the FPS
     */
    private void onTick() {
        long now = System.currentTimeMillis();
        long elapsedSinceLastTick = now - timeAtLastTick;
        timeAtLastTick = now;
        // Map the actual time to in-game time and notify the controller
        double elapsedInGameTime = elapsedSinceLastTick * timeSpeedFactor;
        secondsElapsed = elapsedInGameTime / 1000.0;
        handler.handle(null);
    }

    /**
     * Pause the timer
     */
    public void pause() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    /**
     * Resume the timer
     */
    public void resume() {
        if (timeline != null) {
            long now = System.currentTimeMillis();
            timeAtLastTick = now;
            timeline.play();
        }
    }

    /**
     * Convert the elapsed time in seconds into hours, minutes and seconds for
     * better reading.
     * @param totalTimeInSeconds a number of seconds
     * @return a string of the format hours:minutes:seconds
     */
    public static String totalSecondsToStringHourMinSec(double totalTimeInSeconds) {
        if (totalTimeInSeconds < 0) {
            return "None";
        }
        int seconds = (int) (totalTimeInSeconds % 60);
        int minutes = (int) ((totalTimeInSeconds / 60) % 60);
        int hours = (int) (totalTimeInSeconds / (60 * 60));
        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Convert the elapsed time in seconds into minutes and seconds for
     * better reading.
     * @param totalTimeInSeconds a number of seconds
     * @return a string of the format minutes:seconds
     */
    public static String totalSecondsToStringMinSec(double totalTimeInSeconds) {
        if (totalTimeInSeconds < 0) {
            return "None";
        }
        int seconds = (int) (totalTimeInSeconds % 60);
        int minutes = (int) (totalTimeInSeconds / 60);
        return String.format("%02dm %02ds", minutes, seconds);
    }
}
