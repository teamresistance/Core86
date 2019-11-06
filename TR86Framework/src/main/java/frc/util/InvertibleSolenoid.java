//The InvertibleSolenoid class is part of the util folder, a folder of supporting files for the main robot folder

//Similar to the InvertibleDigitalInput class, this class makes the creation of Solenoids easier and allows them to be inverted
//easily.

package frc.util;

import edu.wpi.first.wpilibj.Solenoid;

public class InvertibleSolenoid extends Solenoid implements ISolenoid {

  private final boolean isInverted;

  // Default Solenoid, not inverted
  public InvertibleSolenoid(int module, int channel) {
    this(module, channel, false);
  }

  // Makes Solenoid Object
  public InvertibleSolenoid(int module, int channel, boolean isInverted) {
    super(module, channel);
    this.isInverted = isInverted;
  }

  // This activates the Solenoid
  @Override
  public void set(boolean state) {
    if (isInverted) {
      super.set(!state);
    } else {
      super.set(state);
    }
  }

}
