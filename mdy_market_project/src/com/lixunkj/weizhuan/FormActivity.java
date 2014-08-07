package com.lixunkj.weizhuan;

/********************************
 * @Author 70
 * 
 * @Connect QQ781787584
 * 
 * @Time 2013-05-22 10:13:10
 * 
 * @Describe 安卓学习Demo 用户登录功能实现
 * 
 * @Copyright 版权所有_未经许可不得用于任何用途_侵权必究
 * *******************************
 * 这是70写的一个<用户登录>的功能模块
 * 采用JSON的方式与服务器交互
 * 服务器端采用(ThinkPHP)PHP+MYSQL
 * 
 * *******************************
 */


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lixunkj.weizhuan.module.login.UserLoginActivity;
import com.umeng.analytics.MobclickAgent;

public class FormActivity extends BaseActivity {
	//提现方式
	private String payWay;
	
	// 定义进度对话框
	private ProgressDialog pDialog;
	
	
	App App;
	
	//定义所有的组件
	private EditText qqNumEditText,mobileNumEditText,alipayNumEditText,tenpayNumEditText,bankNumEditText;
	private EditText alipayUserEditText,tenpayUserEditText,bankUserEditText;
	private EditText bankAddressEditText;
	private EditText payPassEditText;
	
	//定义所有的spinner
	private Spinner qqAmountSpinner,mobileAmountSpinner,alipayAmountSpinner,tenpayAmountSpinner,bankAmountSpinner,bankNameSpinner;
	
	
	//定义所有的字符串
	private String qqNumString,mobileNumString,alipayNumString,tenpayNumString,bankNumString;
	private String alipayUserString,tenpayUserString,bankUserString;
	private String bankAddressString;
	private String paypassString;
	
	
	private String qqAmountString,mobileAmountString,alipayAmountString,tenpayAmountString,bankAmountString,bankNameString;
	
	
	
	private Integer qqAmountInteger,mobileAmountInteger,alipayAmountInteger,tenpayAmountInteger,bankAmountInteger;
	
	private List<NameValuePair> params;
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exchange_xxx);
		
		App = (App) getApplication(); // 获得我们的应用程序MyApplication
		
		// 设置标题
		TextView titleTextView = (TextView) findViewById(R.id.titlebar_tv);
		titleTextView.setText("财务管理");
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
		// 右边的按钮
		TextView rightButton = (TextView) findViewById(R.id.titlebar_right_text);
		// rightButton.setVisibility(View.GONE);
		rightButton.setText("提交");
//		rightButton.setVisibility(View.INVISIBLE);

		
		String[] m = { "兑换成Q币", "兑换成话费", "提现到支付宝", "提现到财付通", "提现到银行卡" };
		Spinner spinner = (Spinner) findViewById(R.id.form_spinner_way);
		// 将可选内容与ArrayAdapter连接起来
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(),
				R.layout.my_spinner, m);
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(R.layout.my_spinner_list);
		// 将adapter 添加到spinner中
		spinner.setAdapter(adapter);
		// 添加事件Spinner事件监听
		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
		
		
		qqForm();
		mobileForm();
		alipayForm();
		tenpayForm();
		bankForm();
		bankNameForm();
		
		
		
		
		qqNumEditText = (EditText)findViewById(R.id.form_editText_qqNum);
		mobileNumEditText= (EditText)findViewById(R.id.form_editText_mobileNum);
		alipayNumEditText = (EditText) findViewById(R.id.form_editText_alipayNum);
		tenpayNumEditText = (EditText) findViewById(R.id.form_editText_tenpayNum);
		bankNumEditText = (EditText) findViewById(R.id.form_editText_bankNum);
		payPassEditText = (EditText) findViewById(R.id.form_editText_paypass);
		

		alipayUserEditText = (EditText) findViewById(R.id.form_editText_alipayUser);
		tenpayUserEditText = (EditText) findViewById(R.id.form_editText_tenpayUser);
		bankUserEditText = (EditText) findViewById(R.id.form_editText_bankUser);
		
		bankAddressEditText = (EditText) findViewById(R.id.form_editText_bankAddress);
		
		
		
		
		//QQ
		
		qqNumEditText.setText(App.getCache("tx_qqNum"));
		//支付宝
//		alipayNumEditText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		alipayUserEditText.setText(App.getCache("tx_alipayUser"));
		alipayNumEditText.setText(App.getCache("tx_alipayNum"));
		//财付通
		tenpayUserEditText.setText(App.getCache("tx_tenpayUser"));
		tenpayNumEditText.setText(App.getCache("tx_tenpayNum"));
		//手机
		mobileNumEditText.setText(App.getCache("tx_mobileNum"));
		//提现
