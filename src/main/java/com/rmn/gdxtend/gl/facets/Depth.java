package com.rmn.gdxtend.gl.facets;

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
	public boolean enabled = false;

	/**
	 * The depth test function, see glDepthFunc
	 */
	public ComparisonFunction func = ComparisonFunction.LESS;

	/**
	 * Whether or not to write to the depth buffer, see glDepthMask
	 */
	public boolean mask = true;

	/**
	 * See glDepthRange
	 */
	public float near = 0;

	/**
	 * See glDepthRange
	 */
	public float far = 1;

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
	public void transitionFrom( Depth d, GL20 context ) {
		if( enabled && !d.enabled ) {
			context.glEnable( GL20.GL_DEPTH_TEST );
		}
		else if( !enabled && d.enabled ) {
			context.glDisable( GL20.GL_DEPTH_TEST );
		}

		if( func != d.func ) {
			context.glDepthFunc( func.value );
		}

		if( mask != d.mask ) {
			context.glDepthMask( mask );
		}

		if( near != d.near || far != d.far ) {
			context.glDepthRangef( near, far );
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
