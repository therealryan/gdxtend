package com.rmn.gdxtend.gl.facets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.gl.Facet;
import com.rmn.gdxtend.gl.enums.BlendEquation;
import com.rmn.gdxtend.gl.enums.DestinationFactor;
import com.rmn.gdxtend.gl.enums.SourceFactor;
import com.rmn.gdxtend.util.Comparison;

/**
 * Controls the blending function
 */
public class Blend extends Facet<Blend> {
	/**
	 * Whether blending is enabled
	 */
	public boolean enabled = false;

	/**
	 * The blending function
	 */
	public BlendEquation equation = BlendEquation.GL_FUNC_ADD;

	/**
	 * Blend colour
	 */
	public final Color color = new Color( 0, 0, 0, 0 );

	/**
	 * The blending source factor, see glBlendFunc
	 */
	public SourceFactor srcFactor = SourceFactor.ONE;

	/**
	 * The blending destination factor, see glBlendFunc
	 */
	public DestinationFactor destFactor = DestinationFactor.ZERO;

	public Blend enabled( boolean e ) {
		enabled = e;
		return this;
	}

	public Blend src( SourceFactor f ) {
		srcFactor = f;
		return this;
	}

	public Blend dst( DestinationFactor f ) {
		destFactor = f;
		return this;
	}

	public Blend equation( BlendEquation e ) {
		equation = e;
		return this;
	}

	public Blend r( float r ) {
		color.set( r, color.g, color.b, color.a );
		return this;
	}

	public Blend g( float g ) {
		color.set( color.r, g, color.b, color.a );
		return this;
	}

	public Blend b( float b ) {
		color.set( color.r, color.g, b, color.a );
		return this;
	}

	public Blend a( float a ) {
		color.set( color.r, color.g, color.b, a );
		return this;
	}

	public Blend color( Color c ) {
		color.set( c );
		return this;
	}

	@Override
	public Blend from( Blend b ) {
		return enabled( b.enabled )
				.color( b.color )
				.dst( b.destFactor )
				.src( b.srcFactor )
				.equation( b.equation );
	}

	@Override
	public void transitionFrom( Blend b, GL20 context ) {
		if( enabled && !b.enabled ) {
			context.glEnable( GL20.GL_BLEND );
		}
		else if( !enabled && b.enabled ) {
			context.glDisable( GL20.GL_BLEND );
		}

		if( srcFactor != b.srcFactor || destFactor != b.destFactor ) {
			context.glBlendFunc( srcFactor.value, destFactor.value );
		}

		if( !color.equals( b.color ) ) {
			context.glBlendColor( color.r, color.g, color.b, color.a );
		}

		if( equation != b.equation ) {
			context.glBlendEquation( equation.value );
		}
	}

	@Override
	public int compareTo( Blend b ) {
		return Comparison.instance
				.compare( enabled, b.enabled )
				.compare( srcFactor, b.srcFactor )
				.compare( destFactor, b.destFactor )
				.compare( equation, b.equation )
				.compare( color, b.color )
				.result();
	}

	@SuppressWarnings( "boxing" )
	@Override
	public String toString() {
		return String.format( "Blending %s src:%s dest:%s eq:%s colour %s",
				enabled, srcFactor, destFactor, equation, color );
	}
}
