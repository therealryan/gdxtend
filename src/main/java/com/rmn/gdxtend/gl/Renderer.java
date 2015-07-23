package com.rmn.gdxtend.gl;

import java.util.Arrays;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.rmn.gdxtend.geom.Shape;
import com.rmn.gdxtend.util.ArrayUtil;

/**
 * Batches rendering to avoid state changes
 */
public class Renderer {

	private int compilationBatch = -2;

	private int initialSize = 100;

	GeometryBatch[] geometry = new GeometryBatch[ 0 ];

	/**
	 * Sets the initial size of new geometry batches
	 * 
	 * @param size
	 *          The number of vertices and indices that can be accommodated
	 *          without resizing the buffers
	 * @return this
	 */
	public Renderer withInitialSize( int size ) {
		initialSize = size;
		return this;
	}

	/**
	 * Adds states to this Renderer's internal ordering.
	 * 
	 * @param states
	 *          the states to add
	 * @return this
	 */
	public Renderer with( State<?>... states ) {

		State<?>[] larger = grow( states );

		int distinct = State.compile( larger );

		compilationBatch = larger[ 0 ].getCompilationBatch();

		if( distinct != geometry.length ) {
			GeometryBatch[] ngl = new GeometryBatch[ distinct ];

			// new lists
			for( State<?> s : states ) {
				ngl[ s.getCompiledIndex() ] =
						new GeometryBatch( s, initialSize, initialSize );
			}

			// copy old lists - their states know where to go
			for( GeometryBatch existing : geometry ) {
				ngl[ existing.state.getCompiledIndex() ] = existing;
			}

			geometry = ngl;
		}

		return this;
	}

	/**
	 * @param additional
	 * @return an array containing all of the currently-known states and the
	 *         additional ones
	 */
	protected State<?>[] grow( State<?>... additional ) {

		State<?>[] larger = new State[ geometry.length + additional.length ];
		System.arraycopy( additional, 0, larger, 0, additional.length );

		for( int i = 0; i < geometry.length; i++ ) {
			larger[ additional.length + i ] = geometry[ i ].state;
		}

		return larger;
	}

	public void add( State<?> gls, Shape shape ) {
		addTriangles( gls, shape.vertexData, shape.indices );
	}

	/**
	 * Adds triangles to be rendered
	 * 
	 * @param state
	 * @param vertices
	 * @param indices
	 */
	public void addTriangles( State<?> state, float[] vertices, short[] indices ) {
		if( state.getCompilationBatch() != compilationBatch ) {
			with( state );
		}

		geometry[ state.getCompiledIndex() ].addGeometry( vertices, indices );
	}

	/**
	 * Draws all geometry
	 */
	public void render() {
		for( GeometryBatch b : geometry ) {
			b.render();
			b.reset();
		}
	}

	/**
	 * A list of geometry to be rendered using a particular shader
	 */
	class GeometryBatch {

		public final State<?> state;
		float[] vertices;
		int vi = 0;

		short[] indices;
		int ii = 0;

		boolean dirty = true;
		private Mesh mesh;

		public GeometryBatch( State<?> s, int vertCount, int indexCount ) {
			this.state = s;
			vertices = new float[ vertCount * s.shader.attributes.vertexSize / 4 ];
			indices = new short[ indexCount ];
		}

		public void reset() {
			vi = 0;
			ii = 0;
			dirty = true;
		}

		public void addGeometry( float[] verts, short[] triangles ) {
			int existingVertexCount = vi / ( state.shader.attributes.vertexSize / 4 );
			vertices = ArrayUtil.append( vertices, vi, verts );
			indices = ArrayUtil.append( indices, ii, triangles );

			for( int i = 0; i < triangles.length; i++ ) {
				indices[ i + ii ] += existingVertexCount;
			}

			vi += verts.length;
			ii += triangles.length;
			dirty = true;
		}

		public void render() {
			if( mesh == null
					|| mesh.getMaxVertices() < vi
					|| mesh.getMaxIndices() < ii ) {
				mesh = new Mesh( false, vi, ii, state.shader.attributes );
				dirty = true;
			}

			if( dirty ) {
				mesh.setVertices( vertices, 0, vi );
				mesh.setIndices( indices, 0, ii );
			}

			state.apply();
			mesh.render( state.shader.program, GL20.GL_TRIANGLES );
		}

		@Override
		public String toString() {
			return Arrays.toString( vertices ) + Arrays.toString( indices );
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for( GeometryBatch gb : geometry ) {
			sb.append( gb ).append( "\n" );
		}
		return sb.toString();
	}
}
