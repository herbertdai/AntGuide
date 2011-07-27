package com.howfun.android.antguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class AntGuide extends Activity implements OnTouchListener {
	private static final String TAG = "AntGuide";

	// private AntView mAntView;

	public static int DEVICE_WIDTH;
	public static int DEVICE_HEIGHT;

	Intent mIntentService = null;
	Intent mIntentReceiver = null;

	private TextView gameScore;
	private ImageView gamePause;
	private ImageView gamePlay;
	private AntView antView;
	private TextView gameInfo;

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Utils.MSG_ANT_HOME:
				antView.stopThread();
				gameInfo.setVisibility(View.VISIBLE);
				gameInfo.setText("Ant gets home...");
				break;
			case Utils.MSG_ANT_LOST:
				antView.stopThread();
				gameInfo.setVisibility(View.VISIBLE);
				gameInfo.setText("Ant is lost!!!");
				break;
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		DEVICE_WIDTH = dm.widthPixels;
		DEVICE_HEIGHT = dm.heightPixels;

		setContentView(R.layout.game_view);

		findViews();
		setupListeners();

		init();

	}

	private void findViews() {
		gameScore = (TextView) findViewById(R.id.game_score);
		gamePause = (ImageView) findViewById(R.id.game_pause);
		gamePlay = (ImageView) findViewById(R.id.game_play);
		antView = (AntView) findViewById(R.id.ant_view);
		gameInfo = (TextView) findViewById(R.id.game_info);
	}

	private void setupListeners() {

		if (gamePause != null) {
			gamePause.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					pauseGame();
				}
			});
		}

		if (gamePlay != null) {
			gamePlay.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					playGame();
				}
			});
		}

		if (antView != null) {
			antView.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					int action = event.getAction();
					float x = event.getX();
					float y = event.getY();
					switch (action) {
					case MotionEvent.ACTION_DOWN:
						antView.setDownPos(x, y);

						break;
					case MotionEvent.ACTION_MOVE:
						break;
					case MotionEvent.ACTION_UP:
						antView.setUpPos(x, y);
						antView.showBlockLine();
						break;
					}
					return true;
				}

			});
		}
	}

	private void init() {
		antView.setHandler(mHandler);
		gamePause.setVisibility(View.VISIBLE);
		gamePlay.setVisibility(View.INVISIBLE);
		mIntentService = new Intent("com.howfun.android.antguide.MusicService");
		mIntentReceiver = new Intent(
				"com.howfun.android.antguide.MusicReceiver");
	}

	private void pauseGame() {
		gamePause.setVisibility(View.INVISIBLE);
		gamePlay.setVisibility(View.VISIBLE);
		antView.stopThread();
	}

	private void playGame() {
		gamePause.setVisibility(View.VISIBLE);
		gamePlay.setVisibility(View.INVISIBLE);
		gameInfo.setText("");
		gameInfo.setVisibility(View.INVISIBLE);
		antView.startGame();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

	@Override
	protected void onResume() {
		super.onResume();

		sendBroadcast(mIntentReceiver);
		// startService(mIntentService);
	}

	@Override
	protected void onPause() {
		super.onPause();

		stopService(mIntentService);
	}
}