//		bankNameEditText.setText(App.getCache("tx_bankNameNum"));
		bankAddressEditText.setText(App.getCache("tx_bankAddress"));
		bankUserEditText.setText(App.getCache("tx_bankUser"));
		bankNumEditText.setText(App.getCache("tx_bankNum"));
		
		
		bankNameSpinner = (Spinner) findViewById(R.id.form_spinner_bankName);
		
		
		String bankName11 = App.getCache("tx_bankName");
		int dddd = 0;
//		"工商银行", "农业银行", "建设银行", "邮政银行", "招商银行", "中国银行"
		if (bankName11.equals("工商银行")) {
			dddd = 0;
		} else if (bankName11.equals("农业银行")) {
			dddd = 1;
		} else if (bankName11.equals("建设银行")) {
			dddd = 2;
		} else if (bankName11.equals("邮政银行")) {
			dddd = 3;
		} else if (bankName11.equals("招商银行")) {
			dddd = 4;
		} else if (bankName11.equals("中国银行")) {
			dddd = 5;
		}else {
			dddd = 0;
		}
		
		
		
		bankNameSpinner.setSelection(dddd,true);
		
		
//		App.setCache("tx_bankName", bankNameString);
//		App.setCache("tx_bankAddress", bankAddressString);
//		App.setCache("tx_bankUser", bankUserString);
//		App.setCache("tx_bankNum", bankNumString);
		
		
		
		
		
		
		
		
		

		rightButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (chkForm()) {
					
					//QQ
					App.setCache("tx_qqNum", qqNumString);
					//支付宝
					App.setCache("tx_alipayUser", alipayUserString);
					App.setCache("tx_alipayNum", alipayNumString);
					//财付通
					App.setCache("tx_tenpayUser", tenpayUserString);
					App.setCache("tx_tenpayNum", tenpayNumString);
					//手机
					App.setCache("tx_mobileNum", mobileNumString);
					//提现
					App.setCache("tx_bankName", bankNameString);
					App.setCache("tx_bankAddress", bankAddressString);
					App.setCache("tx_bankUser", bankUserString);
					App.setCache("tx_bankNum", bankNumString);

					
					
					
					
					//showMsg("通过了所有的验证,向服务器提交");
					//
					params = new ArrayList<NameValuePair>();
					
					
					params.add(new BasicNameValuePair("paypass", paypassString));
					//
					params.add(new BasicNameValuePair("ajax", "1"));
					params.add(new BasicNameValuePair("paytype", payWay));
					//
					params.add(new BasicNameValuePair("qqnum", qqNumString));
					params.add(new BasicNameValuePair("qqamount", qqAmountInteger.toString()));
					//
					params.add(new BasicNameValuePair("mobilenum", mobileNumString));
					params.add(new BasicNameValuePair("mobileamount", mobileAmountInteger.toString()));
					//
					params.add(new BasicNameValuePair("alipaynum", alipayNumString));
					params.add(new BasicNameValuePair("alipayuser", alipayUserString));
					params.add(new BasicNameValuePair("alipayamount", alipayAmountInteger.toString()));
					//
					params.add(new BasicNameValuePair("tenpaynum", tenpayNumString));
					params.add(new BasicNameValuePair("tenpayuser", tenpayUserString));
					params.add(new BasicNameValuePair("tenpayamount", tenpayAmountInteger.toString()));
					//
					params.add(new BasicNameValuePair("bankname", bankNameString));
					params.add(new BasicNameValuePair("banknum", bankNumString));
					params.add(new BasicNameValuePair("bankaddress", bankAddressString));
					params.add(new BasicNameValuePair("bankuser", bankUserString));
					params.add(new BasicNameValuePair("bankamount", bankAmountInteger.toString()));
					
					new runBackground().execute();
					
				} 
				
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

	
	// 从服务器获取最新的版本信息
	private String submit() {
		// 传递参数 用了post不穿个参数 报空指针 错误
	
		
		String resultString = App.webservice.pay(params);
		
		
		return resultString;
	}
	
	
	
	
	//提现到QQ
	private void qqForm(){
		String[] qqStrings = { "1 个Q币", "5 个Q币", "10 个Q币", "20 个Q币", "30 个Q币" };
		Spinner qqSpinner = (Spinner) findViewById(R.id.form_spinner_qqAmount);
		// 将可选内容与ArrayAdapter连接起来
		ArrayAdapter<String> qqAdapter = new ArrayAdapter<String>(
				getApplicationContext(),
				R.layout.my_spinner, qqStrings);
		// 设置下拉列表的风格
		qqAdapter.setDropDownViewResource(R.layout.my_spinner_list);
		// 将adapter 添加到spinner中
		qqSpinner.setAdapter(qqAdapter);
		// 添加事件Spinner事件监听
		//qqSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());			
	}
	//提现到话费
	private void mobileForm(){
//		提现（话题）10元  30元 50元 100元 200元  300元 500元 1000元 
		String[] m = { "10 元", "30 元", "50 元", "100 元", "200 元", "300 元", "500 元", "1000 元" };
		Spinner spinner = (Spinner) findViewById(R.id.form_spinner_mobileAmount);
		// 将可选内容与ArrayAdapter连接起来
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(),
				R.layout.my_spinner, m);
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(R.layout.my_spinner_list);
		// 将adapter 添加到spinner中
		spinner.setAdapter(adapter);
		// 添加事件Spinner事件监听
