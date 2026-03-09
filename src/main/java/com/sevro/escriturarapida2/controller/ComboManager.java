package com.sevro.escriturarapida2.controller;

import com.sevro.escriturarapida2.handler.IComboManager;
import com.sevro.escriturarapida2.model.GameModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Manages the combo system including rank tracking,
 * visual updates and automatic decrease over time.
 */
public class ComboManager implements IComboManager {

    private final GameModel model;
    private final Label comboLabel;
    private Timeline shrinkAnimation;
    private Timeline comboTimer;

    /**
     * Constructs a ComboManager with the given model and label.
     *
     * @param model      the game model
     * @param comboLabel the label to display the combo rank
     */
    public ComboManager(GameModel model, Label comboLabel) {
        this.model = model;
        this.comboLabel = comboLabel;
    }

    @Override
    public void increaseCombo() {
        model.increaseCombo();
        updateCombo();
    }

    @Override
    public void decreaseCombo() {
        model.decreaseCombo();
        updateCombo();
    }

    @Override
    public void resetCombo() {
        model.resetCombo();
        updateCombo();
    }

    @Override
    public void updateCombo() {
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

    @Override
    public void startComboTimer() {
        if (comboTimer != null) comboTimer.stop();
        comboTimer = new Timeline(new KeyFrame(Duration.seconds(GameModel.COMBO_THRESHOLD), e -> {
            decreaseCombo();
            startComboTimer();
        }));
        comboTimer.setCycleCount(1);
        comboTimer.play();
    }

    @Override
    public void stopComboTimer() {
        if (comboTimer != null) comboTimer.stop();
    }
}