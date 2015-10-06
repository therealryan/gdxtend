package com.rmn.gdxtend.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

/**
 * Tests for {@link ArrayUtil}
 */
public class ArrayUtilTest {

	/**
	 * Tests for parsing string to array
	 */
	@Test
	public void bytesFromString() {
		byte[] input = new byte[ 1000 ];
		new Random().nextBytes( input );

		String serialised = Arrays.toString( input );

		{ // null dest
			byte[] output = ArrayUtil.fromString( serialised, (byte[]) null );
			assertThat( output ).isEqualTo( input );
		}

		{ // dest too small
			byte[] dest = new byte[ input.length - 1 ];
			byte[] output = ArrayUtil.fromString( serialised, dest );
			assertThat( output ).isEqualTo( input );
			assertThat( output ).isNotSameAs( dest );
		}

		{ // dest fits
			byte[] dest = new byte[ input.length ];
			byte[] output = ArrayUtil.fromString( serialised, dest );
			assertThat( output ).isEqualTo( input );
			assertThat( output ).isSameAs( dest );
		}
	}
}
