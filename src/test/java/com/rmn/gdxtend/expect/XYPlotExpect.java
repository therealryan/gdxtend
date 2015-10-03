package com.rmn.gdxtend.expect;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.junit.runner.Description;

import com.rmn.gdxtend.math.Function;
import com.rmn.gdxtend.math.RangeMap;

/**
 * For tests where the result is a line graph
 */
public class XYPlotExpect extends AbstractSketchExpect<XYPlotExpect> {

	private static final XYPlotExpect BASE_DEFAULTS = new XYPlotExpect();

	/**
	 * The config we return to after each test and call to {@link #reset()}
	 */
	private XYPlotExpect defaults = BASE_DEFAULTS;

	/**
	 * Defaults to traversing from 0 to 1 in 100 steps
	 */
	private RangeMap pointsToXValue = new RangeMap()
			.source( 0, 99 )
			.destination( 0, 1 );
	private XYSeriesCollection plots = new XYSeriesCollection();

	private int width = 400;
	private int height = 400;

	@Override
	protected void finished( Description description ) {
		super.finished( description );
		reset();
	}

	/**
	 * Sets the default config that will be returned to after each test and call
	 * to {@link #reset()}
	 * 
	 * @param gr
	 *          A {@link XYPlotExpect}, configured as desired
	 * @return this
	 */
	public XYPlotExpect defaults( XYPlotExpect gr ) {
		this.defaults = gr;
		return this;
	}

	@Override
	public XYPlotExpect reset() {
		width = defaults.width;
		height = defaults.height;
		pointsToXValue.from( defaults.pointsToXValue );
		plots = new XYSeriesCollection();
		return super.reset();
	}

	/**
	 * Sets the range over which the functions are evaluated
	 * 
	 * @param from
	 *          the minimum x value
	 * @param to
	 *          the maximum x value
	 * @return this
	 */
	public XYPlotExpect range( float from, float to ) {
		pointsToXValue.destination( from, to );
		return self();
	}

	/**
	 * @param p
	 *          the number of data points to evaluate
	 * @return this
	 */
	public XYPlotExpect points( int p ) {
		pointsToXValue.source( 0, p - 1 );
		return self();
	}

	/**
	 * @param w
	 *          width of resulting graph
	 * @return this
	 */
	public XYPlotExpect w( int w ) {
		this.width = w;
		return self();
	}

	/**
	 * @param h
	 *          height of resulting graph
	 * @return this
	 */
	public XYPlotExpect h( int h ) {
		this.height = h;
		return self();
	}

	/**
	 * Adds a plot to the next graph to be checked
	 * 
	 * @param name
	 *          a name
	 * @param f
	 *          the function to evaluate
	 * @return this
	 */
	public XYPlotExpect with( String name, Function f ) {
		XYSeries s = new XYSeries( name );

		for( int i = 0; i <= pointsToXValue.source.to; i++ ) {
			float x = pointsToXValue.linearMap( i );
			float y = f.map( x );

			s.add( x, y );
		}

		plots.addSeries( s );
		return self();
	}

	@Override
	public void check( SVGGraphics2D g ) {
		String svg = g.getSVGDocument();
		svg = pretty( svg );

		// clipPath ids change with every run, so we need to mask them
		svg = new Mask()
				.maskCaptures( "<clipPath id=\"(.*?)\">" )
				.with( "clip_path" )
				.apply( svg );

		super.check( svg );
	}

	/**
	 * Creates a graph of the added plots and compares it against the expected
	 * graph
	 */
	public void check() {
		JFreeChart jfc = ChartFactory.createXYLineChart(
				resultName(), "x", "y", plots,
				PlotOrientation.VERTICAL, true, true, false );

		jfc.setBackgroundPaint( Color.white );
		jfc.getXYPlot().setBackgroundPaint( Color.WHITE );
		jfc.getXYPlot().setDomainGridlinePaint( Color.GRAY );
		jfc.getXYPlot().setRangeGridlinePaint( Color.GRAY );
		// jfc.getXYPlot().setBackgroundAlpha( 0 );

		SVGGraphics2D g = new SVGGraphics2D( width, height );
		jfc.draw( g, new Rectangle2D.Float( 0, 0, width, height ) );

		check( g );

		plots = new XYSeriesCollection();
	}
}
