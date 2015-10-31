package com.rmn.gdxtend.geom;

import com.badlogic.gdx.math.Vector3;
import com.rmn.gdxtend.math.TimeVariable;

/**
 * Defines how some object's position changes over time
 *
 * @param <T>
 *          self type
 */
public abstract class Path<T extends Path<T>> extends TimeVariable<T> {

	/**
	 * Start point
	 */
	public final Vector3 start = new Vector3();

	/**
	 * End point
	 */
	public final Vector3 end = new Vector3();

	/**
	 * Makes this path like another
	 *
	 * @param other
	 *          the path to copy
	 * @return this
	 */
	@Override
	public T from( T other ) {
		start.set( other.start );
		end.set( other.end );
		return super.from( other );
	}

	/**
	 * Computes the position for a given time
	 *
	 * @param dest
	 *          the position to populate
	 * @return the computed position
	 */
	public final Vector3 get( Vector3 dest ) {
		return forProgress( progress(), dest );
	}

	/**
	 * Computes the position for a given progress quotient, where 0 = start point
	 * and 1 = end point
	 *
	 * @param p
	 *          progress on the path
	 * @param dest
	 *          point to populate with the result
	 * @return the result
	 */
	public abstract Vector3 forProgress( float p, Vector3 dest );

	/**
	 * Gives the path a new destination. The new start point is the current
	 * location.
	 *
	 * @param x
	 *          x coord
	 * @param y
	 *          y coord
	 * @param z
	 *          z coord
	 * @return this
	 */
	public T to( float x, float y, float z ) {
		get( start );
		end.set( x, y, z );
		span.from( time );
		return self();
	}

	@Override
	@SuppressWarnings( "unchecked" )
	protected T self() {
		return (T) this;
	}

	@Override
	public String toString() {
		return "From " + start + " to " + end + " over " + span;
	}
}
