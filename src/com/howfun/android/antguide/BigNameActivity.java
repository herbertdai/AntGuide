package com.howfun.android.antguide;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.howfun.android.antguide.entity.Score;
import com.howfun.android.antguide.utils.DBAdapter;
import com.howfun.android.antguide.utils.Utils;

public class BigNameActivity extends Activity {

   private EditText bigName;
   private Button ok, cancel;

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.big_name);
      bigName = (EditText) findViewById(R.id.big_name);
      ok = (Button) findViewById(R.id.btn_ok);
      cancel = (Button) findViewById(R.id.btn_cancel);
      cancel.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
            finish();
         }
      });
      ok.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
            String name = bigName.getText().toString().trim();
            if ("".equals(name)) {
               name = "i_am_no_body";
            }
            DBAdapter db = new DBAdapter(BigNameActivity.this);
            db.open();
            Score score = new Score(name, getIntent().getLongExtra(Utils.SCORE,
                  0));
            db.addScore(score);
            db.close();
            finish();
         }
      });
   }

}
