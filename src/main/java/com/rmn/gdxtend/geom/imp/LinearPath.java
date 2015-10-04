package com.rmn.gdxtend.geom.imp;

import com.badlogic.gdx.math.Interpolation;
import com.rmn.gdxtend.geom.Path;
import com.rmn.gdxtend.geom.Position;
import com.rmn.gdxtend.math.Range;

/**
 * A straight-line track between two positions
 */
public class LinearPath extends Path<LinearPath> {

	private Interpolation interp = Interpolation.linear;

	@Override
	protected Position compute( float t, Position dest ) {
		float tq = interp.apply( duration.unlerp( t ) );

		// position
		dest.position.x = Range.lerp( tq, start.position.x, end.position.x );
		dest.position.y = Range.lerp( tq, start.position.y, end.position.y );
		dest.position.z = Range.lerp( tq, start.position.z, end.position.z );

		// rotation
		dest.rotation.set( start.rotation );
		dest.rotation.slerp( end.rotation, tq );

		// scale
		dest.scale = Range.lerp( tq, start.scale, end.scale );

		return dest;
	}
}
