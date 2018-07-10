package com.rmn.gdxtend.gl.enums;

import com.badlogic.gdx.graphics.Texture.TextureFilter;

/**
 * Texture minifying functions
 */
public enum MinFilter {
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
	LINEAR( TextureFilter.Linear ),
	/**
	 * Chooses the mipmap that most closely matches the size of the pixel being
	 * textured and uses the GL_NEAREST criterion (the texture element nearest to
	 * the center of the pixel) to produce a texture value.
	 */
	NEAREST_MIPMAP_NEAREST( TextureFilter.MipMapNearestNearest ),
	/**
	 * Chooses the mipmap that most closely matches the size of the pixel being
	 * textured and uses the GL_LINEAR criterion (a weighted average of the four
	 * texture elements that are closest to the center of the pixel) to produce a
	 * texture value.
	 */
	LINEAR_MIPMAP_NEAREST( TextureFilter.MipMapLinearNearest ),
	/**
	 * Chooses the two mipmaps that most closely match the size of the pixel being
	 * textured and uses the GL_NEAREST criterion (the texture element nearest to
	 * the center of the pixel) to produce a texture value from each mipmap. The
	 * final texture value is a weighted average of those two values.
	 */
	NEAREST_MIPMAP_LINEAR( TextureFilter.MipMapNearestLinear ),
	/**
	 * Chooses the two mipmaps that most closely match the size of the pixel being
	 * textured and uses the GL_LINEAR criterion (a weighted average of the four
	 * texture elements that are closest to the center of the pixel) to produce a
	 * texture value from each mipmap. The final texture value is a weighted average
	 * of those two values.
	 */
	LINEAR_MIPMAP_LINEAR( TextureFilter.MipMapLinearLinear );

	/***/
	public final TextureFilter value;

	private MinFilter( TextureFilter value ) {
		this.value = value;
	}
}
