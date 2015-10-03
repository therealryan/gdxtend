package com.rmn.gdxtend.expect;

import org.junit.Rule;

/**
 * A {@link Rule} to allow convenient assertion of expected results held in
 * files
 */
public class Expect extends AbstractExpect<Expect> {

	public Expect() {
		super( ".expect" );
	}
}
