package cn.com.dc.app.client.mvp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import cn.com.dc.app.client.CommenNewsFragment;
import cn.com.dc.app.client.bean.ArticalPageModel;
import cn.com.dc.app.client.biz.BizNews;
import cn.com.dc.app.client.biz.IBizNews;

import com.sinxiao.mvp.bean.ErrorInfor;
import com.sinxiao.mvp.bean.Rsponse2BeanCallBack;
import com.sinxiao.mvp.control.ICommenControlView;

public class NewsPressent {

	private IBizNews bizNews;
	private ICommenControlView commenView;
	private INewsView newsview;
	private Context mContext;
	private Handler mHandler = new Handler();

	public NewsPressent(Object obj) {

		bizNews = new BizNews();
		if (obj instanceof Fragment) {
			Fragment fragment = (Fragment) obj;
			Activity act = fragment.getActivity();
			mContext = act.getApplicationContext();
			if (fragment instanceof INewsView) {
				newsview = (INewsView) fragment;
			}
			if (act instanceof ICommenControlView) {
				commenView = (ICommenControlView) act;
			}
		} else if (obj instanceof android.support.v4.app.Fragment) {
			android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) obj;
			Activity act = fragment.getActivity();
			mContext = act.getApplicationContext();
			if (fragment instanceof INewsView) {
				newsview = (INewsView) fragment;
			}
			if (act instanceof ICommenControlView) {
				commenView = (ICommenControlView) act;
			}
		} else {
			newsview = (INewsView) obj;
			commenView = (ICommenControlView) obj;
		}
		if (commenView == null) {
			Log.e("", "NewsPressent init error ICommenControlView is null");
		}
		if (newsview == null) {
			Log.e("", "NewsPressent init error INewsView is null");
		}
		if (mContext != null) {
			mHandler = new Handler(mContext.getMainLooper());
		}
	}

	public void bindArticals(String cid, String idx) {
		int type = Integer.parseInt(cid);
		if (type == CommenNewsFragment.VIEW_AIR) {
			type = 6;
		} else if (type == CommenNewsFragment.VIEW_NEWS) {
			type = 12;
		} else if (type == CommenNewsFragment.VIEW_OLDS) {
			type = 11;
		}
		bizNews.getArticalsByCid(type + "", idx,
				new Rsponse2BeanCallBack<ArticalPageModel>() {

					@Override
					public void onGetBeans(final ArticalPageModel t) {
						if (t != null && t.getListnews() != null) {
							if (newsview != null) {
								mHandler.post(new Runnable() {
									public void run() {
										commenView.removeWiat();
										newsview.showArticals(t.getListnews());
									}
								});
							}
						}
					}

					public void OnResponse(String data) {

					}

					public void onFailed(final ErrorInfor error) {
						if (commenView != null) {
							mHandler.post(new Runnable() {
								public void run() {
									commenView.removeWiat();
//									commenView.showError("error",
//											error.getDescription());
								}
							});

						}

					}
				});
	}

}
