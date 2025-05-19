package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seng201.team0.GameManager;
import seng201.team0.models.GameStats;

import java.io.IOException;
import java.util.*;

public class StartScreenController extends ParentController {
    @FXML
    private Label gameTitle;
    @FXML
    private Label gameDesc;
    @FXML
    private Text diffLabel;
    @FXML
    private Label diffDesc;

    @FXML
    private ImageView easyPic;
    @FXML
    private ImageView medPic;
    @FXML
    private ImageView hardPic;

    @FXML
    private ImageView leftArrow;
    @FXML
    private ImageView rightArrow;

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
    private GridPane titleGridPane;
    @FXML
    private GridPane chooseDifficultyGridPane;
    @FXML
    private GridPane startMenuGridPane;
    @FXML
    private GridPane chooseCarGridPane;
    @FXML
    private GridPane finalSelectScreenGridPane;
    @FXML
    private GridPane chooseSeasonLengthGridPane;
    @FXML
    private GridPane chooseNameGridPane;

    @FXML
    private Label finishStartScreenLabel;

    @FXML
    private Pane selectAttributesPane;
    @FXML
    private Pane startingPane;

    @FXML
    private ImageView RArrow;

    @FXML
    private Label startLabel;

    @FXML
    private Text subtitleText;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private GameStats.Difficulty chosenDifficulty = GameStats.Difficulty.REGULAR;

    private static final Map<GameStats.Difficulty,String> difficultyDescriptions =
            new EnumMap<>(GameStats.Difficulty.class);
    static {
        difficultyDescriptions.put(GameStats.Difficulty.EASY, "Take the easy route and start with more resources");
        difficultyDescriptions.put(GameStats.Difficulty.REGULAR, "Not too easy, not too forgiving");
        difficultyDescriptions.put(GameStats.Difficulty.HARD, "For the racers with something to prove");
    }

    private int raceCount = 3;

    static int optionIndex = 0;

    public void startSelecting() {
        selectAttributesPane.setVisible(true);
        startingPane.setVisible(false);
        inputNameArea.requestFocus(); // Autofocus the name field
    }

    public void showNewOptionSlide() {
//        List<GridPane> chooseOptions = Arrays.asList(startMenuGridPane, chooseNameGridPane, chooseDifficultyGridPane,chooseSeasonLengthGridPane);
//        GridPane newPaneToShow = chooseOptions.get(optionIndex);
//
//        newPaneToShow.setVisible(true);
//        // can make this more efficient
//        for(GridPane option : chooseOptions) {
//            if (option != newPaneToShow) {
//                option.setVisible(false);
//            }
//        }

    }

    public String getDifficultyDesc() {
        return difficultyDescriptions.get(chosenDifficulty);
    }

    private void updateSeasonCount(int newRaceCount) {
        raceCount = newRaceCount;
        seasonCountLabel.setText("Number of races: " + raceCount);
    }

    public int getRaceCount() {
        return raceCount;
    }

    @FXML
    private ImageView difficultyPic;

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
        // Player/Game Database
        GameStats gameDB = GameManager.getGameStats();

        gameDB.setRaceDifficulty(diff);

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

    // Helper to validate name len and no special chars
    private boolean isValidName(String name) {
        return name != null && name.matches("^[A-Za-z0-9]{3,15}$");
    }

    public void setDifficulty(GameStats.Difficulty diff) {
        this.chosenDifficulty = diff;
        diffDesc.setText(difficultyDescriptions.get(diff));
    }

    private GameStats.Difficulty getChosenDifficulty() {
        return chosenDifficulty;
    }

    public void initialize(Stage stage) {
        finishStartScreenLabel.setVisible(false);

        diffDesc.setText(getDifficultyDesc());
        showNewOptionSlide();
        diffSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateDifficulty(newValue.intValue());
        });
        seasonSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateSeasonCount(newValue.intValue());
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

    public void switchToShopScene(MouseEvent event) throws IOException {
        // Upload all the input (name, difficulty and season length) onto the GameStats "DB"
        // Proceed to the next scene

        // Player/Game Database
        GameStats gameDB = GameManager.getGameStats();

        gameDB.setRaceCount(getRaceCount());
        gameDB.setRaceDifficulty(getChosenDifficulty());
        gameDB.setUserName(inputNameArea.getText());

        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/shop.fxml"));
        Parent root = baseLoader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ShopController baseController = baseLoader.getController();
        baseController.initialize(stage);

    }

}
