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
import android.widget.TextView;

public class AntGuide extends Activity implements OnTouchListener {
   private static final String TAG = "AntGuide";

   private AntView mAntView;
   
   public static int DEVICE_WIDTH;
   public static int DEVICE_HEIGHT;
   
   Intent mIntentService = null;
   Intent mIntentReceiver = null;
   
   //Test dwy
   private Button restartBtn; 

   private Handler mHandler = new Handler() {
      
      public void handleMessage(Message msg) {
         switch (msg.what) {
         case Utils.MSG_ANT_HOME:
            mAntView.stopThread();
            restartBtn.setVisibility(View.VISIBLE); 
            break;
         case Utils.MSG_ANT_LOST:
            mAntView.stopThread();
            restartBtn.setVisibility(View.VISIBLE); 
            break;
         }
      }
      
   };
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      this.requestWindowFeature(Window.FEATURE_NO_TITLE);
      
      DisplayMetrics dm = new DisplayMetrics();
      this.getWindowManager().getDefaultDisplay().getMetrics(dm); 
      DEVICE_WIDTH = dm.widthPixels; 
      DEVICE_HEIGHT = dm.heightPixels;
      
      setContentView(R.layout.game_view);
      
      FrameLayout gameView = (FrameLayout)findViewById(R.id.game_frame);
      mAntView = new AntView(this, mHandler);
      gameView.addView(mAntView);
      
      //TODO: add menu layer here
      TextView text = new TextView(this);
      text.setText("TODO: add menu layer here");
      restartBtn = new Button(this);
      restartBtn.setText("Restart");
      restartBtn.setLayoutParams(new FrameLayout.LayoutParams(300, 80));
      restartBtn.setVisibility(View.GONE);
      restartBtn.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
            restartBtn.setVisibility(View.GONE);
            mAntView.startGame();
         }
         
      });
      
      gameView.addView(text);
      gameView.addView(restartBtn);
      
      
      
      Utils.log(TAG, "width="+DEVICE_WIDTH+",height=========:"+DEVICE_HEIGHT);
      mIntentService = new Intent("com.howfun.android.antguide.MusicService");
      mIntentReceiver = new Intent("com.howfun.android.antguide.MusicReceiver");

      mAntView.setOnTouchListener(new OnTouchListener() {

         @Override
         public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            float x = event.getX();
            float y = event.getY();
            switch (action) {
            case MotionEvent.ACTION_DOWN:
               mAntView.setDownPos(x, y);

               break;
            case MotionEvent.ACTION_MOVE:
               break;
            case MotionEvent.ACTION_UP:
               mAntView.setUpPos(x, y);
               mAntView.showBlockLine();
               break;
            }
            return true;
         }

      });
   }

   @Override
   public boolean onTouch(View v, MotionEvent event) {
      return false;
   }
   
   @Override
   protected void onResume() {
      super.onResume();

      sendBroadcast(mIntentReceiver);
//      startService(mIntentService);
   }

   @Override
   protected void onPause() {
      super.onPause();

      stopService(mIntentService);
   }
}