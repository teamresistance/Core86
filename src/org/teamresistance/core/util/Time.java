package org.teamresistance.core.util;

/**
 * Static class used to track elapsed time between updates.
 * 
 * @author Frank McCoy
 *
 */
public class Time {

	public static final long SECOND = 1000000000L;
	private static double delta;
	private static double previousTime = getTime();
	
	/**
	 * Update the amount of time since the last call to this function.
	 * 
	 * <p>The first invocation of this function <b>may</b> not result in an expected value.
	 */
	public static void update() {
		double currentTime = getTime();
		delta = currentTime - previousTime;
		previousTime = currentTime;
	}
	
	/**
	 * Returns the amount of time passed in seconds between the previous two successive calls to {@link #update()}.
	 * 
	 * @return the amount of time passed in seconds between calss to update()
	 */
	public static double getDelta() {
		return delta;
	}
	
    /**
     * Returns the current value of the running Java Virtual Machine's
     * high-resolution time source, in nanoseconds.
     *
     * <p>This method can only be used to measure elapsed time and is
     * not related to any other notion of system or wall-clock time.
     * The value returned represents nanoseconds since some fixed but
     * arbitrary <i>origin</i> time (perhaps in the future, so values
     * may be negative).  The same origin is used by all invocations of
     * this method in an instance of a Java virtual machine; other
     * virtual machine instances are likely to use a different origin.
     *
     * <p>This method provides nanosecond precision, but not necessarily
     * nanosecond resolution (that is, how frequently the value changes)
     * - no guarantees are made except that the resolution is at least as
     * good as that of {@link System#currentTimeMillis()}.
     *
     * <p>The values returned by this method become meaningful only when
     * the difference between two such values, obtained within the same
     * instance of a Java virtual machine, is computed.
     *
     * @return the current value of the running Java Virtual Machine's
     *         high-resolution time source, in seconds
     */
	public static double getTime() {
		return (double)System.nanoTime() / SECOND;
	}
	
}
