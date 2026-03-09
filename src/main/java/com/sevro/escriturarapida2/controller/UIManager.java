package com.sevro.escriturarapida2.controller;

import com.sevro.escriturarapida2.handler.IUIManager;
import com.sevro.escriturarapida2.model.GameModel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Manages all UI updates for the game screen.
 * Responsible for updating labels, showing game over and resetting the view.
 */
public class UIManager implements IUIManager {

    private final GameModel model;
    private final Label wordLabel;
    private final Label timerLabel;
    private final Label levelLabel;
    private final Label feedbackLabel;
    private final Button confirmButton;
    private final Button restartButton;
    private final TextField answerField;

    /**
     * Constructs a UIManager with all required UI components.
     *
     * @param model         the game model
     * @param wordLabel     label that shows the current word
     * @param timerLabel    label that shows the remaining time
     * @param levelLabel    label that shows the current level
     * @param feedbackLabel label that shows the result message
     * @param confirmButton the confirm button
     * @param restartButton the restart button
     * @param answerField   the text field for player input
     */
    public UIManager(GameModel model, Label wordLabel, Label timerLabel,
                     Label levelLabel, Label feedbackLabel,
                     Button confirmButton, Button restartButton,
                     TextField answerField) {
        this.model = model;
        this.wordLabel = wordLabel;
        this.timerLabel = timerLabel;
        this.levelLabel = levelLabel;
        this.feedbackLabel = feedbackLabel;
        this.confirmButton = confirmButton;
        this.restartButton = restartButton;
        this.answerField = answerField;
    }

    /**
     * Updates the word, level and timer labels for a new level.
     */
    public void updateLevel() {
        wordLabel.setText(model.getCurrentWord());
        levelLabel.setText("Nivel: " + model.getCurrentLevel());
        timerLabel.setText(String.format("00:%02d", model.getCurrentTime()));
        answerField.clear();
    }

    /**
     * Displays the game over screen with a message and levels completed.
     *
     * @param message the message to show the player
     */
    public void showGameOver(String message) {
        wordLabel.setText(message);
        answerField.setDisable(true);
        confirmButton.setDisable(true);
        feedbackLabel.setText("Niveles completados: " + (model.getCurrentLevel() - 1));
        feedbackLabel.setVisible(true);
        restartButton.setVisible(true);
    }

    /**
     * Resets all UI components to their initial state.
     */
    public void resetUI() {
        answerField.clear();
        answerField.setDisable(false);
        confirmButton.setDisable(false);
        feedbackLabel.setVisible(false);
        restartButton.setVisible(false);
        levelLabel.setText("Nivel: 1");
        timerLabel.setText("00:20");
        wordLabel.setText(model.getCurrentWord());
    }
}