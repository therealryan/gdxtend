package com.rmn.gdxtend.geom;

import java.util.Arrays;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.rmn.gdxtend.gl.attribute.Colour;
import com.rmn.gdxtend.gl.attribute.Position;

/**
 * A polygonal shape with some number of vertex attributes
 */
public class Shape {
	private final Position pos;
	private final Colour col;

	/**
	 * Vertex attributes
	 */
	public final VertexAttributes attributes;

	/**
	 * Packed vertex data
	 */
	public final float[] vertexData;

	protected int vi = 0;
	private int tii = 0;

	/**
	 * Triangle indices
	 */
	public final short[] indices;

	/**
	 * Constructs a new shape
	 *
	 * @param vertexCount
	 *          the number of vertices
	 * @param triangleCount
	 *          the number of triangles
	 * @param attr
	 *          vertex attributes
	 */
	public Shape( int vertexCount, int triangleCount,
	    VertexAttribute... attr ) {
		attributes = new VertexAttributes( attr );
		vertexData = new float[ vertexCount * attributes.vertexSize / 4 ];
		indices = new short[ triangleCount * 3 ];
		pos = new Position( this );
		col = new Colour( this );
	}

	/**
	 * Constructs a new shape
	 *
	 * @param elements
	 *          the number of elements - what that means depends on the topography
	 * @param t
	 *          the structure of the geometry
	 * @param additional
	 *          vertex attributes over
	 */
	public Shape( int elements, Topology t,
	    VertexAttribute... additional ) {
		this( t.verticesFor( elements ), t.trianglesFor( elements ), additional );
		t.define( this );
	}

	/**
	 * Constructs a gestalt shape
	 *
	 * @param constituents
	 *          the shapes to combine
	 */
	public Shape( Shape... constituents ) {
		attributes = constituents[ 0 ].attributes;
		int vc = 0;
		int ic = 0;
		for( Shape s : constituents ) {
			if( !attributes.equals( s.attributes ) ) {
				throw new IllegalArgumentException( "Different attributes: "
				    + attributes + " != " + s.attributes );
			}
			vc += s.vertexData.length;
			ic += s.indices.length;
		}
		vertexData = new float[ vc ];
		indices = new short[ ic ];
		vc = 0;
		int vi = 0;
		int ii = 0;
		for( Shape s : constituents ) {
			System.arraycopy( s.vertexData, 0, vertexData, vi, s.vertexData.length );
			vi += s.vertexData.length;

			for( int i = 0; i < s.indices.length; i++ ) {
				indices[ ii + i ] = (short) ( s.indices[ i ] + vc );
			}
			ii += s.indices.length;

			vc += s.vertexData.length / ( attributes.vertexSize / 4 );
		}
		pos = new Position( this );
		col = new Colour( this );
	}

	/**
	 * @return Controls for vertex position
	 */
	public final Position pos() {
		return pos;
	}

	/**
	 * @return Controls for vertex colour
	 */
	public final Colour col() {
		return col;
	}

	/**
	 * @return vertex count
	 */
	public int vertices() {
		return vertexData.length / ( attributes.vertexSize / 4 );
	}

	/**
	 * Sets the index of the vertex that will be subject to setter calls
	 *
	 * @param i
	 *          vertex index
	 * @return this
	 */
	public Shape index( int i ) {
		vi = i;
		return this;
	}

	/**
	 * @return the index of the current vertex
	 */
	public int index() {
		return vi;
	}

	/**
	 * Advances the vertex index
	 *
	 * @return this
	 */
	public Shape next() {
		vi++;
		return this;
	}

	/**
	 * Adds a triangle by indexing vertices
	 *
	 * @param a
	 *          vertex index
	 * @param b
	 *          vertex index
	 * @param c
	 *          vertex index
	 * @return this
	 */
	public Shape triangle( int a, int b, int c ) {
		indices[ tii++ ] = (short) a;
		indices[ tii++ ] = (short) b;
		indices[ tii++ ] = (short) c;
		return this;
	}

	@Override
	public String toString() {
		return Arrays.toString( vertexData ) + Arrays.toString( indices );
	}
}
