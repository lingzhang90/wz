package com.lixunkj.weizhuan;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lixunkj.weizhuan.data.AppConfig;
import com.lixunkj.weizhuanl.utils.AppLogger;

public class InitLocation {

	private static InitLocation initLocalInfo;

	public static InitLocation getInstance() {
		if (initLocalInfo == null) {
			initLocalInfo = new InitLocation();
		}
		return initLocalInfo;
	}

	public LocationClient mLocationClient = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	private BDLocation newBDLocation;

	public void init(Context context) {

		mLocationClient = new LocationClient(context);
		mLocationClient.setAK(AppConfig.BDAppKey);
		mLocationClient.registerLocationListener(myListener);
		initLocationOption();
	}

	public LocationClient getLocationClient() {
		return mLocationClient;
	}

	private class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				if (callBack != null) {
					callBack.callback(null);
				}
				return;

			}
			// int locType = location.getLocType();
			// if (161 == locType || 61 == locType) {
			// option.setScanSpan(1);
			// mLocationClient.setLocOption(option);
			// }
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			sb.append("\nsdk version : ");
			sb.append(mLocationClient.getVersion());
			AppLogger.i(sb.toString());
			// initBDLocation(location);
			newBDLocation = location;
			if (callBack != null) {
				callBack.callback(location);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				if (callBack != null) {
					callBack.callback(null);
				}
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
			AppLogger.i(sb.toString());
			newBDLocation = poiLocation;
			if (callBack != null) {
				callBack.callback(poiLocation);
			}
		}
	}

	private LocationClientOption initLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setServiceName("com.lixunkj.mdy.location");
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		option.setScanSpan(1); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		option.setProdName("mdy");// 设置产品线名称
		option.setPriority(LocationClientOption.GpsFirst);// 设置定位方式的优先级
		option.disableCache(true);
		mLocationClient.setLocOption(option);
		return option;
	}

	public interface LocationCallBackLinstener {
		public void callback(BDLocation location);
	};

	public LocationCallBackLinstener callBack;

	public void setLocationCallBackLinstener(LocationCallBackLinstener callBack) {
		this.callBack = callBack;
	}

	public BDLocation getNewBDLoaction() {
		return newBDLocation;
	}

}
