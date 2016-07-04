package cn.com.dc.app.client.biz;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import android.util.Log;
import cn.com.dc.app.client.R;
import cn.com.dc.app.client.bean.ArticalPageModel;
import cn.com.dc.app.client.bean.Article;
import cn.com.dc.app.client.bean.News;

import com.alibaba.fastjson.JSON;
import com.sinxiao.mvp.bean.ErrorInfor;
import com.sinxiao.mvp.bean.Rsponse2BeanCallBack;
import com.sinxiao.utils.Configer;

public class BizNews extends CommonBiz implements IBizNews {
	private OperatManager operate = new OperatManager();

	private final String TAG = "BizNews";

	public Future<String> getArticalsByCid(final String cid, final String idx,
			final Rsponse2BeanCallBack<ArticalPageModel> rsp) {
		final String idString = UUID.randomUUID().toString();
		final Future<String> future = OperateDispatcher.getExecutor().submit(
				new Callable<String>() {

					public java.lang.String call() {

						String data = null;
						try {
							data = operate.getArticalsByCid(cid, idx);
							// 解析数据
							ArticalPageModel pageModel = JSON.parseObject(data,
									ArticalPageModel.class);
							
							if (pageModel == null || data == null) {
								ErrorInfor error = new ErrorInfor();
								error.setConnected(false);
								error.setDescriptionRes(R.string.error);
								error.setType(Configer.ERROR);
								rsp.onFailed(error);
							}
							
							List<Article> articalist = pageModel.getArticals();
							List<News> newslist = new ArrayList<News>();
							int size = articalist.size();
							for (int i = 0; i < size; i++) {
								Article art = articalist.get(i);
								News news = new News();
								news.setClicked(art.getClick());
								news.setCommentsum(0);
								news.setContent(art.getContent());
								news.setDefaultnews(0);
								news.setGentime(art.getAdd_time());
								news.setIdnews(art.getId());
								news.setImageurl(art.getImg_url());
								news.setIntro(art.getZhaiyao());
								news.setItemid(art.getCategory_id());
								news.setKeyword(art.getSeo_keywords());
								news.setNewsurl(art.getLink_url());
								news.setPasscheked(art.getStatus());
								news.setTitle(art.getTitle());
								news.setUserid(art.getUser_name());
								news.setUsername(art.getUser_name());
								news.setVerifyid(0);
								news.setWriter(0);
								newslist.add(news);
							}
							pageModel.setListnews(newslist);
							rsp.OnResponse(data);
							if (pageModel != null) {
								Log.e(TAG, "getArticalsByCid pageIdx >>> "
										+ pageModel.getPageIdx());
								rsp.onGetBeans(pageModel);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							rsp.onFailed(getErrorType(e));
						} finally {
							// tasks.remove(taskshash.get(idString));
							// taskshash.remove(idString);
							OperateDispatcher.removeOperator(idString);
						}

						return data;
					}
				});
		// TaskModule tm = new TaskModule(future, rsp);
		// taskshash.put(idString, tm);
		// tasks.add(tm);
		OperateDispatcher.putOperator(idString, future, rsp);
		return future;
	}
	public Future<String> getArticalByAid(final String aid,
			final Rsponse2BeanCallBack<Article> rsp) {
		final String idString = UUID.randomUUID().toString();
		final Future<String> future = OperateDispatcher.getExecutor().submit(
				new Callable<String>() {

					public java.lang.String call() {

						String data = null;
						try {
							data = operate.getArticalByAid(aid);
							// Rsponse2BeanCallBack.OnResponse(data);
							
							rsp.OnResponse(data);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							rsp.onFailed(getErrorType(e));
						} finally {
							OperateDispatcher.removeOperator(idString);
						}

						return data;
					}
				});
		OperateDispatcher.putOperator(idString, future, rsp);
		return future;

	}

	public Future<String> postInforAid(final String aid,final String uid,final String value,
			final Rsponse2BeanCallBack<ArticalPageModel> rsp) {
		final String idString = UUID.randomUUID().toString();
		final Future<String> future = OperateDispatcher.getExecutor().submit(
				new Callable<String>() {

					public java.lang.String call() {

						String data = null;
						try {
							data = operate.addReviewsByAid(aid,uid, value);
							// 解析获得的xml数据
							ArticalPageModel pageModel = JSON.parseObject(data,
									ArticalPageModel.class);
							
							rsp.onGetBeans(pageModel);
							
							rsp.OnResponse(data);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							rsp.onFailed(getErrorType(e));
						} finally {
							OperateDispatcher.removeOperator(idString);
						}
						return data;
					}
				});
		OperateDispatcher.putOperator(idString, future, rsp);
		return future;
	}

	@Override
	public Future<String> getReviewsByAid(final String aid, final String idx,
			final Rsponse2BeanCallBack<ArticalPageModel> rsp) {
		final String idString = UUID.randomUUID().toString();
		final Future<String> future = OperateDispatcher.getExecutor().submit(
				new Callable<String>() {

					public java.lang.String call() {

						String data = null;
						try {
							data = operate.getReviewsByAid(aid, idx);
							// 解析获得的xml数据
							ArticalPageModel pageModel = JSON.parseObject(data,
									ArticalPageModel.class);
							if(pageModel!=null){
								Log.e(TAG, " size >"+pageModel.getRecordcount());
								if(pageModel.getComments()!=null){
									Log.e(TAG, " getListcomment size > "+pageModel.getComments().size());	
								}else{
									Log.e(TAG, " getListcomment is null");
								}
								
							}
							
							rsp.onGetBeans(pageModel);
							
							rsp.OnResponse(data);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							rsp.onFailed(getErrorType(e));
						} finally {
							OperateDispatcher.removeOperator(idString);
						}
						return data;
					}
				});
		OperateDispatcher.putOperator(idString, future, rsp);
		return future;
	}

}
