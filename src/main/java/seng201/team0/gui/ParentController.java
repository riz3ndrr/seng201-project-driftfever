package seng201.team0.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import seng201.team0.models.Race;

import java.io.IOException;

public class ParentController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public Stage getStage() { return stage; }
    public void setStage(Stage stage) { this.stage = stage; }

    public void scaleUpText(MouseEvent event) {
        Node node = (Node) event.getSource();
        TextEffect.scaleUp(node);
    }

    public void scaleDownText(MouseEvent event) {
        Node node = (Node) event.getSource();
        TextEffect.scaleDown(node);
    }

    public void pressedText(MouseEvent event) {
        Label hoveredLabel = (Label) event.getSource();
        TextEffect.pressedText(hoveredLabel);
    }

    public void unpressedText(MouseEvent event) {
        Label hoveredLabel = (Label) event.getSource();
        TextEffect.unpressedText(hoveredLabel);
    }

    public void switchToStartScreenScene(MouseEvent event) throws IOException {
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/startScreen.fxml"));
        Parent root = baseLoader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        StartScreenController baseController = baseLoader.getController();
        baseController.initialize(stage);
    }

    public void switchToShopScene(MouseEvent event) throws IOException {
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/shop.fxml"));
        Parent root = baseLoader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ShopController baseController = baseLoader.getController();
        baseController.initialize(stage);
    }

    public void switchToSelectRaceScene(MouseEvent event) throws IOException {
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/selectRace.fxml"));
        Parent root = baseLoader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        SelectRaceController baseController = baseLoader.getController();
        baseController.initialize(stage);
    }

    public void switchToGarageScene(MouseEvent event) throws IOException {
        // Upload all the input (name, difficulty and season length) onto the GameStats "DB"
        // Proceed to the next scene
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/garage.fxml"));
        Parent root = baseLoader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        GarageController baseController = baseLoader.getController();
        baseController.initialize(stage);

    }

    public void switchToLeaderboardScene(Stage stage, Race race) throws IOException {
        // Upload all the input (name, difficulty and season length) onto the GameStats "DB"
        // Proceed to the next scene
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/leaderboard.fxml"));
        Parent root = baseLoader.load();

        //stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        LeaderboardController baseController = baseLoader.getController();
        baseController.initialize(race);

    }

    public void switchToEndScreenScene(MouseEvent event) throws IOException {
        FXMLLoader baseLoader = new FXMLLoader(getClass().getResource("/fxml/endScreen.fxml"));
        Parent root = baseLoader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        EndScreenController baseController = baseLoader.getController();
        baseController.initialize(stage);
    }
}
