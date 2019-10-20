//The Update interface is part of the util folder, a folder of supporting files for the main robot folder

//The methods init() and update() are used frequently in subsystems so this interface makes the subsystems more uniform

package frc.util;

public interface Updatable {
	
	public void init();
	
	public void update();

}
