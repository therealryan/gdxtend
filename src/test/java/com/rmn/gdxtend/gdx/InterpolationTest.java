package com.rmn.gdxtend.gdx;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.badlogic.gdx.math.Interpolation;
import com.rmn.gdxtend.math.Function;
import com.rmn.gdxtend.testutil.LineGraph;

/**
 * Doesn't really test anything, just generates graphs of the various
 * interpolators
 */
public class InterpolationTest {

	@Rule
	public TestName name = new TestName();

	@Test
	public void misc() throws Exception {
		graph( Interpolation.linear,
				Interpolation.fade );
	}

	@Test
	public void bounce() throws Exception {
		graph( Interpolation.bounce,
				Interpolation.bounceIn,
				Interpolation.bounceOut );
	}

	@Test
	public void circle() throws Exception {
		graph( Interpolation.circle,
				Interpolation.circleIn,
				Interpolation.circleOut );
	}

	@Test
	public void elastic() throws Exception {
		graph( Interpolation.elastic,
				Interpolation.elasticIn,
				Interpolation.elasticOut );
	}

	@Test
	public void exponent() throws Exception {
		graph( Interpolation.exp10,
				Interpolation.exp10In,
				Interpolation.exp10Out,
				Interpolation.exp5,
				Interpolation.exp5In,
				Interpolation.exp5Out );
	}

	@Test
	public void power() throws Exception {
		graph( Interpolation.pow2,
				Interpolation.pow3,
				Interpolation.pow4,
				Interpolation.pow5 );
	}

	@Test
	public void sine() throws Exception {
		graph( Interpolation.sine,
				Interpolation.sineIn,
				Interpolation.sineOut );
	}

	@Test
	public void swing() throws Exception {
		graph( Interpolation.swing,
				Interpolation.swingIn,
				Interpolation.swingOut );
	}

	public void graph( Interpolation... interpolators )
			throws Exception {
		LineGraph lg = new LineGraph( name.getMethodName(), -0.1f, 1.1f, 100 );

		for( final Interpolation i : interpolators ) {
			lg.with( i.getClass().getSimpleName(), new Function() {

				@Override
				public float map( float f ) {
					return i.apply( f );
				}
			} );
		}

		lg.generate();
	}
}
