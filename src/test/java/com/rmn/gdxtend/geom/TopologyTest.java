package com.rmn.gdxtend.geom;

import static com.badlogic.gdx.graphics.VertexAttribute.Position;
import static com.rmn.gdxtend.geom.Topology.arrow;
import static com.rmn.gdxtend.geom.Topology.fan;
import static com.rmn.gdxtend.geom.Topology.quads;
import static com.rmn.gdxtend.geom.Topology.radial;
import static com.rmn.gdxtend.geom.Topology.strip;
import static com.rmn.gdxtend.geom.Topology.triangles;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class TopologyTest {

	@Test
	public void oneTriangle() {
		Shape s = new Shape( 1, triangles, Position() );
		assertThat( s.vertices() ).isEqualTo( 3 );
		// @formatter:off
		//   2
		//  / \
		// 0---1
		// @formatter:on
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 1, 2 } );
	}

	@Test
	public void twoTriangles() {
		Shape s = new Shape( 2, triangles, Position() );
		assertThat( s.vertices() ).isEqualTo( 6 );
		// @formatter:off
		//   2     5
		//  / \   / \
		// 0---1 3---4
		// @formatter:on
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 1, 2,
				3, 4, 5 } );
	}

	@Test
	public void oneQuad() {
		Shape s = new Shape( 1, quads, Position() );
		assertThat( s.vertices() ).isEqualTo( 4 );
		// 1-3
		// |\|
		// 0-2
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 2, 1,
				2, 3, 1 } );
	}

	@Test
	public void twoQuads() {
		Shape s = new Shape( 2, quads, Position() );
		assertThat( s.vertices() ).isEqualTo( 8 );
		// 1-3 5-7
		// |\| |\|
		// 0-2 4-6
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 2, 1,
				2, 3, 1,
				4, 6, 5,
				6, 7, 5 } );
	}

	@Test
	public void oneStrip() {
		Shape s = new Shape( 1, strip, Position() );
		assertThat( s.vertices() ).isEqualTo( 3 );
		// @formatter:off
		//   1
		//  / \
		// 0---2
		// @formatter:on
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 2, 1 } );
	}

	@Test
	public void twoStrip() {
		Shape s = new Shape( 2, strip, Position() );
		assertThat( s.vertices() ).isEqualTo( 4 );
		// @formatter:off
		//   1---3
		//  / \ /
		// 0---2
		// @formatter:on
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 2, 1,
				1, 2, 3 } );
	}

	@Test
	public void threeStrip() {
		Shape s = new Shape( 3, strip, Position() );
		assertThat( s.vertices() ).isEqualTo( 5 );
		// @formatter:off
		//   1---3
		//  / \ / \
		// 0---2---4
		// @formatter:on
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 2, 1,
				1, 2, 3,
				2, 4, 3 } );
	}

	@Test
	public void threeFan() {
		Shape s = new Shape( 3, fan, Position() );
		assertThat( s.vertices() ).isEqualTo( 3 );
		// @formatter:off
		//   2
		//  / \
		// 0---1
		// @formatter:on
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 1, 2 } );
	}

	@Test
	public void fourFan() {
		Shape s = new Shape( 4, fan, Position() );
		assertThat( s.vertices() ).isEqualTo( 4 );
		// 3-2
		// |/|
		// 0-1
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 1, 2,
				0, 2, 3 } );
	}

	@Test
	public void fiveFan() {
		Shape s = new Shape( 5, fan, Position() );
		assertThat( s.vertices() ).isEqualTo( 5 );
		// @formatter:off
		// 4-3-2
		//  \|/|
		//   0-1
		// @formatter:on
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 1, 2,
				0, 2, 3,
				0, 3, 4 } );
	}

	@Test
	public void twoRadial() {
		Shape s = new Shape( 2, radial, Position() );
		assertThat( s.vertices() ).isEqualTo( 3 );
		// @formatter:off
		//   1
		//  /|\
		// | 0 |
		//  \|/
		//   2
		// @formatter:on
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 1, 2,
				0, 2, 1 } );
	}

	@Test
	public void threeRadial() {
		Shape s = new Shape( 3, radial, Position() );
		assertThat( s.vertices() ).isEqualTo( 4 );
		// @formatter:off
		//    2
		//   /|\
		//  | 0 |
		//  |/ \|
		//  3---1
		// @formatter:on
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 1, 2,
				0, 2, 3,
				0, 3, 1 } );
	}

	@Test
	public void fourRadial() {
		Shape s = new Shape( 4, radial, Position() );
		assertThat( s.vertices() ).isEqualTo( 5 );
		// @formatter:off
		// 2---1
		// |\ /|
		// | 0 |
		// |/ \|
		// 3---4
		// @formatter:on
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 1, 2,
				0, 2, 3,
				0, 3, 4,
				0, 4, 1 } );
	}

	@Test
	public void stemlessArrow() {
		Shape s = new Shape( 0, arrow, Position() );
		assertThat( s.vertices() ).isEqualTo( 5 );
		// @formatter:off
		// 1
		// |\
		// 3_\
		// |  0
		// 4‾/
		// |/
		// 2 
		// @formatter:on
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 1, 3,
				0, 3, 4,
				0, 4, 2
		} );
	}

	@Test
	public void singleSegArrow() {
		Shape s = new Shape( 1, arrow, Position() );
		assertThat( s.vertices() ).isEqualTo( 7 );
		// @formatter:off
		//   1
		//   |\
		// 5-3_\
		// |\|  0
		// 6-4‾/
		//   |/
		//   2
		// @formatter:on
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 1, 3,
				0, 3, 4,
				0, 4, 2,
				3, 5, 4,
				4, 5, 6
		} );
	}

	@Test
	public void tripleSegArrow() {
		Shape s = new Shape( 3, arrow, Position() );
		assertThat( s.vertices() ).isEqualTo( 11 );
		// @formatter:off
		//       1
		//       |\
		// 9-7-5-3_\
		// |\|\|\|  0
		//10-8-6-4‾/
		//       |/
		//       2
		// @formatter:on
		assertThat( s.indices ).isEqualTo( new short[] {
				0, 1, 3,
				0, 3, 4,
				0, 4, 2,
				3, 5, 4,
				4, 5, 6,
				5, 7, 6,
				6, 7, 8,
				7, 9, 8,
				8, 9, 10
		} );
	}
}
