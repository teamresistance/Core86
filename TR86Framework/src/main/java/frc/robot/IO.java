//The IO class is where all of the physical motors, sensors, and solenoids are delcared and initialized.
//Having them all in one class makes it easier to initialize them as well as makes it easier to read.

package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Relay;

import frc.util.InvertibleDigitalInput;
import frc.util.InvertibleSolenoid;
import frc.util.NavX;

//must have CTRE vendor package installed
import edu.wpi.first.wpilibj.Talon;

public class IO {
    private static int examplePort;
    private static int exampleModule;

    //Talons are the general motors for the bot
    public static Talon exampleLeftTalon = new Talon(examplePort);
    public static Talon exampleRightTalon = new Talon(examplePort);

    //The NavX is the gyro sensor of the robot
    public static NavX exampleNavX = new NavX();

    //The Compressor and Relay are used in conjunction with all solenoids on the robot
    public static Compressor exampleCompressor = new Compressor(examplePort);
    public static Relay exampleCompressorRelay = new Relay(examplePort);

    //Solenoids are pneumatic activators (require a module and a port and can be inverted)
    public static InvertibleSolenoid exampleSole = new InvertibleSolenoid(exampleModule, examplePort);
    public static InvertibleSolenoid exampleInvSole = new InvertibleSolenoid(exampleModule, examplePort, true);

    //Digital Inputs are any sensors, LEDs, etc. (can be inverted)
    public static InvertibleDigitalInput exampleDigInput = new InvertibleDigitalInput(examplePort, false);
    public static InvertibleDigitalInput exampInvDigInput = new InvertibleDigitalInput(examplePort, true);
}