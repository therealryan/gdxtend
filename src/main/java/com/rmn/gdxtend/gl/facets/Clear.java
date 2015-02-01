package com.rmn.gdxtend.gl.facets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.gl.Facet;
import com.rmn.gdxtend.util.Comparison;

/**
 * Controls clear state
 */
public class Clear extends Facet<Clear> {

	/**
	 * Clear color
	 */
	public final Color color = new Color( 0, 0, 0, 0 );

	/**
	 * Clear depth
	 */
	public float depth = 0;

	/**
	 * Clear stencil value
	 */
	public int stencil = 0;

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
	public void transitionFrom( Clear c, GL20 context ) {
		if( !color.equals( c.color ) ) {
			context.glClearColor( color.r, color.g, color.b, color.a );
		}

		if( depth != c.depth ) {
			context.glClearDepthf( depth );
		}

		if( stencil != c.stencil ) {
			context.glClearStencil( stencil );
		}
	}

	@SuppressWarnings( "boxing" )
	@Override
	public String toString() {
		return String.format( "Clear %s d:%s s:%s", color, depth, stencil );
	}
}
