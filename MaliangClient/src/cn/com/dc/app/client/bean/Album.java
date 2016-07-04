package cn.com.dc.app.client.bean;

import java.util.Date;

import org.litepal.crud.DataSupport;

import cn.com.dc.app.client.biz.OperatManager;

public class Album extends DataSupport {
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}

	public String getThumb_path() {
		return thumb_path;
	}

	public void setThumb_path(String thumb_path) {
		this.thumb_path = OperatManager.IP+thumb_path;
	}

	public String getOriginal_path() {
		return original_path;
	}

	public void setOriginal_path(String original_path) {
		this.original_path = OperatManager.IP+original_path;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}

	private int id, article_id;
	private String thumb_path, original_path, remark;
	private Date add_time;
}
