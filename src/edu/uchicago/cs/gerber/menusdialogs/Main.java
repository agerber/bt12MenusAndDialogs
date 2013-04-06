package edu.uchicago.cs.gerber.menusdialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


//################################################
//lessons: actionbar, dialogs, menus
//################################################
public class Main extends Activity  {
	/** Called when the activity is first created. */

	// used for progress dialog
	ProgressDialog pdg;
	int nStatus = 0;
	Handler hnd = new Handler();
	Thread thr;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);


	}// end onCreate()





	//################################################
	// helper methods for alert and progress bar simluation
	//################################################
	private void showAlert(Context ctx, String strTitle) {
		AlertDialog.Builder bld = new AlertDialog.Builder(ctx);
		bld.setMessage(strTitle);
		bld.setCancelable(false);

		bld.setPositiveButton("ok",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {

						showProgressBar();
						dialog.dismiss();
					}
				});

		bld.setNegativeButton("cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {

						dialog.cancel();
					}
				});

		AlertDialog ald = bld.create();
		ald.show();
	}
	

	
	private void showProgressBar() {
		// the following code is for progress Dialog
		nStatus = 0;
		pdg = new ProgressDialog(this);
		pdg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

		pdg.setMessage("downloading...");
		pdg.setIndeterminate(false);
		pdg.setMax(100);
		pdg.setCancelable(false);

		pdg.show();

		thr = new Thread(new Runnable() {
			public void run() {
				while (nStatus < 100) {
					nStatus = doSomeWork(nStatus);
					hnd.post(new Runnable() {
						public void run() {

							pdg.setProgress(nStatus);
						}
					});
				}

				hnd.post(new Runnable() {
					public void run() {

						if (nStatus >= 100)
							pdg.dismiss();

					}
				});
			}

			private int doSomeWork(int nParam) {
				try {

					Thread.sleep(150);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return nParam + 3;
			}
		});

		thr.start();

	}


}