package com.sevro.escriturarapida2.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import com.sevro.escriturarapida2.controller.StartController;

/**
 * Represents the start stage of the application.
 * Loads and displays the start view before transitioning to the game.
 */
public class StartStage {

    private final Stage stage;

    /**
     * Constructs the StartStage and displays the start view on the given stage.
     *
     * @param stage the primary stage to use
     * @throws IOException if the FXML file cannot be loaded
     */
    public StartStage(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/sevro/escriturarapida2/start-view.fxml")
        );
        loader.setControllerFactory(c -> new StartController(stage));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Type_shi.");
        stage.setScene(scene);
        stage.show();
    }
}