package com.lixunkj.weizhuan.fragment;

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
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.google.gson.Gson;
import com.lixunkj.weizhuan.App;
import com.lixunkj.weizhuan.BaseFragment;
import com.lixunkj.weizhuan.R;
import com.lixunkj.weizhuan.entities.User;
import com.lixunkj.weizhuan.module.home.CarouselUntils;
import com.lixunkj.weizhuan.module.home.ExchangeActivity;
import com.lixunkj.weizhuan.module.home.PopularizeActivity;
import com.lixunkj.weizhuan.module.home.ViewPagerAdapter;
import com.lixunkj.weizhuan.module.login.UserLoginActivity;
import com.lixunkj.weizhuan.network.InitVolley;
import com.lixunkj.weizhuan.network.NetInterface;
import com.lixunkj.weizhuan.network.NetWorkStringCallBack;
import com.lixunkj.weizhuan.network.NetWorkUtils;
import com.lixunkj.weizhuan.view.CarouselView;
import com.umeng.analytics.MobclickAgent;

public class HomeFragment extends BaseFragment {
	private TextView titleBar_tv;
	private CarouselView carouselView;
	private ViewPager viewPager;
	private ViewPagerAdapter viewPagerAdapter;
	private App app;
	private ArrayList<ImageView> list;
	private CarouselUntils carouselUntils;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.tab_home, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		app = (App) getActivity().getApplication();
		initTitlebar();
		findView();
		getCarouselViewData();
		setCarouselView();
	}

	private void initTitlebar() {
		titleBar_tv = (TextView) getView().findViewById(R.id.titlebar_tv);
		titleBar_tv.setText("微赚");
	}

	private void findView() {
		carouselView = (CarouselView) getView().findViewById(R.id.home_carouse);
		viewPager = (ViewPager) getView().findViewById(R.id.carouseview);
	}

	private void getCarouselViewData() {
		list = new ArrayList<ImageView>();

		NetWorkUtils.getInstance().work(
				NetInterface.getInstance().carouselData(),
				new NetWorkStringCallBack() {
					@Override
					public void onComplete(boolean success, String string) {
						carouselUntils = new CarouselUntils(string);
						int size = carouselUntils.list.size();
						for (int i = 0; i < size; i++) {
							ImageView img = new ImageView(getActivity());
							ImageLoader imgLoader = InitVolley.getInstance()
									.getImageLoader();

							@SuppressWarnings("static-access")
							ImageListener listener = imgLoader
									.getImageListener(img, R.drawable.loading,
											R.drawable.loading);
							imgLoader.get(carouselUntils.list.get(i), listener);
							img.setScaleType(ScaleType.FIT_XY);
							list.add(img);
						}
						viewPagerAdapter.notifyDataSetChanged();
					}
				});
	}

	private void setCarouselView() {
		viewPagerAdapter = new ViewPagerAdapter(list);
		carouselView.setData(list, viewPagerAdapter);
		carouselView.startCarouse();
		viewPager.setOnPageChangeListener(onPageChangeListener);
	}

	OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};

	public void FragmentClick(View v) {
		switch (v.getId()) {
		case R.id.home_imgbtn_daliysigin:
			daliySignDialog();
			break;
		case R.id.home_imgbtn_daliyshake:
			 startActivity(new Intent(getActivity(),
			 PopularizeActivity.class));
			break;
		case R.id.home_imgbtn_taskhot:
			// startActivity(new Intent(getActivity(),
			// WallWapstuanActivity.class));
			break;
		case R.id.home_imgbtn_taskforfew:
			 startActivity(new Intent(getActivity(), ExchangeActivity.class));
			break;
		case R.id.home_imgbtn_exchangerecord:
			// startActivity(new Intent(getActivity(), BankLogActivity.class));
			break;
		case R.id.home_imgbtn_taskrecord:
			// startActivity(new Intent(getActivity(),
			// ShareTencentActivity.class));
			break;
		}
	}

	public void daliySignDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("签到一次可获得1积分，连续7天额外赠送10分，连续30天额外赠送60分！")
				.setTitle("签到提示")
				.setPositiveButton("立即签到",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								new sign().execute();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public class sign extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgress("正在签到..");
		}

		protected String doInBackground(String... args) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", "1"));
			String resultString = app.getApi().userSign(params);
			return resultString;
		}

		protected void onPostExecute(String message) {
			dismissProgress();

			if (!app.webservice.getErrorString().equals("")) {
				app.showMsg(app.webservice.getErrorString());
				if (app.webservice.getStatusCodeString().equals("700")) {
					 Intent intent = new Intent(getActivity(),
					 UserLoginActivity.class);
					 startActivity(intent);
				}
				return;
			}

			try {
				int status = Integer.parseInt(app.webservice
						.getStatusCodeString());
				if (status == 1) {
					MobclickAgent.onEvent(getActivity(), "signUp",
							app.getCache("uid"));
				}
				JSONObject jsonObj = new JSONObject(message);
				Gson gson = new Gson();
				User user = gson.fromJson(message, User.class);
				app.setUserInfo(jsonObj);
				app.showMsg(app.webservice.infoString);
			} catch (JSONException e) {
				e.printStackTrace();
				app.showMsg(e.toString());
			}
		}
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onResume() {
		super.onResume();
	}
}
