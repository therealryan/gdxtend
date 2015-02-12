package com.rmn.gdxtend.gl;

import java.nio.ShortBuffer;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.utils.BufferUtils;

/**
 * Adds support for dynamic resizing and updating of indices
 */
public class DynamicMesh extends Mesh {

	public DynamicMesh( int initialVertexCount, int initialIndexCount,
			VertexAttribute... attributes ) {
		super( false, initialVertexCount, initialIndexCount, attributes );
	}

	public DynamicMesh( int initialVertexCount, int initialIndexCount,
			VertexAttributes attributes ) {
		super( false, initialVertexCount, initialIndexCount, attributes );
	}

	public DynamicMesh reset() {
		getVerticesBuffer().position( 0 ).limit( 0 );
		getIndicesBuffer().position( 0 ).limit( 0 );
		return this;
	}

	/**
	 * Makes sure that we've got a buffer big enough for all your awesome geometry
	 * 
	 * @param vertexCount
	 *          desired max vertex count
	 * @param indexCount
	 *          desired max index count
	 * @return Forget that douche, I'm your {@link DynamicMesh} now.
	 */
	public DynamicMesh withCapacity( int vertexCount, int indexCount ) {
		int vt = getMaxVertices();
		int it = getMaxIndices();

		// double up if needed
		vt *= vt < vertexCount ? 2 : 1;
		it *= it < indexCount ? 2 : 1;

		if( vt > getMaxVertices() || it > getMaxIndices() ) {
			// we need to grow
			DynamicMesh larger = new DynamicMesh( vt, it, getVertexAttributes() );

			// copy existing data
			float[] vd = new float[ getNumVertices() * getVertexSize() / 4 ];
			getVertices( vd );
			larger.setVertices( vd );

			short[] id = new short[ getNumIndices() ];
			getIndices( id );
			larger.setIndices( id );

			// we've been replaced :-(
			dispose();

			return larger;
		}
		else {
			// we're already big enough
			return this;
		}
	}

	/**
	 * Adds geometry to this mesh
	 * 
	 * @param vertices
	 *          the vertex data
	 * @param indices
	 *          the index data
	 */
	public void addGeometry( float[] vertices, short[] indices ) {
		updateVertices( getNumVertices(), vertices );
		updateIndices( getNumIndices(), indices );
	}

	/**
	 * Updates some of the triangle indices. Updates the index range to encompass
	 * the data
	 * 
	 * @param targetOffset
	 *          where to start copying into this mesh
	 * @param indices
	 *          the indices to copy
	 */
	public void updateIndices( int targetOffset, short[] indices ) {
		updateIndices( targetOffset, indices, indices.length );
	}

	/**
	 * Updates some of the triangles indices. Updates the index range to encompass
	 * the data
	 * 
	 * @param targetOffset
	 *          where to start copying into this mesh
	 * @param indices
	 *          the indices to copy
	 * @param offset
	 *          where to start copying from
	 * @param count
	 *          how many to copy
	 */
	public void updateIndices( int targetOffset, short[] indices, int count ) {

		ShortBuffer ib = getIndicesBuffer();
		final int pos = ib.position();

		int limit = Math.max( targetOffset + count, ib.limit() );

		ib.limit( limit );
		ib.position( targetOffset );
		BufferUtils.copy( indices, 0, ib, count );

		ib.position( pos );
	}

	@Override
	public float[] getVertices( int srcOffset, int count, float[] vertices,
			int destOffset ) {
		if( count == 0 || destOffset == vertices.length ) {
			return vertices;
		}
		else {
			return super.getVertices( srcOffset, count, vertices, destOffset );
		}
	}

	@Override
	public void getIndices( int srcOffset, int count, short[] indices,
			int destOffset ) {
		if( count == 0 || destOffset == indices.length ) {
			// we're done here
		}
		else {
			super.getIndices( srcOffset, count, indices, destOffset );
		}
	}
}
