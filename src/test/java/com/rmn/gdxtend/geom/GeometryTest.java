package com.rmn.gdxtend.geom;

import static com.badlogic.gdx.graphics.VertexAttribute.Position;

import org.junit.Assert;
import org.junit.Test;

public class GeometryTest {

	private static final float TOLERANCE = 0.000001f;

	/**
	 * <pre>
	 *   1
	 * 2   0
	 *   3
	 * </pre>
	 */
	@Test
	public void fourSegmentCirle() {
		Shape s = new Shape( 4, 0, Position() );
		Geometry.circle.define( s );

		// assertj is a bit lacking for float arrays
		Assert.assertArrayEquals( new float[] {
				1, 0, 0,
				0, 1, 0,
				-1, 0, 0,
				0, -1, 0,
		}, s.vertexData, TOLERANCE );
	}

	/**
	 * <pre>
	 *   2
	 * 3 0 1
	 *   4
	 * </pre>
	 */
	@Test
	public void fourSegmentCenteredCirle() {
		Shape s = new Shape( 5, 0, Position() );
		Geometry.centeredCircle.define( s );

		// assertj is a bit lacking for float arrays
		Assert.assertArrayEquals( new float[] {
				0, 0, 0,
				1, 0, 0,
				0, 1, 0,
				-1, 0, 0,
				0, -1, 0,
		}, s.vertexData, TOLERANCE );
	}

	/**
	 * <pre>
	 * 3 2 1
	 * 4   0
	 * 5 6 7
	 * </pre>
	 */
	@Test
	public void eightSegmentCirle() {
		Shape shape = new Shape( 8, 0, Position() );
		Geometry.circle.define( shape );

		float c = (float) Math.cos( Math.toRadians( 45 ) );
		float s = (float) Math.sin( Math.toRadians( 45 ) );

		// assertj is a bit lacking for float arrays
		Assert.assertArrayEquals( new float[] {
				1, 0, 0,
				c, s, 0,
				0, 1, 0,
				-c, s, 0,
				-1, 0, 0,
				-c, -s, 0,
				0, -1, 0,
				c, -s, 0,
		}, shape.vertexData, TOLERANCE );
	}

	/**
	 * <pre>
	 * 4 3 2
	 * 5 0 1
	 * 6 7 8
	 * </pre>
	 */
	@Test
	public void eightSegmentCenteredCirle() {
		Shape shape = new Shape( 9, 0, Position() );
		Geometry.centeredCircle.define( shape );

		float c = (float) Math.cos( Math.toRadians( 45 ) );
		float s = (float) Math.sin( Math.toRadians( 45 ) );

		// assertj is a bit lacking for float arrays
		Assert.assertArrayEquals( new float[] {
				0, 0, 0,
				1, 0, 0,
				c, s, 0,
				0, 1, 0,
				-c, s, 0,
				-1, 0, 0,
				-c, -s, 0,
				0, -1, 0,
				c, -s, 0,
		}, shape.vertexData, TOLERANCE );
	}

}
