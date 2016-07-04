package cn.com.dc.app.client.mvp;

import java.util.List;

import cn.com.dc.app.client.bean.News;

public interface INewsView {
	
	/**
	 * 显示新闻列表
	 * 
	 * @param actors
	 */
	public void showArticals(List<News> newslist);

	/**
	 * 显示新闻
	 * 
	 * @param actor
	 */
	public void showArticle(News actor);
	
}
