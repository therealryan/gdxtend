package com.rmn.gdxtend.math;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.badlogic.gdx.math.Interpolation;
import com.rmn.gdxtend.testutil.LineGraph;

public class OscillatorTest {

	@Rule
	public TestName name = new TestName();

	@Test
	public void frequency() throws Exception {
		LineGraph lg = new LineGraph( name.getMethodName(), 0, 1, 1000 );

		lg.with( "1", new Oscillator().frequency( 1 ) );
		lg.with( "2", new Oscillator().frequency( 2 ) );
		lg.with( "3", new Oscillator().frequency( 3 ) );
		lg.with( "4", new Oscillator().frequency( 4 ) );

		lg.generate();
	}

	@Test
	public void amplitude() throws Exception {
		LineGraph lg = new LineGraph( name.getMethodName(), 0, 1, 1000 );

		lg.with( "1", new Oscillator().amplitude( 1 ) );
		lg.with( "2", new Oscillator().amplitude( 2 ) );
		lg.with( "3", new Oscillator().amplitude( 3 ) );
		lg.with( "4", new Oscillator().amplitude( 4 ) );

		lg.generate();
	}

	@Test
	public void bias() throws Exception {
		LineGraph lg = new LineGraph( name.getMethodName(), 0, 1, 1000 );

		lg.with( "1", new Oscillator().bias( 1 ) );
		lg.with( "2", new Oscillator().bias( 2 ) );
		lg.with( "3", new Oscillator().bias( 3 ) );
		lg.with( "4", new Oscillator().bias( 4 ) );

		lg.generate();
	}

	@Test
	public void offset() throws Exception {
		LineGraph lg = new LineGraph( name.getMethodName(), 0, 1, 1000 );

		lg.with( "1/2", new Oscillator().offset( 0.5f ) );
		lg.with( "1/4", new Oscillator().offset( 0.25f ) );
		lg.with( "1/8", new Oscillator().offset( 0.125f ) );

		lg.generate();
	}

	@Test
	public void times() throws Exception {
		LineGraph lg = new LineGraph( name.getMethodName(), 0, 1, 1000 );

		lg.with( "1-2-3-4", new Oscillator().rise( 0.1f ).high( 0.2f ).fall( 0.3f )
				.low( 0.4f ).frequency( 1 ) );

		lg.generate();
	}

	@Test
	public void interpolator() throws Exception {
		LineGraph lg = new LineGraph( name.getMethodName(), 0, 1, 1000 );

		lg.with( "linear", new Oscillator() );
		lg.with( "sine", new Oscillator().with( Interpolation.sine ) );
		lg.with( "bounce", new Oscillator().with( Interpolation.bounce ) );
		lg.with( "swing", new Oscillator().with( Interpolation.swing ) );

		lg.generate();
	}
}
