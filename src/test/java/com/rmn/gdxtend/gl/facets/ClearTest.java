package com.rmn.gdxtend.gl.facets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.badlogic.gdx.Gdx;

public class ClearTest extends FacetTest {
	private final Clear clear = new Clear();
	private final Clear control = new Clear();

	@Test
	public void construction() {
		clear.r( 0.5f )
				.g( 0.25f )
				.b( 0.125f )
				.a( 0.0625f )
				.depth( 2 )
				.stencil( 3 );

		assertEquals( clear.color.r, 0.5f, 0 );
		assertEquals( clear.color.g, 0.25f, 0 );
		assertEquals( clear.color.b, 0.125f, 0 );
		assertEquals( clear.color.a, 0.0625f, 0 );
		assertEquals( clear.depth, 2, 0 );
		assertEquals( clear.stencil, 3, 0 );
	}

	@Test
	public void same() {
		assertTrue( clear.equals( control ) );
	}

	@Test
	public void copy() {
		clear.r( 0.5f )
				.g( 0.25f )
				.b( 0.125f )
				.a( 0.0625f )
				.depth( 2 )
				.stencil( 3 );
		Clear copy = new Clear().from( clear );

		assertTrue( copy.equals( clear ) );
	}

	@Test
	public void colorComparison() {
		comparisonOrder( control, clear.r( 1 ) );
	}

	@Test
	public void depthComparison() {
		comparisonOrder( control, clear.depth( 1 ) );
	}

	@Test
	public void stencilComparison() {
		comparisonOrder( control, clear.stencil( 1 ) );
	}

	@Test
	public void noopTransition() {
		clear.transition( control );

		verify( Gdx.gl, never() ).glClearColor(
				anyFloat(), anyFloat(), anyFloat(), anyFloat() );
		verify( Gdx.gl, never() ).glClearDepthf( anyFloat() );
		verify( Gdx.gl, never() ).glClearStencil( anyInt() );
	}

	@Test
	public void transition() {
		clear.r( 0.5f ).g( 0.25f ).b( 0.125f ).a( 0.0625f ).depth( 2 ).stencil( 3 );

		clear.transition( control );

		verify( Gdx.gl ).glClearColor( 0.5f, 0.25f, 0.125f, 0.0625f );
		verify( Gdx.gl ).glClearDepthf( 2 );
		verify( Gdx.gl ).glClearStencil( 3 );
	}
}
