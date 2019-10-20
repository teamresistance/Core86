//The Time class is part of the util folder, a folder of supporting files for the main robot folder

//This class takes the overall System time on the computer and changes it to seconds (more usable)

package frc.util;

public class Time {

	//A constant to convert the System time to seconds
	public static final long SECOND = 1000000000L;
	private static double delta;
	private static double previousTime = getTime();
	
	//updates the time periodically (put in robotPeriodic/teleopPeriodic/autonomousPeriodic in Robot.java)
	public static void update() {
		double currentTime = getTime();
		delta = currentTime - previousTime;
		previousTime = currentTime;
	}
	
	//returns the change in time between updates (~20 ms)
	public static double getDelta() {
		return delta;
	}
	
	//returns time in seconds
	public static double getTime() {
		return (double)System.nanoTime() / SECOND;
	}
	
}