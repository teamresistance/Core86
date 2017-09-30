package org.teamresistance.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Frank McCoy
 *
 */
public class Util {
	
	private static DateFormat simpleDateFormat = new SimpleDateFormat("yyyy MM dd  HH mm ss");
	
	/**
	 * Scales an input value linearly from one range range to another.
	 * 
	 * @param input value to be scaled
	 * @param inLo lower bound for the input range
	 * @param inHi upper bound for the input range
	 * @param outLo lower bound for the output range
	 * @param outHi upper bound for the output range
	 * @return input scaled linearly along the output range
	 */
	public static double span(double input, double inLo, double inHi, double outLo, double outHi) {
		if (input < inLo) {
			return outLo;
		} else if (input > inHi) {
			return outHi;
		} else {
			return (outLo + (((input - inLo) / (inHi - inLo)) * (outHi - outLo)));
		}
	}

	/**
	 * Alejandro's overly simple way of scaling joystick inputs
	 * @param input value to be scaled
	 * @return scaled input value
	 */
	@Deprecated
	public static double scaleInput(double input) {
		double[] scaleArray = { 0.0, 0.16, 0.18, 0.20, 0.24, 0.28, 0.32, 0.36,
                				0.40, 0.45, 0.50, 0.55, 0.62, 0.72, 0.85, 1.00, 1.00 };
        // get the corresponding index for the scaleInput array.
        int index = (int) (input * scaleArray.length);
        // index should be positive.
        if (index < 0) {
            index = -index;
        }
        // index cannot exceed size of array minus 1.
        if (index >= scaleArray.length) {
            index = scaleArray.length - 1;
        }
        // get value from the array.
        double scaled = 0.0;
        if (input < 0) {
            scaled = -scaleArray[index];
        } else {
            scaled = scaleArray[index];
        }
        // return scaled value.
        return scaled;
	}

	/**
	 * Clips an input value at minimum and maximum value.
	 * 
	 * <pre><code>
	 * if (input &lt;= min) {	
	 *     return min;
	 * } else if (input &gt;= max) {
	 *     return max;
	 * } else {
	 *     return input;
	 * }</code></pre>
	 * 
	 * @param input value to be clipped
	 * @param min lower clipping bound
	 * @param max upper clipping bound
	 * @return the clipped value
	 */
	public static double clip(double input, double min, double max) {
		if (input <= min) {
			return min;
		} else if (input >= max) {
			return max;
		} else {
			return input;
		}
	}
	
	/**
	 * Returns the systems current date and time in the format "yyyy MM dd  HH mm ss"
	 * 
	 * @return the systems current date and time in the format "yyyy MM dd  HH mm ss"
	 */
	public static String dateAndTime() {
		return simpleDateFormat.format(new Date());
	}
}
