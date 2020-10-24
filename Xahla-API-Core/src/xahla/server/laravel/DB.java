package xahla.server.laravel;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import xahla.utils.db.HttpRequestData;
import xahla.utils.db.HttpRequestUtils;

/**
 * This class is an automation for the Database API.
 * 
 * @author Cochetooo
 * @version 1.1
 */
public class DB {
	
	private static HttpClient client = HttpClient.newBuilder()
			.version(HttpClient.Version.HTTP_2)
			.connectTimeout(Duration.ofSeconds(10))
			.build();
		
	private static String apiToken;
	
	static Object call(String URI, String parameters) {
		var data = sampleData();
		
		for (String s : parameters.split("&")) {
			String[] parameter = s.split("=");
			data.put(parameter[0], parameter[1]);
		}
		
		return HttpRequestUtils.post(client, new HttpRequestData(
			data,
			URI,
			sampleHeaders()
		));
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
	
	public static void setApiToken(String token) { apiToken = token; }

}
