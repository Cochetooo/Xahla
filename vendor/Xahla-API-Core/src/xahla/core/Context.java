package xahla.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import xahla.utils.ValidationException;
import xahla.utils.math.Time;
import xahla.utils.system.StringUtils;

/**
 * The Context contains all data about the program.<br>
 * 
 * @author Cochetooo
 * @version 1.1
 */
public class Context implements IAppCore {
	
	private App app;
	private ExceptionHandler exceptionHandler;
	
	private List<Config> configs;
	protected List<XObject> objects;
	
	private Time elapsedTime;
	
	/**
	 * @param app	The Main Application of the API.
	 */
	public Context(App app) {
		this.app = app;
		
		this.init();
	}

	/** Initialize the configurations and objects collection, as well as the exception handler.  */
	@Override
	public void init() {
		this.configs = new ArrayList<>();
		this.objects = new ArrayList<>();
		
		this.exceptionHandler = new ExceptionHandler(this);
		this.elapsedTime = new Time();
	}

	/** Update each objects. */
	@Override
	public void update() {
		objects.forEach((o) -> o.update());
	}
	
	@Override
	public void post_update() {
		objects.forEach((o) -> o.post_update());
	}
	
	/** Call second() to each objects. */
	@Override
	public void second() {
		objects.forEach((o) -> o.second());
	}
	
	/** Dispose all the objects and save the configurations. */
	@Override
	public void dispose() {
		objects.forEach((o) -> o.dispose());
		
		for (Config cfg : configs)
			cfg.save();
	}
	
	/** Called whenever the window is resized. */
	@Override
	public void resize() {
		app.resize();
		
		objects.forEach((o) -> o.resize());
	}
	
	@Override
	public void pre_render() {
		objects.forEach((o) -> o.pre_render());
	}
	
	/** Render all objects. */
	@Override
	public void render() {
		objects.forEach((o) -> o.render());
	}
	
	@Override
	public void post_render() {
		app.post_render();
		
		objects.forEach((o) -> o.post_render());
	}
	
	/** Request to perform a low-level API task.<br>
	 * 	Availables are: 
	 * <ul>
	 * 	<li>start → Start the program.</li>
	 * 	<li>dispose → Quit the program.</li>
	 * </ul>
	 */
	public void request(String action) {
		switch (action) {
		case "start" -> app.start();
		case "dispose" -> app.dispose();
		default -> throw new ValidationException("The requested method is not valid.");
		}
	}
	
	/**
	 * @param title	The name of the configuration.
	 * @param key	The name of the property.
	 * @return 		The value as an int.
	 * @throws 		ValidationException If the value is not an Integer.
	 */
	public int getConfigInt(String title, String key) {
		if (getConfigProperty(title, key) instanceof Integer i)
			return i;
		
		throw new ValidationException("Value is not an integer.");
	}
	
	/**
	 * @param title	The name of the configuration.
	 * @param key	The name of the property.
	 * @return 		The value as a long int.
	 * @throws 		ValidationException If the value is not a long or an integer.
	 */
	public long getConfigLong(String title, String key) {
		if (getConfigProperty(title, key) instanceof Long l)
			return l;
		
		if (getConfigProperty(title, key) instanceof Integer i)
			return (long) i;
		
		throw new ValidationException("Value is not an integer.");
	}
	
	/**
	 * @param title	The name of the configuration.
	 * @param key	The name of the property.
	 * @return 		The value as a float.
	 * @throws 		ValidationException If the value is not a float or a int.
	 */
	public float getConfigFloat(String title, String key) {
		if (getConfigProperty(title, key) instanceof Float f)
			return f;
		
		throw new ValidationException("Value is not an integer.");
	}
	
	/**
	 * @param title	The name of the configuration.
	 * @param key	The name of the property.
	 * @return 		The value as a double.
	 * @throws 		ValidationException If the value is not a number.
	 */
	public double getConfigDouble(String title, String key) {
		if (getConfigProperty(title, key) instanceof Double d)
			return d;
		
		if (getConfigProperty(title, key) instanceof Float f)
			return (double) f;
		
		throw new ValidationException("Value is not an integer.");
	}
	
