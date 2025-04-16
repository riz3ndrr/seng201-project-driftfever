package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Slider diffSlider;


    static private Map<String, String> difficulties = Map.of(
            "Easy", "Take the easy route and start with more resources",
            "Regular", "Not too easy, not too forgiving",
            "Hard", "For the racers with something to prove"
    );

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
        diffSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateDifficulty(newValue.intValue());
        });


    }

}
