package com.lixunkj.weizhuan.network;

import java.lang.ref.SoftReference;
import java.util.HashSet;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapCache implements ImageCache {

	private static int DEFAULT_MEM_CACHE_SIZE = 1024 * 5; // 5MB
	private LruCache<String, Bitmap> mCache;
	// 存放图片的软引用
	public static HashSet<SoftReference<Bitmap>> mReusableBitmaps = new HashSet<SoftReference<Bitmap>>();

	public BitmapCache() {
		mCache = new LruCache<String, Bitmap>(DEFAULT_MEM_CACHE_SIZE) {
			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				// 将超出设置的最大内存限制的图片放入软引用系统会自动回收
				mReusableBitmaps.add(new SoftReference<Bitmap>(oldValue));
			}

			// 重写这个方法计算图片的大小
			@Override
			protected int sizeOf(String key, Bitmap value) {
				final int bitmapSize = getBitmapSize(value) / 1024;
				return bitmapSize == 0 ? 1 : bitmapSize;
			}
		};
	}

	public static int getBitmapSize(Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}

	@Override
	public Bitmap getBitmap(String url) {
		return mCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		mCache.put(url, bitmap);
	}

}
