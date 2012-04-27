package com.howfun.android.antguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.howfun.android.antguide.utils.Utils;
//import com.sosceo.android.ads.AdView;

public class HelpPage extends Activity implements OnClickListener {

	private ImageView mStart;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.helppage);
		findViews();
	}

	private void findViews() {
//		mStart = (ImageView) findViewById(R.id.helppage_start);
//		mStart.setOnClickListener(this);

      Utils.setAD(this);
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent();
		switch (arg0.getId()) {
//		case R.id.helppage_start:
//			intent.setClass(HelpPage.this, LevelActivity.class);
//			startActivityForResult(intent, Utils.RESULT_ANT_GUIDE);
//			finish();
//			break;

		default:
			break;

		}

	}

}
