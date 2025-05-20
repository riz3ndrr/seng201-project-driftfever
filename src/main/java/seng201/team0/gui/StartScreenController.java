package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng201.team0.GameManager;
import seng201.team0.models.GameStats;

import java.io.IOException;
import java.util.*;

public class StartScreenController extends ParentController {

    @FXML
    private Label diffDesc;

    @FXML
    private Slider diffSlider;

    @FXML
    private Slider seasonSlider;

    @FXML
    private Label seasonCountLabel;

    @FXML
    private TextField inputNameArea;
    @FXML
    private Text whatNameLabel;

    @FXML
    private Label finishStartScreenLabel;

    @FXML
    private Pane selectAttributesPane;
    @FXML
    private Pane startingPane;

    private GameStats gameDB = GameManager.getGameStats();
    @FXML
    private ImageView difficultyPic;


    private static final Map<GameStats.Difficulty,String> difficultyDescriptions =
            new EnumMap<>(GameStats.Difficulty.class);
    static {
        difficultyDescriptions.put(GameStats.Difficulty.EASY, "Take the easy route and enjoy lower prices");
        difficultyDescriptions.put(GameStats.Difficulty.REGULAR, "Not too easy, not too forgiving");
        difficultyDescriptions.put(GameStats.Difficulty.HARD, "For the racers with something to prove, higher shop prices");
    }

    private int raceCount = 3;

    /**
     * Change the screen so that the user can now input their name, select their difficulty and the length of the season.
     */
    public void startSelecting() {
        selectAttributesPane.setVisible(true);
        startingPane.setVisible(false);
        inputNameArea.requestFocus(); // Autofocus the name field
    }


    /**
     * Update the length of the season and reflect it on the UI to display the currently selected season length.
     * @param newRaceCount which refers to the new length of the season set by the slider.
     */
    private void updateSeasonCount(int newRaceCount) {
        raceCount = newRaceCount;
        seasonCountLabel.setText("Number of races: " + raceCount);
    }

    /**
     * Update the chosen difficulty of the playthrough and diplay its respective image.
     * The slider value is below 2, it will set the difficulty to easy, else if it is between 2 and 3, it will set it
     * to regular. Otherwise, the difficulty is set to hard.
     * @param newDiffLevel is the value of the difficulty slider.
     */

    private void updateDifficulty(int newDiffLevel) {
        GameStats.Difficulty diff;
        String selectedDiffImgDirectory = "";

        if (newDiffLevel < 2) {
            diff = GameStats.Difficulty.EASY;
        } else if (newDiffLevel < 3) {
            diff = GameStats.Difficulty.REGULAR;
        } else {
            diff = GameStats.Difficulty.HARD;
        }

        setDifficulty(diff);
        gameDB.setDifficulty(diff);

        switch(diff) {
            case EASY:
                selectedDiffImgDirectory = "file:src/main/resources/designs/difficulty/easy.png";
                break;

            case REGULAR:
                selectedDiffImgDirectory = "file:src/main/resources/designs/difficulty/med.png";
                break;

            case HARD:
                selectedDiffImgDirectory = "file:src/main/resources/designs/difficulty/hard.png";
                break;

        }

        Image newDiffImg = new Image(selectedDiffImgDirectory);
        System.out.println(newDiffImg);
        difficultyPic.setImage(newDiffImg);

    }

    /**
     * A helper function to validate the name's length and check if it has no special characters.
     * @param name which is the currently inputted name
     * @return true or false depending on if the inputted name abides with the conditions.
     */
    private boolean isValidName(String name) {
        return name != null && name.trim().matches("^[A-Za-z0-9 ]{3,15}$");
    }

    /**
     * Change the text of the diffDesc label to show a description of the difficulty.
     * @param diff which is the currently chosen difficulty level.
     */
    public void setDifficulty(GameStats.Difficulty diff) {
        gameDB.setDifficulty(diff);
        diffDesc.setText(difficultyDescriptions.get(diff));
    }

    /**
     * Set the default difficulty to regular and display its description on the UI.
     * <p>
     * Add listeners to the two sliders and live validation on the input of the name to see if it abides with our
     * restrictions. If the name is not valid, it will say to the user to "Enter 3-15 alphanumeric characters" and label
     * to proceed to the shop won't be visible. Likewise if the inputted name is valid.
     * @param stage
     */

    public void initialize(Stage stage) {
        finishStartScreenLabel.setVisible(false);
        gameDB.setDifficulty(GameStats.Difficulty.REGULAR);
        diffDesc.setText(difficultyDescriptions.get(gameDB.getDifficulty()));

        diffSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateDifficulty((int) Math.round(newValue.doubleValue()));
        });
        seasonSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateSeasonCount((int) Math.round(newValue.doubleValue()));
        });

        // Live validation on name input
        inputNameArea.textProperty().addListener((observable, oldText, newText) -> {
            if (isValidName(newText)) {
                whatNameLabel.setVisible(false);
                finishStartScreenLabel.setVisible(true);
            }
            else {
                String caption = newText.isEmpty() ? "What is your name?" : "Enter 3-15 alphanumeric characters.";
                String color = newText.isEmpty() ? "white" : "gray";
                whatNameLabel.setText(caption);
                whatNameLabel.setVisible(true);
                whatNameLabel.setStyle("-fx-fill: " + color);

                finishStartScreenLabel.setVisible(false);
            }
        });



    }

    public void saveSettingsAndGoToShop(MouseEvent event) throws IOException {
        // Upload all the input (name, difficulty and season length) onto the GameStats "DB"
        // Proceed to the next scene
        gameDB.setRaceCount(raceCount);
        gameDB.setUserName(inputNameArea.getText());
        gameDB.setBal(gameDB.getStartingBalance());
        switchToShopScene(event);
    }
}
