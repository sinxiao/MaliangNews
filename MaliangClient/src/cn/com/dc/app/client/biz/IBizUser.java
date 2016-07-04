package cn.com.dc.app.client.biz;

import java.util.concurrent.Future;

import com.sinxiao.mvp.bean.Rsponse2BeanCallBack;

import cn.com.dc.app.client.bean.ArticalPageModel;
import cn.com.dc.app.client.bean.User;

public interface IBizUser {
	/**
	 * 登录操作
	 * 
	 * @param name
	 * @param pwd
	 * @param rsp
	 * @return
	 */
	public Future<String> login(String name, String pwd,
			Rsponse2BeanCallBack<ArticalPageModel> rsp);

	/**
	 * 注册
	 * 
	 * @param user
	 * @param rsp
	 * @return
	 */
	public Future<String> reg(User user,
			Rsponse2BeanCallBack<ArticalPageModel> rsp);

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @param rsp
	 * @return
	 */
	public Future<String> update(User user,
			Rsponse2BeanCallBack<ArticalPageModel> rsp);
}
