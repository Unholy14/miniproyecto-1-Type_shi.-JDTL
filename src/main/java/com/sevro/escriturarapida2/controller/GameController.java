package com.sevro.escriturarapida2.controller;

import com.sevro.escriturarapida2.handler.IGameEventHandler;
import com.sevro.escriturarapida2.model.GameModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import com.sevro.escriturarapida2.handler.MouseAdapter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * MVC Controller for the "Escritura Rapida" game.
 * Connects the FXML view with the GameModel and handles all user interactions.
 * Implements {@link IGameEventHandler} to modularize event handling.
 */
public class GameController implements Initializable, IGameEventHandler {

    @FXML private Label wordLabel;
    @FXML private Label timerLabel;
    @FXML private TextField answerField;
    @FXML private Button confirmButton;
    @FXML private Label feedbackLabel;
    @FXML private Button restartButton;
    @FXML private Label levelLabel;
    @FXML private Label comboLabel;

    private GameModel model;
    private Timeline timer;
    private long levelStartTime;
    private Timeline comboTimer;
    private Timeline shrinkAnimation;

    /**
     * Called automatically by JavaFX after the FXML is loaded.
     * Initializes the model, loads the first word, sets up keyboard
     * and mouse event handlers, and starts the timer.
     *
     * @param url            the location of the FXML file
     * @param resourceBundle the resource bundle for localization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new GameModel();
        model.loadNewWord();
        wordLabel.setText(model.getCurrentWord());

        answerField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                onAnswerSubmitted();
            }
        });

        ButtonHoverAdapter confirmAdapter = new ButtonHoverAdapter(confirmButton);
        confirmButton.setOnMouseEntered(confirmAdapter::onMouseEntered);
        confirmButton.setOnMouseExited(confirmAdapter::onMouseExited);
        confirmButton.setOnMousePressed(confirmAdapter::onMousePressed);
        confirmButton.setOnMouseReleased(confirmAdapter::onMouseReleased);

        ButtonHoverAdapter restartAdapter = new ButtonHoverAdapter(restartButton);
        restartButton.setOnMouseEntered(restartAdapter::onMouseEntered);
        restartButton.setOnMouseExited(restartAdapter::onMouseExited);
        restartButton.setOnMousePressed(restartAdapter::onMousePressed);
        restartButton.setOnMouseReleased(restartAdapter::onMouseReleased);

        startTimer();
        startComboTimer();
    }

    /**
     * Creates and starts a one-second repeating timeline.
     * On each tick, decrements the timer and validates the answer if time runs out.
     */
    private void startTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            boolean timeUp = model.tickTimer();
            timerLabel.setText(String.format("00:%02d", model.getCurrentTime()));

            if (timeUp) {
                timer.stop();
                String answer = answerField.getText();
                boolean correct = model.validateAnswer(answer);

                if (correct) {
                    model.decreaseCombo();
                    updateCombo();
                    model.advanceLevel();
                    levelLabel.setText("Nivel: " + model.getCurrentLevel());
                    wordLabel.setText(model.getCurrentWord());
                    answerField.clear();
                    timerLabel.setText(String.format("00:%02d", model.getCurrentTime()));
                    startTimer();
                } else {
                    onTimeOut();

                }
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        levelStartTime = System.currentTimeMillis();
        timer.play();
    }

    /**
     * Displays the game over screen with a message and the number of completed levels.
     *
     * @param message the message to show the player
     */
    private void showGameOver(String message) {
        wordLabel.setText(message);
        answerField.setDisable(true);
        confirmButton.setDisable(true);
        feedbackLabel.setText("Niveles completados: " + (model.getCurrentLevel() - 1));
        feedbackLabel.setVisible(true);
        restartButton.setVisible(true);
        if (comboTimer != null) comboTimer.stop();
    }

    /**
     * {@inheritDoc}
     * Validates the typed answer. Advances the level if correct,
     * or shows game over if incorrect.
     */
    @Override
    public void onAnswerSubmitted() {
        String answer = answerField.getText();
        boolean correct = model.validateAnswer(answer);

        if (correct) {
            timer.stop();
            long elapsed = (System.currentTimeMillis() - levelStartTime) / 1000;
            if (elapsed <= GameModel.COMBO_THRESHOLD) {
                model.increaseCombo();
            } else {
                model.decreaseCombo();
            }
            updateCombo();
            model.advanceLevel();
            levelLabel.setText("Nivel: " + model.getCurrentLevel());
            wordLabel.setText(model.getCurrentWord());
            answerField.clear();
            timerLabel.setText(String.format("00:%02d", model.getCurrentTime()));
            startTimer();
        }
        else {
            timer.stop();
            showGameOver("¡Incorrecto! Era: " + model.getCurrentWord());
            model.resetCombo();
            updateCombo();
        }
    }
    private void startComboTimer() {
        if (comboTimer != null) {
            comboTimer.stop();
        }
        comboTimer = new Timeline(new KeyFrame(Duration.seconds(GameModel.COMBO_THRESHOLD), e -> {
            model.decreaseCombo();
            updateCombo();
            startComboTimer();
        }));
        comboTimer.setCycleCount(1);
        comboTimer.play();
    }

