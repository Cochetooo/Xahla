package xahla.server.laravel;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import xahla.server.eloquent.Action;
import xahla.server.eloquent.Model;
import xahla.utils.ValidationException;
import xahla.utils.logger.Logger;

public class QueryBuilder {
	
	private String query;
	
	public QueryBuilder() {
		query = "";
	}
	
	public QueryBuilder action(Action action) {
		query += action.getName() + "/";
		return this;
	}
	
	public QueryBuilder forEach(Map<Object, Object> attributes) {
		for(Map.Entry<Object, Object> entry : attributes.entrySet())
			this.with(entry.getKey(), entry.getValue());
		
		return this;
	}
	
	public QueryBuilder then() {
		query += "?";
		return this;
	}
	
	public QueryBuilder and() {
		query += "&";
		return this;
	}
	
	public QueryBuilder as(int id) {
		query += id + "/";
		return this;
	}
	
	public QueryBuilder with(Object key, Object value) {
		query += key + "=" + value + "&";
		return this;
	}
	
	public List<Model> get() {
		var resp = getResponse();
		String body = (String) resp.body();
		JSONObject json = new JSONObject(body);
		
		Logger.log(json);
		
		return null;
	}
	
	public Model first() {
		query += "&limit=1";
		
		var resp = getResponse();
		String body = (String) resp.body();
		JSONObject json = new JSONObject(body);
		JSONObject first = json.getJSONArray("list").getJSONObject(0);
		
		Logger.log(first);
		
		return null;
	}
	
	public int run() {
		var resp = getResponse();
		String body = (String) resp.body();
		JSONObject json = new JSONObject(body);
		
		return json.getInt("status");
	}
	
	HttpResponse<?> getResponse() {
		if (DB.call("http://minecraft.edmc73.com/api/", query) instanceof HttpResponse<?> resp) {
			return resp;
		} else {
			throw new ValidationException("A fatal error has occured during the DB::get() process.");
		}
	}

}
