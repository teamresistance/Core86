package org.teamresistance.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import java.util.logging.XMLFormatter;

import org.teamresistance.core.subsystem.IUpdatable;
import org.teamresistance.core.util.Time;
import org.teamresistance.core.util.Util;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * Base class for all of Team Resistance's robot's which handles control flow
 * and updating all basic utilities.
 * 
 * @author Frank McCoy
 *
 */
public class Robot extends RobotBase {
    protected static String LOG_FILE_PATH = "/home/lvuser/logs/";
    protected static Logger logger = Logger.getLogger(Robot.class.getName());
    static {
        // Initialize log file when class is loaded
        initLogger();
    }

    /**
     * Currently running instance of Robot.
     */
    protected static Robot instance;

    /**
     * User-defined {@link IUpdatable} which handles robot-specific global
     * initialization and updating.
     */
    protected IUpdatable robot;
    /**
     * User-defined {@link IUpdatable} which handles robot-specific autonomous
     * initialization and updating.
     */
    protected IUpdatable autonomous;
    /**
     * User-defined {@link IUpdatable} which handles robot-specific disabled
     * initialization and updating.
     */
    protected IUpdatable disabled;
    /**
     * User-defined {@link IUpdatable} which handles robot-specific teleop
     * initialization and updating.
     */
    protected IUpdatable teleop;
    /**
     * User-defined {@link IUpdatable} which handles robot-specific test
     * initialization and updating.
     */
    protected IUpdatable test;

    private static Class<?> robotClass;
    private static Class<?> autonomousClass;
    private static Class<?> disabledClass;
    private static Class<?> teleopClass;
    private static Class<?> testClass;
    
    private boolean autonomousInitialized;
    private boolean disabledInitialized;
    private boolean teleopInitialized;
    private boolean testInitialized;

    public Robot() {
        instance = this;
        autonomousInitialized = false;
        disabledInitialized = false;
        teleopInitialized = false;
        testInitialized = false;
    }

    @Override
    public void startCompetition() {
        HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_Simple);

        init();

        // Tell the DS that the robot is ready to be enabled
        HAL.observeUserProgramStarting();

