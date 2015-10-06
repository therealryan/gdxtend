package com.rmn.gdxtend.math;

import com.badlogic.gdx.math.Interpolation;

/**
 * A numerical range
 */
public class Range {

	public float from, to;

	public float length() {
		return Math.abs( to - from );
	}

	/**
	 * Copies a range
	 * 
	 * @param r
	 *          the {@link Range} to copy
	 * @return this
	 */
	public Range from( Range r ) {
		return from( r.from ).to( r.to );
	}

	/**
	 * Sets the start of the range
	 * 
	 * @param f
	 *          the start of the range
	 * @return this
	 */
	public Range from( float f ) {
		from = f;
		return this;
	}

	/**
	 * Sets the end of the range
	 * 
	 * @param f
	 *          the end of the range
	 * @return this
	 */
	public Range to( float f ) {
		to = f;
		return this;
	}

	/**
	 * Sets the end of the range based on the start
	 * 
	 * @param f
	 *          the delta from the start of the range
	 * @return this
	 */
	public Range lasting( float f ) {
		to = from + f;
		return this;
	}

	/**
	 * Linearly interpolates over the range
	 * 
	 * @param a
	 *          input value
	 * @return the interpolated value
	 */
	public float lerp( float a ) {
		return lerp( a, from, to );
	}

	/**
	 * Linearly interpolates over a range
	 * 
	 * @param f
	 *          the input value
	 * @param from
	 *          the start of the range
	 * @param to
	 *          the end of the range
	 * @return the interpolated value
	 */
	public static float lerp( float f, float from, float to ) {
		return from + ( to - from ) * f;
	}

	/**
	 * Reverses linear interpolation
	 * 
	 * @param f
	 *          a value in this range
	 * @return the position of the value in the range
	 */
	public float unlerp( float f ) {
		return unlerp( f, from, to );
	}

	/**
	 * Reverses linear interpolation
	 * 
	 * @param f
	 *          a value in the range
	 * @param from
	 *          the start of the range
	 * @param to
	 *          the end of the range
	 * @return the position of the value in the range
	 */
	public float unlerp( float f, float from, float to ) {
		return ( f - from ) / ( to - from );
	}

	/**
	 * Interpolates over the range
	 * 
	 * @param a
	 *          input value
	 * @param i
	 *          how to interpolate
	 * @return the interpolated value
	 */
	public float interpolate( float a, Interpolation i ) {
		return lerp( i.apply( a ) );
	}

	/**
	 * @param f
	 *          the value to test
	 * @return <code>true</code> if f lies before the start of this range
	 */
	public boolean before( float f ) {
		return unlerp( f ) < 0;
	}

	/**
	 * @param f
	 *          the value to test
	 * @return <code>true</code> if f lies after the end of this range
	 */
	public boolean after( float f ) {
		return unlerp( f ) > 1;
	}

	/**
	 * @param f
	 *          the value to test
	 * @return <code>true</code> if the value falls within this range
	 */
	public boolean contains( float f ) {
		return ( from <= f && f <= to ) || ( from >= f && f >= to );
	}

	/**
	 * @param f
	 *          the value to clamp
	 * @return The value nearest to f that lies within this range
	 */
	public float clamp( float f ) {
		if( contains( f ) ) {
			return f;
		}
		else {
			float fd = Math.abs( f - from );
			float td = Math.abs( f - to );

			if( fd < td ) {
				return from;
			}
			else {
				return to;
			}
		}
	}

	/**
	 * Shifts the value by multiples of the range length so that it lies within
	 * this range
	 * 
	 * @param f
	 *          the input value
	 * @return the normailsed value
	 */
	public float normalise( float f ) {
		return normalise( f, from, to );
	}

	public static float normalise( float f, float from, float to ) {
		float length = to - from;
		float delta = ( f - from ) / length;
		float count = (float) Math.floor( delta );

		return f -= count * length;
	}

	/**
	 * Expands this range to encompass a point
	 * 
	 * @param f
	 *          value to expand to cover
	 * @return this
	 */
	public Range include( float f ) {
		if( after( f ) ) {
			this.to = f;
		}
		else if( before( f ) ) {
			this.from = f;
		}
		return this;
	}

	@Override
	public String toString() {
		return "[" + from + "→" + to + "]";
	}
}
