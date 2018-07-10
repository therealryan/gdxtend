package com.rmn.gdxtend.gl.facets;

import static com.badlogic.gdx.Gdx.gl;

import com.badlogic.gdx.graphics.Color;
import com.rmn.gdxtend.util.Comparison;

/**
 * Controls clear state
 * 
 * @see <a href="http://docs.gl/es2/glClearColor">glClearColor</a>
 * @see <a href="http://docs.gl/es2/glClearDepthf">glClearDepthf</a>
 * @see <a href="http://docs.gl/es2/glClearStencil">glClearStencil</a>
 */
public class Clear extends Facet<Clear> {

	/** OpenGL default value */
	public final static int DEFAULT_COLOR = Color.argb8888( 0, 0, 0, 0 );
	/** OpenGL default value */
	public final static float DEFAULT_DEPTH = 0;
	/** OpenGL default value */
	public final static int DEFAULT_STENCIL = 0;

	/**
	 * Clear color
	 */
	final Color color = new Color( DEFAULT_COLOR );

	/**
	 * Clear depth
	 */
	float depth = DEFAULT_DEPTH;

	/**
	 * Clear stencil value
	 */
	int stencil = DEFAULT_STENCIL;

	/**
	 * @param r
	 *          new clear colour red component
	 * @return this
	 */
	public Clear r( float r ) {
		color.set( r, color.g, color.b, color.a );
		return this;
	}

	/**
	 * @param g
	 *          new clear colour green component
	 * @return this
	 */
	public Clear g( float g ) {
		color.set( color.r, g, color.b, color.a );
		return this;
	}

	/**
	 * @param b
	 *          new clear colour blue component
	 * @return this
	 */
	public Clear b( float b ) {
		color.set( color.r, color.g, b, color.a );
		return this;
	}

	/**
	 * @param a
	 *          new clear colour alpha component
	 * @return this
	 */
	public Clear a( float a ) {
		color.set( color.r, color.g, color.b, a );
		return this;
	}

	/**
	 * @param c
	 *          new clear colour
	 * @return this
	 */
	public Clear color( Color c ) {
		color.set( c );
		return this;
	}

	/**
	 * @param d
	 *          new clear depth value
	 * @return this
	 */
	public Clear depth( float d ) {
		depth = d;
		return this;
	}

	/**
	 * @param s
	 *          the new clear stencil value
	 * @return this
	 */
	public Clear stencil( int s ) {
		stencil = s;
		return this;
	}

	@Override
	public Clear from( Clear c ) {
		return color( c.color )
		    .depth( c.depth )
		    .stencil( c.stencil );
	}

	@Override
	public int compareTo( Clear c ) {
		return Comparison.instance
		    .compare( color.toIntBits(), c.color.toIntBits(), DEFAULT_COLOR )
		    .compare( depth, c.depth, DEFAULT_DEPTH )
		    .compare( stencil, c.stencil, DEFAULT_STENCIL )
		    .result();
	}

	@Override
	public void transition( Clear c ) {
		if( !color.equals( c.color ) ) {
			gl.glClearColor( color.r, color.g, color.b, color.a );
		}

		if( depth != c.depth ) {
			gl.glClearDepthf( depth );
		}

		if( stencil != c.stencil ) {
			gl.glClearStencil( stencil );
		}
	}

	@Override
	public String toString() {
		return "Clear c:" + color + " d:" + depth + " s:" + stencil;
	}
}
