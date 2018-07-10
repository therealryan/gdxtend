package com.rmn.gdxtend.gl.facets;

import static com.badlogic.gdx.Gdx.gl;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.rmn.gdxtend.gl.enums.MagFilter;
import com.rmn.gdxtend.gl.enums.MinFilter;
import com.rmn.gdxtend.util.Comparison;

/**
 * Controls the bound texture and parameters
 */
public class TextureState extends Facet<TextureState> {

	Texture texture = null;

	MinFilter min = MinFilter.NEAREST_MIPMAP_LINEAR;
	MagFilter mag = MagFilter.LINEAR;

	TextureWrap s = TextureWrap.Repeat;
	TextureWrap t = TextureWrap.Repeat;

	/**
	 * @param t
	 *          the new texture
	 * @return this
	 */
	public TextureState with( Texture t ) {
		texture = t;
		return this;
	}

	/**
	 * @param f
	 *          the new minification filter
	 * @return this
	 */
	public TextureState min( MinFilter f ) {
		min = f;
		return this;
	}

	/**
	 * @param f
	 *          new magnification filter
	 * @return this
	 */
	public TextureState mag( MagFilter f ) {
		mag = f;

		return this;
	}

	/**
	 * @param w
	 *          new horizontal wrap function
	 * @return this
	 */
	public TextureState s( TextureWrap w ) {
		s = w;
		return this;
	}

	/**
	 * @param w
	 *          new vertical wrap function
	 * @return this
	 */
	public TextureState t( TextureWrap w ) {
		t = w;
		return this;
	}

	@Override
	public TextureState from( TextureState t ) {
		return with( t.texture )
		    .min( t.min ).mag( t.mag )
		    .s( t.s ).t( t.t );
	}

	@Override
	public void transition( TextureState from ) {
		if( texture != from.texture ) {
			if( texture == null ) {
				// disable texturing
				gl.glBindTexture( GL20.GL_TEXTURE_2D, 0 );
			}
			else {
				texture.bind();
			}
		}

		if( texture != null ) {
			texture.unsafeSetFilter( min.value, mag.value );
			texture.unsafeSetWrap( s, t );
		}
	}

	@Override
	public int compareTo( TextureState other ) {
		return Comparison.instance
		    .compare(
		        System.identityHashCode( texture ),
		        System.identityHashCode( other.texture ) )
		    .compare( min, other.min )
		    .compare( other.mag, mag )
		    .compare( other.s, s )
		    .compare( other.t, t )
		    .result();
	}

	@Override
	public String toString() {
		return "Texture " + texture + " min:" + min + " mag:" + mag + " s:" + s
		    + " t:" + t;
	}
}
