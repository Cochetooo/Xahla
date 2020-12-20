package xahla.client.graphics.objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;
import static org.lwjgl.opengl.GL30.glCheckFramebufferStatus;

import xahla.client.graphics.Shader;
import xahla.client.graphics.Texture;
import xahla.context.ClientContext;
import xahla.core.Context;
import xahla.utils.ValidationException;

/**
 * The snapshot buffer contains a Framebuffer and Renderbuffer that contains information about
 * the color, stencil and depth of a specific object.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class SnapshotBuffer {
	
	private FrameBufferObject fbo;
	private RenderBufferObject rbo;
	private VertexArrayObject vao;
	private Shader shader;

	public SnapshotBuffer(Shader shader, ClientContext context) {
		this.fbo = new FrameBufferObject(context.getWindow().getWindowDimension(), FrameBufferObject.BufferType.COLOR, GL_LINEAR);
		this.rbo = new RenderBufferObject(context.getWindow(), RenderBufferObject.BufferType.DEPTH_STENCIL);
		rbo.use();
		FrameBufferObject.unbind();
		
		this.vao = new VertexArrayObject(shader, 
				2, 
				0, 
				0, 
				2,
				GL_DYNAMIC_DRAW,
				4,
				new float[] {
						-1, -1, 0, 0,
						 1, -1, 1, 0,
						 1,  1, 1, 1,
						-1,  1, 0, 1
				});
		this.shader = shader;
	}
	
	/**
	 * Take a snapshot of the screen, and saves it in a PPM file.
	 * @param path	The path to the saved file.
	 */
	public void screenshot(String path) {
		this.fbo.saveToFile(path + ".ppm");
	}
	
	public void pre_render() {
		fbo.bind();
		
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
			Context.instance().getExceptionHandler().reportException(new ValidationException("Framebuffer is not complete!"));
	}
	
	public void post_render() {
		FrameBufferObject.unbind();
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		shader.bind();
		
		fbo.getTexture().bind();
		vao.render();
		
		Texture.unbind();
		Shader.unbind();
	}
	
	public void dispose() {
		fbo.dispose();
		rbo.dispose();
		vao.dispose();
	}
}
