package com.rmn.gdxtend.gl.enums;

import com.badlogic.gdx.graphics.GL20;

/**
 * OpenGL blend equations
 */
public enum BlendEquation {

	/**
	 * src * srcFactor + dst * dstFactor
	 */
	GL_FUNC_ADD( GL20.GL_FUNC_ADD ),
	/**
	 * src * srcFactor - dst * dstFactor
	 */
	GL_FUNC_SUBTRACT( GL20.GL_FUNC_SUBTRACT ),
	/**
	 * dst * dstFactor - src * srcFactor
	 */
	GL_FUNC_REVERSE_SUBTRACT( GL20.GL_FUNC_REVERSE_SUBTRACT );

	/**
	 * enum value
	 */
	public final int value;

	private BlendEquation( int value ) {
		this.value = value;
	}
}
