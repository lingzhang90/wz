package com.lixunkj.weizhuan;

import com.lixunkj.weizhuan.fragment.HomeFragment;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;

@SuppressLint("ResourceAsColor")
public class MainActivity extends FragmentActivity {
	public static Fragment[] mFragments;
	public static MainActivity mainActivity;
	private RadioButton homeLinearLayout;
	private RadioButton settingLinearLayout;
	private RadioButton zhuanLinearLayout;
	private RadioButton mineLinearLayout;
	private RadioButton moreLinearLayout;
	
	private  App App;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		App=(App) getApplication();
		setFragmentIndicator(0);
		initLibrary();
		initListener();
	}


	public void initLibrary() {
		mainActivity = this;
		homeLinearLayout = (RadioButton) findViewById(R.id.radio_btnHome);
		settingLinearLayout = (RadioButton) findViewById(R.id.radio_btnSetting);
		zhuanLinearLayout = (RadioButton) findViewById(R.id.radio_btnZhuan);
		mineLinearLayout = (RadioButton) findViewById(R.id.radio_btnMine);
		moreLinearLayout = (RadioButton) findViewById(R.id.radio_btnMore);

	}

	public void initListener() {
		homeLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setFragmentIndicator(0);
			}
		});
		settingLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setFragmentIndicator(1);
			}
		});
		zhuanLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setFragmentIndicator(2);
			}
		});
		mineLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setFragmentIndicator(3);
			}
		});
		moreLinearLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setFragmentIndicator(4);
			}
		});
	}

	/**
	 * 初始化fragment
	 */
	public void setFragmentIndicator(int whichIsDefault) {
		mFragments = new Fragment[5];
		mFragments[0] = getSupportFragmentManager().findFragmentById(
				R.id.fragment_home);
		mFragments[1] = getSupportFragmentManager().findFragmentById(
				R.id.fragment_settings);
		mFragments[2] = getSupportFragmentManager().findFragmentById(
				R.id.fragment_zhuan);
		mFragments[3] = getSupportFragmentManager().findFragmentById(
				R.id.fragment_search);
		mFragments[4] = getSupportFragmentManager().findFragmentById(
				R.id.fragment_more);

		getSupportFragmentManager().beginTransaction().hide(mFragments[0])
				.hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3])
				.hide(mFragments[4]).show(mFragments[whichIsDefault]).commit();
	}

	public static void setTz(int tz) {
		mainActivity.setFragmentIndicator(1);
	}

	public void FragmentClick(View v) {
		switch (v.getId()) {
		case R.id.home_imgbtn_daliysigin:
			((HomeFragment) mFragments[0]).FragmentClick(v);
			break;
		case R.id.home_imgbtn_daliyshake:
			((HomeFragment) mFragments[0]).FragmentClick(v);
			break;
		case R.id.home_imgbtn_taskhot:
			((HomeFragment) mFragments[0]).FragmentClick(v);
			break;
		case R.id.home_imgbtn_taskforfew:
			((HomeFragment) mFragments[0]).FragmentClick(v);
			break;
		case R.id.home_imgbtn_exchangerecord:
			((HomeFragment) mFragments[0]).FragmentClick(v);
			break;
		case R.id.home_imgbtn_taskrecord:
			((HomeFragment) mFragments[0]).FragmentClick(v);
			break;
		}

	}

	@Override
	public void onBackPressed() {
		SharedPreferences sharedPreferences = getSharedPreferences("cache", 0);
		String a = sharedPreferences.getString("restcoin", "restcoin");
		// String a=MineFragment.coinTextView.getText().toString();
		String message = "";
		if (App.getCache("isLogin").equals("1")) {
			message = "您真的要退出吗？您已经有" + a + "个积分，只要在做两三个任务就能兑换了";
		} else {
			message = "您真的要退出吗？";
		}
		new AlertDialog.Builder(this).setTitle("退出提示").setMessage(message)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 友盟统计——退出应用
						MobclickAgent.onEvent(MainActivity.this, "quitApp",
								App.getCache("uid"));
						App.setCache("isLogin", "0");
						App.setCookies("");
						Intent startMain = new Intent(Intent.ACTION_MAIN);
						startMain.addCategory(Intent.CATEGORY_HOME);
						startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(startMain);
						System.exit(0);
					}
				}).setNegativeButton("返回", null).show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			dialog();
			return true;
		}
		return true;
	}

	private void dialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确定要退出微赚吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();

	}

}