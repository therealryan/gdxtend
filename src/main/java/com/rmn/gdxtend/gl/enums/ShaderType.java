package com.rmn.gdxtend.gl.enums;

import com.badlogic.gdx.graphics.GL20;

/**
 * Shader object types
 */
public enum ShaderType {
	/***/
	Vertex( GL20.GL_VERTEX_SHADER ),

	/***/
	Fragment( GL20.GL_FRAGMENT_SHADER );

	/**
	 * Type flag to specify when creating the shader object
	 */
	public final int glFlag;

	private ShaderType( int t ) {
		glFlag = t;
	}
}
