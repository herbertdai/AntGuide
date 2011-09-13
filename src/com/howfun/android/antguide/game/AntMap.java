/*
 * Ant map includes Home position, Obstacle's position
 */
package com.howfun.android.antguide.game;

import java.util.ArrayList;

import com.howfun.android.HF2D.Pos;
import com.howfun.android.antguide.utils.Utils;

public class AntMap {
   
   private static final String TAG = "AntMap";
   
   private static Pos _home1 = new Pos(200, 600);
   private static Pos [] _pos1 = {new Pos(100, 200), new Pos(200, 300), new Pos(300, 400)};
   
   private ArrayList<PosData> mPosDataList;
   
   private final int mObsH = 30;
   private final int mObsW = 30;
   
   public AntMap() {
      mPosDataList = new ArrayList<PosData>();
      mPosDataList.add(new PosData(_home1, _pos1));
      assert(mPosDataList.size()==5);
   }
   
   public Pos getHome(int mapId){
      if(mPosDataList != null) {
         PosData data = mPosDataList.get(mapId);
         if (data == null) {
            return null;
         }
         return data.getHomePos();
      }
      return null;
   }
   
   public ArrayList<Pos> getObs(int mapId) {
      if(mPosDataList != null) {
         PosData data = mPosDataList.get(mapId);
         if (data == null) {
            return null;
         }
         return data.getObsList();
      }
      Utils.log(TAG, "Obstacles in map is null");
      return null;
   }
   
   public int getObsW() {
      return mObsW;
   }
   public int getObsH() {
      return mObsH;
   }
   
   public class PosData {
      private Pos mHomePos;
      private ArrayList<Pos> mObsPosList;
      
      public PosData() {
         mObsPosList = new ArrayList<Pos>(0);
      }
      
      public Pos getHomePos() {
         return mHomePos;
      }
      
      public ArrayList<Pos> getObsList() {
         return mObsPosList;
      }

      public PosData(Pos homePos, Pos[] obs) {
         this();
         setHomePos(homePos);
         setObsPosList(obs);
      }
      
      public void setHomePos (Pos pos) {
         mHomePos = pos; 
      }
      
      public void setObsPosList(Pos [] poses) {
         if (mObsPosList == null) {
            mObsPosList = new ArrayList<Pos>();
         }
         mObsPosList.clear();
         for (int i=0; i<poses.length; i++) {
            mObsPosList.add(poses[i]);
         }
      }
      
   }


}
