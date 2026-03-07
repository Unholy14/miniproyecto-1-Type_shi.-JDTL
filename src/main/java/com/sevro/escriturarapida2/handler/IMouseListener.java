package com.sevro.escriturarapida2.handler;

import javafx.scene.input.MouseEvent;

    /**
     * Interface that defines the contract for handling mouse events on UI components.
     */
    public interface IMouseListener {

        /**
         * Called when the mouse enters a node.
         * @param event the mouse event
         */
        void onMouseEntered(MouseEvent event);

        /**
         * Called when the mouse exits a node.
         * @param event the mouse event
         */
        void onMouseExited(MouseEvent event);

        /**
         * Called when the mouse is pressed on a node.
         * @param event the mouse event
         */
        void onMousePressed(MouseEvent event);

        /**
         * Called when the mouse is released on a node.
         * @param event the mouse event
         */
        void onMouseReleased(MouseEvent event);
    }

