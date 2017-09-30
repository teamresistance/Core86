package org.teamresistance.core.mathd;

import org.teamresistance.core.configuration.Configurable;

import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * Representation of two dimensional Vector
 * 
 * @author Frank McCoy
 *
 */
@Configurable
public class Vector2d implements LiveWindowSendable {
	
	private ITable table;

	@Configurable
	private double x;
	@Configurable
	private double y;

	/**
	 * Initializes vector with its component all the same value.
	 * 
	 * @param a default value for all dimensions.
	 */
	public Vector2d(double a) {
		this.x = a;
		this.y = a;
	}
	
	/**
	 * Initializes vector with its component values.
	 * 
	 * @param x the value of the x component
	 * @param y the value of the y component
	 */
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the length of the vector.
	 * 
	 * @return the length of the vector
	 */
	public double length() {
		return (double) Math.sqrt(x * x + y * y);
	}

	/**
	 * Returns the dot product(scalar product) of this vector and another vector.
	 * 
	 * <pre><code>
	 * Vector2d n = new Vector2d(1.0, 2.0);
	 * Vector2d r = new Vector2d(1.0, 2.0);
	 * double dotProduct = n.dot(r); // n dot r = n.x * r.x + n.y * r.y
	 * </code></pre>
	 * @param r other vector
	 * @return the dot product of this vector and another vector
	 */
	public double dot(Vector2d r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * Returns a new vector equal to the normalized version of this vector.
	 * 
	 * @return the normalized version of this vector
	 */
	public Vector2d normalized() {
		double length = length();

		return new Vector2d(x / length, y / length);
	}

	/**
	 * Returns a new vector which equals this vector rotated around the z-axis.
	 * 
	 * @param angle angle in degrees to rotate the vector
	 * @return the new rotated vector
	 */
	public Vector2d rotate(double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);

		return new Vector2d((double) (x * cos - y * sin), (double) (x * sin + y * cos));
	}

	/**
	 * Performs componentwise addition of this and another vector and returns the result as a new vector.
	 * 
	 * <pre><code>
	 * Vector2d n = new Vector2d(1.0, 2.0);
	 * Vector2d r = new Vector2d(3.0, 4.0);
	 * Vector2d sum = n.add(r); // n + r = &lt;n.x + r.x, n.y + r.y&gt;
	 * </code></pre>
	 * 
	 * @param r other vector
	 * @return the new vector equal to the componentwise sum of this and another vector
	 */
	public Vector2d add(Vector2d r) {
		return new Vector2d(x + r.getX(), y + r.getY());
	}

	/**
	 * Performs componentwise addition of this and another vector with components x and y and returns the result as a new vector.
	 * 
	 * <pre><code>
	 * Vector2d n = new Vector2d(1.0, 2.0);
	 * Vector2d sum = n.add(3.0, 4.0); // n + &lt;3.0, 4.0&gt; = &lt;n.x + 3.0, n.y + 4.0&gt;
	 * </code></pre>
	 * 
	 * @param x x component of other vector
	 * @param y y component of other vector
	 * @return the new vector equal to the componentwise sum of this and another vector
	 */
	public Vector2d add(double x, double y) {
		return new Vector2d(this.x + x, this.y + y);
	}
	
	/**
	 * Adds a value to all components of the vector and returns the result as a new vector
	 * 
	 * <pre><code>
	 * Vector2d n = new Vector2d(1.0, 2.0);
	 * Vector2d sum = n.add(3.0); // n + &lt;3.0, 3.0&gt; = &lt;n.x + 3.0, n.y + 3.0&gt;
	 * </code></pre>
	 * 
	 * @param r value added to all components of this vector
	 * @return a new vector with r added to all components
	 */
	public Vector2d add(double r) {
		return new Vector2d(x + r, y + r);
	}