//		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());		
	}
	//提现到支付宝
	private void alipayForm(){
//		提现（话题）10元  30元 50元 100元 200元  300元 500元 1000元 
		String[] m = { "10 元", "30 元", "50 元", "100 元", "200 元", "300 元", "500 元", "1000 元" };
		Spinner spinner = (Spinner) findViewById(R.id.form_spinner_alipayAmount);
		// 将可选内容与ArrayAdapter连接起来
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(),
				R.layout.my_spinner, m);
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(R.layout.my_spinner_list);
		// 将adapter 添加到spinner中
		spinner.setAdapter(adapter);
		// 添加事件Spinner事件监听
//		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());		
	}
	//提现到财付通
	private void tenpayForm(){
//		提现（话题）10元  30元 50元 100元 200元  300元 500元 1000元 
		String[] m = { "10 元", "30 元", "50 元", "100 元", "200 元", "300 元", "500 元", "1000 元" };
		Spinner spinner = (Spinner) findViewById(R.id.form_spinner_tenpayAmount);
		// 将可选内容与ArrayAdapter连接起来
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(),
				R.layout.my_spinner, m);
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(R.layout.my_spinner_list);
		// 将adapter 添加到spinner中
		spinner.setAdapter(adapter);
		// 添加事件Spinner事件监听
//		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());		
	}
	//提现到银行卡
	private void bankForm(){
//		提现（话题）10元  30元 50元 100元 200元  300元 500元 1000元 
		String[] m = { "10 元", "30 元", "50 元", "100 元", "200 元", "300 元", "500 元", "1000 元" };
		Spinner spinner = (Spinner) findViewById(R.id.form_spinner_bankAmount);
		// 将可选内容与ArrayAdapter连接起来
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(),
				R.layout.my_spinner, m);
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(R.layout.my_spinner_list);
		// 将adapter 添加到spinner中
		spinner.setAdapter(adapter);
		// 添加事件Spinner事件监听
//		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());		
	}
	//银行名称
	private void bankNameForm(){
		//工商银行、农业银行、中国银行、建设银行、交通银行
		String[] m = { "工商银行", "农业银行", "建设银行", "邮政银行", "招商银行", "中国银行" };
		Spinner spinner = (Spinner) findViewById(R.id.form_spinner_bankName);
		// 将可选内容与ArrayAdapter连接起来
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(),
				R.layout.my_spinner, m);
		// 设置下拉列表的风格
		adapter.setDropDownViewResource(R.layout.my_spinner_list);
		// 将adapter 添加到spinner中
		spinner.setAdapter(adapter);
		// 添加事件Spinner事件监听
