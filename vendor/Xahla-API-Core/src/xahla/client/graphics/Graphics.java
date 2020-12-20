package xahla.client.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;
import static org.lwjgl.opengl.GL30.GL_MAX_COLOR_ATTACHMENTS;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL12;

import xahla.context.ClientContext;
import xahla.utils.ValidationException;
import xahla.utils.logger.Level;
import xahla.utils.logger.Logger;

/**
 * Graphics contains a set of utils methods for rendering.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class Graphics {
	
	private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final Vector2f screenDimension = new Vector2f(screenSize.width, screenSize.height);
	
	private static boolean DX11;
	private static int msaa = 0;
	private static ClientContext Context;
	
	/** @return The display screen width and height as a Vector2f. */
	public static final Vector2f screenDimension() { return screenDimension; }
	
	/** Initialize OpenGL standard functions. */
	public static void initGL(ClientContext context) {
		setStandardOpenGLVersion(context);
		Context = context;
		
		setMSAA(context.getWindow().getConfiguration().msaa());
		
		glEnable(GL_TEXTURE_2D);
		if (context.getConfigString("Rendering", "projection").equals("3d"))
			glEnable(GL12.GL_TEXTURE_3D);
			
		Logger.log(Level.CONFIG, "VSync: " + context.getConfigBool("Rendering", "vsync"));
		Logger.log(Level.CONFIG, "Has Modern OpenGL Support: " + Graphics.supportStandardOpenGLVersion() + " (" + glGetString(GL_VERSION) + ")");
		Logger.log(Level.CONFIG, "Texture Enabled.");
		Logger.log(Level.FINEST, "Max Color Attachments: " + glGetInteger(GL_MAX_COLOR_ATTACHMENTS));
	}
	
	/**
	 * Determine if the device supports the defined standard OpenGL version (set in <b>rendering.json</b>).
	 * @return The result as a boolean.
	 */
	public static boolean setStandardOpenGLVersion(ClientContext Context) {
		String glVersionString = glGetString(GL_VERSION).split(" ")[0].trim();
		float glVersion = Float.parseFloat(glVersionString.substring(0, 3));
		
		if (glVersion < Context.getConfigDouble("Rendering", "standard_opengl_version"))
			return DX11 = false;
		
		return DX11 = true;
	}
	
	/**
	 * Change the number of samples for the Multisampling Aliasing effect.
	 * @param level	The number of samples, must be an integer between 0 and 3.
	 */
	public static int setMSAA(int level) {
		if (level < 0 || level > 3)
			Context.getExceptionHandler().reportException(new ValidationException("MSAA level must be between 0 and 3."));
		
		msaa = level;
		
		if (level == 0)
			glDisable(GL_MULTISAMPLE);
		
		glEnable(GL_MULTISAMPLE);
		
		return level;
	}
	
	/** @return True if the device supports the defined standard OpenGL version (set in <b>rendering.json</b>). */
	public static final boolean supportStandardOpenGLVersion() { return DX11; }
	
	/**
	 * @return The level of Multisample Aliasing.<br>
	 * <ul>
	 * <li>0 corresponds to no MSAA</li>
	 * <li>1 corresponds to 2x MSAA</li>
	 * <li>2 corresponds to 4x MSAA</li>
	 * <li>3 corresponds to 8x MSAA</li>
	 * </ul>
	 */
	public static int getMSAALevel() { return msaa; }
	
}
