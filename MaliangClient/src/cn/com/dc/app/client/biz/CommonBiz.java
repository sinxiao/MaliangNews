package cn.com.dc.app.client.biz;

import java.io.IOException;
import java.net.SocketException;

import cn.com.dc.app.client.R;

import com.sinxiao.mvp.bean.ErrorInfor;
import com.sinxiao.utils.Configer;

public class CommonBiz {

	public ErrorInfor getErrorType(Exception ex) {
		ErrorInfor infor = new ErrorInfor();
		if (ex instanceof IOException) {
			IOException ioex = (IOException) ex;
			if (ioex.getClass().toString()
					.equals("java.net.SocketTimeoutException")) {
				infor.setType(Configer.TIMEOUT);
				infor.setConnected(false);
				return infor;
			} else if (ioex.getClass().toString()
					.equals(SocketException.class.toString())) {
				infor.setType(Configer.NETERROR);
				infor.setConnected(false);
				return infor;
			}
		}
		infor.setType(Configer.ERROR);
		infor.setEnameRes(R.string.error);
		// return Configer.SERVERERROR;
		return infor;
	}
	
}
