package xahla.client.graphics.objects;

import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.*;

import xahla.client.graphics.Window;

public class RenderBufferObject {
	
	private int rbo;
	private Window window;
	private BufferType bufferType;
	
	public RenderBufferObject(Window window, BufferType bufferType) {
		this.rbo = glGenRenderbuffers();
		this.bufferType = bufferType;
		this.window = window;
	}
	
	public void use() {
		glBindRenderbuffer(GL_RENDERBUFFER, rbo);
		
		glRenderbufferStorage(GL_RENDERBUFFER, bufferType.getInternalFormat(),
				window.getWindowDimension().x, window.getWindowDimension().y);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, bufferType.getAttachment(), GL_RENDERBUFFER, rbo);
		
		glBindRenderbuffer(GL_RENDERBUFFER, 0);
	}
	
	public void dispose() {
		glDeleteRenderbuffers(rbo);
	}
	
	public enum BufferType {
		COLOR(GL_RGB, GL_COLOR_ATTACHMENT0), 
		STENCIL(GL_STENCIL_INDEX16, GL_STENCIL_ATTACHMENT), 
		DEPTH(GL_DEPTH_COMPONENT32, GL_DEPTH_ATTACHMENT), 
		DEPTH_STENCIL(GL_DEPTH24_STENCIL8, GL_DEPTH_STENCIL_ATTACHMENT);
		
		private final int internalFormat, attachment;
		
		private BufferType(int internalFormat, int attachment) {
			this.internalFormat = internalFormat;
			this.attachment = attachment;
		}
		
		public int getInternalFormat() { return internalFormat; }
		public int getAttachment() { return attachment; }
	}

}
