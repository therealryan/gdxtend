package com.rmn.gdxtend.gl.shader;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.rmn.gdxtend.gl.facets.Shader;
import com.rmn.gdxtend.util.Comparison;

/**
 * Just renders geometry with uniform colour
 */
public class Monotone extends Shader {

	/**
	 * The colour to render with
	 */
	public final Color colour = new Color( Color.WHITE );
	private int vcPos = -1;

	/**
	 * COnstructs a new {@link Monotone} shader
	 */
	public Monotone() {
		super(
				new ShaderProgram(
						Programs.Vertex.monotone,
						Programs.Fragment.passthrough ),
				VertexAttribute.Position() );
	}

	@Override
	protected int uniformCompare( Shader other ) {
		return Comparison.instance.compare(
				colour.toIntBits(),
				( (Monotone) other ).colour.toIntBits() )
				.result();
	}

	@Override
	protected void forceUniforms() {
		if( vcPos == -1 ) {
			vcPos = program.getUniformLocation( Programs.uColour );
		}

		program.setUniformf( vcPos, colour );
	}

	@Override
	protected void uniformTransition( Shader from ) {
		Monotone m = (Monotone) from;
		if( colour.equals( m.colour ) ) {
			forceUniforms();
		}
	}
}
