package org.teamresistance.core.subsystem;

import java.awt.Robot;
import java.util.logging.Logger;

public abstract class Subsystem implements IUpdatable {
	
	protected static Logger logger = Logger.getLogger(Subsystem.class.getName());
	static {
		logger.setParent(Logger.getLogger(Robot.class.getName()));
	}
}
