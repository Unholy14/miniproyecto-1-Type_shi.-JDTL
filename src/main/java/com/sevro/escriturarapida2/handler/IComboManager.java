package com.sevro.escriturarapida2.handler;

/**
 * Interface that defines the contract for managing the combo system.
 */
public interface IComboManager {

    /**
     * Increases the combo rank by one level.
     */
    void increaseCombo();

    /**
     * Decreases the combo rank by one level.
     */
    void decreaseCombo();

    /**
     * Resets the combo rank to the lowest level.
     */
    void resetCombo();

    /**
     * Updates the combo visual display.
     */
    void updateCombo();

    /**
     * Starts the automatic combo decrease timer.
     */
    void startComboTimer();

    /**
     * Stops the automatic combo decrease timer.
     */
    void stopComboTimer();
}