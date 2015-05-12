package com.rmn.gdxtend.gl.attribute;

import java.util.Arrays;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.rmn.gdxtend.geom.Shape;

/**
 * Allows convenient setting of vertex attribute data
 * 
 * @param <T>
 *          self
 */
public abstract class Attribute<T extends Attribute<T>> {

	protected final Shape s;
	public final VertexAttribute va;

	/**
	 * Used to keep a very temporary, very unsafe cache of set values
	 */
	private static float[] values = new float[ 4 ];
	private static boolean[] set = new boolean[ 4 ];

	/**
	 * Whether or not the next call to {@link #apply()} affects all vertices
	 */
	protected static boolean toAll = false;

	public Attribute( Shape s, int usage ) {
		this.va = s.attributes.findByUsage( usage );
		this.s = s;
	}

	/**
	 * The next update will be applied to all vertices in the shape. The Shape's
	 * vertex index will be reset to zero afterwards.
	 * 
	 * @return this
	 */
	public T all() {
		toAll = true;
		return self();
	}

	/**
	 * @return The index in the shape's vertex array of the first element of this
	 *         attribute
	 */
	protected int offset() {
		return s.index() * s.attributes.vertexSize / 4 + va.offset / 4;
	}

	protected Shape set( float a, float b, float c, float d ) {
		values[ 0 ] = a;
		values[ 1 ] = b;
		values[ 2 ] = c;
		values[ 3 ] = d;
		Arrays.fill( set, true );

		apply();
		return s;
	}

	protected Shape set( float a, float b, float c ) {
		values[ 0 ] = a;
		values[ 1 ] = b;
		values[ 2 ] = c;
		Arrays.fill( set, true );
		set[ 3 ] = false;

		apply();
		return s;
	}

	protected Shape set( float a, float b ) {
		values[ 0 ] = a;
		values[ 1 ] = b;
		Arrays.fill( set, true );
		set[ 2 ] = false;
		set[ 3 ] = false;

		apply();
		return s;
	}

	protected Shape set( float a ) {
		values[ 0 ] = a;
		Arrays.fill( set, true );
		set[ 1 ] = false;
		set[ 2 ] = false;
		set[ 3 ] = false;

		apply();
		return s;
	}

	/**
	 * Sets a single component
	 * 
	 * @param e
	 * @param a
	 * @return this
	 */
	protected T component( int e, float a ) {
		values[ e ] = a;
		Arrays.fill( set, false );
		set[ e ] = true;

		apply();
		return self();
	}

	private void apply() {
		if( toAll ) {
			for( int i = 0; i < s.vertices(); i++ ) {
				s.index( i );
				applyToVertex( offset(), values, set );
			}
			s.index( 0 );
		}
		else {
			applyToVertex( offset(), values, set );
		}
		toAll = false;
	}

	/**
	 * Override this to do something custom when copying the data to the vertex
	 * array.
	 * 
	 * @param index
	 *          The index of the first component to copy to
	 * @param values
	 *          The values to copy
	 * @param set
	 *          Which values to copy
	 */
	protected void applyToVertex( int index, float[] values, boolean[] set ) {
		for( int i = 0; i < va.numComponents; i++ ) {
			if( set[ i ] ) {
				s.vertexData[ index + i ] = values[ i ];
			}
		}
	}

	protected float get( int e ) {
		return s.vertexData[ offset() + e ];
	}

	public float get() {
		return get( 0 );
	}

	public Vector2 get( Vector2 v ) {
		int o = offset();
		v.set( s.vertexData[ o ], s.vertexData[ o + 1 ] );
		return v;
	}

	public Vector3 get( Vector3 v ) {
		int o = offset();
		v.set( s.vertexData[ o ], s.vertexData[ o + 1 ], s.vertexData[ o + 2 ] );
		return v;
	}

	/**
	 * @return the parent shape
	 */
	public Shape done() {
		return s;
	}

	@SuppressWarnings( "unchecked" )
	private T self() {
		return (T) this;
	}
}
