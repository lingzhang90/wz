package com.lixunkj.weizhuan.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

	public static HashMap<String, ArrayList<String>> getCategoryMapFromJson(
			String categoryJson) {

		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		try {
			JSONObject jsonObject = new JSONObject(categoryJson);
			@SuppressWarnings("unchecked")
			Iterator<String> keys = jsonObject.keys();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				if (!jsonObject.isNull(key)) {
					JSONArray jsonArray = jsonObject.getJSONArray(key);
					int length = jsonArray.length();
					ArrayList<String> childList = new ArrayList<String>();
					for (int i = 0; i < length; i++) {
						childList.add(jsonArray.getString(i));
					}
					map.put(key, childList);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
}
