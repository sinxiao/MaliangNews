package cn.com.dc.app.client.mvp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import cn.com.dc.app.client.bean.ArticalPageModel;
import cn.com.dc.app.client.bean.Article_Comment;
import cn.com.dc.app.client.biz.BizNews;
import cn.com.dc.app.client.biz.IBizNews;

import com.sinxiao.mvp.bean.ErrorInfor;
import com.sinxiao.mvp.bean.Rsponse2BeanCallBack;
import com.sinxiao.mvp.control.ICommenControlView;

public class NewsDetailPressent {

	private IBizNews bizNews;
	private ICommenControlView commenView;
	private INewsDetailView newsdetailview;
	private Context mContext;
	private Handler mHandler = new Handler();

	public NewsDetailPressent(Object obj) {

		bizNews = new BizNews();
		if (obj instanceof Fragment) {
			Fragment fragment = (Fragment) obj;
			Activity act = fragment.getActivity();
			mContext = act.getApplicationContext();
			if (fragment instanceof INewsView) {
				newsdetailview = (INewsDetailView) fragment;
			}
			if (act instanceof ICommenControlView) {
				commenView = (ICommenControlView) act;
			}
		} else if (obj instanceof android.support.v4.app.Fragment) {
			android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) obj;
			Activity act = fragment.getActivity();
			mContext = act.getApplicationContext();
			if (fragment instanceof INewsView) {
				newsdetailview = (INewsDetailView) fragment;
			}
			if (act instanceof ICommenControlView) {
				commenView = (ICommenControlView) act;
			}
		} else {
			newsdetailview = (INewsDetailView) obj;
			commenView = (ICommenControlView) obj;
		}
		if (commenView == null) {
			Log.e("", "NewsPressent init error ICommenControlView is null");
		}
		if (newsdetailview == null) {
			Log.e("", "NewsPressent init error INewsView is null");
		}
		if (mContext != null) {
			mHandler = new Handler(mContext.getMainLooper());
		}
	}
	
	public void addReview(String aid,String content)
	{
		String uid = "1";
		bizNews.postInforAid(aid,uid,content, new Rsponse2BeanCallBack<ArticalPageModel>() {

			public void onGetBeans(ArticalPageModel t) {
				if(t!=null){
					if(t.getRet()==0){
						commenView.showToast("添加评论成功");
					}else{
						commenView.showToast("添加评论失败");
					}
					
				}
			}

			public void OnResponse(String data) {
				
			}

			public void onFailed(ErrorInfor error) {
				if(commenView!=null){
					commenView.showToast("get comments error ...");
				}
			}
			
		});
	}
	private final String TAG ="NewsDetailPressent";
	public void showArticalComments(String aid,String idx)
	{
		bizNews.getReviewsByAid(aid, idx, new Rsponse2BeanCallBack<ArticalPageModel>() {

			public void onGetBeans(ArticalPageModel t) {
				if(t!=null){
					if(t.getComments()==null){
						t.setComments(new ArrayList<Article_Comment>());
					}
					if(newsdetailview!=null){
						Log.e(TAG, "newsdetailview is not null");
						newsdetailview.showComments(t.getComments());
					}else{
						Log.e(TAG, "newsdetailview is null");
					}
				}
			}

			public void OnResponse(String data) {
				
			}

			public void onFailed(ErrorInfor error) {
				if(commenView!=null){
					commenView.showToast("get comments error ...");
				}
			}
			
		});
	}

}
