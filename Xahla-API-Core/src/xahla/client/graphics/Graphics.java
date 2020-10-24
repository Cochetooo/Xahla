package xahla.client.graphics;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGetString;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL20;

import xahla.context.ClientContext;
import xahla.context.objects.PerspectiveCamera;
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
	
	/** @return The display screen width and height as a Vector2f. */
	public static final Vector2f screenDimension() { return screenDimension; }
	
	/** Initialize OpenGL standard functions. */
	public static void initGL(ClientContext context) {
		setStandardOpenGLVersion(context);
		
		glEnable(GL_TEXTURE_2D);
		if (context.getObjectByName("MainCamera") instanceof PerspectiveCamera)
			glEnable(GL12.GL_TEXTURE_3D);
			
		Logger.log(Level.CONFIG, "VSync: " + context.getConfigBool("Rendering", "vsync"));
		Logger.log(Level.CONFIG, "Has Modern OpenGL Support: " + Graphics.supportStandardOpenGLVersion() + " (" + glGetString(GL_VERSION) + ")");
		Logger.log(Level.CONFIG, "GL Shading Language Version Support: " + glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
		Logger.log(Level.CONFIG, "Texture Enabled.");
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
	 * @return True if the device supports the defined standard OpenGL version (set in <b>rendering.json</b>).
	 */
	public static final boolean supportStandardOpenGLVersion() { return DX11; }
	
}
