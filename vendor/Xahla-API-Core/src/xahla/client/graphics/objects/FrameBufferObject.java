package xahla.client.graphics.objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;

import xahla.client.graphics.Texture;
import xahla.client.graphics.Window;

/**
 * A framebuffer is a portion of random-access memory containing a bitmap that drives a video display.<br>
 * It is a memory buffer containing data representing all the pixels in a complete video frame.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class FrameBufferObject {
	
	private int fbo, texture;
	
	/**
	 * Instanciate a new Framebuffer as a Texture 2D.
	 * @param window		The window that contains the dimension.
	 * @param bufferType	The type of FBO: color, depth, stencil or depth & stencil.
	 * @param filter		The texture filter (usually <b>GL_NEAREST</b> or <b>GL_LINEAR</b>).
	 */
	public FrameBufferObject(Window window, BufferType bufferType, int filter) {
		this.fbo = glGenFramebuffers();
		
		bind();
		
		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		
		glTexImage2D(GL_TEXTURE_2D, 0, bufferType.getInternalFormat(), 
				window.getWindowDimension().x, window.getWindowDimension().y, 
				0, bufferType.getFormat(), bufferType.getType(), (ByteBuffer) null);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
		
		glFramebufferTexture2D(GL_FRAMEBUFFER, bufferType.getAttachment(), GL_TEXTURE_2D, texture, 0);
		
		Texture.unbind();
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
	public int getTexture() { return texture; }
	
	public enum BufferType {
		COLOR(GL_RGB, GL_RGB, GL_UNSIGNED_BYTE, GL_COLOR_ATTACHMENT0), 
		STENCIL(GL_STENCIL_INDEX16, GL_STENCIL, GL_FLOAT, GL_STENCIL_ATTACHMENT), 
		DEPTH(GL_DEPTH_COMPONENT32, GL_DEPTH_COMPONENT, GL_FLOAT, GL_DEPTH_ATTACHMENT), 
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
