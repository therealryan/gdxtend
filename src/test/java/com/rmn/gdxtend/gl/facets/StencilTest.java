package com.rmn.gdxtend.gl.facets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.gl.enums.ComparisonFunction;
import com.rmn.gdxtend.gl.enums.StencilOperation;

public class StencilTest extends GLStateTest {

	private Stencil stencil = new Stencil();
	private Stencil control = new Stencil();

	@Test
	public void construction() {
		stencil.enabled( true )
				.function( ComparisonFunction.GEQUAL )
				.reference( 1 )
				.testMask( 2 )
				.writeMask( 3 )
				.sfail( StencilOperation.DECR )
				.dpfail( StencilOperation.DECR_WRAP )
				.dppass( StencilOperation.INCR );

		assertTrue( stencil.enabled );
		assertEquals( ComparisonFunction.GEQUAL, stencil.function );
		assertEquals( 1, stencil.reference );
		assertEquals( 2, stencil.testMask );
		assertEquals( 3, stencil.writeMask );
		assertEquals( StencilOperation.DECR, stencil.sfail );
		assertEquals( StencilOperation.DECR_WRAP, stencil.dpfail );
		assertEquals( StencilOperation.INCR, stencil.dppass );
	}

	@Test
	public void same() {
		assertTrue( stencil.equals( control ) );
	}

	@Test
	public void copy() {
		stencil.enabled( true )
				.function( ComparisonFunction.GEQUAL )
				.reference( 1 )
				.testMask( 2 )
				.writeMask( 3 )
				.sfail( StencilOperation.DECR )
				.dpfail( StencilOperation.DECR_WRAP )
				.dppass( StencilOperation.INCR );

		Stencil copy = new Stencil().from( stencil );

		assertTrue( copy.equals( stencil ) );
	}

	@Test
	public void enabledComparison() {
		stencil.enabled( true );

		assertEquals( 1, stencil.compareTo( control ) );
		assertEquals( -1, control.compareTo( stencil ) );
	}

	@Test
	public void functionComparison() {
		stencil.function( ComparisonFunction.GEQUAL );

		assertEquals( -1, stencil.compareTo( control ) );
		assertEquals( 1, control.compareTo( stencil ) );
	}

	@Test
	public void referenceComparison() {
		stencil.reference( 3 );

		assertEquals( 1, stencil.compareTo( control ) );
		assertEquals( -1, control.compareTo( stencil ) );
	}

	@Test
	public void testMaskComparison() {
		stencil.testMask( 3 );

		assertEquals( 1, stencil.compareTo( control ) );
		assertEquals( -1, control.compareTo( stencil ) );
	}

	@Test
	public void writeMaskComparison() {
		stencil.writeMask( 3 );

		assertEquals( 1, stencil.compareTo( control ) );
		assertEquals( -1, control.compareTo( stencil ) );
	}

	@Test
	public void sFailCmparison() {
		stencil.sfail( StencilOperation.DECR );

		assertEquals( 1, stencil.compareTo( control ) );
		assertEquals( -1, control.compareTo( stencil ) );
	}

	@Test
	public void dpFailComparison() {
		stencil.dpfail( StencilOperation.DECR );

		assertEquals( 1, stencil.compareTo( control ) );
		assertEquals( -1, control.compareTo( stencil ) );
	}

	@Test
	public void dpPassComparison() {
		stencil.dppass( StencilOperation.DECR );

		assertEquals( 1, stencil.compareTo( control ) );
		assertEquals( -1, control.compareTo( stencil ) );
	}

	public void noopTransition() {
		stencil.transitionFrom( control, context );

		verify( context, never() ).glEnable( anyInt() );
		verify( context, never() ).glDisable( anyInt() );
		verify( context, never() ).glStencilFunc( anyInt(), anyInt(), anyInt() );
		verify( context, never() ).glStencilMask( anyInt() );
		verify( context, never() ).glStencilMask( anyInt() );
	}

	@Test
	public void transition() {

		stencil.enabled( true )
				.function( ComparisonFunction.GEQUAL )
				.reference( 1 )
				.testMask( 2 )
				.writeMask( 3 )
				.sfail( StencilOperation.DECR )
				.dpfail( StencilOperation.DECR_WRAP )
				.dppass( StencilOperation.INCR );

		stencil.transitionFrom( control, context );

		verify( context ).glEnable( GL20.GL_STENCIL_TEST );
		verify( context ).glStencilFunc( GL20.GL_GEQUAL, 1, 2 );
		verify( context ).glStencilMask( 3 );
		verify( context ).glStencilOp( GL20.GL_DECR, GL20.GL_DECR_WRAP,
				GL20.GL_INCR );

		control.transitionFrom( stencil, context );

		verify( context ).glDisable( GL20.GL_STENCIL_TEST );
	}
}
