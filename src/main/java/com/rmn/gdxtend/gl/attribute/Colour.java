package com.rmn.gdxtend.gl.attribute;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.rmn.gdxtend.geom.Shape;

/**
 * Controls vertex colour
 */
public class Colour extends Attribute<Colour> {
	private static final Color tmp = new Color();

	/**
	 * @param s
	 *          The shape whose colours this object will control
	 */
	public Colour( Shape s ) {
		super( s, Usage.ColorPacked );
	}

	/**
	 * @param r
	 *          red component
	 * @param g
	 *          green component
	 * @param b
	 *          blue component
	 * @param a
	 *          alpha component
	 * @return the shape
	 */
	public Shape rgba( float r, float g, float b, float a ) {
		return set( r, g, b, a );
	}

	/**
	 * @param c
	 *          new colour
	 * @return the shape
	 */
	public Shape set( Color c ) {
		return set( c.r, c.g, c.b, c.a );
	}

	/**
	 * @param r
	 *          red component
	 * @return the shape
	 */
	public Colour r( float r ) {
		return component( 0, r );
	}

	/**
	 * @param g
	 *          green component
	 * @return the shape
	 */
	public Colour g( float g ) {
		return component( 1, g );
	}

	/**
	 * @param b
	 *          blue component
	 * @return the shape
	 */
	public Colour b( float b ) {
		return component( 2, b );
	}

	/**
	 * @param a
	 *          alpha component
	 * @return the shape
	 */
	public Colour a( float a ) {
		return component( 3, a );
	}

	/**
	 * As colours are packed we have to do our own application
	 */
	@Override
	protected void applyToVertex( int index, float[] values, boolean[] set ) {
		fromPacked( tmp, s.vertexData[ index ] );
		tmp.set(
		    set[ 0 ] ? values[ 0 ] : tmp.r,
		    set[ 1 ] ? values[ 1 ] : tmp.g,
		    set[ 2 ] ? values[ 2 ] : tmp.b,
		    set[ 3 ] ? values[ 3 ] : tmp.a );

		s.vertexData[ index ] = tmp.toFloatBits();
	}

	/**
	 * The inverse of {@link Color#toFloatBits()}. Bear in mind that we lost the low
	 * alpha bit on the way out.
	 * 
	 * @param c
	 *          the {@link Color} to set
	 * @param packed
	 *          the packed float agbr value
	 * @return the altered {@link Color}
	 */
	public static Color fromPacked( Color c, float packed ) {
		int pi = Float.floatToIntBits( packed );
		int r = ( pi & 0xff ) >>> 0;
		int g = ( pi & 0xff00 ) >>> 8;
		int b = ( pi & 0xff0000 ) >>> 16;
		int a = ( pi & 0xff000000 ) >>> 24;
		c.set( ( r << 24 ) | ( g << 16 ) | ( b << 8 ) | ( a << 0 ) );
		return c;
	}
}
