package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import java.util.ArrayList;

//frc.util is imported so that we are able to use all the interfaces, methods and such from the util folder
import frc.util.*;

public class JoystickIO {
	// These are placeholder ports, replace them with the joystick port and button
	// number in the object instantiation/declaration
	private static int examplePort;
	private static int exampleButtonPort;

	// Making Joystick Objects
	public static Joystick exampleStick = new Joystick(examplePort);

	// ArrayList of Buttons so that it is much easier to update all of the buttons
	// in the update() method through a for loop
	private static ArrayList<Button> buttons = new ArrayList<>();

	// Actual Buttons, usually seperated by which subsystem they are part of or
	// sorted by their use for readability
	public static Button exampleButton = createButton(exampleStick, exampleButtonPort);
	public static Button subsysExButton = createButton(exampleStick, exampleButtonPort);
	public static Button stateSubsysExButton = createButton(exampleStick, exampleButtonPort);

	// This updates all of the buttons using the created array. It should be called
	// from Robot.java
	public static void update() {
		for (Button b : buttons) {
			b.update();
		}
	}

	// This method creates the buttons and adds them to the array of buttons
	private static Button createButton(GenericHID stick, int button) {
		Button newButton = new Button(stick, button);
		buttons.add(newButton);
		return newButton;
	}
}
