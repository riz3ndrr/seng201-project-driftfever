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

    /**
     * Call the scaleUp() method in the TextEffect class to scale up a certain element of the UI.
     * @param event which is the UI element the user is interacting with.
     */
    public void scaleUpText(MouseEvent event) {
        Node node = (Node) event.getSource();
        TextEffect.scaleUp(node);
    }

    /**
     * Call the scaleDown() method in the TextEffect class to revert a certain element of the UI to its original size.
     * @param event which is the UI element the user is interacting with.
     */
    public void scaleDownText(MouseEvent event) {
        Node node = (Node) event.getSource();
        TextEffect.scaleDown(node);
    }

    /**
     * Call the pressedText() method in the TextEffect class to visually show a particular UI element is being clicked on.
     * @param event which is the UI element the user is interacting with.
     */
    public void pressedText(MouseEvent event) {
        Label hoveredLabel = (Label) event.getSource();
        TextEffect.pressedText(hoveredLabel);
    }

    /**
     * Call the unpressedText() method in the TextEffect class to visually show the user stopped clicking on that particular
     * UI element.
     * @param event which is the UI element the user is interacting with.
     */
    public void unpressedText(MouseEvent event) {
        Label hoveredLabel = (Label) event.getSource();
        TextEffect.unpressedText(hoveredLabel);
    }


    /**
     * Switch the user to the simulator scene using the fxml content specified in resources/fxml/simulator.fxml
     * @param event which is the current FXML being used.
     * @throws IOException if there is an issue loading that fxml file.
     */
    public static void switchToSimulatorScene(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SimulatorController.class.getResource("/fxml/simulator.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        SimulatorController controller = loader.getController();
        controller.setStage(stage);
        controller.initialize(stage);
    }

    /**
     * Switch the user to the start screen scene using the fxml content specified in resources/fxml/startScreen.fxml
     * @param event which is the current FXML being used.
     * @throws IOException if there is an issue loading that fxml file.
     */
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

    /**
     * Switch the user to the shop scene using the fxml content specified in resources/fxml/shop.fxml
     * @param event which is the current FXML being used.
     * @throws IOException if there is an issue loading that fxml file.
     */
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

    /**
     * Switch the user to the select race scene using the fxml content specified in resources/fxml/selectRace.fxml
     * @param event which is the current FXML being used.
     * @throws IOException if there is an issue loading that fxml file.
     */
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

    /**
     * Switch the user to the garage scene using the fxml content specified in resources/fxml/garage.fxml
     * @param event which is the current FXML being used.
     * @throws IOException if there is an issue loading that fxml file.
     */

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

    /**
     * Switch the user to the leaderboard scene and show the result summary of the race.
     * @param stage
     * @param race which is the race the cars have raced on.
     * @throws IOException if tehre is an issue loading the fxml file.
     */
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

    /**
     * Switch the user to the end scene using the fxml content specified in resources/fxml/endScreen.fxml
     * @param event which is the current FXML being used.
     * @throws IOException if there is an issue loading that fxml file.
     */

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