    /**
     * Updates the combo label text and color based on the current rank.
     */
    private void updateCombo() {
        String rank = model.getCurrentCombo();
        comboLabel.setText(rank);

        String color = switch (rank) {
            case "D" -> "#aaaaaa";
            case "C" -> "#74b9ff";
            case "B" -> "#55efc4";
            case "A" -> "#fdcb6e";
            case "S" -> "#fd79a8";
            case "SS" -> "#e17055";
            case "SSS" -> "#a29bfe";
            default -> "#ffffff";
        };

        int targetSize = switch (rank) {
            case "D" -> 36;
            case "C" -> 42;
            case "B" -> 48;
            case "A" -> 56;
            case "S" -> 64;
            case "SS" -> 72;
            case "SSS" -> 82;
            default -> 36;
        };

        comboLabel.setStyle(
                "-fx-text-fill: " + color + ";" +
                        "-fx-font-family: Consolas;" +
                        "-fx-font-size: " + (targetSize + 30) + ";" +
                        "-fx-font-weight: bold;"
        );

        if (shrinkAnimation != null) shrinkAnimation.stop();

        int[] currentSize = {targetSize + 30};
        String finalColor = color;
        int finalTargetSize = targetSize;

        shrinkAnimation = new Timeline(new KeyFrame(Duration.millis(30), e -> {
            if (currentSize[0] > finalTargetSize) {
                currentSize[0] -= 2;
                comboLabel.setStyle(
                        "-fx-text-fill: " + finalColor + ";" +
                                "-fx-font-family: Consolas;" +
                                "-fx-font-size: " + currentSize[0] + ";" +
                                "-fx-font-weight: bold;"
                );
            } else {
                shrinkAnimation.stop();
            }
        }));
        shrinkAnimation.setCycleCount(Timeline.INDEFINITE);
        shrinkAnimation.play();
    }

    /**
     * {@inheritDoc}
     * Shows the game over screen when the timer reaches zero.
     */
    @Override
    public void onTimeOut() {
        model.resetCombo();
        showGameOver("¡Tiempo agotado!");
    }

    /**
     * {@inheritDoc}
     * Resets the model and UI to start a new game from level 1.
     */
    @Override
    public void onRestart() {
        model.reset();
        updateCombo();
        model.loadNewWord();
        wordLabel.setText(model.getCurrentWord());
        answerField.clear();
        answerField.setDisable(false);
        confirmButton.setDisable(false);
        feedbackLabel.setVisible(false);
        restartButton.setVisible(false);
        levelLabel.setText("Nivel: 1");
        timerLabel.setText("00:20");
        startTimer();
        startComboTimer();
    }

    /**
     * Handles the Confirmar button click event from FXML.
     */
    @FXML
    private void handleConfirm() {
        onAnswerSubmitted();
    }

    /**
     * Handles the Reiniciar button click event from FXML.
     */
    @FXML
    private void handleRestart() {
        onRestart();
    }

    /**
     * Inner adapter class that provides hover and press visual feedback for buttons.
     * Follows the Adapter pattern to encapsulate mouse event behavior.
     */
    private class ButtonHoverAdapter extends MouseAdapter {

        private final Button button;
        private final String baseStyle;

        /**
         * Constructs a ButtonHoverAdapter for the given button.
         *
         * @param button the button to apply hover effects to
         */
        public ButtonHoverAdapter(Button button) {
            this.button = button;
            this.baseStyle = button.getStyle();
        }

        /** Reduces opacity when the mouse enters the button. */
        public void onMouseEntered() {
            button.setStyle(baseStyle + "-fx-opacity: 0.8;");
        }

        /** Restores the original style when the mouse exits the button. */
        public void onMouseExited() {
            button.setStyle(baseStyle);
        }

        /** Scales down the button slightly when pressed. */
        public void onMousePressed() {
            button.setStyle(baseStyle + "-fx-scale-x: 0.96; -fx-scale-y: 0.96;");
        }

        /** Restores the original style when the mouse is released. */
        public void onMouseReleased() {
            button.setStyle(baseStyle);
        }
    }
}