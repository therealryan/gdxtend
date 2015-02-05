package com.rmn.gdxtend.gl.facets;

import static com.badlogic.gdx.Gdx.gl;

import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.gl.Facet;
import com.rmn.gdxtend.gl.enums.ComparisonFunction;
import com.rmn.gdxtend.util.Comparison;

/**
 * Controls depth state
 */
public class Depth extends Facet<Depth> {
	/**
	 * Whether the depth test is enabled or not
	 */
	boolean enabled = false;

	/**
	 * The depth test function, see glDepthFunc
	 */
	ComparisonFunction func = ComparisonFunction.LESS;

	/**
	 * Whether or not to write to the depth buffer, see glDepthMask
	 */
	boolean mask = true;

	/**
	 * See glDepthRange
	 */
	float near = 0;

	/**
	 * See glDepthRange
	 */
	float far = 1;

	public Depth enabled( boolean e ) {
		enabled = e;
		return this;
	}

	public Depth function( ComparisonFunction f ) {
		func = f;
		return this;
	}

	public Depth mask( boolean m ) {
		mask = m;
		return this;
	}

	public Depth near( float n ) {
		near = n;
		return this;
	}

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
				.compare( enabled, d.enabled )
				.compare( func, d.func )
				.compare( mask, d.mask )
				.compare( near, d.near )
				.compare( far, d.far )
				.result();
	}

	@Override
	public String toString() {
		return "Depth test " + enabled + " func = " + func.value;
	}
}
