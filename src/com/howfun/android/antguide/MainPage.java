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

import com.howfun.android.antguide.hf2djni.hf2d;
import com.howfun.android.antguide.utils.Utils;
//import com.sosceo.android.ads.AdView;

public class MainPage extends Activity implements OnClickListener {

   private ImageView mStart;
   private ImageView mAbout;
   private ImageView mExit;
   private ImageView mSettings;
   private ImageView mHelp;

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
      this.requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.mainpage);
      findViews();
      
      if (Utils.AD_VER) {
         //sosceo();
      }
   }

   private void findViews() {
      mStart = (ImageView) findViewById(R.id.mainpage_start);
      mStart.setOnClickListener(this);
      mHelp = (ImageView) findViewById(R.id.mainpage_help);
      mHelp.setOnClickListener(this);
      mExit = (ImageView) findViewById(R.id.mainpage_exit);
      mExit.setOnClickListener(this);
      mSettings = (ImageView) findViewById(R.id.mainpage_settings);
      mSettings.setOnClickListener(this);
      mAbout= (ImageView)findViewById(R.id.mainpage_about);
      mAbout.setOnClickListener(this);
      
   }
   
   @Override
   public void onClick(View arg0) {
      Intent intent = new Intent();
      switch (arg0.getId()) {
      case R.id.mainpage_start:
//         intent.setClass(MainPage.this, HelpPage.class);
         intent.setClass(MainPage.this, LevelActivity.class);
         startActivity(intent);
         break;
      case R.id.mainpage_help:
         intent.setClass(MainPage.this, HelpPage.class);

//         intent.setClass(MainPage.this, hf2d.class);//test
         startActivity(intent);
         break;
         
      case R.id.mainpage_settings:
         intent.setClass(MainPage.this, SettingsActivity.class);
         startActivity(intent);
         break;

      case R.id.mainpage_exit:
         finish();
         break;
      case R.id.mainpage_about:
         Utils.showAbout(this);
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
