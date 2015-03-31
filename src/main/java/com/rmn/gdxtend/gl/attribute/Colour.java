package com.rmn.gdxtend.gl.attribute;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.utils.NumberUtils;
import com.rmn.gdxtend.geom.Shape;

/**
 * Controls vertex colour
 */
public class Colour extends Attribute<Colour> {
	private static final Color tmp = new Color();

	public Colour( Shape s ) {
		super( s, Usage.ColorPacked );
	}

	public Shape rgba( float r, float g, float b, float a ) {
		return setColour( r, g, b, a ).done();
	}

	public Shape set( Color c ) {
		return setColour( c ).done();
	}

	public Colour r( float r ) {
		getColour();
		tmp.set( r, tmp.g, tmp.b, tmp.a );
		return setColour( tmp );
	}

	public Colour g( float g ) {
		getColour();
		tmp.set( tmp.r, g, tmp.b, tmp.a );
		return setColour( tmp );
	}

	public Colour b( float b ) {
		getColour();
		tmp.set( tmp.r, tmp.g, b, tmp.a );
		return setColour( tmp );
	}

	public Colour a( float a ) {
		getColour();
		tmp.set( tmp.r, tmp.g, tmp.b, a );
		return setColour( tmp );
	}

	private Color getColour() {
		tmp.set( NumberUtils.floatToIntColor( get() ) );
		return tmp;
	}

	private Colour setColour( Color c ) {
		return setColour( c.r, c.g, c.b, c.a );
	}

	private Colour setColour( float r, float g, float b, float a ) {
		set( Float.intBitsToFloat( Color.rgba8888( r, g, b, a ) ) );
		return this;
	}
}
