package com.rmn.gdxtend.gl.facets;

import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.gl.Facet;
import com.rmn.gdxtend.util.Comparison;

/**
 * Controls polygon offset state
 */
public class PolygonOffset extends Facet<PolygonOffset> {
	/**
	 * Controls {@link GL11#GL_POLYGON_OFFSET_FILL}
	 */
	public boolean enabled = false;

	/**
	 * A scale factor that is used to create a variable depth offset for each
	 * polygon.
	 */
	public float factor = 0;

	/**
	 * Multiplied by an implementation-specific value to create a constant depth
	 * offset.
	 */
	public float units = 0;

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
	public void transitionFrom( PolygonOffset po, GL20 context ) {
		if( enabled != po.enabled ) {
			if( enabled ) {
				context.glEnable( GL20.GL_POLYGON_OFFSET_FILL );
			}
			else {
				context.glDisable( GL20.GL_POLYGON_OFFSET_FILL );
			}
		}

		if( factor != po.factor || units != po.units ) {
			context.glPolygonOffset( factor, units );
		}
	}

	@Override
	public int compareTo( PolygonOffset po ) {
		return Comparison.instance
				.compare( enabled, po.enabled )
				.compare( factor, po.factor )
				.compare( units, po.units )
				.result();
	}

	@SuppressWarnings( "boxing" )
	@Override
	public String toString() {
		return String.format( "Polygon offset %s factor %s units %s", enabled,
				factor, units );
	}
}
