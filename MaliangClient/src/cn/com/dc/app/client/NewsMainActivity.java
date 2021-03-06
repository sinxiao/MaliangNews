package cn.com.dc.app.client;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;
import cn.com.dc.app.client.util.Utils;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * 显示各种新闻列表
 * 
 * @author Administrator
 * 
 */
public class NewsMainActivity extends BaseFragmentActivity {
	private static final int VIEW_NEWS = 0; // itemId = 5 ;
	private static final int VIEW_AIR = 1; // 14
	private static final int VIEW_OLDS = 2; // 15
	private static final int VIEW_FNEWS = 3; // 16

	private LayoutInflater mInflater;
	private ViewPager mViewpage;
	private Button btnNews, btnAir, btnOlds, btnLeft;
	private Button btnUserInor, btnGolden, btnAbout;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		mInflater = LayoutInflater.from(this);
		setContentView(R.layout.news_main);
		mViewpage = (ViewPager) findViewById(R.id.viewPage);

		List<String> vls = new ArrayList<String>();

		vls.add("" + VIEW_NEWS);
		vls.add("" + VIEW_AIR);
		vls.add("" + VIEW_OLDS);

		mViewpage.setAdapter(new MyFragmentStatePagerAdapter(
				getSupportFragmentManager(), vls));

		btnNews = (Button) findViewById(R.id.btnNews);
		btnAir = (Button) findViewById(R.id.btnAir);
		btnOlds = (Button) findViewById(R.id.btnOlds);

		btnNews.setOnClickListener(onclick);
		btnAir.setOnClickListener(onclick);
		btnOlds.setOnClickListener(onclick);

		btnLeft = (Button) findViewById(R.id.btnLeft);
		btnLeft.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				menu.toggle();
				// menu.showContent();
				Toast.makeText(getApplicationContext(), "click",
						Toast.LENGTH_SHORT).show();

			}
		});
		initSlideMenu();
	}

	private SlidingMenu menu;

	private void initSlideMenu() {
		// configure the SlidingMenu
		menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.45f);
		// menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.menu_frame);

		btnUserInor = (Button) menu.findViewById(R.id.btnUserInor);
		btnGolden = (Button) menu.findViewById(R.id.btnGolden);
		btnAbout = (Button) menu.findViewById(R.id.btnAbout);

		btnUserInor.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),
						UserLoginAct.class));
				menu.showContent();
			}
		});
		btnAbout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showAbout();
				menu.showContent();

			}
		});
	}

	private void showAbout() {
		String message = getResources().getString(R.string.message);
		String ver = getResources().getString(R.string.version);
		PackageManager pm = getPackageManager();
		// 每次启动都要检查新版本
		PackageInfo pi = null;
		try {
			pi = pm.getPackageInfo(this.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String versionn = pi.versionName + "." + pi.versionCode + "";
		message += "\n\r" + ver + " " + versionn;
		AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle(R.string.about)
				.setMessage(message)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).create();
		dialog.show();
	}

	private View.OnClickListener onclick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnNews:
				mViewpage.setCurrentItem(0);
				break;
			case R.id.btnAir:
				mViewpage.setCurrentItem(1);
				break;
			case R.id.btnOlds:
				mViewpage.setCurrentItem(2);
				break;

			default:
				break;
			}
		}
	};

	public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

		private List<String> newstypes;

		public MyFragmentStatePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public MyFragmentStatePagerAdapter(FragmentManager fm,
				List<String> types) {
			super(fm);
			newstypes = types;
		}

		public int getCount() {

			return newstypes == null ? 0 : newstypes.size();
		}

		public Fragment getItem(int idx) {
			return CommenNewsFragment.instantiate(newstypes.get(idx));
		}

	};

	private ProgressDialog progWait;

	private boolean click = false;

	private void dismissPop() {
		if (mpopup != null) {
			mpopup.dismiss();
			click = false;
			mpopup = null;
		}
	}

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		Utils.showLog(" the keycode is ===== " + event.getKeyCode(),
				String.valueOf(keyCode));
		dismissPop();
		return super.onKeyDown(keyCode, event);
	};

	private PopupWindow mpopup;

	/**
	 * 通过layoutId获得视图
	 * 
	 * @param resId
	 *            layout Id
	 * @return 视图对象
	 */
	private View getViewByLayoutId(int resId) {
		return mInflater.inflate(resId, null);
	}
}
