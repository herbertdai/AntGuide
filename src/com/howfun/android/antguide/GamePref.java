/*
 * Singleton class to store game level and score
 */
package com.howfun.android.antguide;

import com.howfun.android.antguide.utils.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class GamePref {

   private static GamePref instance;

   private Context mContext;

   private static final String PREF = "ant pref";

   private static final String GAME_LEVEL_PREF = "game level pref";

   private static final String GAME_SCORE_PREF = "game score pref";
   
   private static final String GAME_SPEED_PREF = "game speed pref";

   private static final String TAG = "GamePref";

   public GamePref() {

   }

   public GamePref(Context context) {
      mContext = context;
   }

   public static GamePref getInstance(Context context) {
      if (instance == null) {
         instance = new GamePref(context);
      }
      return instance;
   }

   public boolean setLevelPref(int level) {
//      if (mContext != null) {
//         SharedPreferences sp = mContext.getSharedPreferences(PREF,
//               Context.MODE_PRIVATE);
//         sp.edit().putInt(GAME_LEVEL_PREF, level).commit();
//      }
      return setIntPref(GAME_LEVEL_PREF, level);
   }
   
   public boolean setIntPref(String pref, int data) {
      if (mContext != null) {
         SharedPreferences sp = mContext.getSharedPreferences(PREF,
               Context.MODE_PRIVATE);
         return sp.edit().putInt(pref, data).commit();
      }
      return false;
      
   }
   
   public int getLevelPref() {
      int level = 0;
      if (mContext != null) {
         SharedPreferences sp = mContext.getSharedPreferences(PREF,
               Context.MODE_PRIVATE);
         level = sp.getInt(GAME_LEVEL_PREF, -1);
      }
      return level;
   }
   
   public int getScorePref() {
      int score = 0;
      if (mContext != null) {
         SharedPreferences sp = mContext.getSharedPreferences(PREF,
               Context.MODE_PRIVATE);
         score = sp.getInt(GAME_SCORE_PREF, 0);
         
      }
      return score;
   }
   
   public boolean SetScorePref(int score) {
      return setIntPref(GAME_SCORE_PREF, score);
   }

   public int getSpeedRef() {
      int speed = 2;
      if (mContext != null) {
         SharedPreferences sp = mContext.getSharedPreferences(PREF,
               Context.MODE_PRIVATE);
         speed = sp.getInt(GAME_SPEED_PREF, 2);
         
      }
      return speed;
   }
   
   public boolean SetSpeedPref(int speed) {
      Utils.log(TAG, "set ant speed to " + speed);
      return setIntPref(GAME_SPEED_PREF, speed);
   }
}
