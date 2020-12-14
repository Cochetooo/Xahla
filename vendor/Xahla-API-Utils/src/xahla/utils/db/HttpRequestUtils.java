package xahla.utils.db;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * This class contains a set of utils functions for handling HTTP Requests.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public class HttpRequestUtils {
	
	public static Object head(HttpClient client, HttpRequestData data) {
		return null;
	}
	
	public static Object get(HttpClient client, HttpRequestData data) {
		return null;
	}
	
	/**
	 * Send a POST request to a specific URI.
	 * @param client	The HTTP Client.
	 * @param data		The set of data (parameters, URI and headers)
	 * @return			The response of the HTTP Request.
	 */
	public static Object post(HttpClient client, HttpRequestData data) {
		Builder requestBuilder = HttpRequest.newBuilder()
			.POST(ofFormData(data.getData()))
			.uri(URI.create(data.getURI()))
			.setHeader("User-Agent", "Xahla/API");
		for (Map.Entry<String, String> header : data.getHeaders().entrySet())
			requestBuilder.header(header.getKey(), header.getValue());
		
		HttpRequest request = requestBuilder.build();
		
		try {
			return client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			return "Error: " + e.getMessage();
		}
	}
	
	public static Object put(HttpClient client, HttpRequestData data) {
		return null;
	}
	
	public static Object delete(HttpClient client, HttpRequestData data) {
		return null;
	}
	
	/**
	 * @param data	The list of parameters as a Map.
	 * @return		A list of parameters as a URI.
	 */
	public static HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
		var builder = new StringBuilder();
		for (Map.Entry<Object, Object> entry : data.entrySet()) {
			if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
		}
		
		return HttpRequest.BodyPublishers.ofString(builder.toString());
	}

}
