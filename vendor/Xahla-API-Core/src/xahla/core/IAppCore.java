package xahla.core;

/**
 * <p>
 * This interface contains the main function for the engine to work properly.<br>
 * 
 * @author Cochetooo
 * @version 1.1
 */
public interface IAppCore {
	
	/** Called <i>once</i> while the app is built, before the Context is instanciated. */
	default void awake() {}
	/** Called <i>once</i> after the Context has been instanciated, just before the game loop begins. */
	void init();
	
	/** Called after everything has been initialized. */
	default void post_init() {}
	
	/** Called each FPS tick, before the Context renders. */
	default void pre_render() {}
	/** Called each UPS tick, before the Context updates. */
	void update();
	
	/** Called each FPS tick, after the API Context has made its render call. */
	default void render() {}
	/** Called each UPS tick, after the API Context has made its logic and update call. */
	default void post_update() {}
	/** Called each FPS tick, after every objects has been rendered. */
	default void post_render() {}
	/** Called whenever the display is resized. */
	default void resize() {}
	
	/** Called each seconds within the update method. */
	default void second() {}
	
	/** Called just before the Context updates,<br>
	 * <b>only for Context that inherits from a Client-based Context class.</b> */
	default void client_update() {}
	/** Called just before the Context updates,<br>
	 * <b>only for Context that inherits from a Server-based Context class.</b> */
	default void server_update() {}
	
	/** Called when a close request is called. */
	default void dispose() {}
	/** Called just after the dispose function, when the app exits. */
	default void exit() {}

}
