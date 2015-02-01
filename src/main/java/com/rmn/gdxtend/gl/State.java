/*
 * Copyright (c) 2007, Ryan McNally All rights reserved. Redistribution and use
 * in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met: Redistributions of source
 * code must retain the above copyright notice, this list of conditions and the
 * following disclaimer. Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution. Neither
 * the name of the <ORGANIZATION> nor the names of its contributors may be used
 * to endorse or promote products derived from this software without specific
 * prior written permission. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS
 * AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.rmn.gdxtend.gl;

import java.util.Arrays;

import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.gl.facets.Blend;
import com.rmn.gdxtend.gl.facets.Clear;
import com.rmn.gdxtend.gl.facets.Depth;
import com.rmn.gdxtend.gl.facets.PolygonOffset;
import com.rmn.gdxtend.gl.facets.Stencil;
import com.rmn.gdxtend.gl.facets.TextureState;

/**
 * Represents a rendering state
 */
public class State implements Comparable<State> {
	/**
	 * The current state of openGL
	 */
	private static State currentState = new State();

	/**
	 * The {@link State} class mirrors the OpenGL state as much as possible, so
	 * don't change it outside of {@link State}, and call this when the OpenGL
	 * State is refreshed e.g.: when the display has been (re)created
	 */
	public static void stateReset() {
		currentState = new State();
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
	 */
	public static void compile( State... states ) {
		// un-compile so we get the deepCompare()
		for( int i = 0; i < states.length; i++ ) {
			states[ i ].compilationBatch = -1;
		}

		Arrays.sort( states );

		// identical states may have been supplied, we want them to
		// end up with the same index
		int compiledIndex = -1;
		State prev = null;

		for( int i = 0; i < states.length; i++ ) {
			if( prev == null || !prev.equals( states[ i ] ) ) {
				compiledIndex++;
				prev = states[ i ];
			}

			states[ i ].compiledIndex = compiledIndex;
			states[ i ].compilationBatch = compilationBatchCount;
		}

		compilationBatchCount++;
	}

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
	private final Facet[] facets = new Facet[] { texture, blend, depthTest,
			polyOffset, clear };

	/**
	 * Only states that were compiled together can be compared based on their
	 * {@link #compiledIndex}
	 */
	private int compilationBatch = -1;

	/**
	 * The index of the state in its {@link #compilationBatch}
	 */
	private int compiledIndex = -1;

	/**
	 * Applies this rendering state to OpenGL
	 */
	@SuppressWarnings( "unchecked" )
	public void apply( GL20 context ) {
		for( int i = 0; i < facets.length; i++ ) {
			if( facets[ i ] != null ) {
				facets[ i ].transitionFrom( currentState.facets[ i ], context );
			}
		}

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
	public int compareTo( State o ) {
		if( compilationBatch >= 0 && compilationBatch == o.compilationBatch ) {
			// these two states were compiled together
			return compiledIndex - o.compiledIndex;
		}
		else { // have to do a full tree comparison
			return deepCompare( o );
		}
	}

	@SuppressWarnings( "unchecked" )
	private int deepCompare( State o ) {
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
		if( o instanceof State ) {
			return compareTo( (State) o ) == 0;
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
