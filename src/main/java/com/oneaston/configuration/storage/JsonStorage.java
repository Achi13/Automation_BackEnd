package com.oneaston.configuration.storage;

import org.json.JSONObject;

public class JsonStorage {
	
	private static JsonStorage instance;
	
	private JSONObject tempMapJson;
	private JSONObject jsonList;
	
	public static JsonStorage getInstance() {
		if(instance == null) {
			instance = new JsonStorage();
		}
		return instance;
	}

	public JSONObject getTempMapJson() {
		return tempMapJson;
	}

	public void setTempMapJson(JSONObject tempMapJson) {
		this.tempMapJson = tempMapJson;
	}

	public JSONObject getJsonList() {
		return jsonList;
	}
	
	public void setJsonList(JSONObject jsonList) {
		this.jsonList = jsonList;
	}
}
