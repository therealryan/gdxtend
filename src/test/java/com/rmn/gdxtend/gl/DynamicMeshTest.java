package com.rmn.gdxtend.gl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.ShortBuffer;

import org.junit.Test;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.rmn.gdxtend.GdxTest;

public class DynamicMeshTest extends GdxTest {

	/**
	 * The test exists to detect when the failure is fixed upstream so I can
	 * remove the workaround
	 */
	@Test( expected = IndexOutOfBoundsException.class )
	public void badFailureThatIDontLike() {
		Mesh m = new Mesh( false, 1, 1, VertexAttribute.Position() );

		float[] f = new float[ VertexAttribute.Position().numComponents ];

		// for some reason you're not allowed to get zero vertices from a mesh.
		// I'm not sure I'll ever need to outside of a test, but why disallow it?
		m.getVertices( f );

		fail( "You can delete DynamicMesh.getVertices() now" );
	}

	@Test
	public void vertGrowth() {
		DynamicMesh dn = new DynamicMesh( 10, 8, VertexAttribute.Position() );

		assertEquals( 10, dn.getMaxVertices() );
		assertEquals( 8, dn.getMaxIndices() );

		DynamicMesh larger = dn.withCapacity( 11, 8 );

		assertTrue( dn != larger );
		assertEquals( 20, larger.getMaxVertices() );
		assertEquals( 8, larger.getMaxIndices() );
	}

	@Test
	public void indexGrowth() {
		DynamicMesh dn = new DynamicMesh( 10, 8, VertexAttribute.Position() );

		assertEquals( 10, dn.getMaxVertices() );
		assertEquals( 8, dn.getMaxIndices() );

		DynamicMesh larger = dn.withCapacity( 10, 9 );

		assertTrue( dn != larger );
		assertEquals( 10, larger.getMaxVertices() );
		assertEquals( 16, larger.getMaxIndices() );
	}

	@Test
	public void growth() {
		DynamicMesh dn = new DynamicMesh( 10, 5, VertexAttribute.Position() );

		float[] preVerts = new float[] { 1, 2, 3 };
		short[] preInd = new short[] { 4, 5, 6 };

		dn.setVertices( preVerts );
		dn.setIndices( preInd );

		int beforeVertCount = dn.getNumVertices();
		int beforeindexCount = dn.getNumIndices();

		DynamicMesh larger = dn.withCapacity( 11, 6 );

		assertTrue( dn != larger );
		assertEquals( 20, larger.getMaxVertices() );
		assertEquals( 10, larger.getMaxIndices() );

		// check we have the same data
		assertEquals( beforeVertCount, larger.getNumVertices() );
		assertEquals( beforeindexCount, larger.getNumIndices() );

		float[] postVerts =
				new float[ larger.getNumVertices() * larger.getVertexSize() / 4 ];
		larger.getVertices( postVerts );
		assertArrayEquals( preVerts, postVerts, 0 );

		short[] postInd = new short[ larger.getNumIndices() ];
		larger.getIndices( postInd );
		assertArrayEquals( preInd, postInd );
	}

	@Test
	public void updateIndices() {
		DynamicMesh dn = new DynamicMesh( 0, 8, VertexAttribute.Position() );

		dn.updateIndices( 0, new short[] { 5, 6, 7 }, 2 );

		{
			short[] expected = new short[] { 5, 6 };

			ShortBuffer b = dn.getIndicesBuffer();
			assertEquals( 0, b.position() );
			assertEquals( 2, b.limit() );

			for( int i = 0; i < expected.length; i++ ) {
				assertEquals( "index " + i, expected[ i ], b.get( i ) );
			}
		}

		dn.updateIndices( 2, new short[] { 11, 12, 13, 14, 15 } );

		{
			short[] expected = new short[] { 5, 6, 11, 12, 13, 14, 15 };

			ShortBuffer b = dn.getIndicesBuffer();
			assertEquals( 0, b.position() );
			assertEquals( 7, b.limit() );

			for( int i = 0; i < expected.length; i++ ) {
				assertEquals( "index " + i, expected[ i ], b.get( i ) );
			}
		}
	}
}
