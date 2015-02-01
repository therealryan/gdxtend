package com.rmn.gdxtend.util;

import com.badlogic.gdx.graphics.Color;

/**
 * Handy for prioritised object comparison
 */
public class Comparison {

	private int d = 0;

	public static Comparison instance = new Comparison();

	/**
	 * Updates the comparison
	 * 
	 * @param a
	 * @param b
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
	 * @param b
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
	 * @param b
	 * @return this
	 */
	public <E extends Enum<E>> Comparison compare( E a, E b ) {
		return compare( a.ordinal(), b.ordinal() );
	}

	/**
	 * Updates the comparison
	 * 
	 * @param a
	 * @param b
	 * @return this
	 */
	public <C extends Comparable<C>> Comparison compare( C a, C b ) {
		if( d == 0 ) {
			d = a.compareTo( b );
		}
		return this;
	}

	/**
	 * Updates the comparison
	 * 
	 * @param a
	 * @param b
	 * @return this
	 */
	public Comparison compare( Color a, Color b ) {
		return compare( a.r, b.r )
				.compare( a.g, b.g )
				.compare( a.b, b.b )
				.compare( a.a, b.a );
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
