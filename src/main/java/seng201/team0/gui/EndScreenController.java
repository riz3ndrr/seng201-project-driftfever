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
    private Label seasonLabel;
    @FXML
    private Label raceStatsLabel;
    @FXML
    private Label prizeMoneyLabel;
    @FXML
    private Label failMsgLabel;

    GameStats gameDB = GameManager.getGameStats();

    /**
     * Display the user's name, the number of races they have completed in the season along with the
     * total amount of prize money they have earned.
     * @param stage
     */
    public void initialize(Stage stage) {
        String userName = gameDB.getUserName();
        if (Character.toString(userName.charAt(userName.length() - 1)).equals("s")) {
            seasonLabel.setText(userName + "' Season Summary");
        }
        else {
            seasonLabel.setText(userName + "'s Season Summary");
        }

        if (!gameDB.canContinuePlaying()) {
            failMsgLabel.setVisible(true);
        }
        else {
            failMsgLabel.setVisible(false);
        }

        raceStatsLabel.setText(String.format("You have competed in %d races this season.", gameDB.getRacesDone()));
        prizeMoneyLabel.setText(String.format("From those races, you have earned $%,.2f in prize money.", gameDB.getPrizeMoneyWon()));
    }

    /**
     * Attempt to switch to the start screen scene to restart the game barring any exceptions occur.
     * @param event
     */
    public void playAgainClicked(MouseEvent event) {
        try {
            gameDB.reset();
            switchToStartScreenScene(event);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
