package com.sevro.escriturarapida2.controller;

import com.sevro.escriturarapida2.handler.IGameEventHandler;
import com.sevro.escriturarapida2.handler.ITimerListener;
import com.sevro.escriturarapida2.model.GameModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * MVC Controller for the "Escritura Rapida" game.
 * Coordinates TimerManager, ComboManager, UIManager and GameModel.
 */
public class GameController implements Initializable, IGameEventHandler, ITimerListener {

    @FXML private Label wordLabel;
    @FXML private Label timerLabel;
    @FXML private TextField answerField;
    @FXML private Button confirmButton;
    @FXML private Label feedbackLabel;
    @FXML private Button restartButton;
    @FXML private Label levelLabel;
    @FXML private Label comboLabel;

    private GameModel model;
    private TimerManager timerManager;
    private ComboManager comboManager;
    private UIManager uiManager;
    private long levelStartTime;

    /**
     * Called automatically by JavaFX after the FXML is loaded.
     *
     * @param url            the location of the FXML file
     * @param resourceBundle the resource bundle for localization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new GameModel();
        timerManager = new TimerManager(model, timerLabel, this);
        comboManager = new ComboManager(model, comboLabel);
        uiManager = new UIManager(model, wordLabel, timerLabel, levelLabel,
                feedbackLabel, confirmButton, restartButton, answerField);

        model.loadNewWord();
        uiManager.updateLevel();

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

        timerManager.start();
        comboManager.startComboTimer();
        levelStartTime = System.currentTimeMillis();
    }

    @Override
    public void onTick() {
        model.tickTimer();
    }

    @Override
    public void onTimeUp() {
        String answer = answerField.getText();
        boolean correct = model.validateAnswer(answer);

        if (correct) {
            comboManager.decreaseCombo();
            model.advanceLevel();
            uiManager.updateLevel();
            timerManager.start();
            comboManager.startComboTimer();
            levelStartTime = System.currentTimeMillis();
        } else {
            onTimeOut();
        }
    }

    @Override
    public void onAnswerSubmitted() {
        String answer = answerField.getText();
        boolean correct = model.validateAnswer(answer);

        if (correct) {
            timerManager.stop();
            long elapsed = (System.currentTimeMillis() - levelStartTime) / 1000;
            if (elapsed <= GameModel.COMBO_THRESHOLD) {
                comboManager.increaseCombo();
            } else {
                comboManager.decreaseCombo();
            }
            model.advanceLevel();
            uiManager.updateLevel();
            timerManager.start();
            comboManager.startComboTimer();
            levelStartTime = System.currentTimeMillis();
        } else {
            timerManager.stop();
            comboManager.resetCombo();
            uiManager.showGameOver("¡Incorrecto! Era: " + model.getCurrentWord());
            comboManager.stopComboTimer();
        }
    }

    @Override
    public void onTimeOut() {
        comboManager.resetCombo();
        uiManager.showGameOver("¡Tiempo agotado!");
        comboManager.stopComboTimer();
    }

    @Override
    public void onRestart() {
        model.reset();
        model.loadNewWord();
        uiManager.resetUI();
        comboManager.resetCombo();
        timerManager.start();
        comboManager.startComboTimer();
        levelStartTime = System.currentTimeMillis();
    }


    /** Handles the Confirmar button click. */
    @FXML
    private void handleConfirm() {
        onAnswerSubmitted();
    }

    /** Handles the Reiniciar button click. */
    @FXML
    private void handleRestart() {
        onRestart();
    }

    /**
     * Inner class that extends MouseAdapter to provide hover and press
     * visual feedback for buttons.
     */
    private class ButtonHoverAdapter extends com.sevro.escriturarapida2.handler.MouseAdapter {

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

        @Override
        public void onMouseEntered(javafx.scene.input.MouseEvent event) {
            button.setStyle(baseStyle + "-fx-opacity: 0.8;");
        }

        @Override
        public void onMouseExited(javafx.scene.input.MouseEvent event) {
            button.setStyle(baseStyle);
        }

        @Override
        public void onMousePressed(javafx.scene.input.MouseEvent event) {
            button.setStyle(baseStyle + "-fx-scale-x: 0.96; -fx-scale-y: 0.96;");
        }

        @Override
        public void onMouseReleased(javafx.scene.input.MouseEvent event) {
            button.setStyle(baseStyle);
        }
    }
}