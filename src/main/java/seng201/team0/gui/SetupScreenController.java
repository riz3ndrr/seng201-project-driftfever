package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class SetupScreenController {
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
    private GridPane chooseCarGridPane;
    @FXML
    private GridPane finalSelectScreenGridPane;
    @FXML
    private GridPane chooseSeasonLengthGridPane;
    @FXML
    private GridPane chooseNameGridPane;



    static private Map<String, String> difficulties = Map.of(
            "Easy", "Take the easy route and start with more resources",
            "Regular", "Not too easy, not too forgiving",
            "Hard", "For the racers with something to prove"
    );

    private int raceCount = 3;



    static int optionIndex = 0;
    public void showNewOptionSlide() {
        List<GridPane> chooseOptions = Arrays.asList(chooseNameGridPane, chooseDifficultyGridPane,chooseSeasonLengthGridPane);
        GridPane newPaneToShow = chooseOptions.get(optionIndex);

        newPaneToShow.setVisible(true);
        for(GridPane option : chooseOptions) {
            if (option != newPaneToShow) {
                option.setVisible(false);
            }
        }

    }

    public void moveRight(MouseEvent mouseEvent) {
        System.out.println("MOVING RIGHT");
        optionIndex++;
        showNewOptionSlide();
    }

    public void moveLeft(MouseEvent mouseEvent) {
        System.out.println("MOVING LEFT");
        optionIndex--;
        showNewOptionSlide();
    }


    static private String chosenDifficulty = "Regular";

    public String getDifficultyDesc() {
        return difficulties.get(chosenDifficulty);
    }

    public void setDifficulty(String newDiff) {
        chosenDifficulty = newDiff;
    }

    public String getChosenDifficulty() {
        return chosenDifficulty;
    }


    private void updateSeasonCount(int newRaceCount) {
        raceCount = newRaceCount;
        seasonCountLabel.setText("Number of races: " + raceCount);
    }

    private void updateDifficulty(int newDiffLevel) {
        String newDiff;
        if (newDiffLevel >= 1 && newDiffLevel < 2) {
            newDiff = "Easy";
            easyPic.setOpacity(1);
            medPic.setOpacity(0);
            hardPic.setOpacity(0);
        }
        else if (newDiffLevel >= 2 && newDiffLevel < 3) {
            newDiff = "Regular";
            easyPic.setOpacity(0);
            medPic.setOpacity(1);
            hardPic.setOpacity(0);
        }
        else {
            newDiff = "Hard";
            easyPic.setOpacity(0);
            medPic.setOpacity(0);
            hardPic.setOpacity(1);
        }
        setDifficulty(newDiff);
        diffDesc.setText(getDifficultyDesc());
    }


    public void initialize(Stage stage) {
        diffDesc.setText(getDifficultyDesc());
        showNewOptionSlide();
        diffSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateDifficulty(newValue.intValue());
        });
        seasonSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateSeasonCount(newValue.intValue());
        });


    }

    public void bruh(MouseEvent mouseEvent) {
        System.out.println("BRUH");
    }
}
