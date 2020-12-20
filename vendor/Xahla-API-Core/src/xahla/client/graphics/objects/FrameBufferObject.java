package xahla.client.graphics.objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.joml.Vector2i;
import org.lwjgl.system.MemoryUtil;

import xahla.client.graphics.Texture;
import xahla.client.graphics.TextureLoader;

/**
 * A framebuffer is a portion of random-access memory containing a bitmap that drives a video display.<br>
 * It is a memory buffer containing data representing all the pixels in a complete video frame.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class FrameBufferObject {
	
	private int fbo;
	private Texture texture;
	private BufferType bufferType;
	
	/**
	 * Instanciate a new Framebuffer as a Texture 2D.
	 * @param dimension		The dimension of the texture.
	 * @param bufferType	The type of FBO: color, depth, stencil or depth & stencil.
	 * @param filter		The texture filter (usually <b>GL_NEAREST</b> or <b>GL_LINEAR</b>).
	 */
	public FrameBufferObject(Vector2i dimension, BufferType bufferType, int filter) {
		this.fbo = glGenFramebuffers();
		this.bufferType = bufferType;
		
		bind();
		
		texture = TextureLoader.loadFramebufferTexture(dimension, bufferType, filter);
		Texture.unbind();
	}
	
	/**
	 * Take a snapshot of the screen, and saves it in a PPM file.
	 * @param path	The path to the saved file.
	 */
	public void saveToFile(String path) {
		ByteBuffer pixels = MemoryUtil.memAlloc(texture.getWidth() * texture.getHeight() * 3);
		
		glReadBuffer(bufferType.getAttachment());
		glReadPixels(0, 0, texture.getWidth(), texture.getHeight(), bufferType.getFormat(), bufferType.getType(), pixels);
		
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8)) {
			writer.write("P3\n# Screenshot.\n");
			writer.write(texture.getWidth() + " " + texture.getHeight() + "\n");
			writer.write("255\n");
			
			int c = texture.getWidth() * texture.getHeight() * 3 - 3;
			for (int i = 0; i < texture.getWidth(); i++) {
				for (int j = 0; j < texture.getHeight(); j++) {
					writer.write(
						(pixels.get(c) & 0xff) + " " + (pixels.get(c+1) & 0xff) + " " + (pixels.get(c+2) & 0xff) + " "
					);
					c -= 3;
				}
				writer.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MemoryUtil.memFree(pixels);
	}
	
	/**
	 * Bind the frame buffer object to the GPU.
	 */
	public void bind() {
		Texture.unbind();
		glBindFramebuffer(GL_FRAMEBUFFER, fbo);
	}
	
	/**
	 * Set the active frame buffer to the default one.
	 */
	public static void unbind() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		Texture.unbind();
	}
	
	/**
	 * Clear the frame buffer object from the memory.
	 */
	public void dispose() {
		glDeleteFramebuffers(fbo);
	}
	
	/**
	 * Set the framebuffer instance as the default framebuffer.<br>
	 * Rendering commands will have direct impact on the visual output of the window.
	 */
	public void setFramebufferAsDefault() {
		this.fbo = 0;
	}
	
	/** @return The texture which contains the framebuffer. */
	public Texture getTexture() { return texture; }
	
	/**
	 * The type of Framebuffer.
	 * 
	 * @author Cochetooo
	 * @version 1.0
	 */
	public enum BufferType {
		/** Color buffer. */
		COLOR(GL_RGB, GL_RGB, GL_UNSIGNED_BYTE, GL_COLOR_ATTACHMENT0), 
		/** Stencil buffer. */
		STENCIL(GL_STENCIL_INDEX16, GL_STENCIL, GL_FLOAT, GL_STENCIL_ATTACHMENT), 
		/** Depth buffer. */
		DEPTH(GL_DEPTH_COMPONENT32, GL_DEPTH_COMPONENT, GL_FLOAT, GL_DEPTH_ATTACHMENT), 
		/** Depth (24 bits) and Stencil (8 bits) buffer. */
		DEPTH_STENCIL(GL_DEPTH24_STENCIL8, GL_DEPTH_STENCIL, GL_UNSIGNED_INT_24_8, GL_DEPTH_STENCIL_ATTACHMENT);
		
		private final int internalFormat, format, type, attachment;
		
		private BufferType(int internalFormat, int format, int type, int attachment) {
			this.internalFormat = internalFormat;
			this.format = format;
			this.type = type;
			this.attachment = attachment;
		}
		
		public int getInternalFormat() { return internalFormat; }
		public int getFormat() { return format; }
		public int getType() { return type; }
		public int getAttachment() { return attachment; }
	}

}
