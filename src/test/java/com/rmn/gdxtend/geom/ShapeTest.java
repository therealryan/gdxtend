package com.rmn.gdxtend.geom;

import static com.badlogic.gdx.graphics.VertexAttribute.ColorPacked;
import static com.badlogic.gdx.graphics.VertexAttribute.Position;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class ShapeTest {

	@Test
	public void vertexIndex() {
		Shape s = new Shape( 3, 0, Position() );

		s.pos.xyz( 1, 2, 3 ).next();

		assertThat( s.vertexData ).isEqualTo( new float[] {
				1, 2, 3,
				0, 0, 0,
				0, 0, 0 } );

		s.index( 2 ).pos.x( 4 );

		assertThat( s.vertexData ).isEqualTo( new float[] {
				1, 2, 3,
				0, 0, 0,
				4, 0, 0 } );

		s.index( 0 ).pos.z( 5 );

		assertThat( s.vertexData ).isEqualTo( new float[] {
				1, 2, 5,
				0, 0, 0,
				4, 0, 0 } );
	}

	@Test
	public void position() {
		Shape s = new Shape( 5, 0, Position() )
				.pos.xyz( 1, 2, 3 ).next()
				.pos.x( 4 ).done().next()
				.pos.y( 5 ).done().next()
				.pos.z( 6 ).done().next()
				.pos.set( new Vector3( 7, 8, 9 ) );

		assertThat( s.vertexData ).isEqualTo( new float[] {
				1, 2, 3,
				4, 0, 0,
				0, 5, 0,
				0, 0, 6,
				7, 8, 9 } );
	}

	@Test
	public void allPosition() {

		Shape s = new Shape( 3, 0, Position() );

		s.pos.all().xyz( 1, 2, 3 );
		assertThat( s.vertexData ).isEqualTo( new float[] {
				1, 2, 3,
				1, 2, 3,
				1, 2, 3, } );

		s.pos.xyz( 4, 5, 6 );
		assertThat( s.vertexData ).isEqualTo( new float[] {
				4, 5, 6,
				1, 2, 3,
				1, 2, 3, } );

		s.pos.all().x( 7 );
		assertThat( s.vertexData ).isEqualTo( new float[] {
				7, 5, 6,
				7, 2, 3,
				7, 2, 3, } );

		s.pos.all().y( 8 );
		assertThat( s.vertexData ).isEqualTo( new float[] {
				7, 8, 6,
				7, 8, 3,
				7, 8, 3, } );

		s.pos.all().z( 9 );
		assertThat( s.vertexData ).isEqualTo( new float[] {
				7, 8, 9,
				7, 8, 9,
				7, 8, 9, } );
	}

	@Test
	public void colour() {
		Shape s =
				new Shape( 3, 0, ColorPacked() )
				.col.set( Color.RED ).next()
				.col.rgba( 0.5f, 0.25f, 0.125f, 0.0625f ).next()
				.col.r( 0.03125f )
						.g( 0.015625f )
						.b( 0.0078125f )
						.a( 0.00390625f ).done();

		assertThat( s.vertexData ).isEqualTo( new float[] {
				Float.intBitsToFloat(
						Color.rgba8888( Color.RED ) ),
				Float.intBitsToFloat(
						Color.rgba8888( 0.5f, 0.25f, 0.125f, 0.0625f ) ),
				Float.intBitsToFloat(
						Color.rgba8888( 0.03125f, 0.015625f, 0.0078125f, 0.00390625f ) )
		} );
	}

	@Test
	public void globalColour() {
		Shape s =
				new Shape( 3, 0, ColorPacked() )
				.col.all().set( Color.RED );

		assertThat( s.vertexData ).isEqualTo( new float[] {
				Float.intBitsToFloat(
						Color.rgba8888( Color.RED ) ),
				Float.intBitsToFloat(
						Color.rgba8888( Color.RED ) ),
				Float.intBitsToFloat(
						Color.rgba8888( Color.RED ) )
		} );
	}

	@Test
	public void colourAndPosition() {
		Shape s = new Shape( 3, 0, Position(), ColorPacked() )
				.pos.xyz( 1, 2, 3 )
				.col.set( Color.RED )
						.next()
				.pos.xyz( 4, 5, 6 )
				.col.rgba( 0.5f, 0.25f, 0.125f, 0.0625f )
						.next()
				.pos.xyz( 7, 8, 9 ).col
						.r( 0.03125f )
						.g( 0.015625f )
						.b( 0.0078125f )
						.a( 0.00390625f ).done();

		assertThat( s.vertexData ).isEqualTo( new float[] {
				1, 2, 3,
				Float.intBitsToFloat(
						Color.rgba8888( Color.RED ) ),
				4, 5, 6,
				Float.intBitsToFloat(
						Color.rgba8888( 0.5f, 0.25f, 0.125f, 0.0625f ) ),
				7, 8, 9,
				Float.intBitsToFloat(
						Color.rgba8888( 0.03125f, 0.015625f, 0.0078125f, 0.00390625f ) )
		} );

		// check that global setters only affect their own components
		s.pos.all().x( 8 );

		assertThat( s.vertexData ).isEqualTo( new float[] {
				8, 2, 3,
				Float.intBitsToFloat(
						Color.rgba8888( Color.RED ) ),
				8, 5, 6,
				Float.intBitsToFloat(
						Color.rgba8888( 0.5f, 0.25f, 0.125f, 0.0625f ) ),
				8, 8, 9,
				Float.intBitsToFloat(
						Color.rgba8888( 0.03125f, 0.015625f, 0.0078125f, 0.00390625f ) )
		} );
	}

	@Test( expected = NullPointerException.class )
	public void missing() {
		Shape s = new Shape( 3, 1, Position() );

		// we don't have the colour attribute
		s.col.set( Color.RED );
	}
}
