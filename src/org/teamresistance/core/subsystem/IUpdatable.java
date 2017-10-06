package org.teamresistance.core.subsystem;

/**
 * The IUpdatable interface should be implemented by all classes which need to
 * be initialized and updated at specific times within program execution.
 * 
 * @author Frank McCoy
 *
 */
public interface IUpdatable {
    /**
     * Called to handle initialization of state.
     */
    void init();

    /**
     * Called to handle recurring updates.
     */
    void update();
}
