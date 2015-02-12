package com.rmn.gdxtend.gl.shader;

import static com.badlogic.gdx.Gdx.gl;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.rmn.gdxtend.gl.facets.Shader;

/**
 * The undefined 0-handle shader
 */
public class None extends Shader {

	public static final None instance = new None();

	private None() {
		super( null, VertexAttribute.Position() );
	}

	@Override
	protected void bind() {
		gl.glUseProgram( 0 );
	}

	@Override
	protected int uniformCompare( Shader other ) {
		return 0;
	}

	@Override
	protected void forceUniforms() {
		// no uniforms
	}

	@Override
	protected void uniformTransition( Shader from ) {
		// no uniforms
	}

	@Override
	public String toString() {
		return "Default shader";
	}
}
