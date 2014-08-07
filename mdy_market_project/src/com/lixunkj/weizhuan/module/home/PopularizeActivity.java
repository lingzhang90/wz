package com.lixunkj.weizhuan.module.home;

import com.lixunkj.weizhuan.BaseActivity;
import com.lixunkj.weizhuan.R;
import com.lixunkj.weizhuan.entities.PopularizeData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 推广页面
 * 
 * @author dangtao
 */
public class PopularizeActivity extends BaseActivity {

	PopularizeData popularizeData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_popularize);

		initTitlebar();
		initViews();
	}

	private void initTitlebar() {
		TextView titleTextView = (TextView) findViewById(R.id.titlebar_tv);
		titleTextView.setText("推广有奖");
		ImageView leftButton = (ImageView) findViewById(R.id.titlebar_back);
		leftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initViews() {
		((Button) findViewById(R.id.btn_share))
				.setOnClickListener(popOnClickListener);
	}

	OnClickListener popOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.btn_share:
				sharePackage();
				break;

			default:
				break;
			}
		}
	};

	// private void createPackage() {
	// if (Const.USER_STATUS_LOGIN.equals(App.getCache("isLogin"))) {
	// new createPackageUrlAsync().execute();
	// } else {
	// startActivity(new Intent(PopularizeActivity.this,
	// UserLoginActivity.class));
	// }
	// }

	private void sharePackage() {
		if (checkUserStatusAndLogin()) {
			// if (popularizeData == null
			// || TextUtils.isEmpty(popularizeData.urlandroid)) {
			// App.showMsg("请先点击生成按钮\"生成推广包\"");
			// } else {
			// clickUrl();
			Intent shareInt = new Intent(Intent.ACTION_SEND);
			shareInt.setType("text/plain");
			shareInt.putExtra(Intent.EXTRA_SUBJECT, "选择分享方式");
			shareInt.putExtra(
					Intent.EXTRA_TEXT,
					getResources().getString(R.string.pop_share_string)
							+ App.getCache("uid"));
			shareInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(shareInt);
			// }
		}
	}

	// private void clickUrl() {
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// String cache = App.getCache("uid");
	// App.getApi().popPackage(cache);
	// }
	// }).start();
	// }

	// class createPackageUrlAsync extends AsyncTask<String, String, String> {
	//
	// private ProgressDialog pDialog;
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// // 将按钮的值改为 正在加载中
	// pDialog = new ProgressDialog(PopularizeActivity.this);
	// pDialog.setMessage("推广包生成中...");
	// pDialog.setIndeterminate(false);
	// pDialog.setCancelable(true);
	// pDialog.show();
	// }
	//
	// // 加载下一页
	// @TargetApi(Build.VERSION_CODES.FROYO)
	// protected String doInBackground(String... args) {
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	// params.add(new BasicNameValuePair("uid", App.getCache("uid")));
	// return App.getApi().createPackageUrl(params);
	// }
	//
	// /**
	// * 任务结束之后的操作
	// * **/
	// protected void onPostExecute(String message) {
	//
	// pDialog.dismiss();
	// if (!App.webservice.getErrorString().equals("")) {
	// App.showMsg(App.webservice.getErrorString());
	// if (App.webservice.getStatusCodeString().equals("700")) {
	// // 标记登录失效
	// App.setCache("isLogin", "0");
	// Intent intent = new Intent(getApplicationContext(),
	// UserLoginActivity.class);
	// startActivity(intent);
	// }
	// } else {
	// Gson gson = new Gson();
	// popularizeData = gson.fromJson(message, PopularizeData.class);
	// App.showMsg(App.webservice.getInfoString());
	// }
	// }
	// }

}
