package com.rmn.gdxtend.gl.facets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class ClearTest extends GLStateTest {
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
		clear.r( 1 );

		assertEquals( 1, clear.compareTo( control ) );
		assertEquals( -1, control.compareTo( clear ) );
	}

	@Test
	public void depthComparison() {
		clear.depth( 1 );

		assertEquals( 1, clear.compareTo( control ) );
		assertEquals( -1, control.compareTo( clear ) );
	}

	@Test
	public void stencilComparison() {
		clear.stencil( 1 );

		assertEquals( 1, clear.compareTo( control ) );
		assertEquals( -1, control.compareTo( clear ) );
	}

	@Test
	public void noopTransition() {
		clear.transitionFrom( control, context );

		verify( context, never() ).glClearColor(
				anyFloat(), anyFloat(), anyFloat(), anyFloat() );
		verify( context, never() ).glClearDepthf( anyFloat() );
		verify( context, never() ).glClearStencil( anyInt() );
	}

	@Test
	public void transition() {
		clear.r( 0.5f ).g( 0.25f ).b( 0.125f ).a( 0.0625f ).depth( 2 ).stencil( 3 );

		clear.transitionFrom( control, context );

		verify( context ).glClearColor( 0.5f, 0.25f, 0.125f, 0.0625f );
		verify( context ).glClearDepthf( 2 );
		verify( context ).glClearStencil( 3 );
	}
}
