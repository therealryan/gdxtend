package com.rmn.gdxtend.gl.attribute;

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

	private final Shape s;
	public final VertexAttribute va;

	/**
	 * Used to keep a very temporary, very unsafe cache of set values
	 */
	private static float[] values = new float[ 4 ];
	private static int element = -1;
	private static int count = -1;

	/**
	 * Whether or not the next call to {@link #apply()} affects all vertices
	 */
	private static boolean toAll = false;

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
		element = -1;
		count = 4;
		values[ 0 ] = a;
		values[ 1 ] = b;
		values[ 2 ] = c;
		values[ 3 ] = d;

		apply();
		return s;
	}

	protected Shape set( float a, float b, float c ) {
		element = -1;
		count = 3;
		values[ 0 ] = a;
		values[ 1 ] = b;
		values[ 2 ] = c;

		apply();
		return s;
	}

	protected Shape set( float a, float b ) {
		element = -1;
		count = 2;
		values[ 0 ] = a;
		values[ 1 ] = b;

		apply();
		return s;
	}

	protected Shape set( float a ) {
		element = -1;
		count = 1;
		values[ 0 ] = a;

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
		element = e;
		count = 1;
		values[ e ] = a;

		apply();
		return self();
	}

	private void apply() {
		if( toAll ) {
			for( int i = 0; i < s.vertices(); i++ ) {
				s.index( i );
				applyToVertex();
			}
			s.index( 0 );
		}
		else {
			applyToVertex();
		}
		toAll = false;
	}

	private void applyToVertex() {
		if( element >= 0 ) {
			System.arraycopy( values, element, s.vertexData, offset() + element,
					count );
		}
		else {
			System.arraycopy( values, 0, s.vertexData, offset(), count );
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
