package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import seng201.team0.models.GameStats;

import java.util.*;

public class DummyController {
    @FXML
    private GridPane showStatGridPane;

    @FXML
    private Text nameText;
    @FXML
    private Text diffText;
    @FXML
    private Text numRaceText;

    @FXML
    private Label nameLabel;
    @FXML
    private Label diffLabel;
    @FXML
    private Label numRaceLabel;


    public void initialize(Stage stage) {
        GameStats gameDB = GameStats.getInstance();
        diffLabel.setText(gameDB.getRaceDifficulty());
        numRaceLabel.setText(Integer.toString(gameDB.getRaceCount()));
        nameLabel.setText(gameDB.getUserName());


    }
}
