package com.rmn.gdxtend.gl.facets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.gl.enums.ComparisonFunction;
import com.rmn.gdxtend.gl.enums.StencilOperation;

public class StencilTest extends FacetTest {

	private Stencil stencil = new Stencil();
	private Stencil control = new Stencil();

	@Test
	public void construction() {
		stencil.enabled( true )
				.function( ComparisonFunction.GEQUAL )
				.reference( 1 )
				.testMask( 2 )
				.writeMask( 3 )
				.sfail( StencilOperation.DECR )
				.dpfail( StencilOperation.DECR_WRAP )
				.dppass( StencilOperation.INCR );

		assertThat( stencil.enabled ).isTrue();
		assertThat( stencil.function ).isEqualTo( ComparisonFunction.GEQUAL );
		assertThat( stencil.reference ).isEqualTo( 1 );
		assertThat( stencil.testMask ).isEqualTo( 2 );
		assertThat( stencil.writeMask ).isEqualTo( 3 );
		assertThat( stencil.sfail ).isEqualTo( StencilOperation.DECR );
		assertThat( stencil.dpfail ).isEqualTo( StencilOperation.DECR_WRAP );
		assertThat( stencil.dppass ).isEqualTo( StencilOperation.INCR );
	}

	@Test
	public void same() {
		assertThat( stencil ).isEqualTo( control );
	}

	@Test
	public void copy() {
		stencil.enabled( true )
				.function( ComparisonFunction.GEQUAL )
				.reference( 1 )
				.testMask( 2 )
				.writeMask( 3 )
				.sfail( StencilOperation.DECR )
				.dpfail( StencilOperation.DECR_WRAP )
				.dppass( StencilOperation.INCR );

		Stencil copy = new Stencil();

		assertThat( copy ).isNotEqualTo( stencil );

		copy.from( stencil );

		assertThat( copy ).isEqualTo( stencil );
	}

	@Test
	public void enabledComparison() {
		comparisonOrder( control, stencil.enabled( true ) );
	}

	@Test
	public void functionComparison() {
		comparisonOrder( control, stencil.function( ComparisonFunction.GEQUAL ) );
	}

	@Test
	public void referenceComparison() {
		comparisonOrder( control, stencil.reference( 3 ) );
	}

	@Test
	public void testMaskComparison() {
		comparisonOrder( control, stencil.testMask( 3 ) );
	}

	@Test
	public void writeMaskComparison() {
		comparisonOrder( control, stencil.writeMask( 3 ) );
	}

	@Test
	public void sFailCmparison() {
		comparisonOrder( control, stencil.sfail( StencilOperation.DECR ) );
	}

	@Test
	public void dpFailComparison() {
		comparisonOrder( control, stencil.dpfail( StencilOperation.DECR ) );
	}

	@Test
	public void dpPassComparison() {
		comparisonOrder( control, stencil.dppass( StencilOperation.DECR ) );
	}

	public void noopTransition() {
		stencil.transition( control );

		verify( Gdx.gl, never() ).glEnable( anyInt() );
		verify( Gdx.gl, never() ).glDisable( anyInt() );
		verify( Gdx.gl, never() ).glStencilFunc( anyInt(), anyInt(), anyInt() );
		verify( Gdx.gl, never() ).glStencilMask( anyInt() );
		verify( Gdx.gl, never() ).glStencilMask( anyInt() );
	}

	@Test
	public void transition() {

		stencil.enabled( true )
				.function( ComparisonFunction.GEQUAL )
				.reference( 1 )
				.testMask( 2 )
				.writeMask( 3 )
				.sfail( StencilOperation.DECR )
				.dpfail( StencilOperation.DECR_WRAP )
				.dppass( StencilOperation.INCR );

		stencil.transition( control );

		verify( Gdx.gl ).glEnable( GL20.GL_STENCIL_TEST );
		verify( Gdx.gl ).glStencilFunc( GL20.GL_GEQUAL, 1, 2 );
		verify( Gdx.gl ).glStencilMask( 3 );
		verify( Gdx.gl ).glStencilOp( GL20.GL_DECR, GL20.GL_DECR_WRAP,
				GL20.GL_INCR );

		control.transition( stencil );

		verify( Gdx.gl ).glDisable( GL20.GL_STENCIL_TEST );
	}
}
