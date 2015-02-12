package com.rmn.gdxtend.gl.facets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.gl.Facet;
import com.rmn.gdxtend.util.Comparison;

/**
 * Controls scissor test state
 */
public class Scissor extends Facet<Scissor> {

	public static final boolean DEFAULT_ENABLED = false;

	boolean enabled = DEFAULT_ENABLED;
	public static final int DEFAULT_MIN = 0;

	// we don't know the size of the window, so let's just cover everything
	public static final int DEFAULT_SIZE = Integer.MAX_VALUE;

	int x = DEFAULT_MIN;
	int y = DEFAULT_MIN;

	int width = DEFAULT_SIZE;
	int height = DEFAULT_SIZE;

	public Scissor enabled( boolean e ) {
		enabled = e;
		return this;
	}

	public Scissor x( int x ) {
		this.x = x;
		return this;
	}

	public Scissor y( int y ) {
		this.y = y;
		return this;
	}

	public Scissor width( int w ) {
		width = w;
		return this;
	}

	public Scissor height( int h ) {
		height = h;
		return this;
	}

	@Override
	public Scissor from( Scissor s ) {
		return enabled( s.enabled )
				.x( s.x ).y( s.y )
				.width( s.width ).height( s.height );
	}

	@Override
	public int compareTo( Scissor s ) {
		return Comparison.instance
				.compare( enabled, s.enabled, DEFAULT_ENABLED )
				.compare( x, s.x, DEFAULT_MIN )
				.compare( y, s.y, DEFAULT_MIN )
				.compare( width, s.width, DEFAULT_SIZE )
				.compare( height, s.height, DEFAULT_SIZE )
				.result();
	}

	@Override
	public void transition( Scissor s ) {
		if( enabled != s.enabled ) {
			if( enabled ) {
				Gdx.gl.glEnable( GL20.GL_SCISSOR_TEST );
			}
			else {
				Gdx.gl.glDisable( GL20.GL_SCISSOR_TEST );
			}
		}

		if( x != s.x || y != s.y || width != s.width || height != s.height ) {
			Gdx.gl.glScissor( x, y, width, height );
		}
	}

}
