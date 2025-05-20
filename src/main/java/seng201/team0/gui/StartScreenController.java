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


    // Logic
    public void initialize(Stage stage) {
        finishStartScreenLabel.setVisible(false);
        gameDB.setDifficulty(GameStats.Difficulty.REGULAR);
        diffDesc.setText(getDifficultyDesc());
        listenForChangeToSliders();
        listenForChangeToUsername();
    }

    private void listenForChangeToSliders() {
        diffSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateDifficulty((int) Math.round(newValue.doubleValue()));
        });
        seasonSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateSeasonCount((int) Math.round(newValue.doubleValue()));
        });
    }

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

    // Helper to validate name len and no special chars
    private boolean isValidName(String name) {
        return name != null && name.trim().matches("^[A-Za-z0-9 ]{3,15}$");
    }

    private static final Map<GameStats.Difficulty,String> difficultyDescriptions =
            new EnumMap<>(GameStats.Difficulty.class);
    static {
        difficultyDescriptions.put(GameStats.Difficulty.EASY, "Take the easy route and enjoy lower prices");
        difficultyDescriptions.put(GameStats.Difficulty.REGULAR, "Not too easy, not too forgiving");
        difficultyDescriptions.put(GameStats.Difficulty.HARD, "For the racers with something to prove, higher shop prices");
    }

    public String getDifficultyDesc() {
        return difficultyDescriptions.get(gameDB.getDifficulty());
    }

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

    public void startSelecting() {
        selectAttributesPane.setVisible(true);
        startingPane.setVisible(false);
    }

    private void updateSeasonCount(int newRaceCount) {
        gameDB.setRaceCount(newRaceCount);
        seasonCountLabel.setText("Number of races: " + newRaceCount);
    }

    public void saveSettingsAndGoToShop(MouseEvent event) throws IOException {
        // Upload all the input (name, difficulty and season length) onto the GameStats "DB"
        // Proceed to the next scene
        gameDB.setUserName(inputNameArea.getText());
        gameDB.setBal(gameDB.getStartingBalance());
        switchToShopScene(event);
    }
}