        // loop forever, calling the appropriate mode-dependent function
        LiveWindow.setEnabled(false);
        while (true) {
            // Wait for new data to arrive
            m_ds.waitForData();

            update();
            // Call the appropriate function depending upon the current robot mode
            if (isDisabled()) {
                disabledUpdate();
            } else if (isTest()) {
                testUpdate();
            } else if (isAutonomous()) {
                autonomousUpdate();
            } else {
                teleopUpdate();
            }
        }
    }

    /**
     * Called at the beginning of program execution before any other initialization
     * functions.
     */
    private void init() {
        if (robot == null) {
            if(robotClass != null) {
                try {
                    robot = (IUpdatable)robotClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                return;
            }
        } 
        robot.init();
    }

    /**
     * Called while all modes are running but before mode specific updates.
     */
    private void update() {
        Time.update();
        // TODO: Update user input
        if (robot != null)
            robot.update();
    }

    private boolean teleopFirstRun = true;

    /**
     * Called while the "teleop" mode is running.
     */
    private void teleopUpdate() {
        HAL.observeUserProgramTeleop();
        update();
        if(teleop == null && teleopClass != null) {
            try {
                teleop = (IUpdatable)teleopClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (teleop != null) {
            // call teleop.init() if this is the first time
            // we've entered teleop_mode
            if (!teleopInitialized) {
                LiveWindow.setEnabled(false);
                teleop.init();
                teleopInitialized = true;
                testInitialized = false;
                autonomousInitialized = false;
                disabledInitialized = false;
            }
            teleop.update();
        } else {
            if (teleopFirstRun) {
                logger.log(Level.SEVERE, "Teleop not registered!");
            }
            teleopFirstRun = false;
        }
    }

    private boolean autonomousFirstRun = true;

    /**
     * Called while the "autonomous" mode is running.
     */
    private void autonomousUpdate() {
        HAL.observeUserProgramAutonomous();
        update();
        
        if(autonomous == null && autonomousClass != null) {
            try {
                autonomous = (IUpdatable)autonomousClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        if (autonomous != null) {
            // call autonomous.init() if this is the first time
            // we've entered autonomous_mode
            if (!autonomousInitialized) {
                LiveWindow.setEnabled(false);
                autonomous.init();
                autonomousInitialized = true;
                testInitialized = false;
                teleopInitialized = false;
                disabledInitialized = false;
            }
            autonomous.update();
        } else {
            if (autonomousFirstRun) {
                logger.log(Level.SEVERE, "Automous not registered!");
            }
            autonomousFirstRun = false;
        }
    }

    private boolean disabledFirstRun = true;

    /**
     * Called while the "disabled" mode is running.
     */
    private void disabledUpdate() {
        HAL.observeUserProgramDisabled();
        update();
        
        if(disabled == null && disabledClass != null) {
            try {
                disabled = (IUpdatable)disabledClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        if (disabled != null) {
            // call disabled.init() if we are now just entering disabled mode from
            // either a different mode or from power-on
            if (!disabledInitialized) {
                LiveWindow.setEnabled(false);
                disabled.init();
                disabledInitialized = true;
                // reset the initialization flags for the other modes
                autonomousInitialized = false;
                teleopInitialized = false;
                testInitialized = false;
            }
            disabled.update();
        } else {
            if (disabledFirstRun) {
                logger.log(Level.SEVERE, "Disabled not registered!");
            }
        }
    }

    private boolean testFirstRun = true;

    /**
     * Called while the "test" mode is running.
     */
    private void testUpdate() {
        HAL.observeUserProgramTest();
        update();
        
        if(test == null && testClass != null) {
            try {
                test = (IUpdatable)testClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        if (test != null) {
            // call test.init() if we are now just entering test mode from either
            // a different mode or from power-on
            if (!testInitialized) {
                LiveWindow.setEnabled(true);
                test.init();
                testInitialized = true;
                autonomousInitialized = false;
                teleopInitialized = false;
                disabledInitialized = false;
            }
            test.update();
        } else {
            if (testFirstRun) {
                logger.log(Level.SEVERE, "Test not registered!");
            }
        }
    }

    /**
     * Initializes an XML log file for all logged data to be stored within.
     */
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

    /**
     * Returns the currently running instance of Robot.
     * 
     * @return instance of Robot class
     */
    public static Robot getInstance() {
        return instance;
    }
    
    public static void registerRobot(Class<?> robot) {
        robotClass = robot;
    }
    
    public static void registerAutonomous(Class<?> autonomous) {
        autonomousClass = autonomous;
    }
    
    public static void registerTeleop(Class<?> teleop) {
        teleopClass = teleop;
    }

    public static void registerDisabled(Class<?> disabled) {
        disabledClass = disabled;
    }
    
    public static void registerTest(Class<?> test) {
        testClass = test;
    }
    
    /**
     * Returns the user-defined {@link IUpdatable} which handles robot specific
     * global initialization and updating.
     * 
     * @return the user-defined {@link IUpdatable} which handles robot specific
     *         global initialization and updating.
     */
    public IUpdatable getRobot() {
        return robot;
    }

    /**
     * Sets the user-defined {@link IUpdatable} which handles robot specific global
     * initialization and updating.
     * 
     * @param robot
     *            The user-defined {@link IUpdatable} which handles robot specific
     *            global initialization and updating.
     */
    public void setRobot(IUpdatable robot) {
        this.robot = robot;
    }

    /**
     * Returns the user-defined {@link IUpdatable} which handles robot specific
     * autonomous initialization and updating.
     * 
     * @return the user-defined {@link IUpdatable} which handles robot specific
     *         autonomous initialization and updating.
     */
    public IUpdatable getAutonomous() {
        return autonomous;
    }

    /**
     * Sets the user-defined {@link IUpdatable} which handles robot specific
     * autonomous initialization and updating.
     * 
     * @param autonomous
     *            The user-defined {@link IUpdatable} which handles robot specific
     *            autonomous initialization and updating.
     */
    public void setAutonomous(IUpdatable autonomous) {
        this.autonomous = autonomous;
    }

    /**
     * Returns the user-defined {@link IUpdatable} which handles robot specific
     * disabled initialization and updating.
     * 
     * @return the user-defined {@link IUpdatable} which handles robot specific
     *         disabled initialization and updating.
     */
    public IUpdatable getDisabled() {
        return disabled;
    }

    /**
     * Sets the user-defined {@link IUpdatable} which handles robot specific
     * disabled initialization and updating.
     * 
     * @param disabled
     *            The user-defined {@link IUpdatable} which handles robot specific
     *            disabled initialization and updating.
     */
    public void setDisabled(IUpdatable disabled) {
        this.disabled = disabled;
    }

    /**
     * Returns the user-defined {@link IUpdatable} which handles robot specific
     * teleop initialization and updating.
     * 
     * @return the user-defined {@link IUpdatable} which handles robot specific
     *         teleop initialization and updating.
     */
    public IUpdatable getTeleop() {
        return teleop;
    }

    /**
     * Sets the user-defined {@link IUpdatable} which handles robot specific teleop
     * initialization and updating.
     * 
     * @param teleop
     *            The user-defined {@link IUpdatable} which handles robot specific
     *            teleop initialization and updating.
     */
    public void setTeleop(IUpdatable teleop) {
        this.teleop = teleop;
    }

    /**
     * Returns the user-defined {@link IUpdatable} which handles robot specific test
     * initialization and updating.
     * 
     * @return the user-defined {@link IUpdatable} which handles robot specific test
     *         initialization and updating.
     */
    public IUpdatable getTest() {
        return test;
    }

    /**
     * Sets the user-defined {@link IUpdatable} which handles robot specific test
     * initialization and updating.
     * 
     * @param test
     *            The user-defined {@link IUpdatable} which handles robot specific
     *            test initialization and updating.
     */
    public void setTest(IUpdatable test) {
        this.test = test;
    }
}