	/**
	 * Performs componentwise subtraction of this and another vector and returns the result as a new vector.
	 * 
	 * <pre><code>
	 * Vector2d n = new Vector2d(1.0, 2.0);
	 * Vector2d r = new Vector2d(3.0, 4.0);
	 * Vector2d difference = n.sub(r); // n - r = &lt;n.x - r.x, n.y - r.y&gt;
	 * </code></pre>
	 * 
	 * @param r other vector
	 * @return the new vector equal to the componentwise difference of this and another vector
	 */
	public Vector2d sub(Vector2d r) {
		return new Vector2d(x - r.getX(), y - r.getY());
	}

	/**
	 * Performs componentwise subtraction of this and another vector with components x and y and returns the result as a new vector.
	 * 
	 * <pre><code>
	 * Vector2d n = new Vector2d(1.0, 2.0);
	 * Vector2d difference = n.sub(3.0, 4.0); //n - &lt;3.0, 4.0&gt; = &lt;n.x - 3.0, n.y - 4.0&gt;
	 * </code></pre>
	 * 
	 * @param x x component of other vector
	 * @param y y component of other vector
	 * @return the new vector equal to the componentwise difference of this and another vector
	 */
	public Vector2d sub(double x, double y) {
		return new Vector2d(this.x - x, this.y - y);
	}
	
	/**
	 * Subtracts a value from all components of the vector and returns the result as a new vector
	 * 
	 * <pre><code>
	 * Vector2d n = new Vector2d(1.0, 2.0);
	 * Vector2d difference = n.sub(3.0); // n - &lt;3.0, 3.0&gt; = &lt;n.x - 3.0, n.y - 3.0&gt;
	 * </code></pre>
	 * 
	 * @param r value subtracted from all components of this vector
	 * @return a new vector with r added to all components
	 */
	public Vector2d sub(double r) {
		return new Vector2d(x - r, y - r);
	}

	/**
	 * Performs componentwise multiplication of this and another vector and returns the result as a new vector.
	 * 
	 * <p>NOTE: this is not a valid form of multiplication within linear algebra
	 * 
	 * <pre><code>
	 * Vector2d n = new Vector2d(1.0, 2.0);
	 * Vector2d r = new Vector2d(3.0, 4.0);
	 * Vector2d product = n.mul(r); // &lt;n.x * r.x, n.y * r.y&gt;
	 * </code></pre>
	 * 
	 * @param r other vector
	 * @return the new vector equal to the componentwise multiplication of this and another vector
	 */
	public Vector2d mul(Vector2d r) {
		return new Vector2d(x * r.getX(), y * r.getY());
	}

	/**
	 * Performs componentwise multiplication of this and another vector with components x and y and returns the result as a new vector.
	 * 
	 * <p>NOTE: this is not a valid form of multiplication within linear algebra
	 * 
	 * <pre><code>
	 * Vector2d n = new Vector2d(1.0, 2.0);
	 * Vector2d product = n.mul(3.0, 4.0); // n - &lt;3.0, 4.0&gt; = &lt;n.x - 3.0, n.y - 4.0&gt;
	 * </code></pre>
	 * 
	 * @param x x component of other vector
	 * @param y y component of other vector
	 * @return the new vector equal to the componentwise multiplication of this and another vector
	 */
	public Vector2d mul(double x, double y) {
		return new Vector2d(this.x * x, this.y * y);
	}
	
	/**
	 * Scales this vector by a value and returns the result as a new vector
	 * 
	 * <pre><code>
	 * Vector2d n = new Vector2d(1.0, 2.0);
	 * Vector2d product = n.mul(3.0); // n * 3.0 = &lt;n.x * 3.0, n.y * 3.0&gt;
	 * </code></pre>
	 * 
	 * @param r value multiplied by all components of this vector
	 * @return a new vector equal to this vector scaled by a value
	 */
	public Vector2d mul(double r) {
		return new Vector2d(x * r, y * r);
	}

	/**
	 * Performs componentwise division of this and another vector and returns the result as a new vector.
	 * 
	 * <p>NOTE: this is not a valid within linear algebra
	 * 
	 * <pre><code>
	 * Vector2d n = new Vector2d(1.0, 2.0);
	 * Vector2d r = new Vector2d(3.0, 4.0);
	 * Vector2d product = n.div(r); // &lt;n.x / r.x, n.y / r.y&gt;
	 * </code></pre>
	 * 
	 * @param r dividend
	 * @return the new vector equal to the componentwise quotients of this and another vector
	 */
	public Vector2d div(Vector2d r) {
		return new Vector2d(x / r.getX(), y / r.getY());
	}
	
