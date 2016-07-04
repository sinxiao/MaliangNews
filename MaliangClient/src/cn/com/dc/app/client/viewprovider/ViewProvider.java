package cn.com.dc.app.client.viewprovider;

import com.sinxiao.mvp.bean.ErrorInfor;
import com.sinxiao.mvp.bean.Rsponse2BeanCallBack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import cn.com.dc.app.client.R;
import cn.com.dc.app.client.bean.ArticalPageModel;
import cn.com.dc.app.client.bean.User;
import cn.com.dc.app.client.biz.BizUser;
import cn.com.dc.app.client.biz.IBizUser;

public class ViewProvider {
	private static ViewProvider INSTENCE = null;
	private Context mContext;
	private LayoutInflater mInflater;
	private WindowManager windowmanager = null;
	private IBizUser bizuser;

	private ViewProvider(Context ctx) {
		mContext = ctx;
		mInflater = LayoutInflater.from(mContext);
		bizuser = new BizUser();
	}

	public static ViewProvider getInstence(Context mContext) {
		if (INSTENCE == null) {
			INSTENCE = new ViewProvider(mContext);
		}
		return INSTENCE;
	}

	public View getRegView() {
		View view = mInflater.inflate(R.layout.login_reg, null);
		final Button btnLogin = (Button) view.findViewById(R.id.btnLogin);
		final Button btnReg = (Button) view.findViewById(R.id.btnNowReg);
		final EditText edtName = (EditText) view.findViewById(R.id.edtName);
		final EditText edtPwd = (EditText) view.findViewById(R.id.edtPwd);
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				bizuser.login(edtName.getText().toString(), edtPwd.getText()
						.toString(),
						new Rsponse2BeanCallBack<ArticalPageModel>() {

							@Override
							public void onGetBeans(ArticalPageModel t) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onFailed(ErrorInfor error) {
								// TODO Auto-generated method stub

							}

							@Override
							public void OnResponse(String data) {
								// TODO Auto-generated method stub

							}
						});
			}
		});
		btnReg.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				User user = new User();
				user.setUname(edtName.getText().toString());
				user.setPwd(edtPwd.getText().toString());
				bizuser.reg(user, new Rsponse2BeanCallBack<ArticalPageModel>() {

					@Override
					public void onGetBeans(ArticalPageModel t) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onFailed(ErrorInfor error) {
						// TODO Auto-generated method stub

					}

					@Override
					public void OnResponse(String data) {
						// TODO Auto-generated method stub

					}
				});
			}
		});

		return view;
	}

	public void showRegView() {
		View view = getRegView();

		if (windowmanager == null) {
			windowmanager = (WindowManager) mContext
					.getSystemService(Context.WINDOW_SERVICE);
		}

		windowmanager.addView(view, view.getLayoutParams());

	}
}
