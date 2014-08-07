package com.lixunkj.weizhuan.tool;

import android.content.Context;
import android.widget.Toast;

public class MsgTimerUtils {

	private static MsgTimerUtils countDownTimerUtils;

	public static MsgTimerUtils getInstance() {
		if (countDownTimerUtils == null) {
			countDownTimerUtils = new MsgTimerUtils();
		}
		return countDownTimerUtils;
	}

	private long lastTime = -1;

	public boolean checkMsgTimePermission(Context context) {
		long currentTimeMillis = System.currentTimeMillis();
		if (lastTime == -1) {
			return true;
		} else {
			int restTime = (int) (((lastTime + 60 * 1000) - currentTimeMillis) / 1000);
			if (restTime > 0) {
				Toast.makeText(context, "短信功能操作频繁,请在" + restTime + "秒之后使用",
						Toast.LENGTH_SHORT).show();
				return false;
			} else {
				return true;
			}
		}
	}
	
	public void updateTime(){
		lastTime = System.currentTimeMillis();
	}
}
