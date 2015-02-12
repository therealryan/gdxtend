package com.rmn.gdxtend.gl.facets;

import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.rmn.gdxtend.gl.Facet;
import com.rmn.gdxtend.util.Comparison;

/**
 * Extend this to define your shader
 */
public abstract class Shader extends Facet<Shader> {

	public final ShaderProgram program;
	public final VertexAttributes attributes;

	/**
	 * @param p
	 * @param attributes
	 */
	protected Shader( ShaderProgram p, VertexAttribute... attributes ) {
		this.program = p;
		this.attributes = new VertexAttributes( attributes );
	}

	@Override
	public final Shader from( Shader toCopy ) {
		if( getClass().equals( toCopy.getClass() ) ) {
			return uniformFrom( toCopy );
		}

		return this;
	}

	/**
	 * Override to copy uniform values - you can safely cast to self type
	 * 
	 * @param toCopy
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
	 *          the state we're coming from - holds the current values
	 */
	protected abstract void uniformTransition( Shader from );
}
