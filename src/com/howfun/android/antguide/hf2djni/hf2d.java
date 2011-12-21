package com.howfun.android.antguide.hf2djni;

import com.howfun.android.HF2D.AntSprite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class hf2d extends Activity{
   
   public void onCreate(Bundle savedInstanceState) {
      Log.d("jni", "oncreate()");
      super.onCreate(savedInstanceState);

      /*
       * Create a TextView and set its content. the text is retrieved by calling
       * a native function.
       */
      TextView tv = new TextView(this);
      tv.setText(test(null));
      setContentView(tv);
   }
   
   public native String stringFromJNI() ;
   public native String test(AntSprite ant);

   static {
      System.loadLibrary("hf2d");
   }
}
