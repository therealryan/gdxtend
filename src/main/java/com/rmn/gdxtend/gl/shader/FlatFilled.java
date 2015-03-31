package com.rmn.gdxtend.gl.shader;

import static com.badlogic.gdx.graphics.VertexAttribute.ColorPacked;
import static com.badlogic.gdx.graphics.VertexAttribute.Position;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.rmn.gdxtend.gl.attribute.Colour;
import com.rmn.gdxtend.gl.attribute.Position;
import com.rmn.gdxtend.gl.facets.Shader;

/**
 * For rendering geometry that has {@link Position} and {@link Colour}
 * attributes
 */
public class FlatFilled extends Shader {

	private static FlatFilled instance;

	public static FlatFilled instance() {
		if( instance == null ) {
			instance = new FlatFilled();
		}

		return instance;
	}

	private FlatFilled() {
		super(
				new ShaderProgram(
						Programs.Vertex.vertColour,
						Programs.Fragment.passthrough ),
				Position(),
				ColorPacked() );
	}

	@Override
	protected int uniformCompare( Shader other ) {
		// no uniforms
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

}
