package com.lixunkj.weizhuan.module.register;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.lixunkj.weizhuan.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class UserRegActivity extends Activity {
	private ProgressDialog pDialog;
	private String mobileString;
	private EditText mobileEditText;
	private CheckBox agreementCheckBox;
	private Boolean ageementBoolean = false;
	private Button btnLogin, btnReg;
	private List<NameValuePair> userRegParams;

	// private App App;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.user_reg);
		
	}
}
