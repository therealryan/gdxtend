package com.rmn.gdxtend.math;

/**
 * A variable expressed as a time-varying offset from another variable
 */
public class OffsetVariable {

	private final FloatVariable base;

	/**
	 * The offset
	 */
	public final FloatVariable offset;

	/**
	 * @param base
	 *          base variable
	 * @param offset
	 *          offset variable
	 */
	public OffsetVariable( FloatVariable base, FloatVariable offset ) {
		this.base = base;
		this.offset = offset;
	}

	/**
	 * @param time
	 *          the current time
	 * @return this
	 */
	public OffsetVariable now( float time ) {
		base.now( time );
		offset.now( time );
		return this;
	}

	/**
	 * @return the current value
	 */
	public float get() {
		return base.get() + offset.get();
	}
}
