package com.rmn.gdxtend.gl.facets;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.rmn.gdxtend.gl.shader.Programs;
import com.rmn.gdxtend.util.Comparison;

/**
 * Extend this to define your shader
 */
public abstract class Shader extends Facet<Shader> {

	public final ShaderProgram program;
	public final VertexAttributes attributes;

	/**
	 * The camera matrix - combined projection and world matrices
	 */
	public final Matrix4 camera = new Matrix4().idt();
	private int cameraPos = -1;

	/**
	 * The model transform matrix
	 */
	public final Matrix4 model = new Matrix4().idt();
	private int modelPos = -1;

	/**
	 * @param p
	 *          the shader program
	 * @param attributes
	 *          vertex attributes used by the program
	 */
	protected Shader( ShaderProgram p, VertexAttribute... attributes ) {
		this.program = p;
		this.attributes = new VertexAttributes( attributes );

		if( p != null && !p.isCompiled() ) {
			throw new RuntimeException( p.getLog() );
		}
	}

	@Override
	public final Shader from( Shader toCopy ) {
		if( getClass().equals( toCopy.getClass() ) ) {
			return uniformFrom( toCopy );
		}

		camera.set( toCopy.camera );
		model.set( toCopy.model );

		return this;
	}

	/**
	 * Override to copy uniform values - you can safely cast to self type
	 * 
	 * @param toCopy
	 *          The shader to copy
	 * @return this
	 */
	public Shader uniformFrom( Shader toCopy ) {
		return this;
	}

	@Override
	public final int compareTo( Shader s ) {

		int cc = Comparison.instance.compare(
				getClass().getName(),
				s.getClass().getName() )
				.result();

		if( cc == 0 ) {
			cc = uniformCompare( s );
		}

		return cc;
	}

	/**
	 * Compare uniform values - you can safely cast to self type
	 * 
	 * @param other
	 *          the state to compare against
	 * @return the comparison value
	 */
	protected abstract int uniformCompare( Shader other );

	@Override
	public final void transition( Shader from ) {
		if( !getClass().equals( from.getClass() ) ) {
			bind();
			forceUniforms();
		}
		else {
			uniformTransition( from );
		}
	}

	/**
	 * Applies the transform matrices
	 */
	public void updateTransforms() {
		if( cameraPos == -1 ) {
			cameraPos = program.getUniformLocation( Programs.uCam );
		}
		if( modelPos == -1 ) {
			modelPos = program.getUniformLocation( Programs.uMod );
		}

		program.setUniformMatrix( cameraPos, camera );
		program.setUniformMatrix( modelPos, model );
	}

	/**
	 * Bind the shader
	 */
	protected void bind() {
		program.begin();
	}

	/**
	 * Implement to set all your uniform values - all of 'em
	 */
	protected abstract void forceUniforms();

	/**
	 * Implement to set your new uniform values
	 * 
	 * @param from
	 *          the state we're coming from - holds the current values. You can
	 *          safely cast to self type
	 */
	protected abstract void uniformTransition( Shader from );
}
