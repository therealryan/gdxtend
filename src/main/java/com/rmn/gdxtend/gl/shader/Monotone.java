package com.rmn.gdxtend.gl.shader;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.rmn.gdxtend.gl.facets.Shader;
import com.rmn.gdxtend.util.Comparison;

public class Monotone extends Shader {

	public final Color vertexColour = new Color( Color.WHITE );
	private int vcPos = -1;

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
				vertexColour.toIntBits(),
				( (Monotone) other ).vertexColour.toIntBits() )
				.result();
	}

	@Override
	protected void forceUniforms() {
		if( vcPos == -1 ) {
			vcPos = program.getUniformLocation( Programs.uColour );
		}

		program.setUniformf( vcPos, vertexColour );
	}

	@Override
	protected void uniformTransition( Shader from ) {
		Monotone m = (Monotone) from;
		if( vertexColour.equals( m.vertexColour ) ) {
			forceUniforms();
		}
	}
}
