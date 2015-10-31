package com.rmn.gdxtend.geom;

/**
 * Callbacks to set vertex positions
 */
public abstract class Geometry {

	protected float height = 0;
	protected float width = 0;

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

	/**
	 * Sets the height property
	 * 
	 * @param h
	 *          the new height
	 * @return this
	 */
	public Geometry withHeight( float h ) {
		this.height = h;
		return this;
	}

	protected void resetParameters() {
		width = 0;
		height = 0;
	}

	/**
	 * 1-radius circle centred on the z-axis, anticlockwise when viewing in the
	 * negative-z direction.
	 * 
	 * @see Topology#fan
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
	 * 
	 * @see Topology#radial
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
	 * A 1-radius circular strip of quads centered on the z-axis, anticlockwise
	 * when viewing in the negative z direction.
	 * 
	 * @see Topology#loop
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

	/**
	 * A sequence of quads.
	 * 
	 * @see Topology#quads
	 */
	public static Geometry quads = new Geometry() {

		@Override
		public void define( Shape s ) {
			int quads = ( s.vertices() - s.index() ) / 4;

			for( int i = 0; i < quads; i++ ) {
				s.pos.x( 0 ).y( 0 ).done().next();
				s.pos.x( 0 ).y( 1 ).done().next();
				s.pos.x( 1 ).y( 0 ).done().next();
				s.pos.x( 1 ).y( 1 ).done().next();
			}

			resetParameters();
		}
	};
}
