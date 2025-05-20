package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seng201.team0.GameManager;
import seng201.team0.models.GameStats;

public class EndScreenController extends ParentController {
    @FXML
    private Label seasonLabel;
    @FXML
    private Label raceStatsLabel;
    @FXML
    private Label prizeMoneyLabel;

    // Properties
    GameStats gameDB = GameManager.getGameStats();


    // Logic
    public void initialize(Stage stage) {
        String userName = gameDB.getUserName();
        if (Character.toString(userName.charAt(userName.length() - 1)).equals("s")) {
            seasonLabel.setText(userName + "' Season Summary");
        }
        else {
            seasonLabel.setText(userName + "'s Season Summary");
        }
        raceStatsLabel.setText(String.format("You have competed in %d races this season.", gameDB.getRaceCount()));
        prizeMoneyLabel.setText(String.format("From those races, you have earned $%,.2f in prize money.", gameDB.getPrizeMoneyWon()));
    }

    public void playAgainClicked(MouseEvent event) {
        try {
            switchToStartScreenScene(event);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
