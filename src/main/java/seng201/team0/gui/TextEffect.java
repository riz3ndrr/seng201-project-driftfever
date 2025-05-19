package seng201.team0.gui;

import javafx.scene.control.Label;



public class TextEffect {

    public static void scaleUp(Label label) {
        double scaleFactor = 1.1;
        label.setScaleX(scaleFactor);
        label.setScaleY(scaleFactor);
    }

    public static void scaleDown(Label label) {
        label.setScaleX(1);
        label.setScaleY(1);
    }

    public static void pressedText(Label label) {
        label.setStyle("-fx-background-color: #969696");
    }

    public static void unpressedText(Label label) {
        label.setStyle("-fx-background-color: #ababab");
    }


}
