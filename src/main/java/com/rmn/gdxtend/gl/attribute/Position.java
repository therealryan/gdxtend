package com.rmn.gdxtend.gl.attribute;

import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.rmn.gdxtend.geom.Shape;

/**
 * Controls the vertex positions
 */
public class Position extends Attribute<Position> {

	private static Vector3 tmpv = new Vector3();
	private static Matrix4 tmpm = new Matrix4();

	/**
	 * @param s
	 */
	public Position( Shape s ) {
		super( s, Usage.Position );
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @return the shape
	 */
	public Shape xyz( float x, float y, float z ) {
		return set( x, y, z );
	}

	/**
	 * @param v
	 * @return the shape
	 */
	public Shape set( Vector3 v ) {
		return set( v.x, v.y, v.z );
	}

	/**
	 * @return the x component
	 */
	public float x() {
		return get( 0 );
	}

	/**
	 * @param x
	 *          new x component
	 * @return this
	 */
	public Position x( float x ) {
		return component( 0, x );
	}

	/**
	 * @return the y component
	 */
	public float y() {
		return get( 1 );
	}

	/**
	 * @param y
	 *          new y component
	 * @return this
	 */
	public Position y( float y ) {
		return component( 1, y );
	}

	/**
	 * @return the z component
	 */
	public float z() {
		return get( 2 );
	}

	/**
	 * @param z
	 *          new z component
	 * @return this
	 */
	public Position z( float z ) {
		return component( 2, z );
	}

	/**
	 * Adds a translation to the transform
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return this
	 */
	public Position translate( float x, float y, float z ) {
		tmpm.trn( x, y, z );
		return this;
	}

	/**
	 * Adds a rotation to the transform
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param r
	 *          in radians
	 * @return this
	 */
	public Position rotate( float x, float y, float z, float r ) {
		tmpv.set( x, y, z );
		tmpm.rotate( tmpv, r );
		return this;
	}

	/**
	 * Adds a scale to the transform
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return this
	 */
	public Position scale( float x, float y, float z ) {
		tmpm.scl( x, y, z );
		return this;
	}

	/**
	 * Applies and resets the transform
	 * 
	 * @return the shape
	 */
	public Shape apply() {
		if( toAll ) {
			toAll = false;
			for( int i = 0; i < s.vertices(); i++ ) {
				s.index( i );
				get( tmpv );
				tmpv.mul( tmpm );
				set( tmpv );
			}
			s.index( 0 );
		}
		else {
			get( tmpv );
			tmpv.mul( tmpm );
			set( tmpv );
		}
		tmpm.idt();

		return s;
	}

}
