package com.sevro.escriturarapida2.controller;

import com.sevro.escriturarapida2.view.GameStage;
import com.sevro.escriturarapida2.view.StartStage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the start screen view.
 * Handles the transition from the start screen to the game screen.
 */
public class StartController implements Initializable {

    @FXML private Button playButton;

    /**
     * Called automatically by JavaFX after the FXML is loaded.
     *
     * @param url            the location of the FXML file
     * @param resourceBundle the resource bundle for localization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // hover effects on play button
        playButton.setOnMouseEntered(e -> playButton.setStyle(
                playButton.getStyle() + "-fx-opacity: 0.8;"
        ));
        playButton.setOnMouseExited(e -> playButton.setStyle(
                playButton.getStyle().replace("-fx-opacity: 0.8;", "")
        ));
    }

    /**
     * Handles the Jugar button click.
     * Opens the game screen and closes the start screen.
     */
    @FXML
    private void handlePlay() throws IOException {
        new GameStage();
        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.close();
    }
}
