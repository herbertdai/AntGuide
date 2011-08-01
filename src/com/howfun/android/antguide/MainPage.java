package com.howfun.android.antguide;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainPage extends Activity implements OnClickListener{
     
   private Button mStartBtn;
   private Button mHiScoreBtn;
   private Button mAboutBtn;
   
   public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);
		findViews();
   }

   private void findViews() {
      mStartBtn = (Button) findViewById(R.id.mainpage_start_btn);
      mStartBtn.setOnClickListener(this);
      mHiScoreBtn = (Button) findViewById(R.id.mainpage_hiscore_btn);
      mHiScoreBtn.setOnClickListener(this);
      mAboutBtn = (Button)findViewById(R.id.mainpage_about_btn);
      mAboutBtn.setOnClickListener(this);
      
      
   }

   @Override
   public void onClick(View arg0) {
      Intent intent = new Intent();
      switch(arg0.getId()) {
      case R.id.mainpage_start_btn:
         intent.setClass(MainPage.this, AntGuide.class); 
         startActivityForResult(intent, Utils.RESULT_ANT_GUIDE);
         break;
      case R.id.mainpage_hiscore_btn:
         intent.setClass(MainPage.this, ScoreBoardActivity.class);
         startActivity(intent);
         break;
      case R.id.mainpage_about_btn:
         showAbout();
         
         break;
         default:
            break;
         
      }
      
   }
   private void showAbout() {
      new AlertDialog.Builder(this)
         .setIcon(R.drawable.icon)
         .setTitle(R.string.app_name)
         .setMessage(
               getResources().getString(R.string.version)
               + "\n" +
               getResources().getString(R.string.howfun)
               )
         .show().setCanceledOnTouchOutside(true);
   }

}
