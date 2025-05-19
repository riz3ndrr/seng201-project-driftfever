package seng201.team0.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ParentController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void scaleUpText(MouseEvent event) {
        Label hoveredLabel = (Label) event.getSource();
        TextEffect.scaleUp(hoveredLabel);

    }
    public void scaleDownText(MouseEvent event) {
        Label hoveredLabel = (Label) event.getSource();
        TextEffect.scaleDown(hoveredLabel);

    }

    public void pressedText(MouseEvent event) {
        Label hoveredLabel = (Label) event.getSource();
        TextEffect.pressedText(hoveredLabel);
    }
    public void unpressedText(MouseEvent event) {
        Label hoveredLabel = (Label) event.getSource();
        TextEffect.unpressedText(hoveredLabel);
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
}
