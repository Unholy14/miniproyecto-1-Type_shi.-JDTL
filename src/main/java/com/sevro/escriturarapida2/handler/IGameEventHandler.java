package com.sevro.escriturarapida2.handler;

/**
 * Interface that defines the contract for handling game events.
 * Decouples game logic from the user interface.
 */
public interface IGameEventHandler {

    /**
     * Called when the player submits an answer.
     */
    void onAnswerSubmitted();

    /**
     * Called when the level timer reaches zero.
     */
    void onTimeOut();

    /**
     * Called when the player restarts the game.
     */
    void onRestart();
}