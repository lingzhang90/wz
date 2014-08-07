package com.lixunkj.weizhuan.module.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lixunkj.weizhuan.BaseActivity;
import com.lixunkj.weizhuan.MainActivity;
import com.lixunkj.weizhuan.R;
import com.lixunkj.weizhuan.data.AppConfig;
import com.lixunkj.weizhuan.entities.User;
import com.lixunkj.weizhuan.module.register.UserRegActivity;
import com.lixunkj.weizhuan.tool.MsgTimerUtils;
import com.lixunkj.weizhuan.tool.PhoneUtils;
import com.umeng.analytics.MobclickAgent;

public class UserLoginActivity extends BaseActivity {
	private TextView titleBar_tv;
	private EditText editTextUsername, editTextPassword;

	private String mobileString;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);
		initTitle();
		findViews();
		initViews();
	}

	private void initTitle() {
		titleBar_tv = (TextView)findViewById(R.id.titlebar_tv);
		titleBar_tv.setText("用户登录");
	}

	private void findViews() {

		editTextUsername = (EditText) findViewById(R.id.userLogin_username);
		editTextPassword = (EditText) findViewById(R.id.userLogin_password);
	}

	 private void initViews() {
	 editTextUsername.setText(App.getCache(AppConfig.CACHE_LOGIN_USERNAME));
	 editTextPassword.setText(App.getCache(AppConfig.CACHE_LOGIN_PWD));
	 }

	public void UserLoginClick(View view) {
		switch (view.getId()) {
		case R.id.userLogin_textView_getPass:
			if (MsgTimerUtils.getInstance().checkMsgTimePermission(
					UserLoginActivity.this)) {
				showGetpassAlert();
			}
			break;
		case R.id.userLogin_btnLogin:
			// TODO username&password -> user 2.user.isOK
			user = new User();
			user.userName = editTextUsername.getText().toString();
			user.userPassword = editTextPassword.getText().toString();
			String checkloginString = user.checkLoginInput();
			if (TextUtils.isEmpty(checkloginString)) {
				new taskUserLogin().execute();
			} else {
				toast(checkloginString);
			}
			break;
		case R.id.userLogin_btnReg:
			Intent intent = new Intent(getApplicationContext(),
					UserRegActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}

	class taskUserLogin extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgress("正在登录..");
		}

		protected String doInBackground(String... args) {
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", user.userName));
				params.add(new BasicNameValuePair("password", user.userPassword));
				params.add(new BasicNameValuePair("ajax", "1"));

				params = PhoneUtils.getInstance().getPhoneInfo(
						UserLoginActivity.this, params);
				App.setCache(AppConfig.CACHE_LOGIN_USERNAME, user.userName);
				App.setCache(AppConfig.CACHE_LOGIN_PWD, user.userPassword);
				String extif = App.getCache("pid");// 获取缓存
				String ext = getResources().getString(R.string.ext_id);// 返回的为null

				if (!TextUtils.isEmpty(extif)) {
					params.add(new BasicNameValuePair("pid", extif));
					Log.e("params---->>111", params.toString());
				} else {
					extif = "0";
					if (!ext.equals("null")) {
						params.add(new BasicNameValuePair("pid", ext));
						Log.e("params---->>222", params.toString());
					} else {
						params.add(new BasicNameValuePair("pid", "0"));
						Log.e("params---->>333", params.toString());
					}
				}
				
				return App.getApi().userlogin(params);
			} catch (Exception e) {
				return e.toString();
			}
			
		}

		protected void onPostExecute(String message) {

			dismissProgress();
			if (!App.webservice.getErrorString().equals("")) {
				App.showMsg(App.webservice.getErrorString());
				return;
			}
			try {
				JSONObject jsonObj = new JSONObject(message);
				Log.e("message", message);
				App.setUserInfo(jsonObj);
				if (App.webservice.getOutCookiesString() != null) {
					App.setCookies(App.getApi().getOutCookiesString());
				}
				App.setCache("isLogin", "1");
				App.showMsg("登陆成功");
				startActivity(new Intent(UserLoginActivity.this,
						MainActivity.class));
				finish();

			} catch (JSONException e) {
				toast("再来一次呗");
				e.printStackTrace();
			}
		}
	}

	private void showGetpassAlert() {
		LayoutInflater inflater = getLayoutInflater();
		final View layout = inflater.inflate(R.layout.user_getpass2, null);
		new AlertDialog.Builder(this)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						EditText mobileEditText = (EditText) layout
								.findViewById(R.id.getUserPassInput);
						mobileString = mobileEditText.getText().toString();
						// 判断数据有效性
						if (TextUtils.isEmpty(mobileEditText.getText())) {
							App.showMsg("手机号不能为空");
						} else {
							new taskGetPass().execute();
						}

						// App.showMsg(mobileEditText.getText().toString());
					}
				}).setNegativeButton("取消", null).setTitle("请输入注册时的手机号")
				.setView(layout)
				// .setMessage("请输入你注册时的手机号，新密码将发送到您注册的手机上，请注意查收")
				.show();

	}

	/*
	 * 下面是找回密码线程
	 */
	class taskGetPass extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgress("提交中..");
		}

		protected String doInBackground(String... args) {
			try {

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("mobile", mobileString));
				return App.getApi().userGetPass(params);

			} catch (Exception e) {
				// TODO: handle exception
				return e.toString();
			}
		}

		protected void onPostExecute(String message) {
			// 解析JSON字符串
			if (!App.webservice.getErrorString().equals("")) {
				App.showMsg(App.webservice.getErrorString());
			} else {
				MsgTimerUtils.getInstance().updateTime();
				App.showMsg(App.webservice.getInfoString());
				// umeng
				MobclickAgent.onEvent(UserLoginActivity.this, "findPass",
						App.getCache("uid"));
				Intent intent = new Intent(getApplicationContext(),
						UserLoginActivity.class);
				startActivity(intent);
			}
			dismissProgress();
		}
	}
}
