package com.rmn.gdxtend.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComparisonTest {

	private Comparison cmp = Comparison.instance;

	/**
	 * <code>false</code> is less than <code>true</code>
	 */
	@Test
	public void booleanComparison() {
		assertEquals( 0, cmp.compare( false, false ).result() );
		assertEquals( -1, cmp.compare( false, true ).result() );
		assertEquals( 0, cmp.compare( true, true ).result() );
		assertEquals( 1, cmp.compare( true, false ).result() );
	}

	/**
	 * Values that match the minimum are less than those that don't
	 */
	@Test
	public void booleanMinimum() {
		assertEquals( 0, cmp.compare( false, false, false ).result() );
		assertEquals( -1, cmp.compare( false, true, false ).result() );
		assertEquals( 0, cmp.compare( true, true, false ).result() );
		assertEquals( 1, cmp.compare( true, false, false ).result() );

		assertEquals( 0, cmp.compare( false, false, true ).result() );
		assertEquals( 1, cmp.compare( false, true, true ).result() );
		assertEquals( 0, cmp.compare( true, true, true ).result() );
		assertEquals( -1, cmp.compare( true, false, true ).result() );
	}

	/**
	 * Smaller numbers are less than larger numbers, doy!
	 */
	@Test
	public void floatComparison() {
		assertEquals( 0, cmp.compare( 0, 0 ).result() );
		assertEquals( -1, cmp.compare( 0, 1 ).result() );
		assertEquals( 1, cmp.compare( 1, 0 ).result() );
	}

	/**
	 * Numbers closer to the minimum are less
	 */
	@Test
	public void floatMinimum() {
		assertEquals( -1, cmp.compare( 9, 12, 10 ).result() );
		assertEquals( 1, cmp.compare( 12, 9, 10 ).result() );
		assertEquals( 0, cmp.compare( 9, 9, 10 ).result() );

		// when the two values are equidistant, the smaller is less
		assertEquals( -1, cmp.compare( 9, 11, 10 ).result() );
		assertEquals( 1, cmp.compare( 11, 9, 10 ).result() );
	}

	private enum Letters {
		A, B, C, D, E
	}

	/**
	 * Smaller ordinals are less
	 */
	@Test
	public void enumComparison() {
		assertEquals( 0, cmp.compare( Letters.B, Letters.B ).result() );
		assertEquals( -1, cmp.compare( Letters.A, Letters.B ).result() );
		assertEquals( 1, cmp.compare( Letters.B, Letters.A ).result() );
	}

	/**
	 * Ordinals closer to the minimum are less
	 */
	@Test
	public void enumMinimum() {

		assertEquals( -1, cmp.compare( Letters.B, Letters.E, Letters.C ).result() );
		assertEquals( 1, cmp.compare( Letters.E, Letters.B, Letters.C ).result() );
		assertEquals( 0, cmp.compare( Letters.B, Letters.B, Letters.C ).result() );

		// when the two values are equidistant, the lower ordinal is less
		assertEquals( -1, cmp.compare( Letters.B, Letters.D, Letters.C ).result() );
		assertEquals( 1, cmp.compare( Letters.D, Letters.B, Letters.C ).result() );
	}

	private class TestComparable implements Comparable<TestComparable> {
		private final int value;

		public TestComparable( int value ) {
			this.value = value;
		}

		@Override
		public int compareTo( TestComparable o ) {
			return cmp.compare( value, o.value ).result();
		}
	}

	/**
	 * Shows that it's safe to use one instance for nested comparisons
	 */
	@Test
	public void comparable() {
		TestComparable a = new TestComparable( 0 );
		TestComparable b = new TestComparable( 1 );
		assertEquals( 0, cmp.compare( a, a ).result() );
		assertEquals( -1, cmp.compare( a, b ).result() );
		assertEquals( 1, cmp.compare( b, a ).result() );
	}
}
