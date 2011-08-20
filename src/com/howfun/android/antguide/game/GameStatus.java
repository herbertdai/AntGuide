package com.howfun.android.antguide.game;

import com.howfun.android.HF2D.Pos;

public class GameStatus {
   private int mStatus;
   public static final int GAME_INIT = 0; //is to run
   public static final int GAME_RUNNING = 1;
   public static final int GAME_PAUSED = 2;
   public static final int GAME_STOPPED = 3;

   private Pos mAntPos;
   private AntMap mMap;
   private float mAntAngle;

   public GameStatus() {
      mStatus = GAME_INIT;
   }

   public void setStaus(int status) {
      mStatus = status;
   }

   public int getStatus() {
      return mStatus;
   }

   private void setPref(int status) {

   }

   public void setAntPos(Pos mPos) {
      this.mAntPos = mPos;
   }

   public Pos getAntPos() {
      return mAntPos;
   }

   public void setAntAngle(float angle) {
      this.mAntAngle = angle;
   }
   public float getAntAngle() {
      return this.mAntAngle;
   }
}
