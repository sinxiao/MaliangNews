package com.sinxiao.mvp.bean;


public interface ResponseCallBack {
	public void OnResponse(String data);

	public void onFailed(ErrorInfor error);

}
