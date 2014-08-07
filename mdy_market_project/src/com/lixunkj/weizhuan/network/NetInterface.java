package com.lixunkj.weizhuan.network;

import com.android.volley.Request.Method;
import com.lixunkj.weizhuan.data.AppConfig;

public class NetInterface {
	private static NetInterface netService;

	public static NetInterface getInstance() {
		if (netService == null) {
			netService = new NetInterface();
		}
		return netService;
	}

	public RestEntity test() {
		return new RestEntity(Method.GET,
				"http://m0546.com/addshop/update.php?adduser="
						+ AppConfig.USER_NAME);
	}
	public RestEntity carouselData() {
		return new RestEntity(Method.GET, "http://m.dyscxx.cn/a_d/mobile.php");
	}
}
