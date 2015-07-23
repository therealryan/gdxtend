package com.rmn.gdxtend.testutil;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rmn.gdxtend.math.Function;

/**
 * Generates a javascriptline graph in the generated output folder
 */
public class LineGraph {

	private final String name;
	private final float min, max;
	private final int points;

	private List<Line> lines = new ArrayList<>();

	public LineGraph( String name, float min, float max, int points ) {
		this.name = name;
		this.min = min;
		this.max = max;
		this.points = points;
	}

	public LineGraph with( String name, Function f ) {
		lines.add( new Line( name, f ) );
		return this;
	}

	public void generate() throws Exception {

		File dir = new File( "generated" );
		dir.mkdirs();

		FileWriter out =
				new FileWriter( "generated/" + name + ".html" );
		out.write( "<html>\n\t<head>\n\t\t<title>" + name + "</title>\n" );
		out.write( "\t\t<script type=\"text/javascript\" src=\"../src/test/resources/vis.min.js\"></script>\n" );
		out.write( "\t\t<link href=\"../src/test/resources/vis.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n" );
		out.write( "\t</head>\n\t<body>\n\t\t<div id=\"graph\"></div>\n" );
		out.write( "\t\t<script type=\"text/javascript\">\n" );

		out.write( "var groups = new vis.DataSet();" );

		for( int i = 0; i < lines.size(); i++ ) {
			out.write( "\ngroups.add({ id: " + i );
			out.write( ", content: '" );
			out.write( lines.get( i ).name );
			out.write( "', options: { drawPoints: false } });" );
		}

		out.write( "\nvar container = document.getElementById('graph');" );
		out.write( "\nvar dataset = new vis.DataSet();" );

		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" );

		for( int i = 0; i < lines.size(); i++ ) {
			out.write( "\ndataset.add( [" );
			for( float x = min; x <= max; x += ( ( max - min ) / points ) ) {

				// for some reason visjs can only deal with time-dependent data :-/
				Date d = new Date( (long) ( x * 1000 ) );

				out.write( "\n\t{ x: '" );
				out.write( sdf.format( d ) );
				out.write( "', y: " + lines.get( i ).func.map( x ) );
				out.write( ", group: " + i + " }," );
			}
			out.write( "\n]);" );
		}

		out.write( "\nvar options = {" );
		out.write( "\n\tdataPoints: false," );
		out.write( "\n\tdataAxis: {visible: false}," );
		out.write( "\n\tlegend: true," );
		out.write( "\n};" );

		out.write( "\nvar graph2d = new vis.Graph2d(container, dataset, groups, options);" );

		out.write( "\n\t\t</script>\n\t</body>\n</html>" );

		out.close();

	}

	private class Line {
		final String name;
		final Function func;

		public Line( String name, Function func ) {
			this.name = name;
			this.func = func;
		}
	}

}
