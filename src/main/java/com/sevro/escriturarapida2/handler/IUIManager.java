package com.sevro.escriturarapida2.handler;

/**
 * Interface that defines the contract for managing UI updates.
 */
public interface IUIManager {

    /**
     * Updates the word, level and timer labels for a new level.
     */
    void updateLevel();

    /**
     * Displays the game over screen with a message and levels completed.
     *
     * @param message the message to show the player
     */
    void showGameOver(String message);

    /**
     * Resets all UI components to their initial state.
     */
    void resetUI();
}