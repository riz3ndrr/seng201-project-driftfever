package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import seng201.team0.models.GameStats;

import java.util.Arrays;
import java.util.List;

public class SelectRaceController {
    @FXML
    private ImageView car1Img;

    @FXML
    private ImageView car2Img;

    @FXML
    private ImageView car3Img;

    @FXML
    private ImageView car4Img;

    @FXML
    private ImageView car5Img;

    @FXML
    private ImageView car6Img;

    @FXML
    private ImageView car7Img;

    @FXML
    private ImageView car8Img;

    @FXML
    private ImageView car9Img;
    // Player/Game Database
    GameStats gameDB = GameStats.getInstance();

    public void displaySelectedCar(boolean displayImg) {

    }

    public void initialize(Stage stage) {

    }
}
