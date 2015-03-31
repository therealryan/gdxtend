package com.rmn.gdxtend.gl.attribute;

import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Vector3;
import com.rmn.gdxtend.geom.Shape;

/**
 * Controls the vertex positions
 */
public class Position extends Attribute<Position> {

	public Position( Shape s ) {
		super( s, Usage.Position );
	}

	public Shape xyz( float x, float y, float z ) {
		return set( x, y, z );
	}

	public Shape set( Vector3 v ) {
		return set( v.x, v.y, v.z );
	}

	public Position x( float x ) {
		return component( 0, x );
	}

	public Position y( float y ) {
		return component( 1, y );
	}

	public Position z( float z ) {
		return component( 2, z );
	}
}
