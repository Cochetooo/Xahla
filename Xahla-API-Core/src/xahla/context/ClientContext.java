package xahla.context;

import org.joml.Vector4f;

import xahla.client.graphics.Graphics;
import xahla.client.graphics.Shader;
import xahla.client.graphics.Window;
import xahla.context.objects.*;
import xahla.core.App;
import xahla.core.Config;
import xahla.core.Context;
import xahla.core.XObject;
import xahla.utils.logger.Level;
import xahla.utils.logger.Logger;

/**
 * The Client Context is the generic client-sided program provided by the API.<br>
 * It consists of a Window and an Input Handler.<br>
 * It can be overriden to handle other components.
 * 
 * @author Cochetooo
 * @version 1.1
 */
public class ClientContext extends Context {

	private Window window;
	private Shader shader;
	
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
		
		if (projection.equals("3d")) {
			this.getConfigs().add(new Config(this, "perspective.json", "Perspective"));
			this.add(new PerspectiveCamera("MainCamera", this));
		} else if (projection.equals("2d")) {
			this.getConfigs().add(new Config(this, "orthographic.json", "Orthographic"));
			this.add(new OrthographicCamera("MainCamera", this));
		}
		
		Graphics.initGL(this);
	}
	
	/**
	 * Used for shader initialization.
	 */
	@Override
	public void post_init() {	
		this.shader = new Shader(this, "world", false);
		
		objects.forEach((o)->o.post_init());
	}
	
	/**
	 * Update the program and the window after.
	 */
	@Override
	public void update() {
		super.update();
		window.update();
	}

	/**
	 * Render every entity objects of the game.<br>
	 * Objects that are not visible will be ignored,
	 * and detached objects are rendered prior to other objects.
	 */
	@Override
	public void render() {
		for (XObject obj : objects) {
			if (obj instanceof EntityObject eObj) {
				if (eObj.isVisible())
					if (eObj.isDetached()) {
						eObj.render();
					}
			}
		}
		
		shader.bind();
		shader.loadMat(shader.getUniformLocation("projectionMatrix"), ((Camera) this.getObjectByName("MainCamera")).projection().getProjection());
		
		int i = 0;
		for (XObject obj : this.objects) {
			if (obj instanceof DirectionalLight light) {
				if (i == 10) {
					Logger.log(Level.WARNING, "Directional Light numbers exceeded: 10");
					break;
				}
				
				shader.loadVec4(shader.getUniformLocation("directionalLight" + i), new Vector4f(
					light.getLightPosition().x,
					light.getLightPosition().y,
					light.getLightPosition().z,
					light.getStrength()
				));
				i++;
			}
		}
		
		for (XObject obj : objects) {
			if (obj instanceof EntityObject eObj) {
				if (eObj.isVisible())
					if (!eObj.isDetached())
						eObj.render();
			}
		}
		
		Shader.unbind();
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
	
	/** @return The global shader of the world. */
	public Shader getWorldShader() { return shader; }
}
