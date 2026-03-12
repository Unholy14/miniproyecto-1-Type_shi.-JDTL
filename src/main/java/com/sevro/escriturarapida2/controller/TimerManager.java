package com.sevro.escriturarapida2.controller;

import com.sevro.escriturarapida2.handler.ITimerListener;
import com.sevro.escriturarapida2.model.GameModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Manages the game countdown timer.
 * Notifies a listener on each tick and when time runs out.
 */
public class TimerManager {
    private final GameModel model;
    private final Label timerLabel;
    private final ITimerListener listener;
    private Timeline timer;

    /**
     * Constructs a TimerManager.
     *
     * @param model      the game model
     * @param timerLabel the label to display the remaining time
     * @param listener   the listener to notify on tick and time up
     */
    public TimerManager(GameModel model, Label timerLabel, ITimerListener listener) {
        this.model = model;
        this.timerLabel = timerLabel;
        this.listener = listener;
    }

    /**
     * Starts the countdown timer.
     */
    public void start() {
        if (timer != null) timer.stop();
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            listener.onTick();
            timerLabel.setText(String.format("00:%02d", model.getCurrentTime()));
            if (model.getCurrentTime() <= 0) {
                timer.stop();
                listener.onTimeUp();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    /**
     * Stops the countdown timer.
     */
    public void stop() {
        if (timer != null) timer.stop();
    }
}

