package com.rmn.gdxtend.math;

/**
 * Controls how a single float varies over time
 */
public class FloatVariable extends TimeVariable<FloatVariable> {

	/**
	 * The value range
	 */
	private final Range value = new Range();

	/**
	 * Sets the value range
	 *
	 * @param from
	 *          start value
	 * @param to
	 *          end value
	 * @return this
	 */
	public FloatVariable set( float from, float to ) {
		value.from( from ).to( to );
		return this;
	}

	/**
	 * Sets a new start value
	 *
	 * @param start
	 *          start value
	 * @return this
	 */
	public FloatVariable from( float start ) {
		value.from( start );
		return this;
	}

	/**
	 * Sets a new destination, maintaining a continuous range and velocity
	 *
	 * @param target
	 *          target value
	 * @return this
	 */
	public FloatVariable to( float target ) {
		value.from( get() ).to( target );
		span.from( time );
		return this;
	}

	/**
	 * @return the current value
	 */
	public float get() {
		return value.lerp( progress() );
	}
}
