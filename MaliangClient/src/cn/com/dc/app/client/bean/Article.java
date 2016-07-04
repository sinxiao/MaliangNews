package cn.com.dc.app.client.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;

import cn.com.dc.app.client.biz.OperatManager;

public class Article extends DataSupport implements Serializable {
	private int id, channel_id, category_id, sort_id, click, status, is_msg,
			is_top, is_red, is_hot, is_slide, is_sys;
	private String call_index, title, link_url, img_url, seo_title,
			seo_keywords, seo_description, zhaiyao, content, user_name;
	private Date add_time, update_time;
	private List<Album> albums;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public int getSort_id() {
		return sort_id;
	}

	public void setSort_id(int sort_id) {
		this.sort_id = sort_id;
	}

	public int getClick() {
		return click;
	}

	public void setClick(int click) {
		this.click = click;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIs_msg() {
		return is_msg;
	}

	public void setIs_msg(int is_msg) {
		this.is_msg = is_msg;
	}

	public int getIs_top() {
		return is_top;
	}

	public void setIs_top(int is_top) {
		this.is_top = is_top;
	}

	public int getIs_red() {
		return is_red;
	}

	public void setIs_red(int is_red) {
		this.is_red = is_red;
	}

	public int getIs_hot() {
		return is_hot;
	}

	public void setIs_hot(int is_hot) {
		this.is_hot = is_hot;
	}

	public int getIs_slide() {
		return is_slide;
	}

	public void setIs_slide(int is_slide) {
		this.is_slide = is_slide;
	}

	public int getIs_sys() {
		return is_sys;
	}

	public void setIs_sys(int is_sys) {
		this.is_sys = is_sys;
	}

	public String getCall_index() {
		return call_index;
	}

	public void setCall_index(String call_index) {
		this.call_index = call_index;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink_url() {
		return link_url;
	}

	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = OperatManager.IP+img_url;
	}

	public String getSeo_title() {
		return seo_title;
	}

	public void setSeo_title(String seo_title) {
		this.seo_title = seo_title;
	}

	public String getSeo_keywords() {
		return seo_keywords;
	}

	public void setSeo_keywords(String seo_keywords) {
		this.seo_keywords = seo_keywords;
	}

	public String getSeo_description() {
		return seo_description;
	}

	public void setSeo_description(String seo_description) {
		this.seo_description = seo_description;
	}

	public String getZhaiyao() {
		return zhaiyao;
	}

	public void setZhaiyao(String zhaiyao) {
		this.zhaiyao = zhaiyao;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Date getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Date add_time) {
		this.add_time = add_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
}
