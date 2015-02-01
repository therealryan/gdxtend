package com.rmn.gdxtend.gl.enums;

import com.badlogic.gdx.graphics.GL20;

/**
 * Comparison functions, used for alpha and depth tests
 */
public enum ComparisonFunction {
	/**
	 * Never passes
	 */
	NEVER( GL20.GL_NEVER ),
	/**
	 * Passes if the incoming value is less than the reference value.
	 */
	LESS( GL20.GL_LESS ),
	/**
	 * Passes if the incoming value is equal to the reference value.
	 */
	EQUAL( GL20.GL_EQUAL ),
	/**
	 * Passes if the incoming value is less than or equal to the reference value.
	 */
	LEQUAL( GL20.GL_LEQUAL ),
	/**
	 * Passes if the incoming value is greater than the reference value.
	 */
	GREATER( GL20.GL_GREATER ),
	/**
	 * Passes if the incoming value is not equal to the reference value.
	 */
	NOTEQUAL( GL20.GL_NOTEQUAL ),
	/**
	 * Passes if the incoming value is greater than or equal to the reference
	 * value.
	 */
	GEQUAL( GL20.GL_GEQUAL ),
	/**
	 * Always passes.
	 */
	ALWAYS( GL20.GL_ALWAYS );

	/**
	 * The value of the OpenGL constant
	 */
	public final int value;

	private ComparisonFunction( int value ) {
		this.value = value;
	}
}
