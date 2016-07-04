package cn.com.dc.app.client.biz;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import cn.com.dc.app.client.bean.ArticalPageModel;
import cn.com.dc.app.client.bean.User;

import com.alibaba.fastjson.JSON;
import com.sinxiao.mvp.bean.Rsponse2BeanCallBack;

public class BizUser extends CommonBiz implements IBizUser {

	private OperatManager operate = new OperatManager();

	public Future<String> login(final String name, final String pwd,
			final Rsponse2BeanCallBack<ArticalPageModel> rsp) {
		final String idString = UUID.randomUUID().toString();
		final Future<String> future = OperateDispatcher.getExecutor().submit(
				new Callable<String>() {

					public java.lang.String call() {

						String data = null;
						try {
							data = operate.login(name, pwd);
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

	public Future<String> reg(final User user,
			final Rsponse2BeanCallBack<ArticalPageModel> rsp) {
		final String idString = UUID.randomUUID().toString();
		final Future<String> future = OperateDispatcher.getExecutor().submit(
				new Callable<String>() {

					public java.lang.String call() {

						String data = null;
						try {
							data = operate.reg(user.getUname(), user.getPwd(),
									user.getEmail(), user.getMobile());
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

	public Future<String> update(final User user,
			final Rsponse2BeanCallBack<ArticalPageModel> rsp) {

		final String idString = UUID.randomUUID().toString();
		final Future<String> future = OperateDispatcher.getExecutor().submit(
				new Callable<String>() {

					public java.lang.String call() {

						String data = null;
						try {
							data = operate.reg(user.getUname(), user.getPwd(),
									user.getEmail(), user.getMobile());
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
}
