package xahla.client.graphics;

import org.joml.Vector2i;

/**
 * GLFWConfiguration simplifies storage for the window data.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public record GLFWConfiguration(int resizable, int width, int height, String title) {

	/** @return True if the window allows resize. */
	public int isResizable() { return resizable; }
	/** @return The dimension of the window as a vector. */
	public Vector2i getSize() { return new Vector2i(width, height); }
	/** @return The title of the window. */
	public String getTitle() { return title; }
	
}
