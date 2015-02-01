package com.rmn.gdxtend.gl.enums;

import com.badlogic.gdx.graphics.GL20;

public enum StencilOperation {

	/**
	 * Keeps the current value.
	 */
	KEEP( GL20.GL_KEEP ),
	/**
	 * Sets the stencil buffer value to 0.
	 */
	ZERO( GL20.GL_ZERO ),
	/**
	 * Sets the stencil buffer value to ref, as specified by glStencilFunc.
	 */
	REPLACE( GL20.GL_REPLACE ),
	/**
	 * Increments the current stencil buffer value. Clamps to the maximum
	 * representable unsigned value.
	 */
	INCR( GL20.GL_INCR ),
	/**
	 * Increments the current stencil buffer value. Wraps stencil buffer value to
	 * zero when incrementing the maximum representable unsigned value.
	 */
	INCR_WRAP( GL20.GL_INCR_WRAP ),
	/**
	 * Decrements the current stencil buffer value. Clamps to 0.
	 */
	DECR( GL20.GL_DECR ),
	/**
	 * Decrements the current stencil buffer value. Wraps stencil buffer value to
	 * the maximum representable unsigned value when decrementing a stencil buffer
	 * value of zero.
	 */
	DECR_WRAP( GL20.GL_DECR_WRAP ),
	/**
	 * Bitwise inverts the current stencil buffer value.
	 */
	INVERT( GL20.GL_INVERT );

	public final int value;

	private StencilOperation( int value ) {
		this.value = value;
	}
}
