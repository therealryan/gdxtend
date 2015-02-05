package com.rmn.gdxtend.gl.facets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.Mock;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;

public class TextureStateTest extends GLStateTest {

	private TextureState tex = new TextureState();
	private TextureState control = new TextureState();

	@Mock
	private Texture imageA;

	@Mock
	private Texture imageB;

	@Test
	public void construction() {
		tex.with( imageA );
		tex.min( TextureFilter.MipMapLinearLinear ).mag( TextureFilter.Nearest );
		tex.s( TextureWrap.MirroredRepeat ).t( TextureWrap.ClampToEdge );

		assertEquals( imageA, tex.texture );
		assertEquals( TextureFilter.MipMapLinearLinear, tex.min );
		assertEquals( TextureFilter.Nearest, tex.mag );
		assertEquals( TextureWrap.MirroredRepeat, tex.s );
		assertEquals( TextureWrap.ClampToEdge, tex.t );
	}

	@Test
	public void same() {
		assertTrue( tex.equals( control ) );
	}

	@Test
	public void copy() {
		tex.with( imageA );
		tex.min( TextureFilter.MipMapLinearLinear ).mag( TextureFilter.Nearest );
		tex.s( TextureWrap.MirroredRepeat ).t( TextureWrap.ClampToEdge );

		TextureState copy = new TextureState();

		assertFalse( copy.equals( tex ) );

		copy.from( tex );

		assertTrue( copy.equals( tex ) );
	}

	@Test
	public void idComparison() {
		tex.with( imageA );

		assertEquals( 1, tex.compareTo( control ) );
		assertEquals( -1, control.compareTo( tex ) );
	}

	@Test
	public void minComparison() {
		tex.min( TextureFilter.MipMapLinearLinear );

		assertEquals( 1, tex.compareTo( control ) );
		assertEquals( -1, control.compareTo( tex ) );
	}

	@Test
	public void magComparison() {
		tex.mag( TextureFilter.Nearest );

		assertEquals( -1, tex.compareTo( control ) );
		assertEquals( 1, control.compareTo( tex ) );
	}

	@Test
	public void sComparison() {
		tex.s( TextureWrap.MirroredRepeat );

		assertEquals( -1, tex.compareTo( control ) );
		assertEquals( 1, control.compareTo( tex ) );
	}

	@Test
	public void tComparison() {
		tex.t( TextureWrap.MirroredRepeat );

		assertEquals( -1, tex.compareTo( control ) );
		assertEquals( 1, control.compareTo( tex ) );
	}

	@Test
	public void noopTransition() {
		tex.transition( control );

		verify( context, never() ).glEnable( anyInt() );
		verify( context, never() ).glDisable( anyInt() );
		verify( context, never() ).glTexParameterf( anyInt(), anyInt(), anyFloat() );
	}

	@Test
	public void enableTransition() {
		tex.with( imageA )
				.min( TextureFilter.MipMapLinearLinear )
				.mag( TextureFilter.Nearest )
				.s( TextureWrap.MirroredRepeat )
				.t( TextureWrap.ClampToEdge );

		tex.transition( control );

		verify( imageA )
				.bind();
		verify( imageA )
				.unsafeSetFilter( TextureFilter.MipMapLinearLinear,
						TextureFilter.Nearest );
		verify( imageA )
				.unsafeSetWrap( TextureWrap.MirroredRepeat, TextureWrap.ClampToEdge );
	}

	@Test
	public void paramTransition() {
		tex.with( imageA );
		tex.min( TextureFilter.MipMapLinearLinear ).mag( TextureFilter.Nearest );
		tex.s( TextureWrap.MirroredRepeat ).t( TextureWrap.ClampToEdge );

		TextureState other = new TextureState().from( tex );
		other.min( TextureFilter.Linear ).mag( TextureFilter.Linear )
				.s( TextureWrap.ClampToEdge ).t( TextureWrap.Repeat );

		other.transition( tex );

		verify( imageA, never() )
				.bind();
		verify( imageA )
				.unsafeSetFilter( TextureFilter.Linear, TextureFilter.Linear );
		verify( imageA )
				.unsafeSetWrap( TextureWrap.ClampToEdge, TextureWrap.Repeat );
	}

	@Test
	public void disableTransition() {

		tex.with( imageA );

		control.transition( tex );

		verify( context )
				.glBindTexture( GL20.GL_TEXTURE_2D, 0 );
		verify( imageA, never() )
				.unsafeSetFilter( isA( TextureFilter.class ), isA( TextureFilter.class ) );
		verify( imageA, never() )
				.unsafeSetWrap( isA( TextureWrap.class ), isA( TextureWrap.class ) );
	}
}
