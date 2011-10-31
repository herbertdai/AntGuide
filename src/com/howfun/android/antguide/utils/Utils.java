package com.howfun.android.antguide.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.howfun.android.antguide.AntGuideApplication;
import com.howfun.android.antguide.R;

public final class Utils {

   private static final String TAG = "Ant Guide";
   
   public static final boolean AD_VER = true;

   public static final String PREF_SETTINGS = "settings";
   public static final String PREF_SETTINGS_BACK_MUSIC_OFF = "back_musci_off";
   public static final String PREF_SETTINGS_SOUND_EFFECT_OFF = "sound_effect_off";

   public static final long TIMEOUT = 3 * 60; //3 Min 
   public static final int TOP_SCORE_COUNT = 5;

   public static final int MSG_ANT_HOME = 1;
   public static final int MSG_ANT_LOST = 2;
   public static final int MSG_ANT_FOOD = 3;
   public static final int MSG_ANT_COLLISION = 4;
   public static final int MSG_ANT_TIMEOUT = 5;
   public static final int MSG_SCORE_BOARD = 6;
   public static final int MSG_TIME_UPDATED = 7;
   public static final int MSG_ANT_TRAPPED = 8;

   public static final int ANT_HOME = 1;
   public static final int ANT_LOST = 2;
   public static final int ANT_TIMEOUT = 3;
   public static final int ANT_TRAPPED = 4;
   public static final int ANT_PAUSED = 5;

   public static final int RESULT_ANT_GUIDE = 1;

   public static final String SCORE = "score";

   public static final String LEVEL_REF = "level ref";


   public static void log(String tag, String info) {
      if (AntGuideApplication.DEBUG) {
         Log.e(TAG + ">>>>>>>>>" + tag, "-------->" + info);
      }
   }

   public static void showMessageDlg(Context context, int stringId) {
      new AlertDialog.Builder(context).setIcon(R.drawable.icon).setTitle(
            R.string.app_name).setMessage(stringId).setPositiveButton(
            android.R.string.ok, null).show();
   }

   public static void showAbout(Context context) {
      new AlertDialog.Builder(context).setIcon(R.drawable.icon).setTitle(
            R.string.app_name).setMessage(
            context.getResources().getString(R.string.version) + "\n"
                  + context.getResources().getString(R.string.howfun)).show()
            .setCanceledOnTouchOutside(true);
   }

   public static void recycleBitmap(Bitmap bitmap) {
      if (bitmap != null) {
         bitmap.recycle();
         bitmap = null;
      }
   }

   public static void setAD(Activity a) {
      if (!Utils.AD_VER) {
         View ad = (View) a.findViewById(R.id.ad);
         ad.setVisibility(View.GONE);
      }
      
   }

}
