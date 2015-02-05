package com.rmn.gdxtend.gl.facets;

import static com.badlogic.gdx.Gdx.gl;

import com.badlogic.gdx.graphics.Color;
import com.rmn.gdxtend.gl.Facet;
import com.rmn.gdxtend.util.Comparison;

/**
 * Controls clear state
 */
public class Clear extends Facet<Clear> {

	/**
	 * Clear color
	 */
	final Color color = new Color( 0, 0, 0, 0 );

	/**
	 * Clear depth
	 */
	float depth = 0;

	/**
	 * Clear stencil value
	 */
	int stencil = 0;

	public Clear r( float r ) {
		color.set( r, color.g, color.b, color.a );
		return this;
	}

	public Clear g( float g ) {
		color.set( color.r, g, color.b, color.a );
		return this;
	}

	public Clear b( float b ) {
		color.set( color.r, color.g, b, color.a );
		return this;
	}

	public Clear a( float a ) {
		color.set( color.r, color.g, color.b, a );
		return this;
	}

	public Clear color( Color c ) {
		color.set( c );
		return this;
	}

	public Clear depth( float d ) {
		depth = d;
		return this;
	}

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
				.compare( color, c.color )
				.compare( depth, c.depth )
				.compare( stencil, c.stencil )
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
