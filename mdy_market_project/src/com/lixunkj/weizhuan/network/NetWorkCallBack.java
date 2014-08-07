package com.lixunkj.weizhuan.network;

import com.lixunkj.weizhuan.entities.BaseResult;

public abstract class NetWorkCallBack<T extends BaseResult> extends
		NetWorkCallBackFather<T> {

	public abstract void onComplete(T response);

}
