package com.rmn.gdxtend.geom;

import com.badlogic.gdx.math.Vector3;

/**
 * A path that tracks another with some time-varying offset
 *
 * @param <B>
 *          The type of the base path
 * @param <O>
 *          The type of the offset path
 */
public class OffsetPath<B extends Path<B>, O extends Path<O>> {

	/**
	 * Base path
	 */
	private final B base;

	/**
	 * Offset path
	 */
	public final O offset;

	private static final Vector3 tmp = new Vector3();

	/**
	 * @param base
	 *          base path
	 * @param offset
	 *          offset from base path
	 */
	public OffsetPath( B base, O offset ) {
		this.base = base;
		this.offset = offset;
	}

	/**
	 * Computes a position on the path
	 *
	 * @param time
	 *          the time for the position
	 * @param dest
	 *          the {@link Position} to set
	 * @return the computed position
	 */
	public Vector3 forTime( float time, Vector3 dest ) {
		base.now( time ).get( dest );
		offset.now( time ).get( tmp );
		return dest.add( tmp );
	}

}