	/**
	 * @param title	The name of the configuration.
	 * @param key	The name of the property.
	 * @return 		The value as a String.
	 * @throws 		ValidationException If the value is not a String.
	 */
	public String getConfigString(String title, String key) {
		if (getConfigProperty(title, key) instanceof String s)
			return s;
		
		throw new ValidationException("Value is not a string.");
	}
	
	/**
	 * @param title	The name of the configuration.
	 * @param key	The name of the property.
	 * @return 		The value as a boolean.
	 * @throws 		ValidationException If the value is not a parsable boolean {@link xahla.utils.system.StringUtils#parseBoolean}.
	 */
	public boolean getConfigBool(String title, String key) {
		if (getConfigProperty(title, key) instanceof String s)
			return StringUtils.parseBoolean(s);
		
		if (getConfigProperty(title, key) instanceof Boolean b)
			return b;
		
		throw new ValidationException("Value is not a boolean.");
	}
	
	/**
	 * @param title	The name of the configuration.
	 * @param key	The name of the property.
	 * @return 		The value as a generic Object. False if it has not been found.
	 */
	public Object getConfigProperty(String title, String key) {
		Config cfg = getConfig(title);
		
		for (Entry<String, Object> s : cfg.getProperties().entrySet())
			if (s.getKey().toLowerCase().equals(key.toLowerCase()))
				return s.getValue();
		
		return "false";
	}
	
	/**
	 * @param title	The name of the configuration.
	 * @param key	The name of the property.
	 * @param value	The new value of the property.
	 * @return		True if the operation has been made succesfully.<br>
	 * 				False if the property has not been found.
	 */
	public boolean setConfigProperty(String title, String key, String value) {
		Config cfg = getConfig(title);
		
		for (Entry<String, Object> s : cfg.getProperties().entrySet())
			if (s.getKey().equals(key)) {
				s.setValue(value);
				return true;
			}
		
		return false;
	}
	
	/** @return The list of all configurations. */
	public List<Config> getConfigs() { return configs; }
	
	/**
	 * @param title	The name of the configuration.
	 * @return		The configuration with the given name.
	 */
	public Config getConfig(String title) {
		for (Config cfg : configs) {
			if (cfg.getTitle().equals(title))
				return cfg;
		}
		
		return null;
	}
	
	/**
	 * @param o	The XObject to append.
	 */
	public void add(XObject o) {
		o.init();
		objects.add(o);
		
		Collections.sort(objects);
		
//		for (Field f : o.getClass().getFields()) {
//			for (Annotation a : f.getAnnotations()) {
//				
//			}
//		}
	}
	
	/**
	 * @param o	The XObject to remove.
	 */
	public void remove(XObject o) {
		objects.remove(o);
	}
	
	/**
	 * @return	The XObject with the given name.
	 */
	public XObject getObjectByName(String name) {
		for (XObject o : objects) {
			if (o.getName().equals(name))
				return o;
		}
		
		return null;
	}
	
	/**
	 * @return	A collection of objects that inherits the given class.
	 * @error Not working as intended.
	 */
	public List<XObject> getObjectsByClass(Class<?> objClass) {
		var objects = new ArrayList<XObject>();
		
		for (XObject o : objects) {
			if (objClass.isInstance(o))
				objects.add(o);
		}
		
		return objects;
	}
	
	/** @return The current context. */
	public static Context instance() { return App.instance().getProgram(); }
	/** @return The Exception handler. */
	public ExceptionHandler getExceptionHandler() { return exceptionHandler; }
	
	/** @return The time that has been elapsed since the program start, in seconds. */
	public Time getElapsedTime() { return elapsedTime; }
	
	/** @return The framerate at the last second. */
	public int getFPS() { return App.instance().getFPS(); }
	/** @param newFramerate The new framerate. */
	public void setFramerate(int newFramerate) { App.instance().setFramerate(newFramerate); }
	
	/** @return The update rate at the last second. */
	public int getUPS() { return App.instance().getUPS(); }

}
