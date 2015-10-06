package com.rmn.gdxtend.math;

import com.badlogic.gdx.math.Interpolation;

/**
 * Has a low and a high value. Spends some time at each and some time moving
 * from one t'other via some {@link Interpolation} function..
 */
public class Oscillator implements Function {

	private float riseTime = 0.5f;
	private float highTime = 0;
	private float fallTime = 0.5f;
	private float lowTime = 0;

	private float totalTime = 1;

	private float amplitude = 1;
	private float offset = 0;
	private float bias = 0;

	private Interpolation interp = Interpolation.linear;

	/**
	 * Sets the rise time
	 * 
	 * @param r
	 *          how long it takes to transition from low to high
	 * @return this
	 */
	public Oscillator rise( float r ) {
		riseTime = r;
		totalTime = riseTime + highTime + fallTime + lowTime;
		return this;
	}

	/**
	 * Sets the high rest time
	 * 
	 * @param r
	 *          how long we spend at the high value
	 * @return this
	 */
	public Oscillator high( float r ) {
		highTime = r;
		totalTime = riseTime + highTime + fallTime + lowTime;
		return this;
	}

	/**
	 * Sets the fall time
	 * 
	 * @param f
	 *          how long it takes to transition from high to low
	 * @return this
	 */
	public Oscillator fall( float f ) {
		fallTime = f;
		totalTime = riseTime + highTime + fallTime + lowTime;
		return this;
	}

	/**
	 * Sets the low rest time
	 * 
	 * @param l
	 *          how long we spend at the low value
	 * @return this
	 */
	public Oscillator low( float l ) {
		lowTime = l;
		totalTime = riseTime + highTime + fallTime + lowTime;
		return this;
	}

	/**
	 * Sets the frequency of oscillation
	 * 
	 * @param f
	 *          how many full oscillations in a second
	 * @return this
	 */
	public Oscillator frequency( float f ) {
		float m = 1 / ( f * totalTime );
		riseTime *= m;
		highTime *= m;
		fallTime *= m;
		lowTime *= m;

		totalTime = riseTime + highTime + fallTime + lowTime;

		return this;
	}

	/**
	 * Sets the amplitude of the function
	 * 
	 * @param a
	 *          the half-height of the output signal
	 * @return this
	 */
	public Oscillator amplitude( float a ) {
		amplitude = a;
		return this;
	}

	/**
	 * Sets the input offset
	 * 
	 * @param o
	 *          new offset - will be added to input values
	 * @return this
	 */
	public Oscillator offset( float o ) {
		offset = o;
		return this;
	}

	/**
	 * Sets the bias of the output function
	 * 
	 * @param b
	 *          the midpoint value of the wave
	 * @return this
	 */
	public Oscillator bias( float b ) {
		bias = b;
		return this;
	}

	/**
	 * Sets the function with which to transition
	 * 
	 * @param i
	 *          how we get from low to high and high to low
	 * @return this
	 */
	public Oscillator with( Interpolation i ) {
		interp = i;
		return this;
	}

	@Override
	public float map( float f ) {
		f += offset;
		f = Range.normalise( f, 0, totalTime );

		if( f < riseTime ) {
			f /= riseTime;
			return bias + amplitude * ( 2 * interp.apply( f ) - 1 );
		}
		f -= riseTime;

		if( f < highTime ) {
			return bias + amplitude;
		}
		f -= highTime;

		if( f < fallTime ) {
			f /= fallTime;
			return bias + amplitude * ( 2 * ( 1 - interp.apply( f ) ) - 1 );
		}
		f -= fallTime;

		return bias + amplitude * -1;
	}
}
