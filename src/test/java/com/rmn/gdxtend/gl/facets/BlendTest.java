package com.rmn.gdxtend.gl.facets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.badlogic.gdx.graphics.GL20;
import com.rmn.gdxtend.gl.enums.BlendEquation;
import com.rmn.gdxtend.gl.enums.DestinationFactor;
import com.rmn.gdxtend.gl.enums.SourceFactor;

public class BlendTest extends GLStateTest {

	private final Blend blend = new Blend();
	private final Blend control = new Blend();

	@Test
	public void construction() {
		blend.enabled( true )
				.r( 0.5f )
				.g( 0.25f )
				.b( 0.125f )
				.a( 0.0625f )
				.src( SourceFactor.CONSTANT_COLOR )
				.dst( DestinationFactor.CONSTANT_COLOR )
				.equation( BlendEquation.GL_FUNC_SUBTRACT );

		assertTrue( blend.enabled );
		assertEquals( 0.5f, blend.color.r, 0 );
		assertEquals( 0.25f, blend.color.g, 0 );
		assertEquals( 0.125f, blend.color.b, 0 );
		assertEquals( 0.0625f, blend.color.a, 0 );
		assertEquals( SourceFactor.CONSTANT_COLOR, blend.srcFactor );
		assertEquals( DestinationFactor.CONSTANT_COLOR, blend.destFactor );
		assertEquals( BlendEquation.GL_FUNC_SUBTRACT, blend.equation );
	}

	@Test
	public void same() {
		assertTrue( blend.equals( control ) );
	}

	@Test
	public void copy() {
		blend.enabled( true )
				.r( 0.5f )
				.g( 0.25f )
				.b( 0.125f )
				.a( 0.0625f )
				.src( SourceFactor.CONSTANT_COLOR )
				.dst( DestinationFactor.CONSTANT_COLOR )
				.equation( BlendEquation.GL_FUNC_SUBTRACT );

		Blend copy = new Blend().from( blend );

		assertTrue( copy.equals( blend ) );
	}

	@Test
	public void enabledComparison() {
		blend.enabled( true );

		assertEquals( 1, blend.compareTo( control ) );
		assertEquals( -1, control.compareTo( blend ) );
	}

	@Test
	public void srcFactorComparison() {
		blend.src( SourceFactor.CONSTANT_COLOR );

		assertEquals( 1, blend.compareTo( control ) );
		assertEquals( -1, control.compareTo( blend ) );
	}

	@Test
	public void dstFactorComparison() {
		blend.dst( DestinationFactor.CONSTANT_COLOR );

		assertEquals( 1, blend.compareTo( control ) );
		assertEquals( -1, control.compareTo( blend ) );
	}

	@Test
	public void equationComparison() {
		blend.equation( BlendEquation.GL_FUNC_SUBTRACT );

		assertEquals( 1, blend.compareTo( control ) );
		assertEquals( -1, control.compareTo( blend ) );
	}

	@Test
	public void colorComparison() {
		blend.r( 1 );

		assertEquals( 1, blend.compareTo( control ) );
		assertEquals( -1, control.compareTo( blend ) );
	}

	public void noopTransition() {
		blend.transition( control );

		verify( context, never() ).glEnable( anyInt() );
		verify( context, never() ).glDisable( anyInt() );
		verify( context, never() ).glBlendFunc( anyInt(), anyInt() );
		verify( context, never() ).glBlendEquation( anyInt() );
		verify( context, never() ).glBlendColor( anyFloat(), anyFloat(),
				anyFloat(), anyFloat() );
	}

	@Test
	public void transition() {

		blend.enabled( true ).r( 0.5f ).g( 0.25f ).b( 0.125f ).a( 0.0625f )
				.src( SourceFactor.CONSTANT_COLOR ).dst( DestinationFactor.DST_COLOR )
				.equation( BlendEquation.GL_FUNC_SUBTRACT );

		blend.transition( control );

		verify( context ).glEnable( GL20.GL_BLEND );
		verify( context ).glBlendFunc( GL20.GL_CONSTANT_COLOR, GL20.GL_DST_COLOR );
		verify( context ).glBlendEquation( GL20.GL_FUNC_SUBTRACT );
		verify( context ).glBlendColor( 0.5f, 0.25f, 0.125f, 0.0625f );

		control.transition( blend );

		verify( context ).glDisable( GL20.GL_BLEND );
	}
}
