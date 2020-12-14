package xahla.client.graphics;

import org.joml.Vector2i;

/**
 * GLFWConfiguration contains the initial settings for the window's property.
 * 
 * @author Cochetooo
 * @version 1.1
 */
public record GLFWConfiguration(int resizable, int fullscreen, int width, int height, String title, int colorBufferBits, int floating, int decoration, int msaa, int centerCursor, int vsync) {

	/** @return The dimension of the window as a vector. */
	public Vector2i getSize() { return new Vector2i(width, height); }
	
}
