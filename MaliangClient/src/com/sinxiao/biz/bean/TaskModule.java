package com.sinxiao.biz.bean;

import java.util.concurrent.Future;

import android.os.SystemClock;
import cn.com.dc.app.client.R;
import cn.com.dc.app.client.biz.Utils;

import com.sinxiao.mvp.bean.ErrorInfor;
import com.sinxiao.mvp.bean.Rsponse2BeanCallBack;
import com.sinxiao.utils.Configer;

public class TaskModule {

	public Future<String> future;
	public long beginTimes;
	/**
	 * ��ʱʱ��Ϊ15��
	 */
	long timeout = 15 * 1000;
	public Rsponse2BeanCallBack Rsponse2BeanCallBack;

	public TaskModule(Future<String> mFuture,
			Rsponse2BeanCallBack mRsponse2BeanCallBack) {
		future = mFuture;
		Rsponse2BeanCallBack = mRsponse2BeanCallBack;
		beginTimes = SystemClock.elapsedRealtime();

	}

	public boolean isTimeOut() {
		long diff = SystemClock.elapsedRealtime() - beginTimes;
		boolean bl = timeout < diff;
		Utils.showLog(" diff >> ", " " + diff);
		if (bl) {
			if (Rsponse2BeanCallBack != null) {
				ErrorInfor error = new ErrorInfor();
				error.setConnected(false);
				error.setEnameRes(R.string.controltimeout);
				error.setType(Configer.TIMEOUTCONTROL);
				Rsponse2BeanCallBack.onFailed(error);
			}
		}
		return bl;
		// return false;
	}


}
