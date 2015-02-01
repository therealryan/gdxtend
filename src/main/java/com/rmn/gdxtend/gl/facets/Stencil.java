package com.rmn.gdxtend.gl.facets;

import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.gl.Facet;
import com.rmn.gdxtend.gl.enums.ComparisonFunction;
import com.rmn.gdxtend.gl.enums.StencilOperation;
import com.rmn.gdxtend.util.Comparison;

/**
 * Controls stencil test state
 */
public class Stencil extends Facet<Stencil> {

	public boolean enabled = false;

	public ComparisonFunction function = ComparisonFunction.ALWAYS;
	public int reference = 0;
	public int testMask = 0xFFFFFFFF;

	public int writeMask = 0xFFFFFFFF;

	public StencilOperation sfail = StencilOperation.KEEP;
	public StencilOperation dpfail = StencilOperation.KEEP;
	public StencilOperation dppass = StencilOperation.KEEP;

	/**
	 * @param e
	 * @return this
	 */
	public Stencil enabled( boolean e ) {
		this.enabled = e;
		return this;
	}

	/**
	 * Specifies the test function
	 * 
	 * @param f
	 * @return this
	 */
	public Stencil function( ComparisonFunction f ) {
		function = f;
		return this;
	}

	/**
	 * Specifies the reference value for the stencil test. ref is clamped to the
	 * range 0 2 n - 1 , where n is the number of bitplanes in the stencil buffer.
	 * The initial value is 0.
	 * 
	 * @param r
	 * @return this
	 */
	public Stencil reference( int r ) {
		reference = r;
		return this;
	}

	/**
	 * Specifies a mask that is ANDed with both the reference value and the stored
	 * stencil value when the test is done. The initial value is all 1's.
	 * 
	 * @param m
	 * @return this
	 */
	public Stencil testMask( int m ) {
		testMask = m;
		return this;
	}

	/**
	 * Specifies a bit mask to enable and disable writing of individual bits in
	 * the stencil planes. Initially, the mask is all 1's.
	 * 
	 * @param m
	 * @return this
	 */
	public Stencil writeMask( int m ) {
		writeMask = m;
		return this;
	}

	/**
	 * Specifies the action to take when the stencil test fails.
	 * 
	 * @param s
	 * @return this
	 */
	public Stencil sfail( StencilOperation s ) {
		sfail = s;
		return this;
	}

	/**
	 * Specifies the stencil action when the stencil test passes, but the depth
	 * test fails.
	 * 
	 * @param s
	 * @return this
	 */
	public Stencil dpfail( StencilOperation s ) {
		dpfail = s;
		return this;
	}

	/**
	 * Specifies the stencil action when both the stencil test and the depth test
	 * pass, or when the stencil test passes and either there is no depth buffer
	 * or depth testing is not enabled.
	 * 
	 * @param s
	 * @return this
	 */
	public Stencil dppass( StencilOperation s ) {
		dppass = s;
		return this;
	}

	@Override
	public Stencil from( Stencil s ) {
		return enabled( s.enabled )
				.function( s.function )
				.reference( s.reference )
				.testMask( s.testMask )
				.writeMask( s.writeMask )
				.sfail( s.sfail )
				.dpfail( s.dpfail )
				.dppass( s.dppass );
	}

	@Override
	public int compareTo( Stencil s ) {
		return Comparison.instance
				.compare( enabled, s.enabled )
				.compare( function, s.function )
				.compare( reference, s.reference )
				.compare( testMask, s.testMask )
				.compare( writeMask, s.writeMask )
				.compare( sfail, s.sfail )
				.compare( dpfail, s.dpfail )
				.compare( dppass, s.dppass )
				.result();
	}

	@Override
	public void transitionFrom( Stencil s, GL20 context ) {
		if( enabled != s.enabled ) {
			if( enabled ) {
				context.glEnable( GL20.GL_STENCIL_TEST );
			}
			else {
				context.glDisable( GL20.GL_STENCIL_TEST );
			}
		}

		if( function != s.function || reference != s.reference
				|| testMask != s.testMask ) {
			context.glStencilFunc( function.value, reference, testMask );
		}

		if( writeMask != s.writeMask ) {
			context.glStencilMask( writeMask );
		}

		if( sfail != s.sfail || dpfail != s.dpfail || dppass != s.dppass ) {
			context.glStencilOp( sfail.value, dpfail.value, dppass.value );
		}
	}

	@SuppressWarnings( "boxing" )
	@Override
	public String toString() {
		return String.format(
				"Stencil enabled:%s function:%s ref:%s testmask:%s "
						+ "writemask:%s fail:%s zfail:%s zpass:%s",
				enabled, function, reference, testMask, writeMask, sfail, dpfail,
				dppass );
	}
}
