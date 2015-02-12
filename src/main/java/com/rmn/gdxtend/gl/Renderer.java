package com.rmn.gdxtend.gl;

import com.badlogic.gdx.graphics.GL20;

/**
 * Batches rendering to avoid state changes
 */
public class Renderer {

	private int compilationBatch = -2;

	private GeometryList[] geometry = new GeometryList[ 0 ];

	/**
	 * Adds states to this Renderer's internal ordering.
	 * 
	 * @param states
	 *          the states to add
	 * @return this
	 */
	public Renderer with( State... states ) {

		State[] larger = new State[ geometry.length + states.length ];
		System.arraycopy( states, 0, larger, 0, states.length );

		for( int i = 0; i < geometry.length; i++ ) {
			larger[ states.length + i ] = geometry[ i ].state;
		}

		int distinct = State.compile( larger );

		compilationBatch = larger[ 0 ].getCompilationBatch();

		if( distinct != geometry.length ) {
			GeometryList[] ngl = new GeometryList[ distinct ];

			// new lists
			for( State s : states ) {
				ngl[ s.getCompiledIndex() ] = new GeometryList( s, 100, 100 );
			}

			// copy old lists - their states know where to go
			for( GeometryList existing : ngl ) {
				ngl[ existing.state.getCompiledIndex() ] = existing;
			}

			geometry = ngl;
		}

		return this;
	}

	/**
	 * Adds triangles to be rendered
	 * 
	 * @param state
	 * @param vertices
	 * @param indices
	 */
	public void addTriangles( State state, float[] vertices, short[] indices ) {
		if( state.getCompilationBatch() != compilationBatch ) {
			with( state );
		}

		geometry[ state.getCompiledIndex() ].addGeometry( vertices, indices );
	}

	/**
	 * Draws all geometry
	 */
	public void render() {
		for( GeometryList gl : geometry ) {
			gl.state.apply();
			gl.geometry.render( gl.state.shader.program, GL20.GL_TRIANGLES );
		}
	}

	/**
	 * A list of geometry to be rendered using a particular shader
	 */
	private class GeometryList {

		public final State state;
		private DynamicMesh geometry;

		public GeometryList( State s, int vertCount, int indexCount ) {
			this.state = s;
			geometry = new DynamicMesh( vertCount, indexCount, s.shader.attributes );
		}

		public void addGeometry( float[] vertices, short[] indices ) {
			geometry = geometry.withCapacity(
					geometry.getNumVertices() +
							vertices.length / geometry.getVertexSize(),
					geometry.getNumIndices() + indices.length );
			geometry.addGeometry( vertices, indices );
		}
	}
}
