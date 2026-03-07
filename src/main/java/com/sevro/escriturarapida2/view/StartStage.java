package com.sevro.escriturarapida2.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the start screen stage of the application.
 * Loads and displays the start view FXML.
 */
public class StartStage extends Stage {

    /**
     * Constructs the StartStage, loads the FXML and configures the window.
     *
     * @throws IOException if the FXML file cannot be loaded
     */
    public StartStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/sevro/escriturarapida2/start-view.fxml")
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        setTitle("Type_shi.");
        setScene(scene);
        show();
    }
}