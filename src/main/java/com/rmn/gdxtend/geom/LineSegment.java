package com.rmn.gdxtend.geom;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class LineSegment {

	public final Vector3 start = new Vector3();

	public final Vector3 end = new Vector3();

	/**
	 * Copy setter
	 * 
	 * @param other
	 *          the {@link LineSegment} to copy
	 * @return this
	 */
	public LineSegment from( LineSegment other ) {
		return start( other.start ).end( other.end );
	}

	public LineSegment start( float x, float y, float z ) {
		start.set( x, y, z );
		return this;
	}

	public LineSegment start( Vector3 s ) {
		return start( s.x, s.y, s.z );
	}

	public LineSegment end( float x, float y, float z ) {
		end.set( x, y, z );
		return this;
	}

	public LineSegment end( Vector3 e ) {
		return end( e.x, e.y, e.z );
	}

	/**
	 * Sets this linesegment's end point based on the start point and a direction
	 * vector
	 * 
	 * @param x
	 *          direction x component
	 * @param y
	 *          direction y component
	 * @param z
	 *          direction z component
	 * @return this
	 */
	public LineSegment inDirection( float x, float y, float z ) {
		end.set( start ).add( x, y, z );
		return this;
	}

	/**
	 * @param dst
	 *          The direction will be stored in this {@link Vector2}
	 * @return the normalised direction of this line segment in the xy plane
	 */
	public Vector2 direction( Vector2 dst ) {
		return dst.set( end.x, end.y ).sub( start.x, start.y ).nor();
	}

	/**
	 * @param dst
	 *          The direction will be stored in this {@link Vector3}
	 * @return the normalised direction of this line segment
	 */
	public Vector3 direction( Vector3 dst ) {
		return dst.set( end ).sub( start ).nor();
	}

	public Vector3 interpolate( Interpolation i, float a, Vector3 dst ) {
		if( i != null ) {
			a = i.apply( a );
		}
		return dst.set( end ).sub( start ).scl( a ).add( start );
	}

	public float length() {
		return Vector3.len(
				end.x - start.x,
				end.y - start.y,
				end.z - start.z );
	}

	@Override
	public String toString() {
		return start + "->" + end;
	}
}
