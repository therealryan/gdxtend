package com.rmn.gdxtend.gl.enums;

import com.badlogic.gdx.graphics.GL20;

/**
 * Texture magnifying functions
 */
public enum MagFilter {
	/**
	 * Returns the value of the texture element that is nearest (in Manhattan
	 * distance) to the center of the pixel being textured.
	 */
	NEAREST( GL20.GL_NEAREST ),
	/**
	 * Returns the weighted average of the four texture elements that are closest
	 * to the center of the pixel being textured. These can include border texture
	 * elements, depending on the values of GL_TEXTURE_WRAP_S and
	 * GL_TEXTURE_WRAP_T, and on the exact mapping.
	 */
	LINEAR( GL20.GL_LINEAR );

	/***/
	public final int value;

	private MagFilter( int value ) {
		this.value = value;
	}
}
