package com.rmn.gdxtend.geom;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.rmn.gdxtend.gl.attribute.Colour;
import com.rmn.gdxtend.gl.attribute.Position;

/**
 * A polygonal shape with some number of vertex attributes
 */
public class Shape {
	public final Position pos;

	public final Colour col;

	public final VertexAttributes attributes;

	public final float[] vertexData;

	protected int vi = 0;
	private int tii = 0;

	public final short[] indices;

	/**
	 * Constructs a new shape
	 * 
	 * @param vertexCount
	 *          the number of vertices
	 * @param triangleCount
	 *          the number of triangles
	 * @param additional
	 *          vertex attributes over and above position
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
	 *          vertex attributes over and above position
	 */
	public Shape( int elements, Topology t,
			VertexAttribute... additional ) {
		this( t.verticesFor( elements ), t.trianglesFor( elements ), additional );
		t.define( this );
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
	 * @return this
	 */
	public Shape index( int i ) {
		this.vi = i;
		return this;
	}

	/**
	 * Gets the index of the current vertex
	 * 
	 * @return
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
	 * @param b
	 * @param c
	 * @return this
	 */
	public Shape triangle( int a, int b, int c ) {
		indices[ tii++ ] = (short) a;
		indices[ tii++ ] = (short) b;
		indices[ tii++ ] = (short) c;
		return this;
	}

}
