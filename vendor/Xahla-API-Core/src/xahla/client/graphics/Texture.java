package xahla.client.graphics;

import static org.lwjgl.opengl.GL11.*;

/**
 * A Texture contains an ID, a width and a height.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class Texture {
	
	private int id;
	private int width, height;
	private boolean mipmap;
	
	/**
	 * @param id		The ID.
	 * @param width		The Width.
	 * @param height	The Height.
	 */
	public Texture(int id, int width, int height) {
		this.id = id;
		this.width = width;
		this.height = height;
	}
	
	/** Enable the texture to the OpenGL drawing context. */
	public void bind() { glBindTexture(GL_TEXTURE_2D, id); }
	/** Unbind the previous texture from the OpenGL drawing context. */
	public static void unbind() { glBindTexture(GL_TEXTURE_2D, 0); }
	
	/** @return The width of the texture. */
	public int getWidth() { return width; }
	/** @return The height of the texture. */
	public int getHeight() { return height; }
	
	/** @return True if the texture has mipmapping. */
	public boolean hasMipmap() { return mipmap; }
	void setMipmap(boolean b) { this.mipmap = b; }

}
