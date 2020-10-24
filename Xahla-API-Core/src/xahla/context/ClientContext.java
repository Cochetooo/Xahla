package xahla.context;

import xahla.client.graphics.Graphics;
import xahla.client.graphics.Window;
import xahla.context.objects.OrthographicCamera;
import xahla.context.objects.PerspectiveCamera;
import xahla.core.App;
import xahla.core.Config;
import xahla.core.Context;

/**
 * The Client Context is the generic client-sided program provided by the API.<br>
 * It consists of a Window and an Input Handler.<br>
 * It can be overriden to handle other components.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class ClientContext extends Context {

	private Window window;
	
	/**
	 * @param app	The Main Application of the API.
	 */
	public ClientContext(App app) {
		super(app);
	}
	
	/**
	 * Initialize the Context, the Camera and the Window.<br>
	 * All projection and window settings are stored in JSON files and set in Configuration objects.
	 * <ul>
	 * 	<li>rendering.json → All data about the projection and the window.</li>
	 * 	<li>input.json → All data about the input handling.</li>
	 * </ul>
	 */
	@Override
	public void init() {
		super.init();
	
		this.getConfigs().add(new Config(this, "rendering.json", "Rendering"));
		this.getConfigs().add(new Config(this, "input.json", "Input"));
		
		String projection = this.getConfigString("Rendering", "projection");
		
		this.window = new Window(this);
		window.init();
		window.changeTitle(this.getConfigString("Rendering", "window_title"));
		
		window.setVSync(this.getConfigBool("Rendering", "vsync"));
		
		if (projection.equals("perspective")) {
			this.getConfigs().add(new Config(this, "perspective.json", "Perspective"));
			this.add(new PerspectiveCamera("MainCamera", this));
		} else if (projection.equals("orthographic")) {
			this.getConfigs().add(new Config(this, "orthographic.json", "Orthographic"));
			this.add(new OrthographicCamera("MainCamera", this));
		}
		
		Graphics.initGL(this);
	}
	
	/**
	 * Unused but here for shader initialization.
	 */
	@Override
	public void post_init() {
		// Shader initialization
	}
	
	/**
	 * Update the program and the window after.
	 */
	@Override
	public void update() {
		super.update();
		window.update();
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	/**
	 * Swap the buffer of the window.
	 */
	public void post_render() {
		window.render();
	}
	
	/**
	 * Quit the window and the program.
	 */
	@Override
	public void dispose() {
		window.dispose();
		super.dispose();
	}
	
	/** @return The current context. */
	public static ClientContext instance() { return (ClientContext) Context.instance(); }
	
	/** @return The GLFW window instance. */
	public final Window getWindow() { return window; }
}
