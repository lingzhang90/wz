package com.lixunkj.weizhuan.module.home;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter {

	ArrayList<? extends View> views;

	public ViewPagerAdapter(ArrayList<? extends View> views) {
		super();
		this.views = views;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup view, int position) {
		view.addView(views.get(position));
		return views.get(position);
	}

	@Override
	public void destroyItem(ViewGroup view, int position, Object object) {
		view.removeView(views.get(position));
	}
}
