package com.rmn.gdxtend.geom;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * Callbacks to set vertex positions
 */
public abstract class Geometry {

	private static Vector3 tmp = new Vector3();

	protected Matrix4 transform = null;

	/**
	 * Sets the vertex positions
	 * 
	 * @param s
	 *          the shape to update
	 */
	public abstract void define( Shape s );

	/**
	 * Sets a transform that will be applied to all vertex positions
	 * 
	 * @param transform
	 * @return this
	 */
	public Geometry with( Matrix4 transform ) {
		this.transform = transform;
		return this;
	}

	/**
	 * Sets the current vertex, advances to the next
	 * 
	 * @param s
	 *          The shape to alter
	 * @param x
	 * @param y
	 * @param z
	 */
	protected void set( Shape s, float x, float y, float z ) {
		tmp.set( x, y, z );

		if( transform != null ) {
			tmp.mul( transform );
		}

		s.pos.set( tmp );
		s.next();
	}

	/**
	 * 1-radius circle centred on the z-axis, anticlockwise when viewing in the
	 * negative-z direction.
	 */
	public static Geometry circle = new Geometry() {

		@Override
		public void define( Shape s ) {
			int edges = s.vertices() - s.index();
			float inc = (float) ( Math.PI * 2 / edges );
			for( int i = 0; i < edges; i++ ) {
				set( s, (float) Math.cos( i * inc ), (float) Math.sin( i * inc ), 0 );
			}
		}
	};

	/**
	 * 1-radius circle centred on the z-axis, anticlockwise when viewing in the
	 * negative-z direction. The first vertex is at (0, 0)
	 */
	public static Geometry centeredCircle = new Geometry() {

		@Override
		public void define( Shape s ) {
			int edges = s.vertices() - s.index() - 1;
			set( s, 0, 0, 0 );
			float inc = (float) ( Math.PI * 2 / edges );
			for( int i = 0; i < edges; i++ ) {
				set( s, (float) Math.cos( i * inc ), (float) Math.sin( i * inc ), 0 );
			}
		}
	};
}
