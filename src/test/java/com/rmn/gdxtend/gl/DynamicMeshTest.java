package com.rmn.gdxtend.gl;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.ShortBuffer;

import org.assertj.core.api.Assertions;
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

		Assertions.fail( "You can delete DynamicMesh.getVertices() now" );
	}

	@Test
	public void vertGrowth() {
		DynamicMesh dn = new DynamicMesh( 10, 8, VertexAttribute.Position() );

		assertThat( dn.getMaxVertices() ).isEqualTo( 10 );
		assertThat( dn.getMaxIndices() ).isEqualTo( 8 );

		DynamicMesh larger = dn.withCapacity( 11, 8 );

		assertThat( dn ).isNotEqualTo( larger );
		assertThat( larger.getMaxVertices() ).isEqualTo( 20 );
		assertThat( larger.getMaxIndices() ).isEqualTo( 8 );
	}

	@Test
	public void indexGrowth() {
		DynamicMesh dn = new DynamicMesh( 10, 8, VertexAttribute.Position() );

		assertThat( dn.getMaxVertices() ).isEqualTo( 10 );
		assertThat( dn.getMaxIndices() ).isEqualTo( 8 );

		DynamicMesh larger = dn.withCapacity( 10, 9 );

		assertThat( dn ).isNotEqualTo( larger );
		assertThat( larger.getMaxVertices() ).isEqualTo( 10 );
		assertThat( larger.getMaxIndices() ).isEqualTo( 16 );
	}

	@Test
	public void growth() {
		DynamicMesh dn = new DynamicMesh( 10, 5, VertexAttribute.Position() );

		float[] preVerts = new float[] { 1, 2, 3 };
		short[] preInd = new short[] { 4, 5, 6 };

		dn.setVertices( preVerts );
		dn.setIndices( preInd );

		int beforeVertCount = dn.getNumVertices();
		int beforeIndexCount = dn.getNumIndices();

		DynamicMesh larger = dn.withCapacity( 11, 6 );

		assertThat( dn ).isNotEqualTo( larger );
		assertThat( larger.getMaxVertices() ).isEqualTo( 20 );
		assertThat( larger.getMaxIndices() ).isEqualTo( 10 );

		// check we have the same data
		assertThat( larger.getNumVertices() ).isEqualTo( beforeVertCount );
		assertThat( larger.getNumIndices() ).isEqualTo( beforeIndexCount );

		float[] postVerts =
				new float[ larger.getNumVertices() * larger.getVertexSize() / 4 ];
		larger.getVertices( postVerts );
		assertThat( postVerts ).isEqualTo( preVerts );

		short[] postInd = new short[ larger.getNumIndices() ];
		larger.getIndices( postInd );
		assertThat( postInd ).isEqualTo( preInd );
	}

	@Test
	public void updateIndices() {
		DynamicMesh dn = new DynamicMesh( 0, 8, VertexAttribute.Position() );

		dn.updateIndices( 0, new short[] { 5, 6, 7 }, 2 );

		{
			short[] expected = new short[] { 5, 6 };

			ShortBuffer b = dn.getIndicesBuffer();
			assertThat( b.position() ).isEqualTo( 0 );
			assertThat( b.limit() ).isEqualTo( 2 );

			for( int i = 0; i < expected.length; i++ ) {
				assertThat( b.get( i ) )
						.as( "index " + i )
						.isEqualTo( expected[ i ] );
			}
		}

		dn.updateIndices( 2, new short[] { 11, 12, 13, 14, 15 } );

		{
			short[] expected = new short[] { 5, 6, 11, 12, 13, 14, 15 };

			ShortBuffer b = dn.getIndicesBuffer();
			assertThat( b.position() ).isEqualTo( 0 );
			assertThat( b.limit() ).isEqualTo( 7 );

			for( int i = 0; i < expected.length; i++ ) {
				assertThat( b.get( i ) )
						.as( "index " + i )
						.isEqualTo( expected[ i ] );
			}
		}
	}
}
