package com.sevro.escriturarapida2.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the main game stage of the application.
 * Loads and displays the game view FXML.
 */
public class GameStage extends Stage {

    /**
     * Constructs the GameStage, loads the FXML and configures the window.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public GameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/sevro/escriturarapida2/game-view.fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        setTitle("Type_shi.");
        setScene(scene);
        show();
    }
}