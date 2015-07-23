package com.rmn.gdxtend.geom;

/**
 * Callbacks to set vertex positions
 */
public abstract class Geometry {

	protected float width = 0.0f;

	/**
	 * Sets the vertex positions
	 * 
	 * @param s
	 *          the shape to update
	 */
	public abstract void define( Shape s );

	/**
	 * Sets the width property
	 * 
	 * @param width
	 *          the new width
	 * @return this
	 */
	public Geometry withWidth( float width ) {
		this.width = width;
		return this;
	}

	protected void resetParameters() {
		width = 0;
	}

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

			resetParameters();
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

			resetParameters();
		}
	};

	/**
	 * 
	 */
	public static Geometry circleStrip = new Geometry() {

		@Override
		public void define( Shape s ) {
			int segments = ( s.vertices() - s.index() ) / 2;

			float inc = (float) ( Math.PI * 2 / segments );

			for( int i = 0; i < segments; i++ ) {
				s.pos
						.x( (float) Math.cos( i * inc ) )
						.y( (float) Math.sin( i * inc ) )
						.done()
						.next();
				s.pos
						.x( (float) Math.cos( i * inc ) * ( 1 - width ) )
						.y( (float) Math.sin( i * inc ) * ( 1 - width ) )
						.done()
						.next();
			}

			resetParameters();
		}
	};
}
