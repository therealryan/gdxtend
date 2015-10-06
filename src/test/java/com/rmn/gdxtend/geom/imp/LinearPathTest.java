package com.rmn.gdxtend.geom.imp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.rmn.gdxtend.geom.Position;

/**
 * tests for {@link LinearPath}
 */
public class LinearPathTest {

	/**
	 * Simple clamped and unclamped path tes
	 */
	@Test
	public void diagonal() {
		LinearPath clamped = new LinearPath();
		clamped.duration.from( 0 ).to( 1 );
		clamped.start.position.set( 0, 0, 0 );
		clamped.end.position.set( 1, 1, 1 );

		LinearPath unclamped = new LinearPath()
				.from( clamped )
				.clamp( false );

		Position p = new Position();
		float t = -1;
		while( t < 2 ) {
			unclamped.forTime( t, p );
			assertThat( p.position.x ).isEqualTo( t );
			assertThat( p.position.y ).isEqualTo( t );
			assertThat( p.position.z ).isEqualTo( t );

			clamped.forTime( t, p );
			assertThat( p.position.x ).isEqualTo( t < 0 ? 0 : t > 1 ? 1 : t );
			assertThat( p.position.y ).isEqualTo( t < 0 ? 0 : t > 1 ? 1 : t );
			assertThat( p.position.z ).isEqualTo( t < 0 ? 0 : t > 1 ? 1 : t );

			t += 0.125f;
		}
	}
}
