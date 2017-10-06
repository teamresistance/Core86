package org.teamresistance.core.util.joystick;

import edu.wpi.first.wpilibj.GenericHID;

/**
 * Stores the state of a button on a {@link GenericHID} and allows the user to
 * query its state.
 * 
 * @author Frank McCoy
 *
 */
public class Button {

    private GenericHID joystick;
    private int buttonID;

    private boolean previousState = false;
    private boolean currentState = false;

    /**
     * Instantiate a new button on a given {@link GenericHID}
     * 
     * @param joystick
     *            the joystick from which to get data
     * @param buttonID
     *            the button on the {@link GenericHID} that this object represents
     */
    public Button(GenericHID joystick, int buttonID) {
        this.joystick = joystick;
        this.buttonID = buttonID;
        this.currentState = joystick.getRawButton(buttonID);
    }

    /**
     * Updates the current and previous states of this button.
     */
    public void update() {
        previousState = currentState;
        currentState = joystick.getRawButton(buttonID);
    }

    /**
     * Returns whether this button is currently being held down.
     * 
     * @return whether this button is currently being held down
     */
    public boolean isDown() {
        return currentState;
    }

    /**
     * Returns whether this button was pressed sometime between the current and
     * previous updates.
     * 
     * @return whether this button was pressed sometime between the current and
     *         previous updates
     */
    public boolean onButtonPressed() {
        return currentState && !previousState;
    }

    /**
     * Returns whether this button was released sometime between the current and
     * previous updates.
     * 
     * @return whether this button was released sometime between the current and
     *         previous updates
     */
    public boolean onButtonReleased() {
        return previousState && !currentState;
    }
}
