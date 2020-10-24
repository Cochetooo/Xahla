package xahla.utils.db;

import java.util.Map;

/**
 * A HttpRequestData contains a set of information for a HTTP Request.
 * 
 * @author Cochetooo
 * @version 1.0
 */
public record HttpRequestData(Map<Object, Object> data, String URI, Map<String, String> headers) {
	
	/** @return The list of parameters at the end of the URI. */
	public Map<Object, Object> getData() { return data; }
	
	/** @return The URI of the HTTP Request. */
	public String getURI() { return URI; }
	
	/** @return The headers of the HTTP Request. */
	public Map<String, String> getHeaders() { return headers; }
	
}
