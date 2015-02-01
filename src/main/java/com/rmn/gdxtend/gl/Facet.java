package com.rmn.gdxtend.gl;

import com.badlogic.gdx.graphics.GL20;

/**
 * Represents some aspect of the rendering state.
 * 
 * @param <T>
 *          self type
 */
public abstract class Facet<T extends Facet<T>> implements Comparable<T> {

	/**
	 * Copies state
	 * 
	 * @param toCopy
	 *          the state to copy from
	 * @return this
	 */
	public abstract T from( T toCopy );

	/**
	 * Alter the OpenGL state if necessary
	 * 
	 * @param facet
	 *          The current state.
	 */
	public abstract void transitionFrom( T facet, GL20 context );

	@SuppressWarnings( "unchecked" )
	@Override
	public boolean equals( Object obj ) {
		if( obj instanceof Facet<?> ) {
			return compareTo( (T) obj ) == 0;
		}

		return false;
	}
}
