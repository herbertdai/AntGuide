/*
 * Ant map includes Home position, Obstacle's position
 */
package com.howfun.android.antguide.game;

import java.util.ArrayList;

import com.howfun.android.HF2D.Pos;
import com.howfun.android.antguide.utils.Utils;

public class AntMap {
   
   private static final String TAG = "AntMap";
   
   private static Pos _home1 = new Pos(100, 600);
   private static Pos _home2 = new Pos(200, 600);
   private static Pos _home3 = new Pos(300, 600);
   private static Pos _home4 = new Pos(400, 600);
   private static Pos _home5 = new Pos(400, 700);
   
   private static Pos [] _pos1 = {new Pos(200, 300)};
   private static Pos [] _pos2 = {new Pos(200, 300), new Pos(300, 400)};
   private static Pos [] _pos3 = {new Pos(100, 200), new Pos(200, 300), new Pos(300, 400)};
   private static Pos [] _pos4 = {new Pos(100, 200), new Pos(300, 300), new Pos(400, 400)};
   private static Pos [] _pos5 = {new Pos(100, 200), new Pos(200, 400), new Pos(300, 500)};
   
   private ArrayList<PosData> mPosDataList;
   
   private final int mObsH = 30;
   private final int mObsW = 30;
   
   public AntMap() {
      mPosDataList = new ArrayList<PosData>();
      mPosDataList.add(new PosData(_home1, _pos1));
      mPosDataList.add(new PosData(_home2, _pos2));
      mPosDataList.add(new PosData(_home3, _pos3));
      mPosDataList.add(new PosData(_home4, _pos4));
      mPosDataList.add(new PosData(_home5, _pos5));
   }
   
   public int getMapCount() {
      if (mPosDataList != null) {
         return mPosDataList.size();
      }
      return 0;
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
