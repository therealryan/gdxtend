package com.rmn.gdxtend.gl.shader;

import static com.badlogic.gdx.graphics.glutils.ShaderProgram.COLOR_ATTRIBUTE;
import static com.badlogic.gdx.graphics.glutils.ShaderProgram.POSITION_ATTRIBUTE;

/**
 * A collection of standard programs
 */
public class Programs {

	public static final String aPos = POSITION_ATTRIBUTE;
	public static final String aCol = COLOR_ATTRIBUTE;
	/**
	 * The uniform name under which the camera matrix will be passed
	 */
	public static final String uCam = "u_camera";
	/**
	 * The uniform name under which the camera matrix will be passed
	 */
	public static final String uMod = "u_model";
	/**
	 * The uniform name under which the vertex colour will be passed
	 */
	public static final String uColour = "u_colour";

	private Programs() {
	}

	public static class Vertex {
		private Vertex() {
		}

		/**
		 * Colour is passed in a uniform, camera and model matrices
		 */
		public static final String monotone = ""
				+ "attribute vec4 " + aPos + ";            \n"
				+ "uniform mat4 " + uCam + ";              \n"
				+ "uniform mat4 " + uMod + ";              \n"
				+ "uniform vec4 " + uColour + ";           \n"
				+ "varying vec4 v_color;                   \n"
				+ "                                        \n"
				+ "void main() {                           \n"
				+ "  v_color = " + uColour + ";            \n"
				// this is because we have to mask out the high bits when converting
				// from packed int to float, hence we don't get the full alpha range
				+ "  v_color.a = v_color.a * (255.0/254.0);\n"
				+ "  gl_Position =  u_camera               \n"
				+ "               * u_model                \n"
				+ "               * " + aPos + ";          \n"
				+ "}                                       \n";

		/**
		 * Standard position/colour shader with camera and model matrices
		 */
		public static final String vertColour = ""
				+ "attribute vec4 " + aPos + ";            \n"
				+ "attribute vec4 " + aCol + ";            \n"
				+ "uniform mat4 " + uCam + ";              \n"
				+ "uniform mat4 " + uMod + ";              \n"
				+ "varying vec4 v_color;                   \n"
				+ "                                        \n"
				+ "void main() {                           \n"
				+ "  v_color = " + aCol + ";               \n"
				// this is because we have to mask out the high bits when converting
				// from packed int to float, hence we don't get the full alpha range
				+ "  v_color.a = v_color.a * (255.0/254.0);\n"
				+ "  gl_Position =  u_camera               \n"
				+ "               * u_model                \n"
				+ "               * " + aPos + ";          \n"
				+ "}                                       \n";
	}

	public static class Fragment {

		private Fragment() {
		}

		/**
		 * Standard vertex colour interpolation
		 */
		public static final String passthrough = ""
				+ "#ifdef GL_ES                  \n"
				+ "#define LOWP lowp             \n"
				+ "precision mediump float;      \n"
				+ "#else                         \n"
				+ "#define LOWP                  \n"
				+ "#endif                        \n"
				+ "varying LOWP vec4 v_color;    \n"
				+ "void main() {                 \n"
				+ "  gl_FragColor = v_color;     \n"
				+ "}                             \n";
	}

}
