package com.lixunkj.weizhuan.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 获取手机信息
 * 
 * @author dangtao
 */
public class PhoneUtils {

	private static PhoneUtils phoneUtils;

	public static PhoneUtils getInstance() {
		if (phoneUtils == null) {
			phoneUtils = new PhoneUtils();
		}
		return phoneUtils;
	}

	// sb.append("\nDeviceId(IMEI) = " + tm.getDeviceId());
	// sb.append("\nDeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());
	// sb.append("\nLine1Number = " + tm.getLine1Number());
	// sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
	// sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
	// sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
	// sb.append("\nNetworkType = " + tm.getNetworkType());
	// sb.append("\nPhoneType = " + tm.getPhoneType());
	// sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
	// sb.append("\nSimOperator = " + tm.getSimOperator());
	// sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
	// sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
	// sb.append("\nSimState = " + tm.getSimState());
	// sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
	// sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());

	public List<NameValuePair> getPhoneInfo(Context context,
			List<NameValuePair> params) {

		if (params == null) {
			params = new ArrayList<NameValuePair>();
		}

		// 手机系统：android ,ios ------os
		params.add(new BasicNameValuePair("os", "android"));
		// app版本
		params.add(new BasicNameValuePair("version", getAppVersionName(context)));
		// 手机硬件：三星，htc,苹果------hardware
		params.add(new BasicNameValuePair("hardware", Build.MODEL));
		// 手机系统版本:android 4.1, ios 7---softver
		params.add(new BasicNameValuePair("softver", Build.VERSION.RELEASE));
		// MAC地址：手机mac地址-----mac
		params.add(new BasicNameValuePair("mac", getMac(context)));
		// 内网ip：获取手机内网ip----clientip
		params.add(new BasicNameValuePair("clientip", getIp(context)));
		// IMEI号：手机的唯一标示------imei
		params.add(new BasicNameValuePair("imei", getImei(context)));
		// 联网方式：wifi,2g/3g-------netType
		params.add(new BasicNameValuePair("nettype", getNetWorkType(context)));
		// uuid:唯一标示【】----------uuid
		params.add(new BasicNameValuePair("uuid", getMyUUID(context)));
		// 是否越狱-------------------isjarbroken
		params.add(new BasicNameValuePair("isjarbroken", String
				.valueOf(getRootStatus() ? 1 : 0)));
		// 手机号：--------------------telephone
		params.add(new BasicNameValuePair("telephone", getPhoneNumber(context)));
		params.add(new BasicNameValuePair("screen",
				getScreenString((Activity) context)));
		return params;
	}

	public String getImei(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	public String getMac(Context context) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

	public String getNetWorkType(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		if (netinfo == null) {
			return "";
		} else {
			return netinfo.getTypeName();
		}
	}

	public String getPhoneNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}

	// 得到内网IP
	public String getIp(Context context) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		int ipAddress = info.getIpAddress();
		return (ipAddress & 0xFF) + "." + ((ipAddress >> 8) & 0xFF) + "."
				+ ((ipAddress >> 16) & 0xFF) + "." + ((ipAddress >> 24) & 0xFF);
	}

	// 得到手机UUID
	private String getMyUUID(Context context) {
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String uniqueId = deviceUuid.toString();
		Log.d("debug", "uuid=" + uniqueId);
		return uniqueId;
	}

	// 判断当前手机是否有ROOT权限
	public boolean getRootStatus() {
		boolean bool = false;
		try {
			if ((!new File("/system/bin/su").exists())
					&& (!new File("/system/xbin/su").exists())) {
				bool = false;
			} else {
				bool = true;
			}
			Log.d("TAG", "bool = " + bool);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	public String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "999";
			}
		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	public String getScreenString(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels + "x" + dm.heightPixels;
	}

	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	public String getChongzhiString(String paypassString, String payWay,
			String versionString, String timeString, String number,
			String Amount) {
		String signString = "Paypass=" + paypassString + "&Paytype=" + payWay
				+ "&os=android&Version=" + versionString + "&timetamp="
				+ timeString + "&keypass=4b600697ff47f97e62f20d4c66&number="
				+ number + "&Amount=" + Amount;
//		System.out.println(signString);
//		System.out.println(PasswordUtils.getMD5(signString));
		return signString;
	}

}
