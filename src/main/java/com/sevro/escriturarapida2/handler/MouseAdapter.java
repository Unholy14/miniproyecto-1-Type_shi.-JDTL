package com.sevro.escriturarapida2.handler;

import javafx.scene.input.MouseEvent;

/**
 * Adapter class that provides empty default implementations for all mouse events.
 * Classes that only need specific mouse events should extend this class
 * and override only the methods they need.
 */
public class MouseAdapter implements IMouseListener {

    @Override
    public void onMouseEntered(MouseEvent event) {}

    @Override
    public void onMouseExited(MouseEvent event) {}

    @Override
    public void onMousePressed(MouseEvent event) {}

    @Override
    public void onMouseReleased(MouseEvent event) {}
}