package seng201.team0.gui;

import javafx.scene.Node;
import javafx.scene.control.Label;


public class TextEffect {

    public static void scaleUp(Node node) {
        double scaleFactor = 1.1;
        node.setScaleX(scaleFactor);
        node.setScaleY(scaleFactor);
    }

    public static void scaleDown(Node node) {
        node.setScaleX(1);
        node.setScaleY(1);
    }

    public static void pressedText(Label label) {
        label.setStyle("-fx-background-color: #969696");
    }

    public static void unpressedText(Label label) {
        label.setStyle("-fx-background-color: #ababab");
    }


}
