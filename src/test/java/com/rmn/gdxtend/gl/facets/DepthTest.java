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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.gl.enums.ComparisonFunction;

public class DepthTest extends FacetTest {

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
		comparisonOrder( control, depth.enabled( true ) );
	}

	@Test
	public void functionComparison() {
		comparisonOrder( control, depth.function( ComparisonFunction.ALWAYS ) );
	}

	@Test
	public void maskComparison() {
		comparisonOrder( control, depth.mask( false ) );
	}

	@Test
	public void nearComparison() {
		comparisonOrder( control, depth.near( 0.5f ) );
	}

	@Test
	public void farComparison() {
		comparisonOrder( control, depth.far( 1.5f ) );
	}

	@Test
	public void noopTransition() {
		depth.transition( control );

		verify( Gdx.gl, never() ).glEnable( anyInt() );
		verify( Gdx.gl, never() ).glDisable( anyInt() );
		verify( Gdx.gl, never() ).glDepthFunc( anyInt() );
		verify( Gdx.gl, never() ).glDepthMask( anyBoolean() );
		verify( Gdx.gl, never() ).glDepthRangef( anyFloat(), anyFloat() );
	}

	@Test
	public void transition() {
		depth.enabled( true )
				.function( ComparisonFunction.ALWAYS )
				.mask( false )
				.near( 0.5f )
				.far( 0.25f );

		depth.transition( control );

		verify( Gdx.gl ).glEnable( GL20.GL_DEPTH_TEST );
		verify( Gdx.gl ).glDepthFunc( GL20.GL_ALWAYS );
		verify( Gdx.gl ).glDepthMask( false );
		verify( Gdx.gl ).glDepthRangef( 0.5f, 0.25f );

		control.transition( depth );

		verify( Gdx.gl ).glDisable( GL20.GL_DEPTH_TEST );
	}
}
