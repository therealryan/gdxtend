package com.rmn.gdxtend.geom;

import com.rmn.gdxtend.math.Range;

/**
 * Defines how some object's {@link Position} changes over time
 */
public abstract class Path {

	public final Position start = new Position();
	public final Position end = new Position();
	public final Range duration = new Range();

	/**
	 * Computes the position for a given time
	 * 
	 * @param time
	 * @param dest
	 *          the position to populate
	 * @return the position
	 */
	public abstract Position forTime( float time, Position dest );
}
