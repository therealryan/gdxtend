package com.rmn.gdxtend.gl.facets;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.rmn.gdxtend.gl.Facet;
import com.rmn.gdxtend.gl.enums.MagFilter;
import com.rmn.gdxtend.gl.enums.MinFilter;
import com.rmn.gdxtend.util.Comparison;

/**
 * Controls the bound texture and parameters
 */
public class TextureState extends Facet<TextureState> {
	/**
	 * The openGL texture name. Set to -1 to disable texturing
	 */
	public int id = -1;

	/**
	 * Texture filter modes
	 */
	public final Filters filter = new Filters();

	/**
	 * Texture wrapping parameters
	 */
	public final WrapParameters wrap = new WrapParameters();

	public TextureState id( int i ) {
		id = i;
		return this;
	}

	@Override
	public TextureState from( TextureState t ) {
		filter.from( t.filter );
		wrap.from( t.wrap );
		return id( t.id );
	}

	@Override
	public void transitionFrom( TextureState t, GL20 context ) {
		if( id != t.id ) {
			if( t.id == -1 ) {
				context.glEnable( GL20.GL_TEXTURE_2D );
			}

			if( id == -1 ) {
				context.glDisable( GL20.GL_TEXTURE_2D );
			}
			else {
				context.glBindTexture( GL20.GL_TEXTURE_2D, id );
			}
		}

		if( id != t.id ) {
			// we've just bound a different texture, who knows what
			// params it last had set?
			filter.force( context );
			wrap.force( context );
		}
		else { // we can do the minimal change
			filter.transitionFrom( t.filter, context );
			wrap.transitionFrom( t.wrap, context );
		}
	}

	@Override
	public int compareTo( TextureState t ) {
		return Comparison.instance
				.compare( id, t.id )
				.compare( filter, t.filter )
				.compare( wrap, t.wrap )
				.result();
	}

	@SuppressWarnings( "boxing" )
	@Override
	public String toString() {
		return String.format( "Texture %s %s %s", id, filter, wrap );
	}

	public static class Filters extends Facet<Filters> {
		/**
		 * The texture minification filter, see glTexParameters
		 */
		public MinFilter min = MinFilter.NEAREST_MIPMAP_LINEAR;

		/**
		 * The texture magnification filter, see glTexParameters
		 */
		public MagFilter mag = MagFilter.LINEAR;

		public Filters min( MinFilter m ) {
			min = m;
			return this;
		}

		public Filters mag( MagFilter m ) {
			mag = m;
			return this;
		}

		@Override
		public Filters from( Filters f ) {
			return min( f.min ).mag( f.mag );
		}

		@Override
		public int compareTo( Filters f ) {
			return Comparison.instance
					.compare( min, f.min )
					.compare( mag, f.mag )
					.result();
		}

		@Override
		public void transitionFrom( Filters t, GL20 context ) {
			if( min != t.min ) {
				context.glTexParameteri( GL20.GL_TEXTURE_2D,
						GL20.GL_TEXTURE_MIN_FILTER, min.value );
			}

			if( mag != t.mag ) {
				context.glTexParameteri( GL20.GL_TEXTURE_2D,
						GL20.GL_TEXTURE_MAG_FILTER, mag.value );
			}
		}

		private void force( GL20 context ) {
			context.glTexParameteri( GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MIN_FILTER,
					min.value );
			context.glTexParameteri( GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAG_FILTER,
					mag.value );
		}

		@Override
		public String toString() {
			return String.format( "filters min = %s mag = %s", min, mag );
		}
	}

	public static class WrapParameters extends Facet<WrapParameters> {
		/**
		 * Wrapping in the s direction
		 */
		public TextureWrap s = TextureWrap.Repeat;

		/**
		 * Wrapping in the t direction
		 */
		public TextureWrap t = TextureWrap.Repeat;

		public WrapParameters s( TextureWrap w ) {
			s = w;
			return this;
		}

		public WrapParameters t( TextureWrap w ) {
			t = w;
			return this;
		}

		@Override
		public WrapParameters from( WrapParameters w ) {
			return s( w.s ).t( w.t );
		}

		@Override
		public void transitionFrom( WrapParameters w, GL20 context ) {
			if( s != w.s ) {
				context.glTexParameteri( GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S,
						s.getGLEnum() );
			}

			if( t != w.t ) {
				context.glTexParameteri( GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T,
						t.getGLEnum() );
			}
		}

		private void force( GL20 context ) {
			context.glTexParameteri( GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S,
					s.getGLEnum() );
			context.glTexParameteri( GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T,
					t.getGLEnum() );
		}

		@Override
		public int compareTo( WrapParameters w ) {
			return Comparison.instance
					.compare( s, w.s )
					.compare( t, w.t )
					.result();
		}

		@Override
		public String toString() {
			return String.format( "Wrap s = %s t = %S", s, t );
		}
	}
}
