package com.howfun.android.antguide;

import android.content.Context;

public class GameStatus {
   private int mStatus;
   public static final int GAME_RUNNING = 1;
   public static final int GAME_PAUSED = 2;
   public static final int GAME_STOPPED = 3;
   
   private Context mContext;
   
   public GameStatus(Context context) {
      mContext = context;
   }
   public void setStaus(int status) {
      mStatus = status;
      switch(mStatus) {
         
      }
   }
   
   public int getStatus() {
      return mStatus;
   }
   private void setPref(int status) {
     
      
   }

}
