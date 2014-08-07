package com.lixunkj.weizhuan.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

public class WebService {

	private WebApi webApi = new WebApi();

	// 用户注册
	public String userReg(List<NameValuePair> params) {
		String urlString = webApi.userReg();
		return httpQuery(urlString, params);
	}

	// 提现接口
	public String pay(List<NameValuePair> params) {
		String urlString = webApi.pay();
		return httpQuery(urlString, params);
	}

	// 用户登录
	public String userlogin(List<NameValuePair> params) {
		String urlString = webApi.userLogin();
		return httpQuery(urlString, params);
	}

	// 用户找回密码
	public String userGetPass(List<NameValuePair> params) {
		String urlString = webApi.userGetPass();
		return httpQuery(urlString, params);
	}

	// 获取用户信息
	public String userGetUserInfo() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String urlString = webApi.userGetUserInfo();
		return httpQuery(urlString, params);
	}

	// 提现记录接口
	public String bankLog(List<NameValuePair> params) {
		String urlString = webApi.banklog();
		return httpQuery(urlString, params);
	}

	// 邀请用户记录
	public String inviteUser(List<NameValuePair> params) {
		String urlString = new WebApi().inviteUser();
		return httpQuery(urlString, params);
	}

	// 用户等级升级
	public String userGradeUpdate(List<NameValuePair> params) {
		String urlString = new WebApi().UserGradeUpdate();
		return httpQuery(urlString, params);
	}

	// 用户签到
	public String userSign(List<NameValuePair> params) {
		String urlString = webApi.userSign();
		return httpQuery(urlString, params);
	}

	// 用户设置个人资料
	public String setUserinfo(List<NameValuePair> params) {
		String urlString = webApi.setUserInfo();
		return httpQuery(urlString, params);
	}

	// 检测新版本
	public String version(List<NameValuePair> params) {
		String urlString = webApi.version();
		return httpQuery(urlString, params);
	}

	// 修改用户密码
	public String UserChangePass(List<NameValuePair> params) {
		String urlString = webApi.UserChangePass();
		return httpQuery(urlString, params);
	}

	// 积分明细
	public String coinLog(List<NameValuePair> params) {
		String urlString = webApi.coinLog();
		return httpQuery(urlString, params);
	}

	// 排行榜
	public String rank(List<NameValuePair> params) {
		String urlString = webApi.rank();
		return httpQuery(urlString, params);
	}

	// 生成推广包路径
	public String createPackageUrl(List<NameValuePair> params) {
		String urlString = webApi.createPackageUrl();
		return httpQuery(urlString, params);
	}

	// 在分享推广链接的时候，要调用一下这个接口
	public String popPackage(String uid) {
		String urlString = "http://www.weizhuan.me/apktool.php?uid=" + uid;
		return httpQuery(urlString, new ArrayList<NameValuePair>());
	}

	// 用户退出
	public String logout() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String urlString = webApi.logout();
		return httpQuery(urlString, params);
	}

	// 用户修改提现密码
	public String userChangePayPass(List<NameValuePair> params) {
		String urlString = webApi.userChangePayPass();
		return httpQuery(urlString, params);
	}

	// 用户找回提现密码
	public String userGetPayPass() {
		String urlString = webApi.userGetPayPass();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		return httpQuery(urlString, params);
	}

	// 用户启动应用
	public String enterApp(List<NameValuePair> params) {
		String urlString = webApi.enterApp();
		return httpQuery(urlString, params);
	}

	// 用抽奖
	public String lottery() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String urlString = webApi.lottery();
		return httpQuery(urlString, params);
	}

	// 用微博加积分
	public String addCoin(List<NameValuePair> params) {
		String urlString = webApi.addCoin();
		return httpQuery(urlString, params);
	}

	public String getADList(List<NameValuePair> params) {
		String urlString = webApi.getAdList();
		return httpQuery(urlString, params);
	}

	// ===========================================================================

	// public static DefaultHttpClient httpClient = new DefaultHttpClient();
	// 输入的cookies
	public String inCookiesString;
	// 获取的cookies
	public String outCookiesString;

	// 出错信息
	public String errorString;
	// 出错代码
	public String statusCodeString;
	// 提示信息
	public String infoString;

	// 设置COOKIES
	public void setInCookiesString(String cookieString) {
		this.inCookiesString = cookieString;
	}

	// 设置COOKIES
	public String getInCookiesString() {
		return this.inCookiesString;
	}

	// 获取输出的COOKIES
	public String getOutCookiesString() {
		return this.outCookiesString;
	}

	// 获取输出的ERROR
	public String getErrorString() {
		return this.errorString;
	}

	// 获取输出的提示信息
	public String getInfoString() {
		return this.infoString;
	}

	// 获取出错代码
	public String getStatusCodeString() {
		return this.statusCodeString;
	}


	// 统一数据请求函数
	public String httpQuery(String url, List<NameValuePair> params) {
		// 所有请求做AJAX处理
		this.errorString = "";
		params.add(new BasicNameValuePair("ajax", "1"));
		String json = null;
		InputStream is = null;
		// 获取 JSON 字符串
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			// 设置COOKIES
			if (!inCookiesString.equals("")) {
				httpPost.setHeader("Cookie", inCookiesString);
			} else {
				httpPost.setHeader("Cookie", "");
			}

//			System.out.println("params  " + params.toString());
			httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpParams httpparams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpparams, 10000); // 设置连接超时
			HttpConnectionParams.setSoTimeout(httpparams, 20000); // 设置请求超时
			httpPost.setParams(httpparams);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();

			// 如果HTTP状态码正常
			int httpStatusCode = httpResponse.getStatusLine().getStatusCode();
			if (httpStatusCode != HttpStatus.SC_OK) {
				this.errorString = "HTTP连接失败" + httpStatusCode;
				return null;
			}
			// 保存 cookies
			String cookieStr2 = "";
			List<Cookie> cookies = httpClient.getCookieStore().getCookies();
			if (!cookies.isEmpty()) {
				for (int i = 0; i < cookies.size(); i++) {
					cookieStr2 += cookies.get(i).getName() + "="
							+ cookies.get(i).getValue() + "; ";
				}
				this.outCookiesString = cookieStr2;
			}

			// 保存 cookies
			is = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			json = sb.toString();
			System.out.println("接口返回的Json数据是    ：   " + json);
			Log.e("xuleilei", json);

			JSONObject jsonObj = new JSONObject(json);

			String status = jsonObj.getString("status");
			if (status.equals("700")) {
				inCookiesString = null;
			}
			String info = jsonObj.getString("info");
			this.statusCodeString = status;

			this.infoString = info;
			if (!status.equals("1")) {
				this.errorString = info;
				return null;
			}

			String data = jsonObj.getString("data");
			if(TextUtils.isEmpty(data)){
				return " ";
			}
			return data;
		} catch (JSONException e) {
			this.errorString = "网络异常,请重试~";

			// this.errorString = "服务器返回数据格式 错误"+messageString;
			return null;
		} catch (UnknownHostException e) {
			// TODO: handle exception
			this.errorString = "网络异常,请重试~";
			// this.errorString = "找不到该主机";

			return null;
		} catch (SocketTimeoutException e) {
			// TODO: handle exception
			this.errorString = "网络不稳定,请重试~";

			// this.errorString = "服务器响应时间过长，超时";
			return null;
		} catch (ConnectTimeoutException e) {
			// TODO: handle exception
			this.errorString = "网络不稳定,请重试~";
			// this.errorString = "连接到该URL时间过长，超时";
			return null;
		} catch (Exception e) {
			this.errorString = "出错啦！请重试~";
			// this.errorString = "未知错误：" + e.toString();
			return null;
		}
	}

}
