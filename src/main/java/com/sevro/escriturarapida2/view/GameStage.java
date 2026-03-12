package com.sevro.escriturarapida2.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the main game stage of the application.
 * Loads and displays the game view FXML on the provided stage.
 */
public class GameStage {

    /**
     * Constructs the GameStage and displays the game view on the given stage.
     *
     * @param stage the primary stage to use
     * @throws IOException if the FXML file cannot be loaded
     */
    public GameStage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/sevro/escriturarapida2/game-view.fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}