//		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());		
	}
	
	
	
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
				long arg3) {
			LinearLayout qqlineLayout = (LinearLayout) findViewById(R.id.form_LinearLayout_qq);
			LinearLayout mobilelineLayout = (LinearLayout) findViewById(R.id.form_LinearLayout_mobile);
			LinearLayout alipaylineLayout = (LinearLayout) findViewById(R.id.form_LinearLayout_alipay);
			LinearLayout tenpaylineLayout = (LinearLayout) findViewById(R.id.form_LinearLayout_tenpay);
			LinearLayout banklineLayout = (LinearLayout) findViewById(R.id.form_LinearLayout_bank);
			
			switch (pos) {
			case 0:
				payWay = "qq";
				qqlineLayout.setVisibility(View.VISIBLE);
				mobilelineLayout.setVisibility(View.GONE);
				alipaylineLayout.setVisibility(View.GONE);
				tenpaylineLayout.setVisibility(View.GONE);
				banklineLayout.setVisibility(View.GONE);				
				break;
			case 1:
				payWay = "mobile";
				qqlineLayout.setVisibility(View.GONE);
				mobilelineLayout.setVisibility(View.VISIBLE);
				alipaylineLayout.setVisibility(View.GONE);
				tenpaylineLayout.setVisibility(View.GONE);
				banklineLayout.setVisibility(View.GONE);
				break;
			case 2:
				payWay = "alipay";
				qqlineLayout.setVisibility(View.GONE);
				mobilelineLayout.setVisibility(View.GONE);
				alipaylineLayout.setVisibility(View.VISIBLE);
				tenpaylineLayout.setVisibility(View.GONE);
				banklineLayout.setVisibility(View.GONE);
				break;
			case 3:
				payWay = "tenpay";
				qqlineLayout.setVisibility(View.GONE);
				mobilelineLayout.setVisibility(View.GONE);
				alipaylineLayout.setVisibility(View.GONE);
				tenpaylineLayout.setVisibility(View.VISIBLE);
				banklineLayout.setVisibility(View.GONE);				
				break;
			case 4:
				payWay = "bank";
				qqlineLayout.setVisibility(View.GONE);
				mobilelineLayout.setVisibility(View.GONE);
				alipaylineLayout.setVisibility(View.GONE);
				tenpaylineLayout.setVisibility(View.GONE);
				banklineLayout.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
		
			
//			Toast.makeText(parent.getContext(),
//					"你选择了 " + parent.getItemAtPosition(pos).toString(),
//					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// nothing to do
		}
	}
	
	//检测数据的合法性
	private boolean chkForm(){
		
		
		//检测 是否输入了  提现密码
	
		if (TextUtils.isEmpty(payPassEditText.getText())) {
			showMsg("提现密码不得为空");
			return false;
		}
	
		
		paypassString = payPassEditText.getText().toString();
		
		//获取所有的组件
//		qqNumEditText = (EditText)findViewById(R.id.form_editText_qqNum);
//		mobileNumEditText= (EditText)findViewById(R.id.form_editText_mobileNum);
//		alipayNumEditText = (EditText) findViewById(R.id.form_editText_alipayNum);
//		tenpayNumEditText = (EditText) findViewById(R.id.form_editText_tenpayNum);
//		bankNumEditText = (EditText) findViewById(R.id.form_editText_bankNum);
//
//		alipayUserEditText = (EditText) findViewById(R.id.form_editText_alipayUser);
//		tenpayUserEditText = (EditText) findViewById(R.id.form_editText_tenpayUser);
//		bankUserEditText = (EditText) findViewById(R.id.form_editText_bankUser);
//		
//		bankAddressEditText = (EditText) findViewById(R.id.form_editText_bankAddress);
		
		
		qqAmountSpinner = (Spinner) findViewById(R.id.form_spinner_qqAmount);
		mobileAmountSpinner = (Spinner) findViewById(R.id.form_spinner_mobileAmount);
		alipayAmountSpinner = (Spinner) findViewById(R.id.form_spinner_alipayAmount);
		tenpayAmountSpinner = (Spinner) findViewById(R.id.form_spinner_tenpayAmount);
		bankAmountSpinner = (Spinner) findViewById(R.id.form_spinner_bankAmount);
		
		
//		bankNameSpinner = (Spinner) findViewById(R.id.form_spinner_bankName);
		
		
		qqAmountString = qqAmountSpinner.getSelectedItem().toString();
		mobileAmountString = mobileAmountSpinner.getSelectedItem().toString();
		alipayAmountString = alipayAmountSpinner.getSelectedItem().toString();
		tenpayAmountString = tenpayAmountSpinner.getSelectedItem().toString();
		bankAmountString = bankAmountSpinner.getSelectedItem().toString();
		bankNameString = bankNameSpinner.getSelectedItem().toString();
		
		
		//把字符串截取 并且切割出 里边的数字 转为 数字类型
		
		qqAmountInteger = Integer.valueOf(qqAmountString.split(" ")[0]);
		mobileAmountInteger = Integer.valueOf(mobileAmountString.split(" ")[0]);
		alipayAmountInteger = Integer.valueOf(alipayAmountString.split(" ")[0]);
		tenpayAmountInteger = Integer.valueOf(tenpayAmountString.split(" ")[0]);
		bankAmountInteger = Integer.valueOf(bankAmountString.split(" ")[0]);
		
		
		
		
		
		
		//private Spinner qqAmountSpinner,mobileAmountSpinner,alipayAmountSpinner,tenpayAmountSpinner,bankAmountSpinner,bankNameSpinner;
		
		
//		//定义所有的组件
//		private EditText qqNumEditText,mobileNumEditText,alipayNumEditText,tenpayNumEditText,bankNumEditText;
//		private EditText alipayUserEditText,tenpayUserEditText,bankUserEditText;
//		private EditText bankAddressEditText;
//		//定义所有的spinner
//		private Spinner qqAmountSpinner,mobileAmountSpinner,alipayAmountSpinner,tenpayAmountSpinner,bankAmountSpinner,bankNameSpinner;
//		
		
		
		
		
		//qq充值
		if (payWay.equals("qq")) {
			// 判断QQ的数据是否合法
			if (TextUtils.isEmpty(qqNumEditText.getText())) {
				showMsg("QQ号码不得为空");
				return false;
			}
			
		}
		//话费充值
		if (payWay.equals("mobile")) {
			if (TextUtils.isEmpty(mobileNumEditText.getText())) {
				showMsg("手机号不得为空");
				return false;
			}
			
			

			
		}
		//支付宝
		if (payWay.equals("alipay")) {
			if (TextUtils.isEmpty(alipayNumEditText.getText())) {
				showMsg("支付宝帐号不得为空");
				return false;
			}
			if (TextUtils.isEmpty(alipayUserEditText.getText())) {
				showMsg("用户 姓名不得为空");
				return false;
			}
		
		}
		
		//财付通
		if (payWay.equals("tenpay")) {
			if (TextUtils.isEmpty(tenpayNumEditText.getText())) {
				showMsg("财付通帐号不得为空");
				return false;
			}
			if (TextUtils.isEmpty(tenpayUserEditText.getText())) {
				showMsg("用户 姓名不得为空");
				return false;
			}

		}
		
		// 提现到银行卡
		if (payWay.equals("bank")) {
			if (TextUtils.isEmpty(bankNumEditText.getText())) {
				showMsg("银行卡号不得为空");
				return false;
			}
			if (TextUtils.isEmpty(bankAddressEditText.getText())) {
				showMsg("开户行地址不得为空");
				return false;
			}
			if (TextUtils.isEmpty(bankUserEditText.getText())) {
				showMsg("姓名不得为空");
				return false;
			}

		}
		
		
		
//		private String qqNumString,mobileNumString,alipayNumString,tenpayNumString,bankNumString;
//		private String alipayUserString,tenpayUserString,bankUserString;
//		private String bankAddString;
//		
//		private String qqAmountString,mobileAmountString,alipayAmountString,tenpayAmountString,bankAmountString,bankNameString;
		
		//Q币的
		qqNumString = qqNumEditText.getText().toString();
		//话费的
		mobileNumString = mobileNumEditText.getText().toString();
		//支付宝
		alipayNumString = alipayNumEditText.getText().toString();
		alipayUserString = alipayUserEditText.getText().toString();
		//财付通 
		tenpayNumString = tenpayNumEditText.getText().toString();
		tenpayUserString = tenpayUserEditText.getText().toString();
		//银行卡
		bankNumString = bankNumEditText.getText().toString();
		bankUserString = bankUserEditText.getText().toString();
		bankAddressString = bankAddressEditText.getText().toString();
		
		
		
		


	    
		
		//showMsg(bankAmountString);
		
		
		return true;
	}
	
	// 异步执行任务
	class runBackground extends AsyncTask<String, String, String> {
		// 开始任务前的 操作
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(FormActivity.this);
			pDialog.setMessage("正在提交..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		// 执行任务
		@TargetApi(Build.VERSION_CODES.FROYO)
		protected String doInBackground(String... args) {
			try {
				return submit();
			} catch (Exception e) {
				// TODO: handle exception
				return e.toString();
			}
		}

		// 任务结束之后的操作
		protected void onPostExecute(String message) {
			pDialog.dismiss();
			if (!App.webservice.getErrorString().equals("")) {
				App.showMsg(App.webservice.getErrorString());
				if (App.webservice.getStatusCodeString().equals("700")) {
					//标记登录失效
					App.setCache("isLogin","0");
					Intent intent = new Intent(getApplicationContext(),
							UserLoginActivity.class);
					startActivity(intent);

				}
			} else {
			
				
				App.showMsg(App.webservice.getInfoString());
				finish();
			}
			
			
			

		}
	}
	
	
	// Toast 显示信息
	private void showMsg(String messageString) {
		Toast.makeText(getApplicationContext(), messageString,
				Toast.LENGTH_SHORT).show();
	}
	// 关闭该activity
	private void _close() {
		this.finish();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//App.taskRefreshUserInfo();
	}

}