package com.rmn.gdxtend.util;

/**
 * A boolean flag that tracks dirty status. Flag value is initially false
 */
public class DirtyBoolean {

	private boolean value;
	private boolean dirty = true;

	/**
	 * Sets the value of the flag
	 *
	 * @param b
	 *          the new value
	 * @return this
	 */
	public DirtyBoolean set( boolean b ) {
		if( b != value ) {
			dirty = true;
		}
		value = b;
		return this;
	}

	/**
	 * @return the flag value
	 */
	public boolean get() {
		return value;
	}

	/**
	 * @return true if the flag has been changed since the last call to
	 *         {@link #dirty()}
	 */
	public boolean dirty() {
		boolean b = dirty;
		clear();
		return b;
	}

	/**
	 * Resets the dirty status
	 *
	 * @return this
	 */
	public DirtyBoolean clear() {
		dirty = false;
		return this;
	}

	/**
	 * Forces the dirty status to <code>true</code>
	 *
	 * @return this
	 */
	public DirtyBoolean force() {
		dirty = true;
		return this;
	}

	@Override
	public String toString() {
		return "State:" + value + " dirty:" + dirty;
	}
}
