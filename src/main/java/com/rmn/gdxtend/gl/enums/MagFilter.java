package com.rmn.gdxtend.gl.enums;

import com.badlogic.gdx.graphics.Texture.TextureFilter;

/**
 * Texture magnifying functions
 */
public enum MagFilter {
	/**
	 * Returns the value of the texture element that is nearest (in Manhattan
	 * distance) to the center of the pixel being textured.
	 */
	NEAREST( TextureFilter.Nearest ),
	/**
	 * Returns the weighted average of the four texture elements that are closest to
	 * the center of the pixel being textured. These can include border texture
	 * elements, depending on the values of GL_TEXTURE_WRAP_S and GL_TEXTURE_WRAP_T,
	 * and on the exact mapping.
	 */
	LINEAR( TextureFilter.Linear );

	/***/
	public final TextureFilter value;

	private MagFilter( TextureFilter value ) {
		this.value = value;
	}
}
