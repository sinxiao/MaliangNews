package cn.com.dc.app.client.biz;

import java.util.concurrent.Future;

import cn.com.dc.app.client.bean.ArticalPageModel;
import cn.com.dc.app.client.bean.Article;

import com.sinxiao.mvp.bean.Rsponse2BeanCallBack;

public interface IBizNews {

	/**
	 * 获取新闻列表
	 * @param cid
	 * @param idx
	 * @param rsp
	 * @return
	 */
	public Future<String> getArticalsByCid(String cid, String idx, Rsponse2BeanCallBack<ArticalPageModel> rsp);
	
	/***
	 * 根据aid获得新闻的详情， 如 评论，推荐列表 ，最新版的新闻 ，阅读量，赞 等
	 * @param aid
	 * @param rsp
	 * @return
	 */
	public Future<String> getArticalByAid(String aid,Rsponse2BeanCallBack<Article> rsp);
	
	/**
	 * 对一篇文章提交评论，标签...
	 * @param aid
	 * @param value
	 * @return
	 */
	public Future<String> postInforAid(String aid,String uid,String value,Rsponse2BeanCallBack<ArticalPageModel> rsp);
	
	/**
	 * 对一篇文章获取评论，标签...
	 * @param aid
	 * @param idx
	 * @param rsp
	 * @return
	 */
	public Future<String> getReviewsByAid(String aid,String idx,Rsponse2BeanCallBack<ArticalPageModel> rsp);
	
}
