package com.rmn.gdxtend.gl.attribute;

import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.rmn.gdxtend.geom.Shape;

/**
 * Controls the vertex positions
 */
public class Position extends Attribute<Position> {

	private static Vector3 tmpv = new Vector3();
	private static Matrix4 tmpm = new Matrix4();

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

	public Position translate( float x, float y, float z ) {
		tmpm.trn( x, y, z );
		return this;
	}

	public Position rotate( float x, float y, float z, float r ) {
		tmpv.set( x, y, z );
		tmpm.rotate( tmpv, r );
		return this;
	}

	public Position scale( float x, float y, float z ) {
		tmpm.scl( x, y, z );
		return this;
	}

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

}
