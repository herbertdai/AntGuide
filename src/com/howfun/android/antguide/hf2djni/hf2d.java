package com.howfun.android.antguide.hf2djni;

import com.howfun.android.hf2d.AntSprite;
import com.howfun.android.hf2d.LineSprite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class hf2d extends Activity{
   
   public String s = "herbert";
   
   public void onCreate(Bundle savedInstanceState) {
      Log.d("jni", "oncreate()");
      super.onCreate(savedInstanceState);
      

      /*
       * Create a TextView and set its content. the text is retrieved by calling
       * a native function.
       */
      TextView tv = new TextView(this);
      tv.setText(stringFromJNI());
      accessField();
      System.out.println("jni out=" + s);
      setContentView(tv);
   }
   
   public native String stringFromJNI();
   public native String accessField();
   /*
    * Check collision of rect and line if collision, then set a mirror angle 
    * @return true collision
    */
   public native static boolean checkRectAndLineCollisionMirror(AntSprite ant, LineSprite line);

   static {
      System.loadLibrary("hf2d");
   }
}
