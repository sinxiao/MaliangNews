package cn.com.dc.app.client;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.sinxiao.mvp.control.ICommenControlView;

public class BaseFragmentActivity extends FragmentActivity implements ICommenControlView {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private ProgressDialog prgDialog = null;

	public void showWiat(String title, String infor) {
		if (prgDialog == null) {
			prgDialog = new ProgressDialog(this);
		}
		prgDialog.setTitle(title);
		prgDialog.setMessage(infor);
		prgDialog.setCancelable(false);
		prgDialog.setCanceledOnTouchOutside(false);

		prgDialog.show();
	}

	public void showTip(String title, String infor) {
		AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle(title)
				.setMessage(infor)
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface mDialog,
									int which) {
								mDialog.dismiss();
								mDialog = null;
							}
						}).create();
		dialog.show();
	}

	public void removeWiat() {
		if (prgDialog != null) {
			if (prgDialog.isShowing()) {
				prgDialog.dismiss();
			}
		}
		prgDialog = null;
	}

	public void showWiat(String title, String infor, boolean showCancel,
			final DialogInterface.OnClickListener oncancelListener) {
		if (prgDialog == null) {
			prgDialog = new ProgressDialog(this);
		}
		prgDialog.setTitle(title);
		prgDialog.setMessage(infor);
		prgDialog.setCancelable(false);
		prgDialog.setCanceledOnTouchOutside(false);
		if (showCancel) {
			prgDialog.setButton(ProgressDialog.BUTTON_NEGATIVE,
					getString(R.string.cancel),
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							oncancelListener.onClick(dialog, which);
						}
					});
		}
		prgDialog.show();
	}

	@Override
	public void showError(String title, String error) {
		AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle(title)
				.setMessage(error)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface mDialog,
									int which) {
								mDialog.dismiss();
								mDialog = null;
							}
						}).create();
		dialog.show();

	}

	@Override
	public void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
