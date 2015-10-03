package com.rmn.gdxtend.gl;

import java.util.Arrays;

import com.rmn.gdxtend.gl.facets.Blend;
import com.rmn.gdxtend.gl.facets.Clear;
import com.rmn.gdxtend.gl.facets.Depth;
import com.rmn.gdxtend.gl.facets.Facet;
import com.rmn.gdxtend.gl.facets.PolygonOffset;
import com.rmn.gdxtend.gl.facets.Shader;
import com.rmn.gdxtend.gl.facets.Stencil;
import com.rmn.gdxtend.gl.facets.TextureState;
import com.rmn.gdxtend.gl.shader.None;

/**
 * Represents a rendering state
 *
 * @param <T>
 *          the shader type
 */
public class State<T extends Shader> implements Comparable<State<?>> {
	/**
	 * The current state of openGL
	 */
	private static State<?> currentState = build( None.instance );

	/**
	 * The {@link State} class mirrors the OpenGL state as much as possible, so
	 * don't change it outside of {@link State}, and call this when the OpenGL
	 * State is refreshed e.g.: when the display has been (re)created
	 */
	public static void stateReset() {
		currentState = build( None.instance );
	}

	/**
	 * How many batches of compiled {@link State}s have been produced
	 */
	private static int compilationBatchCount = 0;

	/**
	 * Compiles a set of states so that <code>compiledState.compareTo(
	 * otherCompiledState )</code> is fast: a simple int comparison rather than a
	 * traversal of the entire state tree.
	 * 
	 * @param states
	 *          the states to compile
	 * @return the number of distinct states
	 */
	public static int compile( State<?>... states ) {
		// un-compile so we get the deepCompare()
		for( int i = 0; i < states.length; i++ ) {
			states[ i ].compilationBatch = -1;
		}

		Arrays.sort( states );

		// identical states may have been supplied, we want them to
		// end up with the same index
		int compiledIndex = -1;
		State<?> prev = null;

		for( State<?> s : states ) {

			if( prev == null || !prev.equals( s ) ) {
				compiledIndex++;
				prev = s;
			}

			s.compiledIndex = compiledIndex;
			s.compilationBatch = compilationBatchCount;
		}

		compilationBatchCount++;
		return compiledIndex + 1;
	}

	public final T shader;

	public final TextureState texture = new TextureState();

	public final Blend blend = new Blend();

	public final Depth depthTest = new Depth();

	public final PolygonOffset polyOffset = new PolygonOffset();

	public final Clear clear = new Clear();

	public final Stencil stencil = new Stencil();

	/**
	 * A list of state facets, in descending order of
	 * <ol>
	 * <li>change cost</li>
	 * <li>likelihood of difference</li>
	 * </ol>
	 */
	@SuppressWarnings( "rawtypes" )
	private final Facet[] facets;

	/**
	 * Only states that were compiled together can be compared based on their
	 * {@link #compiledIndex}
	 */
	private int compilationBatch = -1;

	/**
	 * The index of the state in its {@link #compilationBatch}
	 */
	private int compiledIndex = -1;

	private State( T shader ) {
		this.shader = shader;
		facets = new Facet[] { shader, texture, blend,
				depthTest, polyOffset, clear, stencil };
	}

	/**
	 * Builds a state
	 * 
	 * @param shader
	 *          the shader to use
	 * @return the state
	 */
	public static <S extends Shader> State<S> build( S shader ) {
		return new State<>( shader );
	}

	/**
	 * Alters this state to match that passed in
	 * 
	 * @param s
	 *          the state to copy
	 * @return this
	 */
	public State<T> from( State<?> s ) {
		if( shader.getClass().equals( s.shader.getClass() ) ) {
			shader.from( s.shader );
		}
		return this;
	}

	/**
	 * Applies this rendering state to OpenGL
	 */
	@SuppressWarnings( "unchecked" )
	public void apply() {
		if( currentState != this ) {
			for( int i = 0; i < facets.length; i++ ) {
				if( facets[ i ] != null ) {
					facets[ i ].transition( currentState.facets[ i ] );
				}
			}
		}

		// the transforms are going to be changing a lot, so
		// let's avoid the whole tree-comparison thing
		shader.updateTransforms();

		currentState = this;
	}

	/**
	 * Comparisons based on {@link #getCompiledIndex()} between states with
	 * different {@link #getCompilationBatch()} values are invalid
	 * 
	 * @return the batch number of the compilation of this {@link State}, or -1 if
	 *         this state has not been compiled
	 */
	public int getCompilationBatch() {
		return compilationBatch;
	}

	/**
	 * Gets the index that this state achieved in its compilation batch. Remember
	 * that states compiled in different batches can have the same compiled index
	 * 
	 * @return The index of this state in its compilation batch, or -1 if the
	 *         state has not been compiled
	 */
	public int getCompiledIndex() {
		return compiledIndex;
	}

	@Override
	public int compareTo( State<?> o ) {
		if( compilationBatch >= 0 && compilationBatch == o.compilationBatch ) {
			// these two states were compiled together
			return compiledIndex - o.compiledIndex;
		}
		else { // have to do a full tree comparison
			return deepCompare( o );
		}
	}

	@SuppressWarnings( "unchecked" )
	private int deepCompare( State<?> o ) {
		for( int i = 0; i < facets.length; i++ ) {
			int d = facets[ i ].compareTo( o.facets[ i ] );

			if( d != 0 ) {
				return d;
			}
		}

		return 0;
	}

	@Override
	public boolean equals( Object o ) {
		if( o instanceof State<?> ) {
			return compareTo( (State<?>) o ) == 0;
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuilder buff = new StringBuilder( "RenderState" );

		for( int i = 0; i < facets.length; i++ ) {
			if( facets[ i ] != null ) {
				buff.append( "\n\t" ).append( facets[ i ] );
			}
		}

		return buff.toString();
	}
}
