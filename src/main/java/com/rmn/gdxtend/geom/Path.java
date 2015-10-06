package com.rmn.gdxtend.geom;

import com.rmn.gdxtend.math.Range;

/**
 * Defines how some object's {@link Position} changes over time
 *
 * @param <T>
 *          self type
 */
public abstract class Path<T extends Path<T>> {

	public final Position start = new Position();
	public final Position end = new Position();
	public final Range duration = new Range();

	/**
	 * When <code>true</code>, the path is limited to the specified time duration.
	 */
	public boolean clamped = true;

	/**
	 * Makes this path like another
	 * 
	 * @param other
	 *          the path to copy
	 * @return this
	 */
	public T from( T other ) {
		start.from( other.start );
		end.from( other.end );
		duration.from( other.duration );
		clamped = other.clamped;
		return self();
	}

	/**
	 * Sets whether or not this path is limited to the time range
	 * 
	 * @param c
	 *          <code>true</code> to clamp time values to the {@link #duration}
	 * @return this
	 */
	public T clamp( boolean c ) {
		this.clamped = c;
		return self();
	}

	/**
	 * Computes the position for a given time
	 * 
	 * @param time
	 *          The time for which to compute the position
	 * @param dest
	 *          the {@link Position} to populate
	 * @return the computed {@link Position}
	 */
	public final Position forTime( float time, Position dest ) {
		if( clamped ) {
			time = duration.clamp( time );
		}
		return compute( time, dest );
	}

	/**
	 * Implement this to compute a position on the path for a given time
	 * 
	 * @param time
	 *          The time
	 * @param dest
	 *          the {@link Position} to populate
	 * @return the computed {@link Position}
	 */
	protected abstract Position compute( float time, Position dest );

	@SuppressWarnings( "unchecked" )
	protected T self() {
		return (T) this;
	}
}
