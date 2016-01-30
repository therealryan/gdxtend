package com.rmn.gdxtend.gl.attribute;

import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.rmn.gdxtend.geom.Shape;
import com.rmn.gdxtend.math.Range;

/**
 * Controls the vertex positions
 */
public class Position extends Attribute<Position> {

	private static Vector3 tmpv = new Vector3();
	private static Matrix4 tmpm = new Matrix4();

	/**
	 * @param s
	 *          The shape whose vertex positions this object will control
	 */
	public Position( Shape s ) {
		super( s, Usage.Position );
	}

	/**
	 * @param x
	 *          new position component
	 * @param y
	 *          new position component
	 * @param z
	 *          new position component
	 * @return the shape
	 */
	public Shape xyz( float x, float y, float z ) {
		return set( x, y, z );
	}

	/**
	 * @param v
	 *          new position
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
	 *          translation component
	 * @param y
	 *          translation component
	 * @param z
	 *          translation component
	 * @return this
	 */
	public Position translate( float x, float y, float z ) {
		tmpm.translate( x, y, z );
		return this;
	}

	/**
	 * Adds a rotation to the transform
	 *
	 * @param x
	 *          rotation axis
	 * @param y
	 *          rotation axis
	 * @param z
	 *          rotation axis
	 * @param r
	 *          in radians
	 * @return this
	 */
	public Position rotate( float x, float y, float z, float r ) {
		tmpm.rotate( x, y, z, r );
		return this;
	}

	/**
	 * Adds a scale to the transform
	 *
	 * @param x
	 *          scale component
	 * @param y
	 *          scale component
	 * @param z
	 *          scale component
	 * @return this
	 */
	public Position scale( float x, float y, float z ) {
		tmpm.scale( x, y, z );
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

	/**
	 * @param s
	 *          a shape
	 * @param dst
	 *          destination range
	 * @return the extent of the shape on the x axis
	 */
	public static Range xExtent( Shape s, Range dst ) {
		return extent( s, 0, dst );
	}

	/**
	 * @param s
	 *          a shape
	 * @param dst
	 *          destination range
	 * @return the extent of the shape on the y axis
	 */
	public static Range yExtent( Shape s, Range dst ) {
		return extent( s, 1, dst );
	}

	/**
	 * @param s
	 *          a shape
	 * @param dst
	 *          destination range
	 * @return the extent of the shape on the z axis
	 */
	public static Range zExtent( Shape s, Range dst ) {
		return extent( s, 2, dst );
	}

	private static Range extent( Shape s, int ci, Range dst ) {
		float min = Float.MAX_VALUE, max = -Float.MAX_VALUE;
		for( int i = 0; i < s.vertices(); i++ ) {
			s.index( i );
			min = Math.min( min, s.pos.get( ci ) );
			max = Math.max( max, s.pos.get( ci ) );
		}
		return dst.from( min ).to( max );
	}
}
