package com.sinxiao.mvp.bean;



public abstract class Rsponse2BeanCallBack<T> implements ResponseCallBack,
		IGetBeans<T> {

	private final String TAG = "Rsponse2BeanCallBack";
	public abstract void onGetBeans(T t);

	public abstract void OnResponse(String data);

	public abstract void onFailed(ErrorInfor error);

}
