package com.lixunkj.weizhuan.fragment;

import com.lixunkj.weizhuan.R;
import com.lixunkj.weizhuan.module.login.UserLoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MineFragment extends Fragment {
	private FragmentActivity mActivity;
	private TextView titleBar_tv;
	private RelativeLayout nologinLayout;
	private ScrollView loginedScrollView;
	private Button loginButton;
	private TextView uidTextView;
	private TextView nickNameTextView;
	private TextView coinTextView;
	private TextView costcoinTextView;
	private TextView restcoinTextView;
	private TextView marksTextView;
	private TextView exchangeRecordView;
	private TextView promoteRecordView;
	private LinearLayout minePointinfo;
	private LinearLayout minePointrecord;
	private LinearLayout minePointexplain;
	private Button logoutButton;
	private Button btnUserInfoButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_mine, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = getActivity();
		initTitle();
		initView();
		initViewListener();

	}

	private void initTitle() {
		titleBar_tv = (TextView) getView().findViewById(R.id.titlebar_tv);
		titleBar_tv.setText("我的信息");
	}

	private void initView() {
		mActivity = getActivity();
		nologinLayout = (RelativeLayout) getActivity().findViewById(
				R.id.mine_nologin);
		loginedScrollView = (ScrollView) getActivity().findViewById(
				R.id.mine_logined);

		loginButton = (Button) mActivity.findViewById(R.id.mine_btn_login);
		uidTextView = (TextView) getActivity()
				.findViewById(R.id.mine_person_id);
		nickNameTextView = (TextView) getActivity().findViewById(
				R.id.mine_person_name);
		coinTextView = (TextView) getActivity().findViewById(
				R.id.mine_point_all);
		costcoinTextView = (TextView) getActivity().findViewById(
				R.id.mine_point_use);
		restcoinTextView = (TextView) getActivity().findViewById(
				R.id.mine_point_unuse);
		marksTextView = (TextView) getActivity().findViewById(
				R.id.mine_person_pointinfo);
		exchangeRecordView = (TextView) getActivity().findViewById(
				R.id.mine_person_pointrecord);
		promoteRecordView = (TextView) getActivity().findViewById(
				R.id.mine_person_pointexplain);
		minePointinfo = (LinearLayout) getActivity().findViewById(
				R.id.mine_pointinfo);
		minePointrecord = (LinearLayout) getActivity().findViewById(
				R.id.mine_pointrecord);
		minePointexplain = (LinearLayout) getActivity().findViewById(
				R.id.mine_pointexplain);
		logoutButton = (Button) getView().findViewById(
				R.id.mine_btn_changeNumber);
		btnUserInfoButton = (Button) getActivity().findViewById(
				R.id.mine_btn_setting);

	}

	private void initViewListener() {
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), UserLoginActivity.class));
			}
		});

		minePointinfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		minePointrecord.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		minePointexplain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});

		btnUserInfoButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
}
		});
		logoutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
	}
}