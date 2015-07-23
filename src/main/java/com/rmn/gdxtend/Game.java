package com.rmn.gdxtend;

import static com.badlogic.gdx.Gdx.gl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * A simple game framework. Game's are handled as a graph of {@link GamePhase}
 * objects.
 */
public abstract class Game extends ApplicationAdapter {

	private GamePhase phase;
	private GamePhase lastPhase;

	/**
	 * Implement this to build the starting phase for the game
	 * 
	 * @param phase
	 *          the phase to start with
	 */
	protected abstract GamePhase initialPhase();

	@Override
	public void create() {
		phase = initialPhase();
		phase.start();
		lastPhase = phase;
	}

	@Override
	public void render() {

		// state transition
		lastPhase = phase;
		phase = lastPhase.next();

		if( phase != lastPhase ) {
			lastPhase.end();
			phase.start();
		}

		// update state
		phase.update( Gdx.graphics.getDeltaTime() );

		// draw state
		gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
		phase.draw();
	}
}
