package xahla.client.graphics;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.joml.Vector2i;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
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
		boolean fullscreen = Context.getConfigBool("Rendering", "fullscreen");
		boolean floating = Context.getConfigBool("Rendering", "floating");
		boolean centerCursor = Context.getConfigBool("Rendering", "center_cursor_on_startup");
		boolean decoration = Context.getConfigBool("Rendering", "decoration");
		boolean vsync = Context.getConfigBool("Rendering", "vsync");
		
		int colorBufferBits = Context.getConfigInt("Rendering", "color_buffer_bits");
		int msaa = Context.getConfigInt("Rendering", "msaa");
		
		String title = Context.getConfigString("Rendering", "window_title");
		
		this.config = new GLFWConfiguration(
			resizable ? GLFW_TRUE : GLFW_FALSE, 
			fullscreen ? GLFW_TRUE : GLFW_FALSE,
			windowSize.x, windowSize.y, 
			title,
			colorBufferBits,
			floating ? GLFW_TRUE : GLFW_FALSE,
			decoration ? GLFW_TRUE : GLFW_FALSE,
			msaa,
			centerCursor ? GLFW_TRUE : GLFW_FALSE,
			vsync ? GLFW_TRUE : GLFW_FALSE
		);
	}

	/** Create the window and set callback for input. */
	@Override
	public void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, config.resizable());
		glfwWindowHint(GLFW_DECORATED, config.decoration());
		glfwWindowHint(GLFW_CENTER_CURSOR, config.centerCursor());
		glfwWindowHint(GLFW_FLOATING, config.floating());
		
		glfwWindowHint(GLFW_RED_BITS, config.colorBufferBits() / 4);
		glfwWindowHint(GLFW_GREEN_BITS, config.colorBufferBits() / 4);
		glfwWindowHint(GLFW_BLUE_BITS, config.colorBufferBits() / 4);
		glfwWindowHint(GLFW_ALPHA_BITS, config.colorBufferBits() / 4);
		
		if (config.msaa() > 0)
			glfwWindowHint(GLFW_SAMPLES, (int) (Math.pow(2, config.msaa())));
		
		Vector2i size = config.getSize();
		window = glfwCreateWindow(size.x, size.y, config.title(), NULL, NULL);
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
		
		glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
			@Override
			public void invoke(long window, int width, int height) {
				resize();
			}
		});
		
		GL.createCapabilities();
		glClearColor(0.f, .0f, .0f, .0f);
		
		GL11.glViewport(0, 0, windowSize.x, windowSize.y);
		
		this.setVSync(config.vsync());
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
	
	/** Update viewport on resize. */
	@Override
	public void resize() {
		windowSize = getWindowDimension();
		GL11.glViewport(0, 0, windowSize.x, windowSize.y);
		
		Context.resize();
	}
	
	/** Free callbacks and destroy the window. */
	@Override
	public void dispose() {
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	/**
	 * Change the cursor mode.
	 * @param mode	Can be one of the following:<br>
	 * <ul>
	 * 	<li><b>GLFW_CURSOR_NORMAL</b> makes the cursor visible and behaving normally.</li>
	 *  <li><b>GLFW_CURSOR_HIDDEN</b> makes the cursor invisible when it is over the content area of the window but does not restrict the cursor from leaving.</li>
	 *  <li><b>GLFW_CURSOR_DISABLED</b> hides and grabs the cursor, providing virtual and unlimited cursor movement. This is useful for implementing for
	 *  example 3D camera controls.</li>
	 * </ul>
	 */
	public void setCursorMode(int mode) {
		glfwSetInputMode(window, GLFW_CURSOR, mode);
	}
	
	/** @param b Set display vertical synchronisation. */
	public void setVSync(int b) {
		glfwSwapInterval(b);
	}
	
	/** @param title Set the window title. */
	public void changeTitle(String title) {
		glfwSetWindowTitle(window, title);
	}
	
	/** @return The Window dimension as a Vector. */
	public final Vector2i getWindowDimension() {
		IntBuffer w, h;
		
		try (MemoryStack stack = stackPush()) {
			w = stack.mallocInt(1);
			h = stack.mallocInt(1);
			glfwGetWindowSize(window, w, h);
			
			return windowSize.set(w.get(0), h.get(0));
		}
	}
	
	/** @return The Window properties such as width, height, etc. */
	public final GLFWConfiguration getConfiguration() { return config; }
}
