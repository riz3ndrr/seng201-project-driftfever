package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seng201.team0.GameManager;
import seng201.team0.models.GameStats;

import java.io.IOException;

public class EndScreenController extends ParentController {

    @FXML
    private Label endLabel1;
    @FXML
    private Label endLabel2;
    @FXML
    private Label endLabel3;

    GameStats gameDB = GameManager.getGameStats();

    public void initialize(Stage stage) {

        String userName = gameDB.getUserName();
        if (Character.toString(userName.charAt(userName.length() - 1)).equals("s")) {
            endLabel1.setText(userName + "' Season Summary:");
        }
        else {
            endLabel1.setText(userName + "'s Season Summary:");
        }

        endLabel2.setText("You have completed " + gameDB.getRacesDone() + " races out of " + gameDB.getRaceCount());
        endLabel3.setText("From those races, you have earned $" + String.format("%,.2f", gameDB.getPrizeMoneyWon()) + " in prize money");

    }
}
