//The Button class is part of the util folder, a folder of supporting files for the main robot folder

//The Button class includes many state returning methods for buttons to make it easier to detect when
//a button is pushed on the joysticks

//Button objects are mostly used in the JoystickIO class (where all physical Buttons and Joysticks are defined)

package frc.util;

import edu.wpi.first.wpilibj.GenericHID;

public class Button {
	private GenericHID joystick;
	private int buttonID;
	//Test variables for testing button states
	private boolean previousState = false;
	private boolean currentState = false;

	//Creates button object
	public Button(GenericHID joystick, int buttonID) {
		this.joystick = joystick;
		this.buttonID = buttonID;
		this.currentState = joystick.getRawButton(buttonID);
	}

	//Updates state of the button
	public void update() {
		previousState = currentState;
		currentState = joystick.getRawButton(buttonID);
	}

	//Returns True if the button is being pressed
	public boolean isDown() {
		return currentState;
	}	

	//Returns True when the button is pressed
	public boolean onButtonPressed() {
		return currentState && !previousState;
	}

	//Returns True when the button is released
	public boolean onButtonReleased() {
		return previousState && !currentState;
	}
}
