package com.rmn.gdxtend.gl.facets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.util.Comparison;

/**
 * Controls scissor test state
 * 
 * @see <a href="http://docs.gl/es2/glScissor">glScissor</a>
 */
public class Scissor extends Facet<Scissor> {

	/** OpenGL default value */
	public static final boolean DEFAULT_ENABLED = false;

	/** OpenGL default value */
	public static final int DEFAULT_MIN = 0;

	/** we don't know the size of the window, so let's just cover everything */
	public static final int DEFAULT_SIZE = Integer.MAX_VALUE;

	/**
	 * Whether scissor is enabled
	 */
	public boolean enabled = DEFAULT_ENABLED;

	/**
	 * left edge of scissor box
	 */
	public int x = DEFAULT_MIN;
	/**
	 * bottom edge of scissor box
	 */
	public int y = DEFAULT_MIN;

	/**
	 * Width of scissor box
	 */
	public int width = DEFAULT_SIZE;
	/**
	 * Height of scissor box
	 */
	public int height = DEFAULT_SIZE;

	/**
	 * @param e
	 *          <code>true</code> to enable scissor
	 * @return this
	 */
	public Scissor enabled( boolean e ) {
		enabled = e;
		return this;
	}

	/**
	 * @param x
	 *          new left edge
	 * @return this
	 */
	public Scissor x( int x ) {
		this.x = x;
		return this;
	}

	/**
	 * @param y
	 *          new bottom edge
	 * @return true
	 */
	public Scissor y( int y ) {
		this.y = y;
		return this;
	}

	/**
	 * @param w
	 *          new width
	 * @return this
	 */
	public Scissor width( int w ) {
		width = w;
		return this;
	}

	/**
	 * @param h
	 *          new height
	 * @return this
	 */
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
