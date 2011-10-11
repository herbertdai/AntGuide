/*
 * Singleton class to store game level and score
 */
package com.howfun.android.antguide;

import android.content.Context;
import android.content.SharedPreferences;

public class GamePref {

   private static GamePref instance;

   private Context mContext;

   private static final String PREF = "ant pref";

   private static final String GAME_LEVEL_PREF = "game level pref";

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

   public void setLevelPref(int level) {
      if (mContext != null) {
         SharedPreferences sp = mContext.getSharedPreferences(PREF,
               Context.MODE_PRIVATE);
         sp.edit().putInt(GAME_LEVEL_PREF, level).commit();
      }
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
   
   
   

}
