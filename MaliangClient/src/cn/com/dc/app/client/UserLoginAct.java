package cn.com.dc.app.client;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import cn.com.dc.app.client.bean.User;
import cn.com.dc.app.client.mvp.IUserView;
import cn.com.dc.app.client.mvp.UserPressent;

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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_reg);

		style = getIntent().getIntExtra("style", STYLE_ACT);
		for_waht = getIntent().getIntExtra("for_waht", FOR_LOGIN);

		if (for_waht == FOR_LOGIN) {
			initLogin();
		} else {
			initReg();
		}

		userpressent = new UserPressent(this);
	}

	public void initLogin() {
		edtPwd = (EditText) findViewById(R.id.edtPwd);
		edtName = (EditText) findViewById(R.id.edtName);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnShowReg = (Button) findViewById(R.id.btnShowReg);
		layout_login.setVisibility(View.VISIBLE);
		layout_reg.setVisibility(View.GONE);
	}

	public void initReg() {
		
		edtregName = (EditText) findViewById(R.id.edtregName);
		edtregPwd = (EditText) findViewById(R.id.edtregPwd);
		edtregBirthYM = (EditText) findViewById(R.id.edtregBirthYM);
		edtregGender = (EditText) findViewById(R.id.edtregGender);
		edtregMobile = (EditText) findViewById(R.id.edtregMobile);
		
		btnShowLogin = (Button) findViewById(R.id.btnShowLogin);
		btnNowReg = (Button) findViewById(R.id.btnNowReg);
		
		swhMore = (Switch) findViewById(R.id.swhMore);
		
		swhMore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					layoutMore.setVisibility(View.VISIBLE);
				}else{
					layoutMore.setVisibility(View.GONE);
				}
			}
		});
		btnNowReg.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
			}
		});
		layout_login.setVisibility(View.GONE);
		layout_reg.setVisibility(View.VISIBLE);
	}

	public void regNow()
	{
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
