//The NavX class is part of the util folder, a folder of supporting files for the main robot folder

//The NavX class allows us to read the NavX gyro sensor on the roboRIO and use the values for driving and other things

package frc.util;

//Must have kuaiLabs vendor library installed
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class NavX {
	// Declare a variable using SPI connector
	public AHRS ahrs = new AHRS(SPI.Port.kMXP);

	// Returns actual Z position
	public double getAngle() {
		return ahrs.getAngle();
	}

	// Returns a normalized Z position between 0 and 360 degrees
	public double getNormalizedAngle() {
		return ((ahrs.getAngle() % 360) + 360) % 360;
	}

	// Resets Gyro to 0 degrees.
	public void reset() {
		ahrs.reset();
	}

	// Returns variable
	public AHRS getAHRS() {
		return ahrs;
	}

}
