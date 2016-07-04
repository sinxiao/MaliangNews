package cn.com.dc.app.client.mvp;

import java.util.concurrent.Future;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import cn.com.dc.app.client.R;
import cn.com.dc.app.client.bean.ArticalPageModel;
import cn.com.dc.app.client.bean.User;
import cn.com.dc.app.client.biz.BizUser;
import cn.com.dc.app.client.biz.IBizUser;

import com.sinxiao.mvp.bean.ErrorInfor;
import com.sinxiao.mvp.bean.Rsponse2BeanCallBack;
import com.sinxiao.mvp.control.ICommenControlView;

public class UserPressent {

	private IUserView userView;
	private ICommenControlView commenView;
	private Context mContext;
	private IBizUser bizUser;
	private Handler mHandler = new Handler();

	public UserPressent(Object obj) {

		bizUser = new BizUser();
		if (obj instanceof Fragment) {
			Fragment fragment = (Fragment) obj;
			Activity act = fragment.getActivity();
			mContext = act.getApplicationContext();
			if (fragment instanceof INewsView) {
				userView = (IUserView) fragment;
			}
			if (act instanceof ICommenControlView) {
				commenView = (ICommenControlView) act;
			}
		} else if (obj instanceof android.support.v4.app.Fragment) {
			android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) obj;
			Activity act = fragment.getActivity();
			mContext = act.getApplicationContext();
			if (fragment instanceof INewsView) {
				userView = (IUserView) fragment;
			}
			if (act instanceof ICommenControlView) {
				commenView = (ICommenControlView) act;
			}
		} else {
			userView = (IUserView) obj;
			commenView = (ICommenControlView) obj;
		}
		if (commenView == null) {
			Log.e("", "NewsPressent init error ICommenControlView is null");
		}
		if (userView == null) {
			Log.e("", "NewsPressent init error INewsView is null");
		}
		if (mContext != null) {
			mHandler = new Handler(mContext.getMainLooper());
		}

	}

	public void login(String uname, String pwd) {
		commenView.showWiat(mContext.getString(R.string.tip),
				mContext.getString(R.string.belogin));
		Future<String> future = bizUser.login(uname, pwd,
				new Rsponse2BeanCallBack<ArticalPageModel>() {

					public void onGetBeans(ArticalPageModel t) {

					}

					public void onFailed(ErrorInfor error) {

					}

					public void OnResponse(String data) {

					}
				});

	}

	public void reg(User user) {
		Future<String> future = bizUser.reg(user,
				new Rsponse2BeanCallBack<ArticalPageModel>() {

					public void onGetBeans(ArticalPageModel t) {

					}

					public void onFailed(ErrorInfor error) {

					}

					public void OnResponse(String data) {

					}
				});
	}

	public void update(User user) {
		Future<String> future = bizUser.update(user,
				new Rsponse2BeanCallBack<ArticalPageModel>() {

					public void onGetBeans(ArticalPageModel t) {

					}

					public void onFailed(ErrorInfor error) {

					}

					public void OnResponse(String data) {

					}
				});
	}

}
