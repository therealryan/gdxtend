package com.rmn.gdxtend.gl.facets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Test;

import com.badlogic.gdx.graphics.GL20;

public class PolygonOffsetTest extends GLStateTest {

	PolygonOffset polyOffset = new PolygonOffset();
	PolygonOffset control = new PolygonOffset();

	@Test
	public void construction() {
		polyOffset.enabled( true ).factor( 0.5f ).units( 0.25f );

		assertTrue( polyOffset.enabled );
		assertEquals( 0.5f, polyOffset.factor, 0 );
		assertEquals( 0.25f, polyOffset.units, 0 );
	}

	@Test
	public void same() {
		Assert.assertTrue( polyOffset.equals( control ) );
	}

	@Test
	public void copy() {
		polyOffset.enabled( true ).factor( 0.5f ).units( 0.25f );

		PolygonOffset copy = new PolygonOffset().from( polyOffset );

		Assert.assertTrue( copy.equals( polyOffset ) );
	}

	@Test
	public void enabledComparison() {
		polyOffset.enabled( true );

		assertEquals( 1, polyOffset.compareTo( control ) );
		assertEquals( -1, control.compareTo( polyOffset ) );
	}

	@Test
	public void factorComparison() {
		polyOffset.factor( 0.5f );

		assertEquals( 1, polyOffset.compareTo( control ) );
		assertEquals( -1, control.compareTo( polyOffset ) );
	}

	@Test
	public void unitsComparison() {
		polyOffset.units( 0.5f );

		assertEquals( 1, polyOffset.compareTo( control ) );
		assertEquals( -1, control.compareTo( polyOffset ) );
	}

	@Test
	public void noopTransition() {
		polyOffset.transitionFrom( control, context );

		verify( context, never() ).glEnable( anyInt() );
		verify( context, never() ).glDisable( anyInt() );
		verify( context, never() ).glPolygonOffset( anyFloat(), anyFloat() );
	}

	@Test
	public void transition() {
		polyOffset.enabled( true ).factor( 0.5f ).units( 0.25f );

		polyOffset.transitionFrom( control, context );

		verify( context ).glEnable( GL20.GL_POLYGON_OFFSET_FILL );
		verify( context ).glPolygonOffset( 0.5f, 0.25f );
	}
}
