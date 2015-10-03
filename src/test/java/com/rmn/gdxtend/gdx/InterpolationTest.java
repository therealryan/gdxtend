package com.rmn.gdxtend.gdx;

import org.junit.Rule;
import org.junit.Test;

import com.badlogic.gdx.math.Interpolation;
import com.rmn.gdxtend.expect.XYPlotExpect;
import com.rmn.gdxtend.math.Function;

/**
 * Doesn't really test anything, just generates graphs of the various
 * interpolators
 */
public class InterpolationTest {

	@Rule
	public XYPlotExpect plot = new XYPlotExpect();

	@Test
	public void misc() throws Exception {
		graph( "linear", Interpolation.linear );
		graph( "fade", Interpolation.fade );
		plot.check();
	}

	@Test
	public void bounce() throws Exception {
		graph( Interpolation.bounce,
				Interpolation.bounceIn,
				Interpolation.bounceOut );
	}

	@Test
	public void circle() throws Exception {
		graph( "circle", Interpolation.circle );
		graph( "circleIn", Interpolation.circleIn );
		graph( "circleOut", Interpolation.circleOut );
		plot.check();
	}

	@Test
	public void elastic() throws Exception {
		graph( Interpolation.elastic,
				Interpolation.elasticIn,
				Interpolation.elasticOut );
	}

	@Test
	public void exponent() throws Exception {
		graph( "exp10", Interpolation.exp10 );
		graph( "exp10In", Interpolation.exp10In );
		graph( "exp10Out", Interpolation.exp10Out );
		graph( "exp5", Interpolation.exp5 );
		graph( "exp5In", Interpolation.exp5In );
		graph( "exp5Out", Interpolation.exp5Out );
		plot.check();
	}

	@Test
	public void power() throws Exception {
		graph( "pow2", Interpolation.pow2 );
		graph( "pow3", Interpolation.pow3 );
		graph( "pow4", Interpolation.pow4 );
		graph( "pow5", Interpolation.pow5 );
		plot.check();
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

	public void graph( String name, final Interpolation i ) {
		plot.with( name, new Function() {

			@Override
			public float map( float f ) {
				return i.apply( f );
			}
		} );
	}

	public void graph( Interpolation... interpolators )
			throws Exception {

		for( final Interpolation i : interpolators ) {
			graph( i.getClass().getSimpleName(), i );
		}

		plot.check();
	}
}
