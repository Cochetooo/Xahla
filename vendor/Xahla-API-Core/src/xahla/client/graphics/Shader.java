package xahla.client.graphics;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import xahla.core.Context;
import xahla.utils.ValidationException;

/**
 * Shader class allows GLSL interpretation and execution within the program.<br>
 * 
 * @author Cochetooo
 * @version 1.5
 */
public class Shader {
	
	private int program;
	private Context Context;
	
	/** The path where the shaders are stored. */
	public static final String SHADER_PATH = getShaderPath();
	
	private static String gShader, vShader, fShader;
	
	/**
	 * @param _context 			The context.
	 * @param path				The path to the shader starting from the default shader directory (without extension).
	 * @param geometryShader	Does the shader contains a geometry shader.
	 */
	public Shader(Context _context, String path, boolean geometryShader) {
		this.Context = _context;
		program = glCreateProgram();
		
		if (program == GL_FALSE)
			Context.getExceptionHandler().reportException(new ValidationException("Can't create program for shader: " + path));
		
		if (gShader == null) {
			gShader = Context.getConfigString("Rendering", "geometry_shader_ext");
			vShader = Context.getConfigString("Rendering", "vertex_shader_ext");
			fShader = Context.getConfigString("Rendering", "fragment_shader_ext");
		}
		
		if (geometryShader)
			createShader(loadShader(path + "." + gShader), GL_GEOMETRY_SHADER);
		
		createShader(loadShader(path + "." + vShader), GL_VERTEX_SHADER);
		createShader(loadShader(path + "." + fShader), GL_FRAGMENT_SHADER);
		
		glLinkProgram(program);
		glValidateProgram(program);
	}
	
	private int createShader(String source, int type) {
		int shader = glCreateShader(type);
		if (shader == GL_FALSE)
			Context.getExceptionHandler().reportException(new ValidationException("(ID " + shader + ") Error during shader creation."));
		
		glShaderSource(shader, source);
		glCompileShader(shader);
		
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE)
			Context.getExceptionHandler().reportException(new ValidationException("(ID " + shader + ") Error during shader's compute. Details:\n" + glGetShaderInfoLog(shader, 2048)));
		
		glAttachShader(program, shader);
		
		return shader;
	}
	
	private String loadShader(String path) {
		final String INCLUDE_FUNC = "#include";
		String r = "";
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new InputStreamReader(Class.class.getResourceAsStream(SHADER_PATH + path)));
		} catch (NullPointerException npe1) {
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(SHADER_PATH + path)));
			} catch (NullPointerException npe2) {
				Context.getExceptionHandler().reportException(new ValidationException("(Path " + path + ") File not found! "));
			} catch (IOException ie2) {
				Context.getExceptionHandler().reportException(new ValidationException("(Path " + path + ") Error during retrieving source code: " + ie2.getLocalizedMessage()));
			}
		}
		
		try {
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
				if (buffer.startsWith(INCLUDE_FUNC)) {
					String[] fileDir = path.split("/");
					String dir = path.substring(0, path.length() - fileDir[fileDir.length - 1].length());
					r += loadShader(dir + buffer.substring(INCLUDE_FUNC.length() + 2, buffer.length() - 1));
				} else {
					r += buffer + "\n";
				}
			}
		} catch (IOException e) {
			Context.getExceptionHandler().reportException(new ValidationException("(Path " + path + ") Error during retrieving source code: " + e.getLocalizedMessage()));
		}
		
		return r;
	}
	
	/**
	 * Load an Integer into the shader.
	 * @param location	The Uniform Location.
	 * @param v			The Value.
	 */
	public void loadInt(int location, int v) { 									glUniform1i(location, v); 									}
	/**
	 * Load a Float into the shader.
	 * @param location	The Uniform Location.
	 * @param v			The Value.
	 */
	public void loadFloat(int location, float v) { 								glUniform1f(location, v); 									}
	/**
	 * Load a Vector2f into the shader.
	 * @param location	The Uniform Location.
	 * @param v			The Vector.
	 */
	public void loadVec2(int location, Vector2f v) { 							glUniform2f(location, v.x, v.y); 							}
	/**
	 * Load two floats into the shader.
	 * @param location	The Uniform Location.
	 * @param x			The first value.
	 * @param y			The second value.
	 */
	public void load2f(int location, float x, float y) { 						glUniform2f(location, x, y); 								}
	/**
	 * Load a Vector3f into the shader.
	 * @param location	The Uniform Location.
	 * @param v			The Vector.
	 */
	public void loadVec3(int location, Vector3f v) { 							glUniform3f(location, v.x, v.y, v.z); 						}
	/**
	 * Load three floats into the shader.
	 * @param location	The Uniform Location.
	 * @param x			The first value.
	 * @param y			The second value.
	 * @param z			The third value.
	 */
	public void load3f(int location, float x, float y, float z) { 				glUniform3f(location, x, y, z); 							}
	/**
	 * Load a Vector4f into the shader.
	 * @param location	The Uniform Location.
	 * @param v			The Vector.
	 */
	public void loadVec4(int location, Vector4f v) { 							glUniform4f(location, v.x, v.y, v.z, v.w); 					}
	/**
	 * Load four floats into the shader.
	 * @param location	The Uniform Location.
	 * @param x			The first value.
	 * @param y			The second value.
	 * @param z			The third value.
	 * @param w			The fourth value.
	 */
	public void load4f(int location, float x, float y, float z, float w) { 		glUniform4f(location, x, y, z, w); 							}
	/**
	 * Load a Matrix4f into the shader.
	 * @param location	The Uniform Location.
	 * @param v			The Matrix.
	 */
	public void loadMat(int location, Matrix4f mat) { 
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer fb = stack.mallocFloat(16);
			mat.get(fb);
			glUniformMatrix4fv(location, false, fb);
		}
	}
	
	/** Use the shader program. */
	public void bind() { glUseProgram(program); }
	
	/** Detach the shader program from the context. */
	public static void unbind() { glUseProgram(0); }
	
	/** @return The location ID of the desired uniform. */
	public int getUniformLocation(String name) { 	return glGetUniformLocation(program, name); }
	/** @return The location ID of the desired shader attribute. */
	public int getAttribLocation(String name) { 	return glGetAttribLocation(program, name); 	}
	
	/** @return The shader GL program. */
	public int getProgram() { return this.program; }
	
	/**
	 * The path where the shaders are stored.
	 * @return <b>std</b> if the device supports standard OpenGL version, <b>comp</b> if not.
	 */
	public static String getShaderPath() {
		if (Graphics.supportStandardOpenGLVersion())
			return "res/shaders/std/";
		return "res/shaders/comp/";
	}
}

