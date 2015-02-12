package com.rmn.gdxtend.gl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.rmn.gdxtend.GdxTest;
import com.rmn.gdxtend.gl.enums.MagFilter;
import com.rmn.gdxtend.gl.shader.None;

public class StateTest extends GdxTest {

	private State base;
	private State[] altered = new State[ 6 ];

	@Override
	@Before
	public void before() {
		base = new State( None.instance );

		for( int i = 0; i < altered.length; i++ ) {
			altered[ i ] = new State( None.instance );
		}

		altered[ 0 ].blend.r( 1 );
		altered[ 1 ].clear.r( 1 );
		altered[ 2 ].depthTest.enabled( true );
		altered[ 3 ].polyOffset.enabled( true );
		altered[ 4 ].stencil.enabled( true );
		altered[ 5 ].texture.mag( MagFilter.NEAREST );
	}

	@Test
	public void comparison() {
		{ // same
			State s = new State( None.instance );
			assertTrue( s.equals( base ) );
			assertTrue( s.compareTo( base ) == 0 );
		}

		// check altered ordering
		for( int i = 0; i < altered.length; i++ ) {
			State a = altered[ i ];

			assertFalse( "index " + i, base.equals( a ) );
			assertEquals( "index " + i, -1, base.compareTo( a ) );
			assertEquals( "index " + i, 1, a.compareTo( base ) );
		}
	}

	@Test
	public void compiledComparison() {

		State s = new State( None.instance );
		State t = new State( None.instance );

		State.compile( s, t );

		// alter a facet
		t.blend.r( 1 );

		// we're now comparing by index, so the change is not noticed
		assertTrue( s.equals( t ) );
	}
}
