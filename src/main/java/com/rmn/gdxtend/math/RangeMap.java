package com.rmn.gdxtend.math;

import com.badlogic.gdx.math.Interpolation;

/**
 * Allows the relation of two {@link Range}s
 */
public class RangeMap implements Function {

	/**
	 * Range of input values
	 */
	public final Range source = new Range();

	/**
	 * Range of output values
	 */
	public final Range destination = new Range();

	private Interpolation interp = Interpolation.linear;

	/**
	 * Copies the supplied {@link RangeMap} values into this one
	 * 
	 * @param rm
	 *          the map to copy
	 * @return this
	 */
	public RangeMap from( RangeMap rm ) {
		this.source.from( rm.source );
		this.destination.from( rm.destination );
		this.interp = rm.interp;
		return this;
	}

	/**
	 * Sets the source range
	 * 
	 * @param from
	 *          minimum input value
	 * @param to
	 *          maximum input value
	 * @return this
	 */
	public RangeMap source( float from, float to ) {
		source.from( from ).to( to );
		return this;
	}

	/**
	 * Sets the destination range
	 * 
	 * @param from
	 *          minimum output value
	 * @param to
	 *          maximum output value
	 * @return this
	 */
	public RangeMap destination( float from, float to ) {
		destination.from( from ).to( to );
		return this;
	}

	/**
	 * Computes the linear map from the source to destination map
	 * 
	 * @param f
	 *          input value
	 * @return the value in the destination range that corresponds to f's position
	 *         in the source map
	 */
	public float linearMap( float f ) {
		return destination.lerp( source.unlerp( f ) );
	}

	/**
	 * The inverse of {@link #linearMap(float)}
	 * 
	 * @param f
	 *          output value
	 * @return the value in the source range that corresponds to f's position in
	 *         the destination map
	 */
	public float linearUnmap( float f ) {
		return source.lerp( destination.unlerp( f ) );
	}

	/**
	 * Sets the interpolation function used in {@link #map(float)} calls
	 * 
	 * @param i
	 *          new {@link Interpolation} function
	 * @return this
	 */
	public RangeMap via( Interpolation i ) {
		interp = i;

		// avoid null interpolator
		if( interp == null ) {
			interp = Interpolation.linear;
		}

		return this;
	}

	/**
	 * Maps from a point in the source range (linear) to a point in the
	 * destination range (based on the current interpolation function)
	 * 
	 * @param f
	 *          the source value
	 * @return the destination value
	 */
	@Override
	public float map( float f ) {
		return destination.interpolate( source.unlerp( f ), interp );
	}
}