	/**
	 * Performs componentwise division of this and another vector with components x and y and returns the result as a new vector.
	 * 
	 * <p>NOTE: this is not a valid within linear algebra
	 * 
	 * <pre><code>
	 * Vector2d n = new Vector2d(1.0, 2.0);
	 * Vector2d product = n.div(3.0, 4.0); // &lt;n.x / 3.0, n.y / 4.0&gt;
	 * </code></pre>
	 * 
	 * @param x x component of other vector
	 * @param y y component of other vector
	 * @return the new vector equal to the componentwise division of this and another vector
	 */
	public Vector2d div(double x, double y) {
		return new Vector2d(this.x / x, this.y / y);
	}

	/**
	 * Scales this vector by the reciprocal of a value and returns the result as a new vector.
	 * 
	 * <pre><code>
	 * Vector2d n = new Vector2d(1.0, 2.0);
	 * Vector2d product = n.mul(3.0); // n / 3.0 = &lt;n.x / 3.0, n.y / 3.0&gt;
	 * </code></pre>
	 * 
	 * @param r dividend
	 * @return a new vector equal to this vector scaled by the reciprocal of a value
	 */
	public Vector2d div(double r) {
		return new Vector2d(x / r, y / r);
	}

	/**
	 * Returns a new vector with components equal to the absolute value of the components of this vector.
	 * 
	 * @return a new vector with components equal to the absolute value of the components of this vector
	 */
	public Vector2d abs() {
		return new Vector2d(Math.abs(x), Math.abs(y));
	}

	@Override
	public String toString() {
		return x + "," + y;
	}

	/**
	 * Attempts to take a string in the form of x,y and store it within this vector.
	 * 
	 * @param sVec String version of a vector
	 */
	public void fromString(String sVec) {
		String[] coords = sVec.split(",");
		if (coords.length != 2) {
			System.err.println("Invalid vector in form of string!");
			new Exception().printStackTrace();
			System.exit(1);
		}
		try {
			setX(Double.parseDouble(coords[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			setY(Double.parseDouble(coords[1]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns true if all corresponding components of this and another vector are equal.
	 * 
	 * @param r other vector
	 * @return true if corresponding components of this and another vector are equal
	 */
	public boolean equals(Vector2d r) {
		return (this.x == r.getX() && this.y == r.getY());
	}

	/**
	 * Returns x component.
	 * 
	 * @return x component
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets x component.
	 * 
	 * @param x x component
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Returns y component.
	 * 
	 * @return y component
	 */
	public double getY() {
		return y;
	}

	/**
	 * Sets y component.
	 * 
	 * @param y y component
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Returns a new vector with components in the order given by the name of the vector.
	 * <pre>
	 * Given a vector n = &lt;x,y&gt;
	 * n.getYX() = &lt;y,x&gt;
	 * </pre>
	 * @return swizzled vector
	 */
	public Vector2d getXY() { 
		return new Vector2d(getX(), getY()); 
	}
	/**
	 * Returns a new vector with components in the order given by the name of the vector.
	 * <pre>
	 * Given a vector n = &lt;x,y&gt;
	 * n.getYX() = &lt;y,x&gt;
	 * </pre>
	 * @return swizzled vector
	 */
	public Vector2d getYX() { 
		return new Vector2d(getY(), getX()); 
	}

	@Override
	public void initTable(ITable subtable) {
		this.table = subtable;
		updateTable();
	}

	@Override
	public ITable getTable() {
		return table;
	}

	@Override
	public String getSmartDashboardType() {
		return "Vector2d";
	}

	@Override
	public void updateTable() {
		if(table == null) return;
		table.putNumber("X", getX());
		table.putNumber("Y", getY());
	}

	@Override
	public void startLiveWindowMode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopLiveWindowMode() {
		// TODO Auto-generated method stub
		
	}

}
