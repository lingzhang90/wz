package com.lixunkj.weizhuan;

import com.lixunkj.weizhuan.view.ActionTitlebar;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

	private ProgressDialog pDialog;
	protected ActionTitlebar actionbar;
	public void showProgress(String hintString) {
		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage(hintString);
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}

	public void dismissProgress() {
		if (pDialog != null) {
			pDialog.dismiss();
		}
	}

}
