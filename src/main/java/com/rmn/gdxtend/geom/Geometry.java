package com.rmn.gdxtend.geom;

import com.badlogic.gdx.math.MathUtils;

/**
 * Callbacks to set vertex positions
 */
public abstract class Geometry {

	protected float height = 1;
	protected float width = 1;
	protected float radius = 1;

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

	/**
	 * Sets the radius property
	 *
	 * @param r
	 *          the new radius
	 * @return this
	 */
	public Geometry withRadius( float r ) {
		this.radius = r;
		return this;
	}

	protected void resetParameters() {
		width = 1;
		height = 1;
		radius = 1;
	}

	/**
	 * Circle centred on the z-axis, anticlockwise when viewing in the negative-z
	 * direction.
	 *
	 * @see Topology#fan
	 */
	public static Geometry circle = new Geometry() {

		@Override
		public void define( Shape s ) {
			int edges = s.vertices() - s.index();
			float inc = (float) ( Math.PI * 2 / edges );
			for( int i = 0; i < edges; i++ ) {
				s.pos.x( (float) Math.cos( i * inc ) * radius )
						.y( (float) Math.sin( i * inc ) * radius ).done().next();
			}

			resetParameters();
		}
	};

	/**
	 * Circle centred on the z-axis, anticlockwise when viewing in the negative-z
	 * direction. The first vertex is at (0, 0)
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
				s.pos.x( (float) Math.cos( i * inc ) * radius )
						.y( (float) Math.sin( i * inc ) * radius ).done().next();
			}

			resetParameters();
		}
	};

	/**
	 * A circular strip of quads centered on the z-axis, anticlockwise when viewing
	 * in the negative z direction.
	 *
	 * @see Topology#loop
	 */
	public static Geometry circleStrip = new Geometry() {

		@Override
		public void define( Shape s ) {
			int segments = ( s.vertices() - s.index() ) / 2;

			float inc = (float) ( Math.PI * 2 / segments );

			for( int i = 0; i < segments; i++ ) {
				s.pos.x( (float) Math.cos( i * inc ) * radius )
						.y( (float) Math.sin( i * inc ) * radius ).done().next();
				s.pos.x( (float) Math.cos( i * inc ) * ( radius - width ) )
						.y( (float) Math.sin( i * inc ) * ( radius - width ) ).done()
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
				s.pos.x( 0 ).y( height ).done().next();
				s.pos.x( width ).y( 0 ).done().next();
				s.pos.x( width ).y( height ).done().next();
			}

			resetParameters();
		}
	};

	/**
	 * A quad with rounded corners
	 */
	public static Geometry roundedQuad = new Geometry() {

		@Override
		public void define( Shape s ) {
			int vertsPerCorner = ( s.vertices() - s.index() ) / 4;

			// bottom -> right
			cornerArc( s, vertsPerCorner, width - radius, radius,
					1.5f * MathUtils.PI );
			// right -> top
			cornerArc( s, vertsPerCorner, width - radius, height - radius, 0 );
			// top -> left
			cornerArc( s, vertsPerCorner, radius, height - radius, MathUtils.PI / 2 );
			// left -> bottom
			cornerArc( s, vertsPerCorner, radius, radius, MathUtils.PI );

			resetParameters();
		}

		private void cornerArc( Shape s, int vertsPerCorner,
				float centerX, float centerY, float startAngle ) {
			float angleIncrement = MathUtils.PI / 2 / ( vertsPerCorner - 1 );
			for( int i = 0; i < vertsPerCorner; i++ ) {
				s.pos
						.x( centerX
								+ MathUtils.cos( startAngle + i * angleIncrement ) * radius )
						.y( centerY
								+ MathUtils.sin( startAngle + i * angleIncrement ) * radius )
						.done().next();
			}
		}
	};
}
