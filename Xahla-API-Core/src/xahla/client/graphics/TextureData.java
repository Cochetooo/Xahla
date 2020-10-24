package xahla.client.graphics;

import java.nio.IntBuffer;

/**
 * TextureData is a record containing the dimension and the buffer for the texture.
 * 
 * @author Cochetooo
 * @version 1.1
 */
public record TextureData(int id, int width, int height, IntBuffer buffer) {
	
	/** @return The texture ID. */
	public int getID() { return id; }
	/** @return The texture width. */
	public int getWidth() { return width; }
	/** @return The texture height. */
	public int getHeight() { return height; }
	/** @return The texture buffer. */
	public IntBuffer getBuffer() { return buffer; }

}
