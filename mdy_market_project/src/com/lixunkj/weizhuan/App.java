package com.lixunkj.weizhuan;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lixunkj.weizhuan.entities.User;
import com.lixunkj.weizhuan.network.InitVolley;
import com.lixunkj.weizhuan.tool.WebService;

public class App extends Application {

	private static App mApp;
	// Cookies变量名
	private String cookies;
	// 缓存存储文件
	static private SharedPreferences cache;
	// 网络请求接口
	public WebService webservice;

	@Override
	public void onCreate() {
		super.onCreate();
		InitVolley.getInstance().init(this);
		InitLocation.getInstance().init(this);
		// 初始化HTTP工具类
		// 建立一个存储缓存的介质
		this.webservice = new WebService();
		webservice.statusCodeString = "0";
		// 建立一个存储缓存的介质
		cache = getApplicationContext().getSharedPreferences("cache", 0);
	}

	public static App getInstance() {
		return mApp;
	}

	// 返回版本号
	public String getVersion() {
		return "1.3";
	}

	public WebService getApi() {
		String cookiesString = cache.getString("cookies", "");
		this.webservice.setInCookiesString(cookiesString);
		return this.webservice;
	}

	// 读取缓存
	public static String getCache(String nameString) {
		String aaString = cache.getString(nameString, "");
		if (TextUtils.isEmpty(aaString)) {
			aaString = "";
		}
		return aaString;
	}

	// 设置缓存
	public void setCache(String nameString, String valueString) {
		Editor editor = cache.edit();
		editor.putString(nameString, valueString);
		editor.commit();
	}

	// 设置cookies
	public void setCookies(String cookies) {
		this.cookies = cookies;
		this.webservice.setInCookiesString(cookies);
		this.setCache("cookies", cookies);
	}

	// 获取cookies
	public String getCookies() {
		return cookies;
	}

	public void showMsg(final String message) {
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

	// 保存用户最新信息到缓存
	public boolean setUserInfo(JSONObject jsonObj) {
		clearUserInfo();
		Editor editor = cache.edit();
		try {
			User user = new User();
			user.userName = jsonObj.getString("username");
			user.uid = jsonObj.getString("id");
			user.nickname = jsonObj.getString("nickname");
			user.grade = jsonObj.getString("grade");
			user.coin = jsonObj.getString("coin");
			user.restcoin = jsonObj.getString("restcoin");
			user.costcoin = jsonObj.getString("costcoin");
			user.mobile = jsonObj.getString("mobile");
			user.fullname = jsonObj.getString("fullname");
			user.sex = jsonObj.getString("sex");
			user.birth = jsonObj.getString("birth");
			user.pid = jsonObj.getString("pid");
			Gson gson = new Gson();
			String json = gson.toJson(user);
			if (jsonObj.has("coinnum")) {
				user.coinnum = jsonObj.getString("coinnum");
			}
			if (jsonObj.has("banknum")) {
				user.banknum = jsonObj.getString("banknum");
			}
			if (jsonObj.has("tuiguangnum")) {
				user.tuiguangnum = jsonObj.getString("tuiguangnum");
			}
			// TODO user->jsonString->save
			editor.putString("userString", json);

		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		editor.commit();
		return true;
	}

	public void clearUserInfo() {
		Editor edit = cache.edit();
		edit.remove("userString");
		edit.commit();
	}

}
