package com.rmn.gdxtend.gl.facets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.rmn.gdxtend.gl.enums.MagFilter;
import com.rmn.gdxtend.gl.enums.MinFilter;

public class TextureStateTest extends GLStateTest {

	private TextureState tex = new TextureState();
	private TextureState control = new TextureState();

	@Test
	public void construction() {
		tex.id( 1 );
		tex.filter.min( MinFilter.LINEAR_MIPMAP_LINEAR ).mag( MagFilter.NEAREST );
		tex.wrap.s( TextureWrap.MirroredRepeat ).t( TextureWrap.ClampToEdge );

		assertEquals( 1, tex.id );
		assertEquals( MinFilter.LINEAR_MIPMAP_LINEAR, tex.filter.min );
		assertEquals( MagFilter.NEAREST, tex.filter.mag );
		assertEquals( TextureWrap.MirroredRepeat, tex.wrap.s );
		assertEquals( TextureWrap.ClampToEdge, tex.wrap.t );
	}

	@Test
	public void same() {
		assertTrue( tex.equals( control ) );
	}

	@Test
	public void copy() {
		tex.id( 1 );
		tex.filter.min( MinFilter.LINEAR_MIPMAP_LINEAR ).mag( MagFilter.NEAREST );
		tex.wrap.s( TextureWrap.MirroredRepeat ).t( TextureWrap.ClampToEdge );

		TextureState copy = new TextureState().from( tex );

		assertTrue( copy.equals( tex ) );
	}

	@Test
	public void idComparison() {
		tex.id( 1 );

		assertEquals( 1, tex.compareTo( control ) );
		assertEquals( -1, control.compareTo( tex ) );
	}

	@Test
	public void minComparison() {
		tex.filter.min( MinFilter.LINEAR_MIPMAP_LINEAR );

		assertEquals( 1, tex.compareTo( control ) );
		assertEquals( -1, control.compareTo( tex ) );
	}

	@Test
	public void magComparison() {
		tex.filter.mag( MagFilter.NEAREST );

		assertEquals( -1, tex.compareTo( control ) );
		assertEquals( 1, control.compareTo( tex ) );
	}

	@Test
	public void sComparison() {
		tex.wrap.s( TextureWrap.MirroredRepeat );

		assertEquals( -1, tex.compareTo( control ) );
		assertEquals( 1, control.compareTo( tex ) );
	}

	@Test
	public void tComparison() {
		tex.wrap.t( TextureWrap.MirroredRepeat );

		assertEquals( -1, tex.compareTo( control ) );
		assertEquals( 1, control.compareTo( tex ) );
	}

	@Test
	public void noopTransition() {
		tex.transitionFrom( control, context );

		verify( context, never() ).glEnable( anyInt() );
		verify( context, never() ).glDisable( anyInt() );
		verify( context, never() ).glTexParameterf( anyInt(), anyInt(), anyFloat() );
	}

	@Test
	public void enableTransition() {
		tex.id( 1 );
		tex.filter.min( MinFilter.LINEAR_MIPMAP_LINEAR ).mag( MagFilter.NEAREST );
		tex.wrap.s( TextureWrap.MirroredRepeat ).t( TextureWrap.ClampToEdge );

		tex.transitionFrom( control, context );

		verify( context ).glEnable( GL20.GL_TEXTURE_2D );
		verify( context ).glBindTexture( GL20.GL_TEXTURE_2D, 1 );
		verify( context ).glTexParameteri( GL20.GL_TEXTURE_2D,
				GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_LINEAR_MIPMAP_LINEAR );
		verify( context ).glTexParameteri( GL20.GL_TEXTURE_2D,
				GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST );
		verify( context ).glTexParameteri( GL20.GL_TEXTURE_2D,
				GL20.GL_TEXTURE_WRAP_S, GL20.GL_MIRRORED_REPEAT );
		verify( context ).glTexParameteri( GL20.GL_TEXTURE_2D,
				GL20.GL_TEXTURE_WRAP_T, GL20.GL_CLAMP_TO_EDGE );
	}

	@Test
	public void switchTransition() {
		tex.id( 1 );
		tex.filter.min( MinFilter.LINEAR_MIPMAP_LINEAR ).mag( MagFilter.NEAREST );
		tex.wrap.s( TextureWrap.MirroredRepeat ).t( TextureWrap.ClampToEdge );

		TextureState other = new TextureState().from( tex ).id( 2 );
		other.transitionFrom( tex, context );

		verify( context ).glBindTexture( GL20.GL_TEXTURE_2D, 2 );
		verify( context ).glTexParameteri( GL20.GL_TEXTURE_2D,
				GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_LINEAR_MIPMAP_LINEAR );
		verify( context ).glTexParameteri( GL20.GL_TEXTURE_2D,
				GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST );
		verify( context ).glTexParameteri( GL20.GL_TEXTURE_2D,
				GL20.GL_TEXTURE_WRAP_S, GL20.GL_MIRRORED_REPEAT );
		verify( context ).glTexParameteri( GL20.GL_TEXTURE_2D,
				GL20.GL_TEXTURE_WRAP_T, GL20.GL_CLAMP_TO_EDGE );
	}

	@Test
	public void paramTransition() {
		tex.filter.min( MinFilter.LINEAR_MIPMAP_LINEAR ).mag( MagFilter.NEAREST );
		tex.wrap.s( TextureWrap.MirroredRepeat ).t( TextureWrap.ClampToEdge );

		tex.transitionFrom( control, context );

		verify( context, never() ).glBindTexture( GL20.GL_TEXTURE_2D, 2 );
		verify( context ).glTexParameteri( GL20.GL_TEXTURE_2D,
				GL20.GL_TEXTURE_MIN_FILTER, GL20.GL_LINEAR_MIPMAP_LINEAR );
		verify( context ).glTexParameteri( GL20.GL_TEXTURE_2D,
				GL20.GL_TEXTURE_MAG_FILTER, GL20.GL_NEAREST );
		verify( context ).glTexParameteri( GL20.GL_TEXTURE_2D,
				GL20.GL_TEXTURE_WRAP_S, GL20.GL_MIRRORED_REPEAT );
		verify( context ).glTexParameteri( GL20.GL_TEXTURE_2D,
				GL20.GL_TEXTURE_WRAP_T, GL20.GL_CLAMP_TO_EDGE );
	}

	@Test
	public void disableTransition() {

		tex.id( 1 );
		control.transitionFrom( tex, context );

		verify( context ).glDisable( GL20.GL_TEXTURE_2D );
	}
}
