package com.rmn.gdxtend.gl.facets;

import org.junit.Assert;

import com.rmn.gdxtend.GdxTest;
import com.rmn.gdxtend.gl.Facet;

public abstract class FacetTest extends GdxTest {

	/**
	 * Asserts the facets are order correctly, more-default state to before
	 * less-default
	 * 
	 * @param control
	 *          the more-default state
	 * @param altered
	 *          the less-default state
	 */
	protected <T extends Facet<T>> void comparisonOrder( T control, T altered ) {
		Assert.assertEquals( -1, control.compareTo( altered ) );
		Assert.assertEquals( 1, altered.compareTo( control ) );
	}
}
