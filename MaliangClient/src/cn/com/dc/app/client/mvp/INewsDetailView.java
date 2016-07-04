package cn.com.dc.app.client.mvp;

import java.util.List;

import cn.com.dc.app.client.bean.Article_Comment;
import cn.com.dc.app.client.bean.News;

public interface INewsDetailView {
	
	/**
	 * 显示评论列表
	 * 
	 * @param actors
	 */
	public void showComments(List<Article_Comment> commentlist);

	/**
	 * 显示相关新闻
	 * 
	 * @param actor
	 */
	public void showAssociatNews(List<News> newslist);
	
}
