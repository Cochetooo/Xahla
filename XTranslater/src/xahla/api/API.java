package xahla.api;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import xahla.utils.db.HttpRequestData;
import xahla.utils.db.HttpRequestUtils;
import xahla.utils.logger.Logger;

/**
 * This class dialogues with the remote API and the Data Manager.
 * 
 * @author Cochetooo
 * @version 1.1
 */
public final class API {

	private static String apiToken;
	
	private static HttpClient client;
	
	/**
	 * Try to authenticate the session with the given token.
	 * 
	 * @param token		The developer token that allows access for remote communication.
	 * @return True if the authentication with the remote server has been successful.
	 */
	public static boolean authenticate(String user, String password) {
		client = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10))
			.build();
		
		var data = new HashMap<Object, Object>();
		data.put("username", user);
		data.put("password", password);
		
		try {
			apiToken = (String) HttpRequestUtils.post(client, new HttpRequestData(
				data,
				"http://minecraft.edmc73.com/api/auth?",
				sampleHeaders()
			));
		} catch (ClassCastException cce) {
			Logger.log("Failed to authenticate. Please verify your username and your password.");
			return false;
		}
		
		return true;
	}
	
	private static Map<Object, Object> sampleData() {
		var data = new HashMap<Object, Object>();
		data.put("apiToken", apiToken);
		
		return data;
	}
	
	private static Map<String, String> sampleHeaders() {
		var headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		
		return headers;
	}
	
}
