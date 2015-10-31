package com.rmn.gdxtend.geom.imp;

import com.badlogic.gdx.math.Vector3;
import com.rmn.gdxtend.geom.Path;
import com.rmn.gdxtend.math.Range;

/**
 * A straight-line track between two points
 */
public class LinearPath extends Path<LinearPath> {

	@Override
	public Vector3 forProgress( float p, Vector3 dest ) {
		float x = Range.lerp( p, start.x, end.x );
		float y = Range.lerp( p, start.y, end.y );
		float z = Range.lerp( p, start.z, end.z );
		return dest.set( x, y, z );
	}
}
