package cn.com.dc.app.client.bean;

import java.util.List;

import org.litepal.crud.DataSupport;

public class ArticalPageModel extends DataSupport {
	
	private String Error ;
	
	public String getError() {
		return Error;
	}

	public void setError(String error) {
		Error = error;
	}

	private int ret;

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	private int PageIdx, Pagesie, Recordcount;
	private List<Article> Articals;
	private List<News> listnews;
	private List<Article_Comment> Comments;

	
	public List<News> getListnews() {
		return listnews;
	}

	public void setListnews(List<News> listnews) {
		this.listnews = listnews;
	}

	public List<Article_Comment> getComments() {
		return Comments;
	}

	public void setComments(List<Article_Comment> comments) {
		Comments = comments;
	}

	public int getPageIdx() {
		return PageIdx;
	}

	public List<Article> getArticals() {
		return Articals;
	}

	public void setArticals(List<Article> articals) {
		Articals = articals;
	}

	public void setPageIdx(int pageIdx) {
		PageIdx = pageIdx;
	}

	public int getPagesie() {
		return Pagesie;
	}

	public void setPagesie(int pagesie) {
		Pagesie = pagesie;
	}

	public int getRecordcount() {
		return Recordcount;
	}

	public void setRecordcount(int recordcount) {
		Recordcount = recordcount;
	}

}
