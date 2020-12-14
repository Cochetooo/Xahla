package xahla.server.eloquent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xahla.server.laravel.QueryBuilder;
import xahla.utils.ValidationException;

/**
 * A Model is a class that contains attributes from a list of data given by
 * the Laravel internal API. It uses relationships from database.<br>
 * 
 * @author Cochetooo
 * @version 1.1
 */
public class Model {
	
	protected static String primaryKey = "id";
	
	protected Map<Object, Object> attributes;
	
	public Model() {
		this.attributes = new HashMap<>();
	}
	
	public Model save() {
		return query()
			.action(Action.STORE)
			.as(getInt(primaryKey))
			.then()
			.forEach(attributes)
			.first();
	}
	
	public static List<Model> all() {
		return query()
			.action(Action.INDEX)
			.get();
	}
	
	public static Model find(int id) {
		return query()
			.action(Action.SHOW)
			.as(id)
			.first();
	}
	
	public static int delete(int id) {
		return query()
			.action(Action.DELETE)
			.as(id)
			.run();
	}
	
	/**
	 * Start a new API query.
	 * @return The new instanciated query.
	 */
	public static QueryBuilder query() {
		return new QueryBuilder();
	}
	
	/**
	 * Can be overriden to change the model name.
	 * @return	The model name as the class caller's name to lower case.
	 */
	public static String getName() {
		return new Throwable().getStackTrace()[1].getClassName().toLowerCase();
	}
	
	public int getKey() {
		return getInt(primaryKey);
	}
	
	public int getInt(String key) {
		try {
			return (int) get(key);
		} catch (ClassCastException cce) {
			throw new ValidationException("The value of the attribute is not a int.");
		}
	}
	
	public long getLong(String key) {
		try {
			return (long) get(key);
		} catch (ClassCastException cce) {
			throw new ValidationException("The value of the attribute is not a long.");
		}
	}
	
	public float getFloat(String key) {
		try {
			return (float) get(key);
		} catch (ClassCastException cce) {
			throw new ValidationException("The value of the attribute is not a float.");
		}
	}
	
	public double getDouble(String key) {
		try {
			return (double) get(key);
		} catch (ClassCastException cce) {
			throw new ValidationException("The value of the attribute is not a number.");
		}
	}
	
	public String getString(String key) {
		try {
			return (String) get(key);
		} catch (ClassCastException cce) {
			throw new ValidationException("The value of the attribute is not a string.");
		}
	}
	
	public boolean getBoolean(String key) {
		try {
			return (boolean) get(key);
		} catch (ClassCastException cce) {
			throw new ValidationException("The value of the attribute is not a boolean.");
		}
	}
	
	public Object get(String key) {
		try {
			return this.attributes.get(key);
		} catch (NullPointerException npe) {
			throw new ValidationException("No attribute " + key + " has been found.");
		}
	}

}
