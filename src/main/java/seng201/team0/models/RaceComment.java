package seng201.team0.models;


import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;


public class RaceComment {
    // Properties
    RaceParticipant participant;
    String message;


    // Constructor
    public RaceComment(RaceParticipant participant, String message) {
        this.participant = participant;
        this.message = message;
    }


    // Getters and setters
    public RaceParticipant getParticipant() { return participant; }


    // Logic

    /**
     * Display a message regarding a particular player and an event involving them e.g. "Driver has stop to refuel"
     *
     * @return an HBox which contains the player's name, entry number and their specialised message.
     */
    public HBox createUI() {
        HBox parent = new HBox();
        parent.setSpacing(5);
        parent.setPrefHeight(25);
        if (participant != null) {
            Image carIcon = participant.getCar().getIcon();
            ImageView imageView = new ImageView(carIcon);
            imageView.setFitWidth(30);
            imageView.setFitHeight(15);
            HBox.setMargin(imageView, new Insets(3, 3, 0, 0));
            parent.getChildren().add(imageView);

            Label driverLabel = new Label();
            driverLabel.setText(String.format("#%d %s:", participant.getEntryNumber(), participant.getDriverName()));
            driverLabel.setPrefSize(180, 0);
            parent.getChildren().add(driverLabel);
        }
        Label commentLabel = new Label();
        commentLabel.setText(message);
        commentLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(commentLabel, Priority.ALWAYS);
        parent.getChildren().add(commentLabel);
        return parent;
    }
}
