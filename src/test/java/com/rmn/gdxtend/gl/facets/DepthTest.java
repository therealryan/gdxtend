package com.rmn.gdxtend.gl.facets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.gl.enums.ComparisonFunction;

public class DepthTest extends GLStateTest {

	public Depth depth = new Depth();
	public Depth control = new Depth();

	@Test
	public void construction() {
		depth.enabled( true )
				.function( ComparisonFunction.ALWAYS )
				.mask( false )
				.near( 0.5f )
				.far( 0.25f );

		assertTrue( depth.enabled );
		assertEquals( ComparisonFunction.ALWAYS, depth.func );
		assertFalse( depth.mask );
		assertEquals( 0.5f, depth.near, 0 );
		assertEquals( 0.25f, depth.far, 0 );
	}

	@Test
	public void same() {
		assertTrue( depth.equals( control ) );
	}

	@Test
	public void copy() {
		depth.enabled( true )
				.function( ComparisonFunction.ALWAYS )
				.mask( false )
				.near( 0.5f )
				.far( 0.25f );

		Depth copy = new Depth().from( depth );

		assertTrue( copy.equals( depth ) );
	}

	@Test
	public void enabledComparison() {
		depth.enabled( true );

		assertEquals( 1, depth.compareTo( control ) );
		assertEquals( -1, control.compareTo( depth ) );
	}

	@Test
	public void functionComparison() {
		depth.function( ComparisonFunction.ALWAYS );
		assertEquals( 1, depth.compareTo( control ) );
		assertEquals( -1, control.compareTo( depth ) );
	}

	@Test
	public void maskComparison() {
		depth.mask( false );

		assertEquals( -1, depth.compareTo( control ) );
		assertEquals( 1, control.compareTo( depth ) );
	}

	@Test
	public void nearComparison() {
		depth.near( 0.5f );

		assertEquals( 1, depth.compareTo( control ) );
		assertEquals( -1, control.compareTo( depth ) );
	}

	@Test
	public void farComparison() {
		depth.far( 0.5f );

		assertEquals( -1, depth.compareTo( control ) );
		assertEquals( 1, control.compareTo( depth ) );
	}

	@Test
	public void noopTransition() {
		depth.transitionFrom( control, context );

		verify( context, never() ).glEnable( anyInt() );
		verify( context, never() ).glDisable( anyInt() );
		verify( context, never() ).glDepthFunc( anyInt() );
		verify( context, never() ).glDepthMask( anyBoolean() );
		verify( context, never() ).glDepthRangef( anyFloat(), anyFloat() );
	}

	@Test
	public void transition() {
		depth.enabled( true )
				.function( ComparisonFunction.ALWAYS )
				.mask( false )
				.near( 0.5f )
				.far( 0.25f );

		depth.transitionFrom( control, context );

		verify( context ).glEnable( GL20.GL_DEPTH_TEST );
		verify( context ).glDepthFunc( GL20.GL_ALWAYS );
		verify( context ).glDepthMask( false );
		verify( context ).glDepthRangef( 0.5f, 0.25f );

		control.transitionFrom( depth, context );

		verify( context ).glDisable( GL20.GL_DEPTH_TEST );
	}
}
