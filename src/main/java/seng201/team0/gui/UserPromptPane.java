package seng201.team0.gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class UserPromptPane extends Pane {
    // Properties
    private Image carImage;
    private String title;
    private String question;
    private String yesCaption;
    private String noCaption;
    private EventHandler<Event> yesHandler;
    private EventHandler<Event> noHandler;


    // Constructor
    public UserPromptPane(Image carImage, String title, String question, String yesCaption, String noCaption) {
        this.carImage = carImage;
        this.title = title;
        this.question = question;
        this.yesCaption = yesCaption;
        this.noCaption = noCaption;
    }


    // Getters and setters
    public void setYesHandler(EventHandler<Event> yesHandler) { this.yesHandler = yesHandler; }
    public void setNoHandler(EventHandler<Event> noHandler) { this.noHandler = noHandler; }


    // Logic
    public StackPane show() {
        StackPane overlay = new StackPane();
        overlay.getStyleClass().add("promptPane");

        VBox popup = new VBox();
        overlay.getChildren().add(popup);

        if (carImage != null) {
            ImageView carIcon = new ImageView(carImage);
            carIcon.setFitHeight(40);
            carIcon.setFitWidth(80);
            popup.getChildren().add(carIcon);
        }

        Label titleLabel = new Label();
        titleLabel.setId("titleLabel");
        titleLabel.setText(title);
        popup.getChildren().add(titleLabel);

        Label questionLabel = new Label();
        questionLabel.setId("questionLabel");
        questionLabel.setText(question);
        popup.getChildren().add(questionLabel);

        HBox buttons = new HBox();
        popup.getChildren().add(buttons);

        if (yesCaption != null) {
            Button yesButton = new Button();
            yesButton.setText(yesCaption);
            yesButton.setOnMouseClicked(event -> {
                event.consume();
                yesHandler.handle(null);
            });
            buttons.getChildren().add(yesButton);
        }

        if (noCaption != null) {
            Button noButton = new Button();
            noButton.setText(noCaption);
            noButton.setOnMouseClicked(event -> {
                event.consume();
                noHandler.handle(null);
            });
            buttons.getChildren().add(noButton);
        }

        return overlay;
    }
}
