package cn.com.dc.app.client;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import cn.com.dc.app.client.bean.User;
import cn.com.dc.app.client.mvp.IUserView;
import cn.com.dc.app.client.mvp.UserPressent;
import cn.com.dc.app.client.util.Utils;

/**
 * 
 * 用户的登陆or注册界面 通过设置style_act 与 for_waht 参数来调节界面的功能 。 style_act： 显示状态 对话框模式 or
 * Act模式 （STYLE_DIALOG STYLE_ACT） for_waht 显示登陆界面 or 注册 界面 （FOR_LOGIN FOR_REG）
 * 
 */
public class UserLoginAct extends BaseActivity implements IUserView {

	/**
	 * 设置显示样式的标志
	 */
	public static final String STYLE = "style";
	/**
	 * 显示操作的标识
	 */
	public static final String WHAT = "for_waht";

	public static final int STYLE_DIALOG = 0;
	public static final int STYLE_ACT = 1;
	public static final int FOR_LOGIN = 0;
	public static final int FOR_REG = 1;

	private int style = STYLE_ACT;
	private int for_waht = FOR_LOGIN;

	private EditText edtPwd, edtName, edtregName, edtregPwd, edtregBirthYM,
			edtregGender, edtregMobile;
	private Switch swhMore;
	private View layout_login, layout_reg, layoutMore;
	private Button btnLogin, btnNowReg, btnShowReg, btnShowLogin;
	private UserPressent userpressent;
	private Button btnLeft, btnRight;
	private TextView txtInfor;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_reg);
		btnLeft = (Button) findViewById(R.id.btnLeft);
		btnLeft.setVisibility(View.GONE);
		btnRight = (Button) findViewById(R.id.btnRight);
		btnRight.setVisibility(View.GONE);
		txtInfor = (TextView) findViewById(R.id.txtInfor);
		txtInfor.setTextColor(Color.WHITE);

		style = getIntent().getIntExtra("style", STYLE_ACT);
		for_waht = getIntent().getIntExtra("for_waht", FOR_LOGIN);

		layout_login = findViewById(R.id.layout_login);
		layout_reg = findViewById(R.id.layout_reg);

		if (for_waht == FOR_LOGIN) {
			initLogin();
		} else {
			initReg();
		}

		userpressent = new UserPressent(this);

	}

	public void initLogin() {
		txtInfor.setText(R.string.loginview);
		edtPwd = (EditText) findViewById(R.id.edtPwd);
		edtName = (EditText) findViewById(R.id.edtName);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnShowReg = (Button) findViewById(R.id.btnShowReg);
		btnShowReg.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				showReg();
			}
		});
		layout_login.setVisibility(View.VISIBLE);
		layout_reg.setVisibility(View.GONE);
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				userpressent.login(edtName.getText().toString(), edtPwd
						.getText().toString());
			}
		});
		if(Utils.debug){
			edtName.setText("13856258699");
			edtPwd.setText("123456");
		}
	}

	public void initReg() {
		txtInfor.setText(R.string.regview);
		edtregName = (EditText) findViewById(R.id.edtregName);
		edtregName.setHint("邮箱地址");
		edtregPwd = (EditText) findViewById(R.id.edtregPwd);
		edtregBirthYM = (EditText) findViewById(R.id.edtregBirthYM);
		edtregGender = (EditText) findViewById(R.id.edtregGender);
		edtregMobile = (EditText) findViewById(R.id.edtregMobile);

		btnShowLogin = (Button) findViewById(R.id.btnShowLogin);
		btnShowLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showLogin();
			}
		});
		
		btnNowReg = (Button) findViewById(R.id.btnNowReg);
		btnNowReg.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				User user = new User();
				user.setEmail(edtregName.getText().toString());
				user.setBirthYM(edtregBirthYM.getText().toString());
				user.setFace("");
				user.setMobile(edtregMobile.getText().toString());
				user.setPwd(edtregPwd.getText().toString());
				user.setSex((edtregGender.getText().toString()));
				userpressent.reg(user);
			}
		});
		layoutMore = findViewById(R.id.layoutMore);

		swhMore = (Switch) findViewById(R.id.swhMore);
		swhMore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					layoutMore.setVisibility(View.VISIBLE);
				} else {
					layoutMore.setVisibility(View.GONE);
				}
			}
		});
		swhMore.setChecked(false);
		layoutMore.setVisibility(View.GONE);
		// btnNowReg.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		//
		// }
		// });
		layout_login.setVisibility(View.GONE);
		layout_reg.setVisibility(View.VISIBLE);
		if(Utils.debug){
			edtregName.setText("aaaa@bbb.com");
			edtregPwd.setText("111222");
			edtregBirthYM.setText("1991-02");
			edtregGender.setText("1");
			edtregMobile.setText("18687667876");
		}
	}

	public void regNow() {
		User user = new User();
		user.setUname(edtregName.getText().toString().trim());
		user.setMobile(edtregMobile.getText().toString().trim());
		user.setPwd(edtregPwd.getText().toString().trim());
		user.setGrade(3);
		user.setBirthYM(edtregBirthYM.getText().toString());
		userpressent.reg(user);
	}

	public void login(View v) {
		userpressent.login(edtName.getText().toString(), edtPwd.getText()
				.toString());
	}

	public void reg(View v) {
		showReg();
	}

	@Override
	public void showLogin() {
		initLogin();
		for_waht = FOR_LOGIN;
		// 显示登陆界面
		layout_reg.setVisibility(View.GONE);
		layout_login.setVisibility(View.VISIBLE);
	}

	public void loginSucess() {

	}

	public void loginFail() {

	}

	public void showReg() {
		for_waht = FOR_REG;
		initReg();
		// 显示登陆界面
		layout_reg.setVisibility(View.VISIBLE);
		layout_login.setVisibility(View.GONE);
	}

	@Override
	public void regSucess() {

	}

	@Override
	public void regFail() {

	}

}
