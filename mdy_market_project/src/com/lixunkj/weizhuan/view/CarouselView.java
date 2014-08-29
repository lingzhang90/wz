package com.lixunkj.weizhuan.view;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lixunkj.weizhuan.R;
import com.lixunkj.weizhuan.data.AppConfig;
import com.lixunkj.weizhuan.module.home.ViewPagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;

public class CarouselView extends RelativeLayout {

	public CarouselView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private ScheduledExecutorService executor;
	private CirclePageIndicator indicator;
	private ViewPager viewPager;
	ArrayList<ImageView> list;
	private ArrayList<? extends View> views;

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.layout_carouseview,
				this);

		viewPager = (ViewPager) findViewById(R.id.carouseview);
		indicator = (CirclePageIndicator) findViewById(R.id.carouseview_indicator);
	}

	public void setData(ArrayList<? extends View> views,
			ViewPagerAdapter adapter) {
		this.views = views;
		viewPager.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});

		viewPager.setAdapter(adapter);
		indicator.setViewPager(viewPager);
	}

	public ViewPager getCarouselView() {
		return viewPager;
	}

	public void startCarouse() {
		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleWithFixedDelay(new ViewpagerTask(),
				AppConfig.INITIALDELAY, AppConfig.PERIOD, TimeUnit.SECONDS);
	}

	public void cancleCarouse() {
		if (executor != null)
			executor.shutdown();
	}

	private int currentItem = 0;// �????页�??

	private class ViewpagerTask implements Runnable {

		@Override
		public void run() {
			currentItem = (viewPager.getCurrentItem() + 1) % views.size();
			post(new Runnable() {
				public void run() {
					viewPager.setCurrentItem(currentItem);
				}
			});
		}
	}
}
