package cn.com.dc.app.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.com.dc.app.client.CustomListView.OnRefreshListener;
import cn.com.dc.app.client.bean.Article_Comment;
import cn.com.dc.app.client.bean.News;
import cn.com.dc.app.client.bean.NewsComment;
import cn.com.dc.app.client.biz.OperatManager;
import cn.com.dc.app.client.mvp.INewsDetailView;
import cn.com.dc.app.client.mvp.NewsDetailPressent;
import cn.com.dc.app.client.util.Configer;

/**
 * 显示新闻详情 ，获取 相关推荐，获取 评论
 * 
 * @author ok
 * 
 */
public class NewsDetailActivity extends DCActivity implements INewsDetailView {
	private static final int HANDLER_SHOWERROR = 0;
	private static final int HANDLER_NOMORE = 1;
	private static final int HANDLER_END = 2;
	// private long newsId;
	private News news;
	private LayoutInflater mInflater;
	private View newsView, commentsViews;
	private Button btnBack, btnShare, btnLove, btnSendComment;
	private Button edtComment;
	private ImageButton btnShowComment;
	private TextView txtItem, txtTitle, txtTime, txtSouce, txtContent;
	private LinearLayout mainFrame;
	private CustomListView lstComments;
	private int commentPage;
	private View layoutBottom;
	private EditText edtInputComment;
	private View bottomBarr;
	private TextView txtTip;
	private WebView webContent;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};
	private boolean showNews = true;
	private List<NewsComment> newscomments = null;
	private LinearLayout newslayout;
	private NewsDetailPressent detailPressent;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_detail);
		detailPressent = new NewsDetailPressent(this);
		// newscomments = new ArrayList<NewsComment>();
		mInflater = LayoutInflater.from(this);
		// newsId = getIntent().getLongExtra(Configer.NEWS_ID,
		// Long.parseLong("-1"));
		news = getIntent().getParcelableExtra(Configer.NEWS_DETAIL);

		newsView = mInflater.inflate(R.layout.seenews, null);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		btnBack = (Button) findViewById(R.id.btnBack);
		btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (showinput) {
					showinput = false;
					layoutBottom.setVisibility(View.GONE);
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(NewsDetailActivity.this
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
				} else {
					if (!showNews) {
						mainFrame.removeAllViews();
						mainFrame.addView(newsView);
						showNews = true;
					} else {
						finish();
					}
				}
			}
		});
		// btnShare = (Button) findViewById(R.id.btnShare);
		// btnLove = (Button) findViewById(R.id.btnLove);

		// edtComment = (Button) findViewById(R.id.edtComment);
		// edtComment.setInputType(InputType.TYPE_CLASS_TEXT);

		layoutBottom = findViewById(R.id.layoutBottom);
		edtInputComment = (EditText) findViewById(R.id.edtInputComment);
		btnSendComment = (Button) findViewById(R.id.btnSendComment);

		edtInputComment.setInputType(InputType.TYPE_CLASS_TEXT);
		edtInputComment.setOnKeyListener(new View.OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO
				if (keyCode == KeyEvent.KEYCODE_ENTER
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					addComment(edtInputComment.getText().toString());
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(NewsDetailActivity.this
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
					layoutBottom.setVisibility(View.GONE);
					showinput = false;
					bottomBarr.setVisibility(View.VISIBLE);
					return true;
				}
				return false;
			}
		});

		btnSendComment.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				addComment(edtInputComment.getText().toString());
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(NewsDetailActivity.this
								.getCurrentFocus().getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				layoutBottom.setVisibility(View.GONE);
				showinput = false;
				bottomBarr.setVisibility(View.VISIBLE);
			}
		});
		btnShowComment = (ImageButton) findViewById(R.id.btnShowComment);
		btnShowComment.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				showComment();
			}
		});
		mainFrame = (LinearLayout) findViewById(R.id.mainFrame);

		newsView = mInflater.inflate(R.layout.seenews, null);

		newslayout = (LinearLayout) newsView.findViewById(R.id.newslayout);

		txtTitle = (TextView) newsView.findViewById(R.id.txtTitle);
		txtTime = (TextView) newsView.findViewById(R.id.txtTime);
		txtSouce = (TextView) newsView.findViewById(R.id.txtSouce);
		txtContent = (TextView) newsView.findViewById(R.id.txtContent);

		txtTitle.setText(news.getTitle());
		txtTime.setText(news.getGentime().toLocaleString());
		txtSouce.setText(news.getNewsurl());
		webContent = (WebView) newsView.findViewById(R.id.webContent);
		 try {
		 //乱码
		 // txtContent.loadData(URLEncoder.encode(news.getContent(),
//		 encoding),mimeType,encoding);
			webContent.loadDataWithBaseURL(null, news.getContent(), mimeType,
					encoding, null);
			webContent.getSettings().setLayoutAlgorithm(
					WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
			webContent.getSettings().setJavaScriptEnabled(false);
			webContent.getSettings().setSupportZoom(false);
			
			webContent.getSettings().setLoadWithOverviewMode(false);
			webContent.getSettings().setUseWideViewPort(false);
			webContent.getSettings().setBuiltInZoomControls(false);
			webContent.setVerticalScrollBarEnabled(false);
			webContent.setVerticalScrollbarOverlay(false);
			webContent.setHorizontalScrollBarEnabled(false);
			webContent.setHorizontalScrollbarOverlay(false);
			
		 } catch (Exception e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		txtContent.setText(Html.fromHtml(news.getContent(),new Html.ImageGetter() {
			
			public Drawable getDrawable(String source) {
				 URL url;
                 Drawable drawable = null;
                 try {
                	 if(source.toLowerCase().startsWith("http://")||source.toLowerCase().startsWith("https://")){
                		 
                	 }else{
                		 source+=OperatManager.IP;
                	 }
                     url = new URL(source);
                     drawable = Drawable.createFromStream(
                             url.openStream(), null);
                     drawable.setBounds(0, 0,
                             drawable.getIntrinsicWidth(),
                             drawable.getIntrinsicHeight());
                 } catch (MalformedURLException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                 } catch (IOException e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                 }
                 return drawable;
			}
		},
//		new Html.TagHandler(){
//			public void handleTag(boolean opening, String tag, Editable output,
//					XMLReader xmlReader) {
//			}}
		null
		));
		txtContent.setVisibility(View.GONE);
		webContent.setVisibility(View.VISIBLE);
		
		commentsViews = mInflater.inflate(R.layout.listcomments, null);
		lstComments = (CustomListView) commentsViews.findViewById(R.id.lstNews);
		txtTip = (TextView) commentsViews.findViewById(R.id.txtTip);
		txtTip.setText("没有评论");
		txtTip.setVisibility(View.VISIBLE);

		// View v = mInflater.inflate(R.layout.head, null);

		mainFrame.addView(newsView);
		// newslayout.addView(v);
		newslayout.addView(commentsViews);

		bottomBarr = findViewById(R.id.bottomBarr);

		// new Thread(){
		// public void run() {
		// increamentRead();
		// }
		// }.start();
		detailPressent.showArticalComments("" + news.getIdnews(), "0");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (webContent != null) {
			webContent.removeAllViews();
			webContent = null;
		}
	}

	private final String mimeType = "text/html";
	private final String encoding = "utf-8";

	/***
	 * 显示输入评论的界面
	 */
	private void showComment() {
		showinput = true;

		layoutBottom.setVisibility(View.VISIBLE);
		edtInputComment.setFocusable(true);
		edtInputComment.requestFocusFromTouch();
		OpenSoftKeyboard();
	}

	private boolean showinput = false;
	private int page = 1;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (showinput) {
				layoutBottom.setVisibility(View.GONE);
				showinput = false;
				bottomBarr.setVisibility(View.VISIBLE);
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void OpenSoftKeyboard() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				InputMethodManager m = (InputMethodManager) edtInputComment
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				runOnUiThread(new Runnable() {
					public void run() {
						edtInputComment.setText("");
						bottomBarr.setVisibility(View.INVISIBLE);
					}
				});

			}
		}, 500);
	}

	private void getNewsComment() {

	}

	private class CommentsAdapter extends BaseAdapter {

		public int getCount() {
			// TODO
			return newscomments == null ? 0 : newscomments.size();
		}

		public Object getItem(int position) {
			// TODO
			return newscomments.get(position);
		}

		public long getItemId(int position) {
			// TODO
			return newscomments.get(position).getIdnews_comment();
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO
			CommentView cv = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.comments_item, null);
				cv = new CommentView();
				cv.txtContent = (TextView) convertView
						.findViewById(R.id.txtContent);
				cv.txtGenTime = (TextView) convertView
						.findViewById(R.id.txtGenTime);
				cv.txtUName = (TextView) convertView
						.findViewById(R.id.txtUName);
				convertView.setTag(cv);
			} else {
				cv = (CommentView) convertView.getTag();
			}
			NewsComment nc = (NewsComment) getItem(position);
			cv.txtContent.setText(nc.getContent());
			cv.txtGenTime.setText(nc.getGen_time().toLocaleString());
			cv.txtUName.setText(nc.getUser_id() + "");
			return convertView;
		}

	}

	private static class CommentView {
		public TextView txtUName, txtGenTime, txtContent;
	}

	protected void addComment(String string) {
		detailPressent.addReview("" + news.getIdnews(), string);
	}

	private Button btnMore;
	private ProgressBar prgWait;

	private View getFootView() {
		// FootView fv = new FootView(this, type);
		// fv.setLayoutParams(new
		// ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
		// ViewGroup.LayoutParams.WRAP_CONTENT));

		View vw = mInflater.inflate(R.layout.footview, null);
		btnMore = (Button) vw.findViewById(R.id.btnMore);
		prgWait = (ProgressBar) vw.findViewById(R.id.prgWait);

		// ViewGroup.LayoutParams layoutParam = new
		// ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
		// ViewGroup.LayoutParams.WRAP_CONTENT);
		// layoutParam.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		// layoutParam.width = ViewGroup.LayoutParams.WRAP_CONTENT;
		// LayoutParams
		// btn.setLayoutParams(layoutParam);
		btnMore.setText(R.string.more);
		btnMore.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				prgWait.setVisibility(View.VISIBLE);
				v.setVisibility(View.GONE);
				// getNewsDataByType(type);
				getNewsComment();
			}
		});

		return vw;
	}

	private View getEmptyView() {
		TextView vw = new TextView(this);
		vw.setText(R.string.nomore);
		return vw;
	}

	private void increamentRead() {
	}

	private int nid = -1;
	private int ctype = -1;
	private int comment_id = -1;
	private static final int COMMENT_NEWS = 1;
	private static final int COMMENT_COMMENT = 2;
	private static final int COMMENT_PIC = 3;

	private void showPopView(View parent, int id) {
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.popup_menu, null);
		final PopupWindow popWindow = new PopupWindow(contentView,
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, false);
		comment_id = id;
		Button btnLove = (Button) contentView.findViewById(R.id.btnLove);
		Button btnShare = (Button) contentView.findViewById(R.id.btnShare);
		Button btnComment = (Button) contentView
				.findViewById(R.id.btnNewComment);
		Button btnTop = (Button) contentView.findViewById(R.id.btnTop);
		Button btnDown = (Button) contentView.findViewById(R.id.btnDown);
		btnComment.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// 执行对评论的评论进行评论
				popWindow.dismiss();
				showComment();
				ctype = COMMENT_COMMENT;

			}
		});
		popWindow.setContentView(contentView);
		popWindow.setOutsideTouchable(true);
		popWindow.update();
		// popWindow.setTouchInterceptor(new View.OnTouchListener() {
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// // TODO Auto-generated method stub
		// if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
		// popWindow.dismiss();
		// }
		//
		// return false;
		// }
		// });
		contentView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					popWindow.dismiss();
				}

				return false;
			}
		});
		popWindow.showAsDropDown(parent, 0,
				-((int) (parent.getHeight() * (2.2f))));
		// popWindow.showAtLocation(parent,Gravity.CENTER , 0, 0);

	}

	private CommentsAdapter commsBind = null;

	private void bindArticalComments(List<NewsComment> newlst) {
		if (newlst == null || newlst.size() == 0) {
			if (btnMore != null) {
				btnMore.setVisibility(View.GONE);
			}

			if (newscomments == null) {
				lstComments.addFooterView(getEmptyView());
				txtTip.setVisibility(View.VISIBLE);
				txtTip.setText(R.string.nomore);
				return;
			}
		}

		// List<NewsComment> newlst = null;
		if (newlst != null) {
			txtTip.setVisibility(View.GONE);
			// newlst = (List<NewsComment>) obj;
			if (newlst.size() != Integer.valueOf(Configer.COUNT)) {
				page = -1;
			} else
				page++;

		} else {
			return;
		}

		if (newscomments == null) {
			newscomments = newlst;
			commsBind = new CommentsAdapter();
			lstComments.addFooterView(getFootView());
			lstComments.setOnRefreshListener(new OnRefreshListener() {
				public void onRefresh() {
					// getNewsComment();
					detailPressent.showArticalComments("" + news.getIdnews(),
							"" + page);
				}
			});
			lstComments.setAdapter(commsBind);
			lstComments
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> adatview,
								View view, int idx, long item) {
							// TODO Auto-generated method stub
							int fid = (int) commsBind.getItemId(idx - 1);
							showPopView(view, fid);
						}
					});
		} else {
			if (!newscomments.containsAll(newlst)) {
				newscomments.addAll(newlst);
				commsBind.notifyDataSetChanged();
			}
			// ((BaseAdapter) lstComments.getAdapter())
			// .notifyDataSetChanged();

		}
		if (lstComments != null && commsBind != null) {
			android.view.ViewGroup.LayoutParams params = lstComments
					.getLayoutParams();
			int height = 0;
			for (int i = 0; i < commsBind.getCount(); i++) {
				View viewItem = commsBind.getView(i, null, lstComments);
				viewItem.measure(0, 0);
				height += viewItem.getMeasuredHeight();
			}
			params.height = height + lstComments.getDividerHeight()
					* commsBind.getCount();

			lstComments.setLayoutParams(params);
		}

		lstComments.onRefreshComplete();
		prgWait.setVisibility(View.GONE);
		btnMore.setVisibility(View.VISIBLE);

	}

	private NewsComment toNewsComment(Article_Comment articalComment) {
		NewsComment comment = new NewsComment();
		comment.setComment_name(articalComment.getUser_name());
		comment.setContent(articalComment.getContent());
		comment.setEmail("");
		comment.setGen_time(articalComment.getAdd_time());
		comment.setIdnews_comment(articalComment.getId());
		comment.setNews_id(articalComment.getArticle_id());
		comment.setUser_id(articalComment.getUser_id());
		return comment;
	}

	public void showAssociatNews(List<News> newslist) {

	}

	private final String TAG = "NewsDetailActivity";

	public void showComments(List<Article_Comment> commentlist) {
		if (commentlist != null) {
			Log.e(TAG,
					"commentlist is not null commentlist > "
							+ commentlist.size());
			int size = commentlist.size();
			final List<NewsComment> comments = new ArrayList<NewsComment>();
			for (int i = 0; i < size; i++) {
				comments.add(toNewsComment(commentlist.get(i)));
			}
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// if (comments.size() == 0) {
					// btnMore.setVisibility(View.GONE);
					// } else
					{
						bindArticalComments(comments);
					}
				}
			});

		} else {
			Log.e(TAG, "commentlist is null");
		}

	}

}
