package com.rmn.gdxtend.util;

/**
 * Handy for prioritised object comparison
 */
public class Comparison {

	private int d = 0;

	/**
	 * Using this is not thread-safe
	 */
	public static Comparison instance = new Comparison();

	/**
	 * Updates the comparison
	 * 
	 * @param a
	 *          left value
	 * @param b
	 *          right value
	 * @return this
	 */
	public Comparison compare( boolean a, boolean b ) {
		if( d == 0 ) {
			d = Boolean.compare( a, b );
		}
		return this;
	}

	/**
	 * Updates the comparison
	 * 
	 * @param a
	 *          left value
	 * @param b
	 *          right value
	 * @param minimum
	 *          the minimum value - comparison will rank according to distance
	 *          from this value
	 * @return this
	 */
	public Comparison compare( boolean a, boolean b, boolean minimum ) {
		return compare( a != minimum, b != minimum );
	}

	/**
	 * Updates the comparison
	 * 
	 * @param a
	 *          left value
	 * @param b
	 *          right value
	 * @return this
	 */
	public Comparison compare( float a, float b ) {
		if( d == 0 ) {
			d = Float.compare( a, b );
		}
		return this;
	}

	/**
	 * Updates the comparison
	 * 
	 * @param a
	 *          left value
	 * @param b
	 *          right value
	 * @param min
	 *          the minimum value - comparison will rank according to distance
	 *          from this value
	 * @return this
	 */
	public Comparison compare( float a, float b, float min ) {
		float da = a - min;
		float db = b - min;

		// first compare on distance to minimum
		return compare( Math.abs( da ), Math.abs( db ) )
				// if they're equidistant, just go for the smaller value
				.compare( da, db );
	}

	/**
	 * Updates the comparison
	 * 
	 * @param a
	 *          left value
	 * @param b
	 *          right value
	 * @return this
	 */
	public <E extends Enum<E>> Comparison compare( E a, E b ) {
		return compare( a.ordinal(), b.ordinal() );
	}

	/**
	 * Updates the comparison
	 * 
	 * @param a
	 *          left value
	 * @param b
	 *          right value
	 * @param min
	 *          comparison will rank according to distance from this value
	 * @return this
	 */
	public <E extends Enum<E>> Comparison compare( E a, E b, E min ) {
		return compare( a.ordinal(), b.ordinal(), min.ordinal() );
	}

	/**
	 * Updates the comparison
	 * 
	 * @param a
	 *          left value
	 * @param b
	 *          right value
	 * @return this
	 */
	public <C extends Comparable<C>> Comparison compare( C a, C b ) {
		if( d == 0 ) {
			d = a.compareTo( b );
		}
		return this;
	}

	/**
	 * Gets and clears the comparison result
	 * 
	 * @return the comparison result
	 */
	public int result() {
		int result = d;
		d = 0;
		return result;
	}

}
