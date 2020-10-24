package xahla.client.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryStack;

import xahla.utils.logger.Level;
import xahla.utils.logger.Logger;

/**
 * Class designed to handle Vertex Array Object and Vertex Buffer Object creation and drawing.
 * 
 * @author Cochetooo
 * @version 1.2
 */
public class VertexArrayObject {
	
	public int vao, vbo;
	
	private FloatBuffer buffer;
	
	private int pos, color, normal, texCoord, rowSize;
	private int positionLocation, colorLocation, normalLocation, texCoordLocation;
	
	/**
	 * @param shader	The shader that will handle the VAO.
	 * @param pos		The number of float necessary for a position point (usually 2 or 3).
	 * @param color		The number of float necessary for a color (usually 4).
	 * @param normal	The number of float necessary for a normal vector (usually 2 or 3)
	 * @param texCoord	The number of float necessary for a texture coordinate (usually 2)
	 * @param drawMode	The draw mode (see <b>GL_STATIC_DRAW</b>, <b>GL_DYNAMIC_DRAW</b> and <b>GL_STREAM_DRAW</b>
	 * @param data		The float array containing the data for the VAO.
	 */
	public VertexArrayObject(Shader shader, int pos, int color, int normal, int texCoord, int drawMode, float[] data) {
		this.vao = glGenVertexArrays();
		this.vbo = glGenBuffers();
		
		this.pos = pos;
		this.color = color;
		this.normal = normal;
		this.texCoord = texCoord;
		
		this.rowSize = pos + color + normal + texCoord;
		
		this.positionLocation = shader.getAttribLocation("in_position");
		this.colorLocation	  = shader.getAttribLocation("in_color");
		this.normalLocation   = shader.getAttribLocation("in_normal");
		this.texCoordLocation = shader.getAttribLocation("in_texCoord");
		
		if (this.buffer == null) {
			try (MemoryStack stack = MemoryStack.stackPush()) {
				buffer = stack.floats(data);
				update(GL_STATIC_DRAW);
			}
		}
	}
	
	/**
	 * Put data into the VBO as well as allocating data into the attributes.<br>
	 * <b>Might be slow.</b>
	 * @param drawMode		The draw mode (see <b>GL_STATIC_DRAW</b>, <b>GL_DYNAMIC_DRAW</b> and <b>GL_STREAM_DRAW</b>
	 */
	public void update(int drawMode) {
		glBindVertexArray(vao);
		
		if (pos > 0)
			glEnableVertexAttribArray(positionLocation);
			
		if (color > 0)
			glEnableVertexAttribArray(colorLocation);
			
		if (normal > 0)
			glEnableVertexAttribArray(normalLocation);
			
		if (texCoord > 0)
			glEnableVertexAttribArray(texCoordLocation);
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, drawMode);
		
		int size = 0;
		
		if (pos > 0) {
			glVertexAttribPointer(positionLocation, pos, GL_FLOAT, false, rowSize * 4, size);
			size += pos;
		}
		
		if (color > 0) {
			glVertexAttribPointer(colorLocation, color, GL_FLOAT, false, rowSize * 4, size * 4);
			size += color;
		}
		
		if (normal > 0) {
			glVertexAttribPointer(normalLocation, normal, GL_FLOAT, false, rowSize * 4, size * 4);
			size += normal;
		}
		
		if (texCoord > 0) {
			glVertexAttribPointer(texCoordLocation, texCoord, GL_FLOAT, false, rowSize * 4, size * 4);
			size += texCoord;
		}
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		
		buffer.clear();
		
		Logger.log(Level.FINER, "VAO updated (pos=" + positionLocation + " color=" + colorLocation + " normalLocation=" + normalLocation + " texCoordLocation=" + texCoordLocation + ")");
		Logger.log(Level.FINEST, "Buffer size: " + rowSize * 4);
	}
	
	/**
	 * Update the buffer data in an efficient way.
	 * @see {@link org.lwjgl.opengl.GL15#glBufferSubData}
	 * @param data		The float array containing the data for the VAO.
	 */
	public void subData(float[] data) {
		buffer.clear();
		try (MemoryStack stack = MemoryStack.stackPush()) {
			buffer = stack.floats(data);
		}
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	/** Draw the VAO. */
	public void render() {
		glBindVertexArray(vao);
		glDrawArrays(GL_QUADS, 0, rowSize * 4);
		glBindVertexArray(0);
	}
	
	/** Dispose and clear VAO and VBO. */
	public void dispose() {
		glDeleteVertexArrays(vao);
		glDeleteBuffers(vbo);
		
		if (pos > 0)
			glDisableVertexAttribArray(positionLocation);
		if (color > 0)
			glDisableVertexAttribArray(colorLocation);
		if (normal > 0)
			glDisableVertexAttribArray(normalLocation);
		if (texCoord > 0)
			glDisableVertexAttribArray(texCoordLocation);
	}
	
	/** @return The Float Buffer containing the data. <i>(might be empty if an update has been called)</i>. */
	public FloatBuffer getBuffer() { return buffer; }
}
