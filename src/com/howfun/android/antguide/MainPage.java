package com.howfun.android.antguide;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainPage extends Activity implements OnClickListener {

   private ImageView mStart;
   private ImageView mHiScore;
   private ImageView mAbout;
   private ImageView mExit;

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
      this.requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.mainpage);
      findViews();
   }

   private void findViews() {
      mStart = (ImageView) findViewById(R.id.mainpage_start);
      mStart.setOnClickListener(this);
      mHiScore = (ImageView) findViewById(R.id.mainpage_shop);
      mHiScore.setOnClickListener(this);
      mExit = (ImageView) findViewById(R.id.mainpage_exit);
      mExit.setOnClickListener(this);

   }

   @Override
   public void onClick(View arg0) {
      Intent intent = new Intent();
      switch (arg0.getId()) {
      case R.id.mainpage_start:
         intent.setClass(MainPage.this, HelpPage.class);
         startActivity(intent);
         break;
      case R.id.mainpage_shop:
         // intent.setClass(MainPage.this, ScoreBoardActivity.class);
         // startActivity(intent);
         intent.setClass(MainPage.this, ShopActivity.class);
         startActivity(intent);
         break;

      case R.id.mainpage_exit:
         finish();
         break;
      default:
         break;

      }

   }

   private void showAbout() {
      new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle(
            R.string.app_name).setMessage(
            getResources().getString(R.string.version) + "\n"
                  + getResources().getString(R.string.howfun)).show()
            .setCanceledOnTouchOutside(true);
   }

}
