package com.rmn.gdxtend.gl.facets;

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
	 * @param from
	 *          The current state.
	 */
	public abstract void transition( T from );

	/**
	 * Implement comparison to order default states before less default
	 */
	@Override
	public abstract int compareTo( T o );

	@SuppressWarnings( "unchecked" )
	@Override
	public boolean equals( Object obj ) {
		if( obj instanceof Facet<?> ) {
			return compareTo( (T) obj ) == 0;
		}

		return false;
	}
}
