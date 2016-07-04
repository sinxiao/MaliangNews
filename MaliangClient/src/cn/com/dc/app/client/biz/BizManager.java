package cn.com.dc.app.client.biz;

public class BizManager {

	private static IBizNews bizNews;
	private static IBizUser bizUser;

	public static IBizNews getBizNews() {
		if (bizNews == null) {
			bizNews = new BizNews();
		}
		return bizNews;
	}

	public static IBizUser getBizUser() {
		if (bizUser == null) {
			bizUser = new BizUser();
		}
		return bizUser;
	}

}
