package com.rmn.gdxtend.util;

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

}
