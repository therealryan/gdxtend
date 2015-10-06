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
	public final Quaternion rotation = new Quaternion( 0, 0, 1, 0 );

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

	@Override
	public String toString() {
		return position.toString();
	}
}
