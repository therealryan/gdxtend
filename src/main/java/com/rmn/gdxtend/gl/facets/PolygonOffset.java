package com.rmn.gdxtend.gl.facets;

import static com.badlogic.gdx.Gdx.gl;

import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.util.Comparison;

/**
 * Controls polygon offset state
 */
public class PolygonOffset extends Facet<PolygonOffset> {
	public static final boolean DEFAULT_ENABLED = false;
	public static final float DEFAULT_FACTOR = 0;
	public static final float DEFAULT_UNITS = 0;

	/**
	 * Controls {@link GL11#GL_POLYGON_OFFSET_FILL}
	 */
	boolean enabled = DEFAULT_ENABLED;

	/**
	 * A scale factor that is used to create a variable depth offset for each
	 * polygon.
	 */
	float factor = DEFAULT_FACTOR;

	/**
	 * Multiplied by an implementation-specific value to create a constant depth
	 * offset.
	 */
	float units = DEFAULT_UNITS;

	public PolygonOffset enabled( boolean e ) {
		enabled = e;
		return this;
	}

	public PolygonOffset factor( float f ) {
		factor = f;
		return this;
	}

	public PolygonOffset units( float u ) {
		units = u;
		return this;
	}

	@Override
	public PolygonOffset from( PolygonOffset p ) {
		return enabled( p.enabled )
				.factor( p.factor )
				.units( p.units );
	}

	@Override
	public void transition( PolygonOffset po ) {
		if( enabled != po.enabled ) {
			if( enabled ) {
				gl.glEnable( GL20.GL_POLYGON_OFFSET_FILL );
			}
			else {
				gl.glDisable( GL20.GL_POLYGON_OFFSET_FILL );
			}
		}

		if( factor != po.factor || units != po.units ) {
			gl.glPolygonOffset( factor, units );
		}
	}

	@Override
	public int compareTo( PolygonOffset po ) {
		return Comparison.instance
				.compare( enabled, po.enabled, DEFAULT_ENABLED )
				.compare( factor, po.factor, DEFAULT_FACTOR )
				.compare( units, po.units, DEFAULT_UNITS )
				.result();
	}

	@Override
	public String toString() {
		return "Polygon offset " + enabled + " factor:" + factor + " units:"
				+ units;
	}
}
