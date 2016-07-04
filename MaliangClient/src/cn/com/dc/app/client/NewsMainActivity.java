package cn.com.dc.app.client;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
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
import cn.com.dc.app.client.util.Utils;

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
	private Button btnNews, btnAir, btnOlds;

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
