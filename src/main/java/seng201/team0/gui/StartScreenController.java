package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng201.team0.GameManager;
import seng201.team0.models.GameStats;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;


public class StartScreenController extends ParentController {
    @FXML
    private Label diffDesc;
    @FXML
    private Slider diffSlider;
    @FXML
    private ImageView difficultyPic;
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


    // Properties
    private GameStats gameDB = GameManager.getGameStats();
    private static final Map<GameStats.Difficulty,String> difficultyDescriptions =
            new EnumMap<>(GameStats.Difficulty.class);
    static {
        difficultyDescriptions.put(GameStats.Difficulty.EASY, "Take the easy route and enjoy lower prices");
        difficultyDescriptions.put(GameStats.Difficulty.REGULAR, "Not too easy, not too forgiving");
        difficultyDescriptions.put(GameStats.Difficulty.HARD, "For the racers with something to prove, higher shop prices");
    }


    // Logic
    /**
     * Set the default difficulty to be regular and show its details on the UI.
     * Listen to any changers on the sliders or to the text area where the user inputs their username.
     * @param stage
     */
    public void initialize(Stage stage) {
        finishStartScreenLabel.setVisible(false);
        gameDB.setDifficulty(GameStats.Difficulty.REGULAR);
        diffDesc.setText(difficultyDescriptions.get(gameDB.getDifficulty()));
        listenForChangeToSliders();
        listenForChangeToUsername();
    }

    /**
     * Update the difficulty or the season count depending on if the sliders value have changed.
     */
    private void listenForChangeToSliders() {
        diffSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateDifficulty((int) Math.round(newValue.doubleValue()));
        });
        seasonSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateSeasonCount((int) Math.round(newValue.doubleValue()));
        });
    }

    /**
     * Listen for any changes to the inputted username. If it is a valid username, then the finish start screen label
     * will be visible and be able to be clicked on to proceed to the shop.
     */
    private void listenForChangeToUsername() {
        inputNameArea.textProperty().addListener((observable, oldText, newText) -> {
            if (isValidName(newText)) {
                whatNameLabel.setVisible(false);
                finishStartScreenLabel.setVisible(true);
            } else {
                String caption = newText.isEmpty() ? "What is your name?" : "Enter 3-15 alphanumeric characters.";
                String color = newText.isEmpty() ? "white" : "gray";
                whatNameLabel.setText(caption);
                whatNameLabel.setVisible(true);
                whatNameLabel.setStyle("-fx-fill: " + color);
                finishStartScreenLabel.setVisible(false);
            }
        });
    }

    /**
     * A helper function which validates the name
     * @param name which refers to the currently inputted name.
     * @return true or false depending on if the name's length is between 3 and 15 and doesn't contain
     * any special characters.
     */
    private boolean isValidName(String name) {
        return name != null && name.trim().matches("^[A-Za-z0-9 ]{3,15}$");
    }

    /**
     * Updates the game's difficulty level (including in the game state) and refreshes the UI to reflect the selected setting when the slider's value
     * has been changed.
     * <p>
     *     <ul>
     *        <li>If the newDiffLevel is below 2, the difficulty is set to easy</li>
     *         <li>If the newDiffLevel is greater or equal than 2 and below 3, the difficulty is set to regular</li>
     *         <li>else, the difficulty is set to hard</li>
     *     </ul>
     *
     * @param newDiffLevel an integer representing the selected difficulty level
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

        switch (diff) {
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
        difficultyPic.setImage(newDiffImg);
    }

    public void setDifficulty(GameStats.Difficulty diff) {
        gameDB.setDifficulty(diff);
        diffDesc.setText(difficultyDescriptions.get(diff));
    }

    /**
     * The first pane is now not visible and the pane containing all the elements to set starting attributes
     * is now visible to the user.
     */
    public void startSelecting() {
        selectAttributesPane.setVisible(true);
        startingPane.setVisible(false);
    }

    private void updateSeasonCount(int newRaceCount) {
        gameDB.setRaceCount(newRaceCount);
        seasonCountLabel.setText("Number of races: " + newRaceCount);
    }

    /**
     * Upload all the input (name, difficulty and season length) onto the GameStats "DB" and proceed to the shop scene.
     * @param event
     * @throws IOException if the shop fxml can not be accessed.
     */
    public void saveSettingsAndGoToShop(MouseEvent event) throws IOException {
        gameDB.setUserName(inputNameArea.getText());
        gameDB.setBal(gameDB.getStartingBalance());
        switchToShopScene(event);
    }
}