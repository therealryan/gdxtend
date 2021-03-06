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
 * 
 * @see <a href="http://docs.gl/es2/glBlendFunc">glBlendFunc</a>
 * @see <a href="http://docs.gl/es2/glBlendColor">glBlendColor</a>
 * @see <a href="http://docs.gl/es2/glBlendEquation">glBlendEquation</a>
 */
public class Blend extends Facet<Blend> {
	/** OpenGL default value */
	public static final DestinationFactor DEFAULT_DEST_FACTOR = ZERO;
	/** OpenGL default value */
	public static final SourceFactor DEFAULT_SRC_FACTOR = ONE;
	/** OpenGL default value */
	public static final boolean DEFAULT_ENABLED = false;
	/** OpenGL default value */
	public static final BlendEquation DEFAULT_EQUATION = GL_FUNC_ADD;
	/** OpenGL default value */
	public static final int DEFAULT_COLOUR = new Color( 0, 0, 0, 0 ).toIntBits();

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
	 *          <code>true</code> to enable blending
	 * @return this
	 */
	public Blend enabled( boolean e ) {
		enabled = e;
		return this;
	}

	/**
	 * @param f
	 *          new source factor
	 * @return this
	 */
	public Blend src( SourceFactor f ) {
		srcFactor = f;
		return this;
	}

	/**
	 * @param f
	 *          new destination factor
	 * @return this
	 */
	public Blend dst( DestinationFactor f ) {
		destFactor = f;
		return this;
	}

	/**
	 * @param e
	 *          new blend equation
	 * @return this
	 */
	public Blend equation( BlendEquation e ) {
		equation = e;
		return this;
	}

	/**
	 * @param r
	 *          new blend colour red component
	 * @return this
	 */
	public Blend r( float r ) {
		color.set( r, color.g, color.b, color.a );
		return this;
	}

	/**
	 * @param g
	 *          new blend colour green component
	 * @return this
	 */
	public Blend g( float g ) {
		color.set( color.r, g, color.b, color.a );
		return this;
	}

	/**
	 * @param b
	 *          new blend colour blue component
	 * @return this
	 */
	public Blend b( float b ) {
		color.set( color.r, color.g, b, color.a );
		return this;
	}

	/**
	 * @param a
	 *          new blend colour alpha component
	 * @return this
	 */
	public Blend a( float a ) {
		color.set( color.r, color.g, color.b, a );
		return this;
	}

	/**
	 * @param c
	 *          new blend colour
	 * @return this
	 */
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
