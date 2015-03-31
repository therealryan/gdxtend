package com.rmn.gdxtend.gl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.rmn.gdxtend.GdxTest;
import com.rmn.gdxtend.gl.shader.None;

public class RendererTest extends GdxTest {

	private Renderer r = new Renderer();

	private State<None> state = State.build( None.instance );

	@Test
	public void grow() {
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
		Renderer r = new Renderer();

		float[] v = new float[ 9 ];
		short[] i = new short[ 3 ];

		State<None> s = State.build( None.instance );
		Arrays.fill( v, 1 );
		Arrays.fill( i, (short) 2 );

		r.addTriangles( s, v, i );
	}
}
