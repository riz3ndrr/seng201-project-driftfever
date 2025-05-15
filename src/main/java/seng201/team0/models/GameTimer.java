package seng201.team0.models;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class GameTimer {
    // Properties

    // timeSpeedFactor maps real world time to in game time, (eg: 1 actual second is equivalent to 1 minute in the game,
    // so a 10-hour race would take 10 minutes to run. In order to get that timeSpeedFactor should be set to 60.)
    private double timeSpeedFactor;
    private EventHandler<Event> handler;
    private long timeAtLastTick;
    private double secondsElapsed;
    private Timeline timeline;


    // Constructor
    public GameTimer(double timeSpeedFactor, EventHandler<Event> handler) {
        this.timeSpeedFactor = timeSpeedFactor;
        this.handler = handler;
    }


    // Getters and setters
    public double getElapsedSecondsInGameTime() { return secondsElapsed; }


    // Logic
    public void start() {
        timeAtLastTick = System.currentTimeMillis();
        Duration interval = Duration.millis(100);
        KeyFrame keyFrame = new KeyFrame(interval, event -> onTick());
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void onTick() {
        long now = System.currentTimeMillis();
        long elapsedSinceLastTick = now - timeAtLastTick;
        timeAtLastTick = now;
        // Map the actual time to in-game time and notify the controller
        double elapsedInGameTime = elapsedSinceLastTick * timeSpeedFactor;
        secondsElapsed = elapsedInGameTime / 1000.0;
        handler.handle(null);
    }

    public void pause() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    public static String totalSecondsToString(double totalTimeInSeconds) {
        if (totalTimeInSeconds < 0) {
            return "None";
        }
        int seconds = (int) (totalTimeInSeconds % 60);
        int minutes = (int) ((totalTimeInSeconds / 60) % 60);
        int hours = (int) (totalTimeInSeconds / (60 * 60));
        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }
}
