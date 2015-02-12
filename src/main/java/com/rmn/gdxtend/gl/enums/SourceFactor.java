package com.rmn.gdxtend.gl.enums;

import com.badlogic.gdx.graphics.GL20;

/**
 * Alpha blending source factors
 */
public enum SourceFactor {
	/***/
	ONE( GL20.GL_ONE ),
	/***/
	ZERO( GL20.GL_ZERO ),
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
	ONE_MINUS_CONSTANT_ALPHA( GL20.GL_ONE_MINUS_CONSTANT_ALPHA ),
	/***/
	SRC_ALPHA_SATURATE( GL20.GL_SRC_ALPHA_SATURATE );

	/***/
	public final int value;

	private SourceFactor( int value ) {
		this.value = value;
	}
}
