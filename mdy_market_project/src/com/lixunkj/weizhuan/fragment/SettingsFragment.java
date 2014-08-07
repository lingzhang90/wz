package com.lixunkj.weizhuan.fragment;

import com.lixunkj.weizhuan.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettingsFragment extends Fragment {
	private TextView titlebar_tv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_setting, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initTitle();

	}

	private void initTitle() {
		titlebar_tv=(TextView) getView().findViewById(R.id.titlebar_tv);
		titlebar_tv.setText("积分兑换");
	}



}
