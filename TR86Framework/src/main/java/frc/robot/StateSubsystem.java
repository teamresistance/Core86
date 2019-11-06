//Subsystems are the seperate files that program seperate parts for the bot

//A subsystem with a switch statement (state machine) is more useful for arms or anything that has multiple positions
//it needs to be in.

package frc.robot;

//must have CTRE vendor package installed
import edu.wpi.first.wpilibj.Talon;

//imported so we can use the Updatable interface because of how common the methods (init() and update()) are
//look there for explanations of init and update
import frc.util.Updatable;

public class StateSubsystem implements Updatable {

    // An example motor used for this subsystem, any motors and sensors used go here
    private Talon stateSubsystemTalon;

    // Any instance variables that define key parts of the subsystem (speed, any
    // checkers) go here
    // An example instance variable for this subsytem that defines the speed that
    // the motor should go
    private double subsysSpeed;

    // The constructor of a Subsystem takes in whatever motors and sensors that the
    // subsystem uses and...
    public StateSubsystem(Talon eStateSubsystemTalon) {
        // ...takes the inputs and uses them to define the motor and sensor instance
        // variables
        subsystemTalon = eSubsystemTalon;
    }

    @Override
    public void init() {
        // anything in here is done when the robot turns on
        subsystemTalon.set(0);
        subsysSpeed = .7;
    }

    @Override
    public void update() {
        // This is a very simple subsystem
        // It checks if the button is being held down, and when it is down, it moves the
        // motor at a set speed (subsysSpeed)
        // if the button is not down, the motor doesn't activate. Subsystems can become
        // much more complex than this.

        if (JoystickIO.subsysExButton.isDown()) {
            subsystemTalon.set(subsysSpeed);
        } else {
            subsystemTalon.set(0);
        }
    }

}