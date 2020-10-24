package xahla.core;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * <p>
 * Config is a class for specific game configuration.<br>
 * It allows interpretation of .cfg file that generates automatically variables 
 * within the Context with specific values.
 * 
 * @author Cochetooo
 * @version 1.1
 */
public class Config {
	
	private Map<String, Object> properties;
	private String path, title;
	
	private Context Context;
	
	/**
	 * <p>
	 * Instanciates a new GameConfig object.<br>
	 * For <b>Version 1.1.0+</b>, it is possible to modify the name of the main directory where config files are stored.
	 * <i>With this constructor, the Context instance will be the default one.</i>
	 * 
	 * @param path The relative path of the file.
	 * @param title The title of the Game Config instance
	 * @see GameConfig
	 * @type Constructor
	 */
	public Config(String path, String title) {
		this.Context = App.instance().getProgram();
		
		this.path = "config/" + path;
		this.title = title;
		this.properties = load();
	}
	
	/**
	 * <p>
	 * Instanciates a new GameConfig object.<br>
	 * For <b>Version 1.1.0+</b>, it is possible to modify the name of the main directory where config files are stored.
	 * 
	 * @param Context The Context instance which the config is affiliated to.
	 * @param path The relative path of the file.
	 * @param title The title of the Game Config instance
	 * @see GameConfig
	 * @type Constructor
	 */
	public Config(Context Context, String path, String title) {
		this.Context = Context;
		
		this.path = "config/" + path;
		this.title = title;
		this.properties = load();
	}
	
	/**
	 * <p>
	 * Saves the config file as following:<br>
	 * For each properties, writes line <i>"key:value"</i>
	 * 
	 * @type Method
	 */
	public void save() {
		JSONObject configFile = new JSONObject();
		
		properties.forEach((k,v) -> configFile.put(k,v));
		
		try (FileWriter file = new FileWriter(path, StandardCharsets.UTF_8)) {
			file.write(configFile.toString(2));
			file.flush();
		} catch (IOException e) {
			Context.getExceptionHandler().reportException(e, "Unable to save data for config file " + title);
		}
	}
	
	/**
	 * <p>
	 * Load in the .cfg file the different properties.<br>
	 * <i>Notice that all lines beginning with # will be ignored</i>
	 * 
	 * @return A Map of Key and Value as String.
	 * @type Method
	 */
	private Map<String, Object> load() {
		Map<String, Object> result = new HashMap<>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));
			
			String st;
			String text = "";
			
			while ((st = br.readLine()) != null) {
				if (st.startsWith("#") || st.isEmpty()) continue;
				
				text += st + "\n";
			}
			
			JSONObject obj = new JSONObject(text);
			result = obj.toMap();
			
			br.close();
		} catch (IOException e) {
			if (e instanceof FileNotFoundException) {
				properties = new HashMap<>();
				save();
				load();
			}
		}
		
		return result;
	}
	
	/**
	 * @return The title of the config.
	 * @type Getter
	 */
	public String getTitle() { return title; }
	
	/**
	 * @return The list of properties as a HashMap containing the key and the value.
	 * @type Getter
	 */
	public Map<String, Object> getProperties() { return properties; }

}
