package com.rmn.gdxtend.gl.facets;

import static com.badlogic.gdx.Gdx.gl;
import static com.rmn.gdxtend.gl.enums.ComparisonFunction.LESS;

import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.gl.enums.ComparisonFunction;
import com.rmn.gdxtend.util.Comparison;

/**
 * Controls depth state
 * 
 * @see <a href="http://docs.gl/es2/glDepthFunc">glDepthFunc</a>
 * @see <a href="http://docs.gl/es2/glDepthMask">geDepthMask</a>
 * @see <a href="http://docs.gl/es2/glDepthRangef">glDepthRangef</a>
 */
public class Depth extends Facet<Depth> {
	/** OpenGL default value */
	public static final boolean DEFAULT_MASK = true;
	/** OpenGL default value */
	public static final ComparisonFunction DEFAULT_FUNCTION = LESS;
	/** OpenGL default value */
	public static final boolean DEFAULT_ENABLED = false;
	/** OpenGL default value */
	public static final int DEFAULT_NEAR = 0;
	/** OpenGL default value */
	public static final int DEFAULT_FAR = 1;

	/**
	 * Whether the depth test is enabled or not
	 */
	boolean enabled = DEFAULT_ENABLED;

	/**
	 * The depth test function, see glDepthFunc
	 */
	ComparisonFunction func = DEFAULT_FUNCTION;

	/**
	 * Whether or not to write to the depth buffer, see glDepthMask
	 */
	boolean mask = DEFAULT_MASK;

	/**
	 * See glDepthRange
	 */
	float near = DEFAULT_NEAR;

	/**
	 * See glDepthRange
	 */
	float far = DEFAULT_FAR;

	/**
	 * @param e
	 *          true to enable depth test
	 * @return this
	 */
	public Depth enabled( boolean e ) {
		enabled = e;
		return this;
	}

	/**
	 * @param f
	 *          new test function
	 * @return this
	 */
	public Depth function( ComparisonFunction f ) {
		func = f;
		return this;
	}

	/**
	 * {@link GL20#glDepthMask(boolean)}
	 * 
	 * @param m
	 *          <code>true</code> to enable depth masking
	 * @return this
	 */
	public Depth mask( boolean m ) {
		mask = m;
		return this;
	}

	/**
	 * {@link GL20#glDepthRangef(float, float)}
	 * 
	 * @param n
	 *          new near value
	 * @return this
	 */
	public Depth near( float n ) {
		near = n;
		return this;
	}

	/**
	 * {@link GL20#glDepthRangef(float, float)}
	 * 
	 * @param f
	 *          new far value
	 * @return this
	 */
	public Depth far( float f ) {
		far = f;
		return this;
	}

	@Override
	public Depth from( Depth d ) {
		return enabled( d.enabled )
				.far( d.far )
				.function( d.func )
				.mask( d.mask )
				.near( d.near );
	}

	@Override
	public void transition( Depth d ) {
		if( enabled && !d.enabled ) {
			gl.glEnable( GL20.GL_DEPTH_TEST );
		}
		else if( !enabled && d.enabled ) {
			gl.glDisable( GL20.GL_DEPTH_TEST );
		}

		if( func != d.func ) {
			gl.glDepthFunc( func.value );
		}

		if( mask != d.mask ) {
			gl.glDepthMask( mask );
		}

		if( near != d.near || far != d.far ) {
			gl.glDepthRangef( near, far );
		}
	}

	@Override
	public int compareTo( Depth d ) {
		return Comparison.instance
				.compare( enabled, d.enabled, DEFAULT_ENABLED )
				.compare( func, d.func, DEFAULT_FUNCTION )
				.compare( mask, d.mask, DEFAULT_MASK )
				.compare( near, d.near, DEFAULT_NEAR )
				.compare( far, d.far, DEFAULT_FAR )
				.result();
	}

	@Override
	public String toString() {
		return "Depth test " + enabled + " func = " + func;
	}
}
