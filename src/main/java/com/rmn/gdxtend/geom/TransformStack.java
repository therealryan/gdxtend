package com.rmn.gdxtend.geom;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * A convenient way to work with hierarchical transformations
 */
public class TransformStack {

	private static final Vector3 tmp = new Vector3();

	private Matrix4[] stack = new Matrix4[ 10 ];
	private boolean[] identity = new boolean[ stack.length ];

	{
		for( int i = 0; i < stack.length; i++ ) {
			stack[ i ] = new Matrix4();
		}
		stack[ 0 ].idt();
		identity[ 0 ] = true;
	}

	/**
	 * index of the top of the stack
	 */
	private int top = 0;

	/**
	 * @return <code>true</code> if this transform is a no-op
	 */
	public boolean identity() {
		return identity[ top ];
	}

	/**
	 * Pushes a matrix onto the stack
	 *
	 * @return this
	 */
	public TransformStack push() {
		top++;
		stack[ top ].set( stack[ top - 1 ] );
		identity[ top ] = identity[ top - 1 ];
		return this;
	}

	/**
	 * Pops a matrix off the stack
	 *
	 * @return this
	 */
	public TransformStack pop() {
		top--;
		return this;
	}

	/**
	 * Clears the stack back to the identity matrix
	 *
	 * @return this
	 */
	public TransformStack clear() {
		top = 0;
		stack[ top ].idt();
		identity[ top ] = true;
		return this;
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
	public TransformStack translate( float x, float y, float z ) {
		stack[ top ].translate( x, y, z );
		identity[ top ] = false;
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
	public TransformStack rotate( float x, float y, float z, float r ) {
		stack[ top ].rotate( x, y, z, r );
		identity[ top ] = false;
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
	public TransformStack scale( float x, float y, float z ) {
		stack[ top ].scale( x, y, z );
		identity[ top ] = false;
		return this;
	}

	/**
	 * Transforms a point
	 *
	 * @param v
	 *          the point
	 * @return the transformed point
	 */
	public Vector3 transform( Vector3 v ) {
		if( identity[ top ] ) {
			return v;
		}
		else {
			return v.mul( stack[ top ] );
		}
	}

	/**
	 * Transforms a point
	 *
	 * @param x
	 *          point x
	 * @param y
	 *          point y
	 * @param z
	 *          point z
	 * @return the transformed point. Don't hang on to this object, it is liable to
	 *         change under you.
	 */
	public Vector3 transform( float x, float y, float z ) {
		tmp.set( x, y, z );
		return transform( tmp );
	}
}
