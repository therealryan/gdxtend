package com.rmn.gdxtend.geom;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/**
 * Encapsulates the position, rotation and scale of some object
 */
public class Position {

	/**
	 * Position
	 */
	public final Vector3 position = new Vector3();

	/**
	 * Orientation
	 */
	public final Quaternion rotation =
	    new Quaternion( new Vector3( 0, 0, 1 ), 0 );

	/**
	 * Scale
	 */
	public float scale = 1;

	/**
	 * Copy setter
	 *
	 * @param p
	 *          the {@link Position} to cope
	 * @return this
	 */
	public Position from( Position p ) {
		position.set( p.position );
		rotation.set( p.rotation );
		this.scale = p.scale;
		return this;
	}

	/**
	 * @param p
	 *          the position to add
	 * @return this
	 */
	public Position add( Position p ) {
		position.add( p.position );
		rotation.mul( p.rotation );
		scale *= p.scale;
		return this;
	}

	@Override
	public boolean equals( Object obj ) {
		if( obj == this ) {
			return true;
		}
		else if( obj instanceof Position ) {
			Position p = (Position) obj;
			return position.equals( p.position )
			    && rotation.equals( p.rotation )
			    && scale == p.scale;
		}

		return false;
	}

	@Override
	public String toString() {
		return position + " " + rotation + " " + scale;
	}
}
