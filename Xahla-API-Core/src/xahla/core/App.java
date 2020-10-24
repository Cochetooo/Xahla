package xahla.core;

import java.lang.reflect.InvocationTargetException;

import xahla.context.ClientContext;
import xahla.utils.math.Timer;

/**
 * <p>
 * This class is the core app for the API engine.<br>
 * It manages the Context loop, updates and rendering.<br>
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class App {
	
	private static App _instance;
	
	private IAppCore app;
	private boolean running;
	private int fps, ups, tick, frame;
	
	private double tickTime, renderTime;
	
	private Context Context;
	
	App(Class<? extends Context> program_type, IAppCore pApp) {
		this.app = pApp;
		app.awake();
		
		if (program_type != null)
			try {
				Context = program_type.getConstructor(App.class).newInstance(this);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.getCause().printStackTrace();
			}
	}
	
	/**
	 * Instanciate the program application.
	 * @param program_type	The context type (client, server, generic, customs...)
	 * @param fps			The framerate of the Window.
	 * @param ups			The update rate of the Program.
	 * @param app			The class that implements the Application interface.
	 */
	public static void build(Class<? extends Context> program_type, int fps, int ups, IAppCore app) {
		_instance = new App(program_type, app);
		_instance.tick = ups;
		_instance.frame = fps;
	}
	
	void start() {
		app.init();
		
		if (Context instanceof ClientContext cp)
			cp.post_init();
		
		running = true;
		
		int frames = 0, ticks = 0;
		tickTime = 1_000_000_000.0 / tick;
		renderTime = 1_000_000_000.0 / frame;
		double renderedTick = 0.0, updatedTick = 0.0;
		int secondTime = 0;
		
		Timer timer = new Timer();
		
		while (running) {
			if (timer.elapsed_time() - updatedTick >= tickTime) {
				update();
				
				ticks++;
				secondTime++;
				
				if (secondTime % tick == 0) {
					secondTime = 0;
					fps = frames;
					ups = ticks;
					
					app.second();
					
					frames = 0;
					ticks = 0;
				}
				
				updatedTick += tickTime;
			} else if (timer.elapsed_time() - renderedTick >= renderTime) {
				render();
				frames++;
				
				renderedTick += renderTime;
			} else {
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					Context.getExceptionHandler().reportException(e, "Unable to make the current thread sleep within the main loop.");
				}
			}
		}
		
		app.exit();
	}
	
	private void update() {
		app.update();
		
		Context.update();
		app.post_update();
	}
	
	private void render() {
		app.pre_render();
		Context.render();
		app.render();
		
		if (Context instanceof ClientContext cp)
			cp.post_render();
	}
	
	void dispose() {
		Context.dispose();
		app.dispose();
		
		running = false;
	}
	
	static App instance() { return _instance; }
	Context getProgram() { return Context; }
	
	int getFPS() { return fps; }
	int getUPS() { return ups; }
	
	void setFramerate(int nFps) { 
		this.frame = nFps;
		this.renderTime = 1_000_000_000.0 / frame;
	}

}
