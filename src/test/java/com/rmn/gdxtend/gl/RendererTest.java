package com.rmn.gdxtend.gl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.rmn.gdxtend.GdxTest;
import com.rmn.gdxtend.gl.shader.None;

public class RendererTest extends GdxTest {

	@Test
	public void grow() {
		Renderer r = new Renderer();

		State<None> state = State.build( None.instance );

		State<?>[] larger = r.grow( state );

		assertThat( larger ).isEqualTo( new State[] { state } );
	}

	@Test
	public void withOne() {
		Renderer r = new Renderer();

		assertThat( r.geometry ).hasSize( 0 );

		State<?> s = State.build( None.instance );

		r.with( s );

		assertThat( r.geometry ).hasSize( 1 );
		assertThat( r.geometry[ 0 ].state ).isEqualTo( s );
	}

	@Test
	public void withSame() {

		Renderer r = new Renderer();

		assertThat( r.geometry ).hasSize( 0 );

		State<?> s = State.build( None.instance );
		State<?> t = State.build( None.instance );

		assertThat( s ).isEqualTo( t );

		r.with( s ).with( t );

		assertThat( r.geometry ).hasSize( 1 );
		assertThat( r.geometry[ 0 ].state ).isEqualTo( s );
	}

	@Test
	public void withTwo() {

		Renderer r = new Renderer();

		assertThat( r.geometry ).hasSize( 0 );

		State<None> s = State.build( None.instance );
		State<None> t = State.build( None.instance );
		t.blend.a( 0.5f );

		assertThat( s ).isLessThan( t );

		r.with( s ).with( t );

		assertThat( r.geometry ).hasSize( 2 );
		assertThat( r.geometry[ 0 ].state ).isEqualTo( s );
		assertThat( r.geometry[ 1 ].state ).isEqualTo( t );
	}

	@Test
	public void addTriangles() {
		Renderer r = new Renderer().withInitialSize( 5 );
		assertThat( r.geometry ).hasSize( 0 );

		State<None> s = State.build( None.instance );

		float[] v = new float[ 3 ];
		Arrays.fill( v, 1 );
		short[] i = new short[] { 0, 1, 2 };

		r.addTriangles( s, v, i );

		assertThat( r.geometry ).hasSize( 1 );
		assertThat( r.geometry[ 0 ].vertices ).isEqualTo( new float[] {
				1, 1, 1,
				0, 0, 0,
				0, 0, 0,
				0, 0, 0,
				0, 0, 0,
		} );
		assertThat( r.geometry[ 0 ].indices ).isEqualTo( new short[] {
				0, 1, 2, 0, 0,
		} );

		Arrays.fill( v, 3 );

		r.addTriangles( s, v, i );
		assertThat( r.geometry ).hasSize( 1 );
		assertThat( r.geometry[ 0 ].vertices ).isEqualTo( new float[] {
				1, 1, 1,
				3, 3, 3,
				0, 0, 0,
				0, 0, 0,
				0, 0, 0,
		} );
		assertThat( r.geometry[ 0 ].indices ).isEqualTo( new short[] {
				0, 1, 2, 1, 2, 3, 0, 0, 0,
		} );
	}
}
