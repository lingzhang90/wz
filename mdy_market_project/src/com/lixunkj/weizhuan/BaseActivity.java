package com.lixunkj.weizhuan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.lixunkj.weizhuan.module.login.UserLoginActivity;
import com.lixunkj.weizhuan.view.ActionTitlebar;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends SherlockFragmentActivity {

	protected ActionTitlebar actionbar;
	private ProgressDialog pDialog;
	protected App App;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getSupportActionBar() != null) {
			buildCustomActionBarTitle();
		}
		App = (App) getApplication();
	}

	public ActionTitlebar getTitleBar() {
		return actionbar;
	}

	private void buildCustomActionBarTitle() {
		actionbar = new ActionTitlebar(this);
		// ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
		// ViewGroup.LayoutParams.MATCH_PARENT,
		// ViewGroup.LayoutParams.MATCH_PARENT, Gravity.FILL);
		getSupportActionBar().setCustomView(actionbar);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		setTitle(getTitle());
	}

	public void hideTitlebar() {
		getSupportActionBar().hide();
	}

	public void showTitlebar() {
		getSupportActionBar().show();
	}

	public void toast(String string) {
		if (!TextUtils.isEmpty(string)) {
			Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
		}
	}

	public void toast(int stringRes) {
		toast(getString(stringRes));
	}

	protected void showProgress(String hintString) {
		pDialog = new ProgressDialog(this);
		pDialog.setMessage(hintString);
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}

	protected void dismissProgress() {
		if (pDialog != null) {
			pDialog.dismiss();
		}
	}

	// 检查登录状态
	protected boolean checkUserStatusAndLogin() {
		boolean userStatus = checkUserStatus();
		if (!checkUserStatus()) {
			App.showMsg("请先登录");
			Intent intent = new Intent(getApplicationContext(),
					UserLoginActivity.class);
			startActivity(intent);
		}
		return userStatus;
	}

	// 检查登录状态
	protected boolean checkUserStatus() {
		String isLogin = App.getCache("isLogin");
		return "1".equals(isLogin);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
