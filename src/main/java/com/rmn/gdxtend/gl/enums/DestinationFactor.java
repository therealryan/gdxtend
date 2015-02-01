package com.rmn.gdxtend.gl.enums;

import com.badlogic.gdx.graphics.GL20;

/**
 * Alpha blending destination factors
 */
public enum DestinationFactor {
	/***/
	ZERO( GL20.GL_ZERO ),
	/***/
	ONE( GL20.GL_ONE ),
	/***/
	SRC_COLOR( GL20.GL_SRC_COLOR ),
	/***/
	ONE_MINUS_SRC_COLOR( GL20.GL_ONE_MINUS_SRC_COLOR ),
	/***/
	DST_COLOR( GL20.GL_DST_COLOR ),
	/***/
	ONE_MINUS_DST_COLOR( GL20.GL_ONE_MINUS_DST_COLOR ),
	/***/
	SRC_ALPHA( GL20.GL_SRC_ALPHA ),
	/***/
	ONE_MINUS_SRC_ALPHA( GL20.GL_ONE_MINUS_SRC_ALPHA ),
	/***/
	DST_ALPHA( GL20.GL_DST_ALPHA ),
	/***/
	ONE_MINUS_DST_ALPHA( GL20.GL_ONE_MINUS_DST_ALPHA ),
	/***/
	CONSTANT_COLOR( GL20.GL_CONSTANT_COLOR ),
	/***/
	ONE_MINUS_CONSTANT_COLOR( GL20.GL_ONE_MINUS_CONSTANT_COLOR ),
	/***/
	CONSTANT_ALPHA( GL20.GL_CONSTANT_ALPHA ),
	/***/
	ONE_MINUS_CONSTANT_ALPHA( GL20.GL_ONE_MINUS_CONSTANT_ALPHA );

	/***/
	public final int value;

	private DestinationFactor( int value ) {
		this.value = value;
	}
}
