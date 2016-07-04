package cn.com.dc.app.client.biz;

import java.util.HashMap;
import java.util.Hashtable;

import com.sinxiao.mvp.bean.ResponseCallBack;
import com.sinxiao.utils.HttpUtils;

/**
 * 
 * //请求格式定义成这样子 methodId A-B 代表 A 类请求的 B 操作 ... //1-1 获取caotory 1-2
 * 根据catoery获取新闻列表 1-3 根据articleId获取article详情 1-4 获取评论 //2-1 获得catory的图册列表 2-2
 * 获得图册的图片列表
 * 
 * 
 */
public class OperatManager {
//	public static final String IP = "http://23.251.49.20:8080";
	 public static final String IP = "http://192.168.0.100:8080/DTCMS";
	private static final String URL = IP + "/bridge/ProcessAction.aspx";

	private Hashtable<String, String> response = new Hashtable<String, String>();

	public String getPicsByAid(String aid, String idx) throws Exception {
		HashMap<String, String> mp = new HashMap<String, String>();
		mp.put("methodId", "2-2");
		mp.put("aid", aid);
		mp.put("idx", idx);
		return HttpUtils.httpPostKeyValue(URL, mp);
	}

	/**
	 * 根据Cid获取列表信息...
	 *  
	 * @param cid
	 *            类别id
	 * @param idx
	 *            序列号
	 * @return
	 */
	public String getArticalsByCid(String cid, String idx) throws Exception {
		HashMap<String, String> mp = new HashMap<String, String>();
		mp.put("methodId", "1-2");
		mp.put("catoryId", cid);
		mp.put("idx", idx);
		mp.put("pagesize", "80");
		String data = HttpUtils.httpPostKeyValue(URL, mp);
		return data;
	}

	/**
	 * 根据aid获取列新闻列表的详细信息...
	 * 
	 * @param aid
	 *            新闻ID
	 * @return
	 */
	public String getArticalByAid(String aid) throws Exception {
		HashMap<String, String> mp = new HashMap<String, String>();
		mp.put("methodId", "1-3");
		mp.put("aid", aid);
		return HttpUtils.httpPostKeyValue(URL, mp);
	}

	/**
	 * 根据aid获取列新闻列表的详细信息...
	 * 
	 * @param aid
	 *            新闻ID
	 * @param idx
	 *            序列号
	 * @return
	 */
	public String getReviewsByAid(String aid, String idx) throws Exception {
		HashMap<String, String> mp = new HashMap<String, String>();
		mp.put("methodId", "1-4");
		mp.put("articalId", aid);
		mp.put("idx", idx);
		mp.put("pagesize", "16");
		return HttpUtils.httpPostKeyValue(URL, mp);
	}

	public String getAlbumByCid(String cid, String idx) throws Exception {
		HashMap<String, String> mp = new HashMap<String, String>();
		mp.put("methodId", "2-1");
		mp.put("cid", cid);
		mp.put("idx", idx);
		return HttpUtils.httpPostKeyValue(URL, mp);
	}

	public String upPicByPid(String pid, String uid) {
		return null;
	}

	public String downPicByPid(String pid, String uid, ResponseCallBack rsp) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean pinglunPicByPid(String pid, String pinglun, String uid,
			ResponseCallBack rsp) {
		return false;
	}

	public boolean uploadPic(String aid, String uid, String name, byte[] src,
			ResponseCallBack rsp) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addFavarPic(String pid, String uid, ResponseCallBack rsp) {
		// TODO Auto-generated method stub
		return false;
	}

	public String addReviewsByAid(String aid,String uid, String value)throws Exception {
		HashMap<String, String> mp = new HashMap<String, String>();
		mp.put("methodId", "1-5");
		mp.put("articalId", aid);
		mp.put("content", value);
		mp.put("uid", uid);
		return HttpUtils.httpPostKeyValue(URL, mp);
	}
	
	public String login(String uname,String pwd)throws Exception
	{
		HashMap<String, String> mp = new HashMap<String, String>();
		mp.put("methodId", "1-5");
		mp.put("uname", uname);
		mp.put("pwd", pwd);
		return HttpUtils.httpPostKeyValue(URL, mp);
	}
	
	public String reg(String uname,String pwd,String email,String mobile)throws Exception
	{
		HashMap<String, String> mp = new HashMap<String, String>();
		mp.put("methodId", "1-5");
		mp.put("uname", uname);
		mp.put("pwd", pwd);
		return HttpUtils.httpPostKeyValue(URL, mp);
	}
	
	public String updateUser(String uname,String pwd,String email,String mobile)throws Exception
	{
		
		return null;
	}

}
