package com.lixunkj.weizhuan.module.home;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixunkj.weizhuan.BaseActivity;
import com.lixunkj.weizhuan.FormActivity;
import com.lixunkj.weizhuan.R;
import com.umeng.analytics.MobclickAgent;

public class ExchangeActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exchange_list);
		// 设置标题
		TextView titleTextView = (TextView) findViewById(R.id.titlebar_tv);
		titleTextView.setText("财富中心");
		// 左边的按钮
		ImageView leftButton = (ImageView) findViewById(R.id.titlebar_back);
		// leftButton.setVisibility(View.GONE);
		leftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_close();
			}
		});

		
		
		// 我要申请
		LinearLayout qqLinearLayout = (LinearLayout) findViewById(R.id.exchange_qq);
		qqLinearLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(getApplicationContext(),
						FormActivity.class);
				startActivity(intent);
				
			}
		});
		
		
		// 申请记录！
		LinearLayout banklogLinearLayout = (LinearLayout) findViewById(R.id.exchange_banklog);
		banklogLinearLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(getApplicationContext(),
						BankLogActivity.class);
				startActivity(intent);
				
			}
		});
		

	}


	//友盟统计重写方法
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		}
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		}

	
	// 关闭该activity
	private void _close() {
		this.finish();
	}

}