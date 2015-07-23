package com.rmn.gdxtend.geom;

/**
 * Callbacks to add triangle structures to {@link Shape}s
 */
public abstract class Topology {

	/**
	 * @param elements
	 *          the desired number of primitives - what that means depends on the
	 *          topography
	 * @return the required number of vertices
	 */
	public abstract int verticesFor( int elements );

	/**
	 * @param elements
	 *          the desired number of primitives - what that means depends on the
	 *          topography
	 * @return the required number of triangles
	 */
	public abstract int trianglesFor( int elements );

	/**
	 * Override to build the triangle structure of the shape
	 * 
	 * @param s
	 */
	public void define( Shape s ) {
		define( s, 0 );
	}

	protected abstract void define( Shape s, int startIndex );

	/**
	 * Separate triangles. e.g.: 0-1-2, 3-4-5
	 * 
	 * <pre>
	 *   2     5
	 *  / \   / \
	 * 0---1 3---4
	 * </pre>
	 */
	public static final Topology triangles = new Topology() {
		@Override
		protected void define( Shape s, int startIndex ) {
			int count = ( s.vertices() - startIndex ) / 3;
			for( int i = 0; i < count; i++ ) {
				int base = i * 3 + startIndex;
				s.triangle( base, base + 1, base + 2 );
			}
		}

		@Override
		public int verticesFor( int triangles ) {
			return triangles * 3;
		}

		@Override
		public int trianglesFor( int triangles ) {
			return triangles;
		};
	};

	/**
	 * Separate quads. e.g.: 0-2-1, 2-3-1, 4-6-5, 6-7-5
	 * 
	 * <pre>
	 * 1-3  5-7
	 * |\|  |\|
	 * 0-2  4-6
	 * </pre>
	 */
	public static final Topology quads = new Topology() {
		@Override
		protected void define( Shape s, int startIndex ) {
			int count = ( s.vertices() - startIndex ) / 4;
			for( int i = 0; i < count; i++ ) {
				int base = i * 4 + startIndex;
				s.triangle( base + 0, base + 2, base + 1 );
				s.triangle( base + 2, base + 3, base + 1 );
			}
		}

		@Override
		public int verticesFor( int quads ) {
			return quads * 4;
		}

		@Override
		public int trianglesFor( int quads ) {
			return quads * 2;
		}
	};

	/**
	 * Triangle strip. e.g.: 0-2-1, 1-2-3, 2-4-3, 3-4-5, 4-6-5, 5-6-7
	 * 
	 * <pre>
	 *   1---3---5---7
	 *  / \ / \ / \ /
	 * 0---2---4---6
	 * </pre>
	 */
	public static final Topology strip = new Topology() {
		@Override
		protected void define( Shape s, int startIndex ) {
			int count = s.vertices() - 2 - startIndex;
			for( int i = 0; i < count; i++ ) {
				int b = i + startIndex;
				if( i % 2 == 0 ) {
					s.triangle( b, b + 2, b + 1 );
				}
				else {
					s.triangle( b, b + 1, b + 2 );
				}
			}
		}

		@Override
		public int verticesFor( int triangles ) {
			return triangles + 2;
		}

		@Override
		public int trianglesFor( int triangles ) {
			return triangles;
		}
	};

	/**
	 * Looped triangle strip
	 * 
	 * <pre>
	 *   1---3---5---7---1
	 *  / \ / \ / \ / \ /
	 * 0---2---4---6---0
	 * </pre>
	 */
	public static final Topology loop = new Topology() {

		@Override
		protected void define( Shape s, int startIndex ) {
			strip.define( s, startIndex );
			s.triangle( s.vertices() - 2, startIndex, s.vertices() - 1 );
			s.triangle( s.vertices() - 1, startIndex, startIndex + 1 );
		}

		@Override
		public int verticesFor( int elements ) {
			return strip.verticesFor( elements ) - 2;
		}

		@Override
		public int trianglesFor( int elements ) {
			return strip.trianglesFor( elements );
		}
	};

	/**
	 * Triangle fan. e.g.: 0-1-2, 0-2-3, 0-3-4
	 * 
	 * <pre>
	 *   3---2
	 *  / \ / \
	 * 4---0---1
	 * </pre>
	 */
	public static final Topology fan = new Topology() {
		@Override
		protected void define( Shape s, int startIndex ) {
			for( int i = 2; i < s.vertices() - startIndex; i++ ) {
				int b = startIndex + i;
				s.triangle( startIndex, b - 1, b );
			}
		}

		@Override
		public int verticesFor( int edges ) {
			return edges;
		}

		@Override
		public int trianglesFor( int edges ) {
			return edges - 2;
		}
	};

	/**
	 * Triangle fan with central vertex. e.g.: 0-1-2, 0-2-3, 0-3-4, 0-4-5, 0-5-6,
	 * 0-6-1
	 * 
	 * <pre>
	 *   3---2
	 *  / \ / \
	 * 4---0---1
	 *  \ / \ /
	 *   5---6
	 * </pre>
	 */
	public static final Topology radial = new Topology() {

		@Override
		public void define( Shape s, int startIndex ) {
			int count = s.vertices() - startIndex - 1;
			for( int i = 1; i < count; i++ ) {
				int b = startIndex + i;
				s.triangle( startIndex, b, b + 1 );
			}
			s.triangle( startIndex, s.vertices() - 1, startIndex + 1 );
		}

		@Override
		public int verticesFor( int edges ) {
			return edges + 1;
		}

		@Override
		public int trianglesFor( int edges ) {
			return edges;
		}
	};

	/**
	 * An arrow, with some number of shaft segments. e.g.: 0-1-3, 0-3-4, 0-4-2,
	 * 3-5-4, 4-5-6, 5-7-6, 6-7-8
	 * 
	 * <pre>
	 *     1
	 *     |\
	 * 7-5-3_\
	 * |\|\|  0
	 * 8-6-4‾/
	 *     |/
	 *     2
	 * </pre>
	 */
	public static final Topology arrow = new Topology() {

		@Override
		public int verticesFor( int segments ) {
			return 5 + segments * 2;
		}

		@Override
		public int trianglesFor( int segments ) {
			return 3 + segments * 2;
		}

		@Override
		public void define( Shape s, int startIndex ) {

			s.triangle( startIndex, startIndex + 1, startIndex + 3 );
			s.triangle( startIndex, startIndex + 3, startIndex + 4 );
			s.triangle( startIndex, startIndex + 4, startIndex + 2 );

			strip.define( s, startIndex + 3 );
		}
	};
}
