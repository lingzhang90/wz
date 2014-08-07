package com.lixunkj.weizhuan.module.home;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lixunkj.weizhuan.App;
import com.lixunkj.weizhuan.BaseActivity;
import com.lixunkj.weizhuan.R;
import com.lixunkj.weizhuan.module.login.UserLoginActivity;
import com.umeng.analytics.MobclickAgent;

public class BankLogActivity extends BaseActivity {

	App App;

	// 定义主listView 用于展示数据
	private ListView listView;
	// 定义当前页数
	private int n = 0;
	// 定义每页显示数目
	private int perPage = 8;
	private int totalPage;

	// 定义数据源
	private SimpleAdapter adpter;
	// 定义加载更多按钮
	private ArrayList<HashMap<String, Object>> list123 = new ArrayList<HashMap<String, Object>>();

	private LinearLayout loadMoreLinearLayout;
	private TextView loadMoreTextView;
	private ProgressBar loadMoreProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.banklog);

		App = (App) getApplication(); // 获得我们的应用程序MyApplication
		// 设置标题
		TextView titleTextView = (TextView) findViewById(R.id.titlebar_tv);
		titleTextView.setText("兑换记录");
		// 左边的按钮
		ImageView leftButton = (ImageView) findViewById(R.id.titlebar_back);
		// leftButton.setVisibility(View.GONE);
		leftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_close();
			}
		});
		// 右边的按钮
		TextView rightButton = (TextView) findViewById(R.id.titlebar_right_text);
		// rightButton.setVisibility(View.GONE);
		rightButton.setText("刷新");

		rightButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		rightButton.setVisibility(View.INVISIBLE);

		listView = (ListView) findViewById(R.id.banklog_listView_log);
		// 加载更多的按钮添加到listView
		View footerView = ((LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.banklog_loadmore, null, false);
		listView.addFooterView(footerView);
		// 默认加载第一页的数据
		// new load().execute();
		// 设置适配器
		adpter = new SimpleAdapter(this, list123, R.layout.banklog_item,
				new String[] { "id", "amount", "content", "addtime" },
				new int[] { R.id.id, R.id.amount, R.id.content, R.id.addtime });
		listView.setAdapter(adpter);
		// 给加载更多的按钮添加单击事件
		loadMoreLinearLayout = (LinearLayout) findViewById(R.id.banklog_LinearLayout_LoadMore);
		loadMoreTextView = (TextView) findViewById(R.id.banklog_textView_loadMore);
		loadMoreProgressBar = (ProgressBar) findViewById(R.id.banklog_progressBar_loadMore);
		loadMoreLinearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new load().execute();
			}
		});
		loadMoreProgressBar.setVisibility(View.GONE);
		new load().execute();
	}
	// 友盟统计重写方法
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	/**
	 * 异步加载数据
	 * */
	class load extends AsyncTask<String, String, String> {
		/**
		 * 开始任务前的 操作
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// 将按钮的值改为 正在加载中
			loadMoreLinearLayout.setEnabled(true);
			loadMoreProgressBar.setVisibility(View.VISIBLE);
			loadMoreTextView.setText("正在努力加载中.......");
		}
		// 加载下一页
		@TargetApi(Build.VERSION_CODES.FROYO)
		protected String doInBackground(String... args) {
			try {
				return loadPage(n = n + 1);
			} catch (Exception e) {
				// TODO: handle exception
				return e.toString();
			}
		}
		/**
		 * 任务结束之后的操作
		 * **/
		protected void onPostExecute(String message) {
			if (!App.webservice.getErrorString().equals("")) {
				App.showMsg(App.webservice.getErrorString());
				if (App.webservice.getStatusCodeString().equals("700")) {
					// 标记登录失效
					App.setCache("isLogin", "0");
					Intent intent = new Intent(getApplicationContext(),
							UserLoginActivity.class);
					startActivity(intent);
				}
			} else {
				try {
					JSONObject jsonObj = new JSONObject(message);
					JSONArray data = new JSONArray(jsonObj.getString("data"));
					for (int i = 0; i < data.length(); i++) {
						JSONObject item = data.getJSONObject(i);
						int id = item.getInt("id");
						String amount = item.getString("amount");
						String content = item.getString("content22");
						String addtime = item.getString("addtime");
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("id", id);
						map.put("amount", amount);
						map.put("content", content);
						String aaaString;
						try {
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yy-MM-dd hh:mm");
							long lcc_time = Long.valueOf(addtime);
							aaaString = sdf.format(new Date(lcc_time * 1000L));
						} catch (Exception e) {
							// TODO: handle exception
							aaaString = "xx-xx-xx xx:xx";
						}
						map.put("addtime", aaaString);
						list123.add(map);
					}
					// 计算总页数
					totalPage = (int) Math.ceil(Float.parseFloat(jsonObj
							.getString("total")) / perPage);
					App.showMsg("共" + totalPage + "页，共"
							+ jsonObj.getString("total") + "条，当前页" + n);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					App.showMsg(e.toString());
				}
			}
			// 通知适配器 有数据 改变
			adpter.notifyDataSetChanged();
			// 隐藏 进度条
			loadMoreProgressBar.setVisibility(View.GONE);
			// 将加载更多的按钮 还原为原状态
			if (n >= totalPage) {
				loadMoreTextView.setText("已加载全部");
				loadMoreLinearLayout.setEnabled(false);
				// loadMoreLinearLayout.setClickable(false);
			} else {
				loadMoreLinearLayout.setEnabled(true);
				loadMoreTextView.setText("加载更多");
			}
		}
	}
	// 加载数据；P是页码，就是读取下一页；perPage定义每页显示的数目，5行
	private String loadPage(int p) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("n", String.valueOf(n)));
		params.add(new BasicNameValuePair("perPage", String.valueOf(perPage)));
		return App.getApi().bankLog(params);
	}
	// 关闭该activity
	private void _close() {
		this.finish();
	}
}
