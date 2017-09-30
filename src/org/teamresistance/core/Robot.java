package org.teamresistance.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import java.util.logging.XMLFormatter;

import org.teamresistance.core.subsystem.IUpdatable;
import org.teamresistance.core.util.Util;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	protected static String LOG_FILE_PATH = "D:\\Users\\jaxfr\\Desktop\\";
	protected static Logger logger = Logger.getLogger(Robot.class.getName());
	static {
		initLogger();
	}

	protected IUpdatable robot;
	protected IUpdatable autonomous;
	protected IUpdatable disabled;
	protected IUpdatable teleop;
	protected IUpdatable test;
	
	@Override
	public void robotInit() {
		if(robot != null) {
			robot.init();
		}
	}
	
	@Override
	public void disabledInit() {
		if(disabled != null) {
			disabled.init();
		}
	}
	
	@Override
	public void autonomousInit() {
		if(autonomous != null) {
			autonomous.init();
		}
	}
	
	@Override
	public void teleopInit() {
		if(teleop != null) {
			teleop.init();
		}
	}
	
	@Override
	public void testInit() {
		if(test != null) {
			test.init();
		}
	}
	
	@Override
	public void robotPeriodic() {
		if(robot != null) {
			robot.update();
		}
	}
	
	@Override
	public void disabledPeriodic() {
		if(disabled != null) {
			disabled.update();
		}
	}
	
	@Override
	public void autonomousPeriodic() {
		if(autonomous != null) {
			autonomous.update();
		}
	}
	
	@Override
	public void teleopPeriodic() {
		if(teleop != null) {
			teleop.update();
		}
	}
	
	@Override
	public void testPeriodic() {
		if(test != null) {
			test.update();
		}
	}

	protected static void initLogger() {
		OutputStream os = null; 
		try {
			os = new FileOutputStream(new File(LOG_FILE_PATH + Util.dateAndTime() + ".xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Formatter formatter = new XMLFormatter();
		Handler handler = new StreamHandler(os, formatter);
		logger.addHandler(handler);
	}
	
	public IUpdatable getRobot() {
		return robot;
	}

	public void setRobot(IUpdatable robot) {
		this.robot = robot;
	}

	public IUpdatable getAutonomous() {
		return autonomous;
	}

	public void setAutonomous(IUpdatable autonomous) {
		this.autonomous = autonomous;
	}

	public IUpdatable getDisabled() {
		return disabled;
	}

	public void setDisabled(IUpdatable disabled) {
		this.disabled = disabled;
	}

	public IUpdatable getTeleop() {
		return teleop;
	}

	public void setTeleop(IUpdatable teleop) {
		this.teleop = teleop;
	}

	public IUpdatable getTest() {
		return test;
	}

	public void setTest(IUpdatable test) {
		this.test = test;
	}

}
