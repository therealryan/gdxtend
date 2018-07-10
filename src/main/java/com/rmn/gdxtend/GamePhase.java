package com.rmn.gdxtend;

/**
 * Defines a distinct phase of a game flow
 */
public interface GamePhase {

	/**
	 * Called when this phase is starting. Use this to register event handlers etc.
	 */
	void start();

	/**
	 * Updates the state of the phase.
	 * 
	 * @param delta
	 *          the time that has passed
	 */
	void update( float delta );

	/**
	 * Draws the state of the phase.
	 */
	void draw();

	/**
	 * Transitions from one state to the next. This will be called every frame, so
	 * feel free to <code>return this;</code> for the vast majority of the time.
	 * 
	 * @return The next phase of the game.
	 */
	GamePhase next();

	/**
	 * Called when this phase is over. Use this to clean up.
	 */
	void end();
}
