package com.rmn.gdxtend.gl.facets;

import static com.badlogic.gdx.Gdx.gl;
import static com.rmn.gdxtend.gl.enums.BlendEquation.GL_FUNC_ADD;
import static com.rmn.gdxtend.gl.enums.DestinationFactor.ZERO;
import static com.rmn.gdxtend.gl.enums.SourceFactor.ONE;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.gl.enums.BlendEquation;
import com.rmn.gdxtend.gl.enums.DestinationFactor;
import com.rmn.gdxtend.gl.enums.SourceFactor;
import com.rmn.gdxtend.util.Comparison;

/**
 * Controls the blending function
 */
public class Blend extends Facet<Blend> {
	private static final DestinationFactor DEFAULT_DEST_FACTOR = ZERO;
	private static final SourceFactor DEFAULT_SRC_FACTOR = ONE;
	private static final boolean DEFAULT_ENABLED = false;
	private static final BlendEquation DEFAULT_EQUATION = GL_FUNC_ADD;
	private static final int DEFAULT_COLOUR = new Color( 0, 0, 0, 0 ).toIntBits();

	/**
	 * Whether blending is enabled
	 */
	boolean enabled = DEFAULT_ENABLED;

	/**
	 * The blending function
	 */
	BlendEquation equation = DEFAULT_EQUATION;

	/**
	 * Blend colour
	 */
	final Color color = new Color( DEFAULT_COLOUR );

	/**
	 * The blending source factor, see glBlendFunc
	 */
	SourceFactor srcFactor = DEFAULT_SRC_FACTOR;

	/**
	 * The blending destination factor, see glBlendFunc
	 */
	DestinationFactor destFactor = DEFAULT_DEST_FACTOR;

	/**
	 * @param e
	 * @return this
	 */
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
	public void transition( Blend b ) {
		if( enabled && !b.enabled ) {
			gl.glEnable( GL20.GL_BLEND );
		}
		else if( !enabled && b.enabled ) {
			gl.glDisable( GL20.GL_BLEND );
		}

		if( srcFactor != b.srcFactor || destFactor != b.destFactor ) {
			gl.glBlendFunc( srcFactor.value, destFactor.value );
		}

		if( !color.equals( b.color ) ) {
			gl.glBlendColor( color.r, color.g, color.b, color.a );
		}

		if( equation != b.equation ) {
			gl.glBlendEquation( equation.value );
		}
	}

	@Override
	public int compareTo( Blend b ) {
		return Comparison.instance
				.compare( enabled, b.enabled, DEFAULT_ENABLED )
				.compare( srcFactor, b.srcFactor, DEFAULT_SRC_FACTOR )
				.compare( destFactor, b.destFactor, DEFAULT_DEST_FACTOR )
				.compare( equation, b.equation, DEFAULT_EQUATION )
				.compare( color.toIntBits(), b.color.toIntBits(), DEFAULT_COLOUR )
				.result();
	}

	@Override
	public String toString() {
		return "Blending " + enabled + " src:" + srcFactor + " dest:" + destFactor
				+ " eq:" + equation + " color:" + color;
	}
}
