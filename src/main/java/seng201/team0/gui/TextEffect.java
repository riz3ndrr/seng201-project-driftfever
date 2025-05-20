package seng201.team0.gui;

import javafx.scene.Node;
import javafx.scene.control.Label;


public class TextEffect {

    /**
     * Scale up a node to emphasise it can be interacted with
     * <p>
     * @param node which the user is currently interacting with.
     */
    public static void scaleUp(Node node) {
        double scaleFactor = 1.1;
        node.setScaleX(scaleFactor);
        node.setScaleY(scaleFactor);
    }

    /**
     * Reset a node to its orginal size
     * <p>
     * @param node which the user is exiting from.
     */
    public static void scaleDown(Node node) {
        node.setScaleX(1);
        node.setScaleY(1);
    }

    /**
     * Change the colour of a label to be darker when clicked on
     * <p>
     * @param label which the user is interacting with
     */
    public static void pressedText(Label label) {
        label.setStyle("-fx-background-color: #969696");
    }

    /**
     * Revert the label to its original colour when the user releases the mouse on the label.
     * <p>
     * @param label which the user was interacting with
     */
    public static void unpressedText(Label label) {
        label.setStyle("-fx-background-color: #ababab");
    }


}
