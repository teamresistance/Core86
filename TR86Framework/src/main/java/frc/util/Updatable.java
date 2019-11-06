//The Update interface is part of the util folder, a folder of supporting files for the main robot folder

//This interface contains the methods init() and update(), which are used frequently in subsystems so 
//this interface makes the subsystems more uniform.

package frc.util;

public interface Updatable {

	// The init() method of a subsystem is put into robotInit() in Robot.java and
	// sets up variables whenever the robot is turned on
	public void init();

	// The update() method of a subsystem is put into teleopPeriodic() or
	// autonomousPeriodic() in Robot.java
	// and periodically (every 20 ms) does whatever is in the method (button
	// checking, motor activation, etc.)

	public void update();

}
