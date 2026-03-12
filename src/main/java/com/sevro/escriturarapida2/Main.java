package com.sevro.escriturarapida2;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import com.sevro.escriturarapida2.view.StartStage;

/**
 * Entry point for the "Escritura Rapida" JavaFX application.
 * Launches the start screen.
 */
public class Main extends Application {

    /**
     * Application main method, launches the JavaFX runtime.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Sets up and displays the start stage.
     *
     * @param primaryStage the primary stage provided by JavaFX
     * @throws IOException if the start stage cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.getIcons().add(new javafx.scene.image.Image(
                Main.class.getResourceAsStream("/com/sevro/escriturarapida2/Icon.png")
        ));
        new StartStage(primaryStage);
    }
}