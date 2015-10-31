package com.rmn.gdxtend.util;

import java.util.Arrays;

import com.badlogic.gdx.utils.ObjectIntMap;

/**
 * Convenient way to maintain a dirty-checking set of values
 */
@SuppressWarnings( "javadoc" )
public class DirtyFloats {

	private final float[] current;
	private boolean dirty = false;

	private byte[] letters = null;
	private ObjectIntMap<String> names = null;

	/**
	 * @param initial
	 *          initial values
	 */
	public DirtyFloats( float... initial ) {
		this.current = initial;
	}

	/**
	 * Sets names for the values
	 *
	 * @param names
	 *          list of names for the values
	 * @return this
	 */
	public DirtyFloats named( String... names ) {
		letters = new byte[ 26 ];
		Arrays.fill( letters, (byte) -1 );
		this.names = new ObjectIntMap<>();

		for( byte i = 0; i < names.length; i++ ) {
			String n = names[ i ];
			if( isLetter( n ) ) {
				letters[ n.charAt( 0 ) - 'a' ] = i;
			}
			else {
				this.names.put( n, i );
			}
		}

		return this;
	}

	/**
	 * Sets a new value
	 *
	 * @param index
	 *          the index of the field
	 * @param value
	 *          the new value
	 * @return this
	 */
	public DirtyFloats set( int index, float value ) {
		if( current[ index ] != value ) {
			dirty = true;
			current[ index ] = value;
		}

		return this;
	}

	/**
	 * Shorthand for single-element sets
	 *
	 * @param value
	 *          the new value
	 * @return this
	 */
	public DirtyFloats set( float value ) {
		return set( 0, value );
	}

	/**
	 * @return the current values
	 */
	public float[] get() {
		return current;
	}

	/**
	 * Shorthand for single-element sets
	 *
	 * @return the first value
	 */
	public float value() {
		return get()[ 0 ];
	}

	/**
	 * @return <code>true</code> if any of the values has changed since last time
	 *         this method was called
	 */
	public boolean changed() {
		boolean d = dirty;
		dirty = false;
		return d;
	}

	/**
	 * Sets a named value
	 *
	 * @param name
	 *          the name of the field
	 * @param value
	 *          the new value
	 * @return this
	 */
	public DirtyFloats set( String name, float value ) {
		if( isLetter( name ) ) {
			return set( letters[ name.charAt( 0 ) - 'a' ], value );
		}
		else {
			return set( names.get( name, -1 ), value );
		}
	}

	public DirtyFloats a( float value ) {
		return set( letters[ 0 ], value );
	}

	public DirtyFloats b( float value ) {
		return set( letters[ 1 ], value );
	}

	public DirtyFloats c( float value ) {
		return set( letters[ 2 ], value );
	}

	public DirtyFloats d( float value ) {
		return set( letters[ 3 ], value );
	}

	public DirtyFloats e( float value ) {
		return set( letters[ 4 ], value );
	}

	public DirtyFloats f( float value ) {
		return set( letters[ 5 ], value );
	}

	public DirtyFloats g( float value ) {
		return set( letters[ 6 ], value );
	}

	public DirtyFloats h( float value ) {
		return set( letters[ 7 ], value );
	}

	public DirtyFloats i( float value ) {
		return set( letters[ 8 ], value );
	}

	public DirtyFloats j( float value ) {
		return set( letters[ 9 ], value );
	}

	public DirtyFloats k( float value ) {
		return set( letters[ 10 ], value );
	}

	public DirtyFloats l( float value ) {
		return set( letters[ 11 ], value );
	}

	public DirtyFloats m( float value ) {
		return set( letters[ 12 ], value );
	}

	public DirtyFloats n( float value ) {
		return set( letters[ 13 ], value );
	}

	public DirtyFloats o( float value ) {
		return set( letters[ 14 ], value );
	}

	public DirtyFloats p( float value ) {
		return set( letters[ 15 ], value );
	}

	public DirtyFloats q( float value ) {
		return set( letters[ 16 ], value );
	}

	public DirtyFloats r( float value ) {
		return set( letters[ 17 ], value );
	}

	public DirtyFloats s( float value ) {
		return set( letters[ 18 ], value );
	}

	public DirtyFloats t( float value ) {
		return set( letters[ 19 ], value );
	}

	public DirtyFloats u( float value ) {
		return set( letters[ 20 ], value );
	}

	public DirtyFloats v( float value ) {
		return set( letters[ 21 ], value );
	}

	public DirtyFloats w( float value ) {
		return set( letters[ 22 ], value );
	}

	public DirtyFloats x( float value ) {
		return set( letters[ 23 ], value );
	}

	public DirtyFloats y( float value ) {
		return set( letters[ 24 ], value );
	}

	public DirtyFloats z( float value ) {
		return set( letters[ 25 ], value );
	}

	private static boolean isLetter( String s ) {
		return s.length() == 1 && 'a' <= s.charAt( 0 ) && s.charAt( 0 ) <= 'z';
	}

}
