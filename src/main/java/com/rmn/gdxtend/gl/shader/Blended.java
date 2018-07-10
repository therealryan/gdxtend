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
public class Blended extends Shader {

	private static Blended instance;

	/**
	 * There are no parameters, so we only need one instance
	 * 
	 * @return the {@link Blended} instance
	 */
	public static Blended instance() {
		if( instance == null ) {
			instance = new Blended();
		}

		return instance;
	}

	private Blended() {
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
