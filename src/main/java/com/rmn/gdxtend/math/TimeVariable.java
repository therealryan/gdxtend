package com.rmn.gdxtend.math;

import com.badlogic.gdx.math.Interpolation;

/**
 * Abstract superclass for things that vary over time
 *
 * @param <T>
 *          self type
 */
public abstract class TimeVariable<T extends TimeVariable<T>> {

	/**
	 * Time duration of the change
	 */
	protected final Range span = new Range();

	/**
	 * How the input value varies over the time {@link #span}
	 */
	Interpolation interpolator = Interpolation.linear;

	/**
	 * When <code>true</code>, the variable is limited to the specified time
	 * duration.
	 */
	boolean clamped = true;

	protected float time = 0;

	/**
	 * Makes this variable like another
	 *
	 * @param other
	 *          the path to copy
	 * @return this
	 */
	public T from( T other ) {
		span.from( other.span );
		interpolator = other.interpolator;
		clamped = other.clamped;
		return self();
	}

	/**
	 * @param i
	 *          the new {@link Interpolation} to use when converting time values
	 *          to input values
	 * @return this
	 */
	public T by( Interpolation i ) {
		this.interpolator = i;
		return self();
	}

	/**
	 * Sets whether or not this path is limited to the time range
	 *
	 * @param c
	 *          <code>true</code> to clamp time values to the {@link #span}
	 * @return this
	 */
	public T clamp( boolean c ) {
		this.clamped = c;
		return self();
	}

	/**
	 * @param time
	 *          the current time
	 * @return this
	 */
	public T now( float time ) {
		this.time = time;
		return self();
	}

	/**
	 * Sets the end of the time span to some point in the future
	 *
	 * @param duration
	 *          the length of the time span
	 * @return this
	 */
	public T over( float duration ) {
		span.from( time ).lasting( duration );
		return self();
	}

	/**
	 * @return the progress in the time span, from 0 to 1, of the call to
	 *         {@link #now(float)}
	 */
	protected float progress() {
		float t = time;
		if( clamped ) {
			t = span.clamp( t );
		}
		return interpolator.apply( span.unlerp( t ) );
	}

	/**
	 * @return true if the variation is complete
	 */
	public boolean complete() {
		return clamped && span.after( time );
	}

	@SuppressWarnings( "unchecked" )
	protected T self() {
		return (T) this;
	}
}
