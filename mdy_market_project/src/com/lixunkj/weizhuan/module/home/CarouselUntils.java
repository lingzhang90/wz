package com.lixunkj.weizhuan.module.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class CarouselUntils {
	public ArrayList<String> list;

	public CarouselUntils(String jsonString) {
		super();
		try {
			list=new ArrayList<String>();
			JSONArray jsonArray = new JSONArray(jsonString);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject temp = (JSONObject) jsonArray.get(i);
				String picurl = temp.getString("picurl");
				list.add(picurl);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
