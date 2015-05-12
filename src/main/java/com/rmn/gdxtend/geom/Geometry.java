package com.rmn.gdxtend.geom;

/**
 * Callbacks to set vertex positions
 */
public abstract class Geometry {

	/**
	 * Sets the vertex positions
	 * 
	 * @param s
	 *          the shape to update
	 */
	public abstract void define( Shape s );

	/**
	 * 1-radius circle centred on the z-axis, anticlockwise when viewing in the
	 * negative-z direction.
	 */
	public static Geometry circle = new Geometry() {

		@Override
		public void define( Shape s ) {
			int edges = s.vertices() - s.index();
			float inc = (float) ( Math.PI * 2 / edges );
			for( int i = 0; i < edges; i++ ) {
				s.pos
						.x( (float) Math.cos( i * inc ) )
						.y( (float) Math.sin( i * inc ) )
						.done()
						.next();
			}
		}
	};

	/**
	 * 1-radius circle centred on the z-axis, anticlockwise when viewing in the
	 * negative-z direction. The first vertex is at (0, 0)
	 */
	public static Geometry centeredCircle = new Geometry() {

		@Override
		public void define( Shape s ) {
			int edges = s.vertices() - s.index() - 1;
			s.pos.x( 0 ).y( 0 ).done().next();
			float inc = (float) ( Math.PI * 2 / edges );
			for( int i = 0; i < edges; i++ ) {
				s.pos
						.x( (float) Math.cos( i * inc ) )
						.y( (float) Math.sin( i * inc ) )
						.done()
						.next();
			}
		}
	};
}
