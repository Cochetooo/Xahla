package xahla.client.graphics;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import xahla.client.input.Input;
import xahla.context.ClientContext;
import xahla.core.IAppCore;

/**
 * This class handles GLFW.
 * It display a window and manage user input.
 * 
 * @author Cochetooo
 * @version 1.1
 */
public class Window implements IAppCore {
	
	private long window;
	private ClientContext Context;
	
	private GLFWConfiguration config;
	
	private Vector2i windowSize;
	
	/**
	 * @param Context 	The program context.
	 */
	public Window(ClientContext Context) {
		this.Context = Context;
		
		this.windowSize = new Vector2i(
			Context.getConfigInt("Rendering", "initial_width"),
			Context.getConfigInt("Rendering", "initial_height")
			);
		
		boolean resizable = Context.getConfigBool("Rendering", "resizable");
		
		this.config = new GLFWConfiguration(resizable ? GLFW_TRUE : GLFW_FALSE, windowSize.x, windowSize.y, "Kina Siern 080920");
	}

	/** Create the window and set callback for input. */
	@Override
	public void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		
		glfwWindowHint(GLFW_RESIZABLE, config.isResizable());
		
		Vector2i size = config.getSize();
		window = glfwCreateWindow(size.x, size.y, config.getTitle(), NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			
			glfwGetWindowSize(window, pWidth, pHeight);
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		}
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		Input.init(window);
		
		glfwSetKeyCallback(window, Input.keyboard);
		glfwSetMouseButtonCallback(window, Input.mouse);
		
		GL.createCapabilities();
		glClearColor(0.f, .0f, .0f, .0f);
		
		GL11.glViewport(0, 0, windowSize.x, windowSize.y);
	}
	
	/** Update the window and the input. */
	@Override
	public void update() {
		if (glfwWindowShouldClose(window))
			Context.request("dispose");
		
		Input.update();
	}
	
	/** Swap display buffers. */
	@Override
	public void render() {
		glfwSwapBuffers(window);
	}
	
	/** Free callbacks and destroy the window. */
	@Override
	public void dispose() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	/** @param b Set display vertical synchronisation. */
	public void setVSync(boolean b) {
		glfwSwapInterval(b ? 1 : 0);
	}
	
	/** @param title Set the window title. */
	public void changeTitle(String title) {
		glfwSetWindowTitle(window, title);
	}
	
	/** @return The Window dimension as a Vector. */
	public final Vector2i getWindowDimension() {
		IntBuffer w, h;
		
		try (MemoryStack stack = stackPush()) {
			w = BufferUtils.createIntBuffer(1);
			h = BufferUtils.createIntBuffer(1);
			glfwGetWindowSize(window, w, h);
			
			return windowSize.set(w.get(0), h.get(0));
		}
	}
	
	/** @return The Window properties such as width, height, etc. */
	public final GLFWConfiguration getConfiguration() { return config; }
}
