package com.sinxiao.mvp.control;

import android.content.DialogInterface;

/**
 * 通用的界面逻辑处理类
 * @author lynch
 *
 */
public interface ICommenControlView {
	
	public void showWiat(String title,String infor);
	public void showWiat(String title,String infor,boolean showCancel,final DialogInterface.OnClickListener oncancelListener);
	public void removeWiat();
	public void showTip(String title,String infor);
	public void showError(String title,String error);
	public void showToast(String msg);
	
	
}
