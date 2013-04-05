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
public class Main extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	// used for progress dialog
	ProgressDialog pdg;
	int nStatus = 0;
	Handler hnd = new Handler();
	Thread thr;

	// buttons
	Button btnContext;
	Button btnAlert;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		//register this button for context menu
		btnContext = (Button) findViewById(R.id.btnContext);
		registerForContextMenu(btnContext);

		//register this button for onClick
		btnAlert = (Button) findViewById(R.id.btnAlert);
		btnAlert.setOnClickListener(this);

	}// end onCreate()

	//################################################
	// method to satisfy OnClickListener interface
	//################################################
	@Override
	public void onClick(View v) {
		showAlert(Main.this, "are you sure you want to download?");

	}
	
	

	//################################################
	//used for actionbar
	//################################################
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater mni = getMenuInflater();
		mni.inflate(R.menu.actbar, menu);

		// need to return true, otherwise it won't show up
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.itm1:
			Toast.makeText(this, "option item 1 clicked", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.itm2:
			Toast.makeText(this, "option item 2 clicked", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.itm3:
			Toast.makeText(this, "option item 3 clicked", Toast.LENGTH_SHORT)
					.show();
			break;
		default:
			break;
		}
		return true;
	}

	
	//################################################
	//used for context menu
	//################################################
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater mni = getMenuInflater();
		mni.inflate(R.menu.context, menu);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.con1:
			Toast.makeText(this, "context item 1 clicked", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.con2:
			Toast.makeText(this, "context item 2 clicked", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.con3:
			Toast.makeText(this, "context item 3 clicked", Toast.LENGTH_SHORT)
					.show();
			break;
		default:
			break;
		}

		return true;
	}
	
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