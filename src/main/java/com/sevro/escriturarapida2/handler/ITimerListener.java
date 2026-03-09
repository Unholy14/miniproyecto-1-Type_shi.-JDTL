package com.sevro.escriturarapida2.handler;

/**
 * Interface that defines the contract for handling game timer events.
 */
public interface ITimerListener {

    /**
     * Called every second when the timer ticks.
     */
    void onTick();

    /**
     * Called when the timer reaches zero.
     */
    void onTimeUp();
}