package com.rmn.gdxtend.util;

import java.util.Arrays;

/**
 * Utility methods for working with arrays
 */
public class ArrayUtil {

	/**
	 * @param f
	 *          the array
	 * @param capacity
	 *          the desired array size
	 * @param growth
	 *          a multiplier for array size
	 * @return a large-enough array holding the same data
	 */
	public static float[] ensureCapacity( float[] f, int capacity, float growth ) {
		while( f.length < capacity ) {
			float[] larger = new float[ (int) ( capacity * growth ) ];
			System.arraycopy( f, 0, larger, 0, f.length );
			f = larger;
		}
		return f;
	}

	/**
	 * @param f
	 *          the array
	 * @param capacity
	 *          the desired array size
	 * @param growth
	 *          a multiplier for array size
	 * @return a large-enough array holding the same data
	 */
	public static short[] ensureCapacity( short[] f, int capacity, float growth ) {
		while( f.length < capacity ) {
			short[] larger = new short[ (int) ( capacity * growth ) ];
			System.arraycopy( f, 0, larger, 0, f.length );
			f = larger;
		}
		return f;
	}

	/**
	 * Copies array data
	 * 
	 * @param dest
	 *          The array to copy to
	 * @param pos
	 *          the position to copy to
	 * @param toAdd
	 *          the data to copy
	 * @return the array containing all the data
	 */
	public static float[] append( float[] dest, int pos, float[] toAdd ) {
		dest = ensureCapacity( dest, pos + toAdd.length, 1.5f );
		System.arraycopy( toAdd, 0, dest, pos, toAdd.length );
		return dest;
	}

	/**
	 * Copies array data
	 * 
	 * @param dest
	 *          The array to copy to
	 * @param pos
	 *          the position to copy to
	 * @param toAdd
	 *          the data to copy
	 * @return the array containing all the data
	 */
	public static short[] append( short[] dest, int pos, short[] toAdd ) {
		dest = ensureCapacity( dest, pos + toAdd.length, 1.5f );
		System.arraycopy( toAdd, 0, dest, pos, toAdd.length );
		return dest;
	}

	/**
	 * Shorthand for simple array copy, avoids cryptic
	 * {@link System#arraycopy(Object, int, Object, int, int)} arguments
	 * 
	 * @param from
	 *          the source
	 * @param to
	 *          the destination
	 * @return the destination array
	 */
	public static float[] copy( float[] from, float[] to ) {
		System.arraycopy( from, 0, to, 0, from.length );
		return to;
	}

	/**
	 * Shorthand for simple array copy, avoids cryptic
	 * {@link System#arraycopy(Object, int, Object, int, int)} arguments
	 * 
	 * @param from
	 *          the source
	 * @param to
	 *          the destination
	 * @return the destination array
	 */
	public static int[] copy( int[] from, int[] to ) {
		System.arraycopy( from, 0, to, 0, from.length );
		return to;
	}

	/**
	 * Shorthand for simple array copy, avoids cryptic
	 * {@link System#arraycopy(Object, int, Object, int, int)} arguments
	 * 
	 * @param from
	 *          the source
	 * @param to
	 *          the destination
	 * @return the destination array
	 */
	public static byte[] copy( byte[] from, byte[] to ) {
		System.arraycopy( from, 0, to, 0, from.length );
		return to;
	}

	/**
	 * Inverse of {@link Arrays#toString(byte[])}
	 * 
	 * @param from
	 *          the string
	 * @param to
	 *          An array to use as the destination. A new array will be created if
	 *          this proves to be <code>null</code> or too small.
	 * @return the array
	 */
	public static byte[] fromString( String from, byte[] to ) {
		String[] e = from.substring( 1, from.length() - 1 ).split( ", " );

		if( to == null || to.length < e.length ) {
			to = new byte[ e.length ];
		}

		for( int i = 0; i < e.length; i++ ) {
			to[ i ] = Byte.parseByte( e[ i ] );
		}

		return to;
	}

	/**
	 * Prints a (possibly multidimensional) array to a string
	 * 
	 * @param a
	 *          the array
	 * @return the string
	 */
	public static String toString( Object[] a ) {
		StringBuilder sb = new StringBuilder( "[" );
		boolean first = true;

		for( Object o : a ) {
			if( !first ) {
				sb.append( ", " );
			}
			first = false;
			if( o == null ) {
				sb.append( String.valueOf( o ) );
			}
			else if( o instanceof boolean[] ) {
				sb.append( Arrays.toString( (boolean[]) o ) );
			}
			else if( o instanceof byte[] ) {
				sb.append( Arrays.toString( (byte[]) o ) );
			}
			else if( o instanceof short[] ) {
				sb.append( Arrays.toString( (short[]) o ) );
			}
			else if( o instanceof char[] ) {
				sb.append( Arrays.toString( (char[]) o ) );
			}
			else if( o instanceof int[] ) {
				sb.append( Arrays.toString( (int[]) o ) );
			}
			else if( o instanceof float[] ) {
				sb.append( Arrays.toString( (float[]) o ) );
			}
			else if( o instanceof long[] ) {
				sb.append( Arrays.toString( (long[]) o ) );
			}
			else if( o instanceof double[] ) {
				sb.append( Arrays.toString( (double[]) o ) );
			}
			else if( o.getClass().isArray() ) {
				sb.append( toString( (Object[]) o ) );
			}
			else {
				sb.append( String.valueOf( o ) );
			}
		}

		return sb.append( "]" ).toString();
	}
}
