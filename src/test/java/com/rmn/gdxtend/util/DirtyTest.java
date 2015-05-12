package com.rmn.gdxtend.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Demonstrates usage of {@link Dirty}
 */
public class DirtyTest {

	@Test
	public void basic() {
		Dirty d = new Dirty( 0 );

		assertThat( d.changed() ).isFalse();

		d.set( 0, 0 );

		assertThat( d.changed() ).isFalse();

		d.set( 0, 1 );

		assertThat( d.changed() ).isTrue();
		assertThat( d.changed() ).isFalse();

		assertThat( d.get() ).isEqualTo( new float[] { 1 } );

		// single-element conveniences
		d.set( 2 );
		assertThat( d.changed() ).isTrue();
		assertThat( d.value() ).isEqualTo( 2 );
	}

	@Test
	public void multiple() {
		Dirty d = new Dirty( 1, 2, 3 );

		d.set( 0, 4 );
		assertThat( d.changed() ).isTrue();

		d.set( 1, 5 );
		assertThat( d.changed() ).isTrue();

		d.set( 2, 6 );
		assertThat( d.changed() ).isTrue();

		assertThat( d.get() ).isEqualTo( new float[] { 4, 5, 6 } );
	}

	@Test
	public void named() {
		Dirty d = new Dirty( 1, 2, 3 ).named( "foo", "bar", "baz" );

		d.set( "foo", 4 );
		assertThat( d.changed() ).isTrue();

		d.set( "bar", 5 );
		assertThat( d.changed() ).isTrue();

		d.set( "baz", 6 );
		assertThat( d.changed() ).isTrue();

		assertThat( d.get() ).isEqualTo( new float[] { 4, 5, 6 } );
	}

	@Test
	public void lettered() {
		Dirty d = new Dirty( 1, 2, 3 ).named( "a", "b", "c" );

		d.a( 4 );
		assertThat( d.changed() ).isTrue();
		d.b( 5 );
		assertThat( d.changed() ).isTrue();
		d.c( 6 );
		assertThat( d.changed() ).isTrue();

		assertThat( d.get() ).isEqualTo( new float[] { 4, 5, 6 } );
	}

	@Test
	public void allLetters() throws Exception {
		String alpha = "abcdefghijklmnopqrstuvxwy";

		for( int i = 0; i < alpha.length(); i++ ) {
			String letter = alpha.substring( i, i + 1 );

			Dirty d = new Dirty( 0 ).named( letter );

			d.set( letter, 1 );
			assertThat( d.changed() ).isTrue();

			Method m = Dirty.class.getMethod( letter, float.class );

			m.invoke( d, new Float( 2 ) );

			assertThat( d.changed() ).isTrue();
		}
	}
}
