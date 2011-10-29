/*
 * Ant map includes Home position, Obstacle's position
 */
package com.howfun.android.antguide.game;

import java.util.ArrayList;

import com.howfun.android.HF2D.Pos;
import com.howfun.android.antguide.utils.Utils;

public class AntMap {

   public static final int MAP_COUNT = 15;

   private static final String TAG = "AntMap";

   private static Pos _home1 = new Pos(100, 600);
   private static Pos[] _pos1 = { new Pos(500, 300) };
   
   private static Pos _home2 = new Pos(200, 600);
   private static Pos[] _pos2 = { new Pos(200, 300) };
   
   private static Pos _home3 = new Pos(300, 600);
   private static Pos[] _pos3 = { new Pos(100, 200), new Pos(200, 300),
         new Pos(300, 400) };
   
   private static Pos _home4 = new Pos(400, 600);
   private static Pos[] _pos4 = { new Pos(100, 200), new Pos(200, 200), new Pos(300, 200),
      new Pos(300, 300),
         new Pos(400, 400), 
         new Pos(500, 500) };
   
   private static Pos _home5 = new Pos(400, 700);
   private static Pos[] _pos5 = { 
      new Pos(50, 100), new Pos(400, 100),
      new Pos(50,200),new Pos(400, 200),
      new Pos(50,300),new Pos(400, 300),
      new Pos(50,400),new Pos(400, 400),
      new Pos(50,500),new Pos(400, 500),
      new Pos(50,600),new Pos(400, 600),
         };

   private static Pos _home6 = new Pos(300, 700);
   private static Pos[] _pos6 = {
      new Pos(100, 200), new Pos(150, 300), new Pos(200,400), new Pos(250, 500),
      new Pos(300, 200), new Pos(350, 300), new Pos(400,400), new Pos(450, 500),
   };
   
   private static Pos _home7 = new Pos(400, 700);
   private static Pos[] _pos7 = { 
      new Pos(100, 200),  new Pos(100, 400),
      new Pos(200, 200),  new Pos(200, 400),
      new Pos(300, 200),  new Pos(300, 400) 
   };
   
   private static Pos _home8 = new Pos(400, 700);
   private static Pos[] _pos8 = { 
      new Pos(100, 200), new Pos(100, 300), new Pos(100, 400),
      new Pos(200, 200), new Pos(200, 300), new Pos(200, 400),
      new Pos(300, 200), new Pos(300, 300), new Pos(300, 400) 
   };
   
   private static Pos _home9 = new Pos(300, 700);
   private static Pos[] _pos9 = { 
      new Pos(50, 200),  new Pos(50, 300),  new Pos(50, 400),
      new Pos(100, 200), new Pos(100, 300), new Pos(100, 400),
      new Pos(150, 200), new Pos(150, 300), new Pos(150, 400),
      new Pos(200, 200), new Pos(200, 300), new Pos(200, 400),
      new Pos(250, 200), new Pos(250, 300), new Pos(250, 400),
      new Pos(300, 200), new Pos(300, 300), new Pos(300, 400), 
      new Pos(350, 200), new Pos(350, 300), new Pos(350, 400) 
   };
   
   private static Pos _home10 = new Pos(400, 700);
   private static Pos[] _pos10 = { 
      new Pos(50, 200),  new Pos(50, 300),
      new Pos(100, 200), new Pos(100, 300), //new Pos(100, 400),
      new Pos(150, 200), new Pos(150, 300), //new Pos(150, 400),
      new Pos(200, 200), new Pos(200, 300), //new Pos(200, 400),
      new Pos(250, 200), //new Pos(250, 300), new Pos(250, 400),
      new Pos(300, 200), //new Pos(300, 300), new Pos(300, 400), 
                         new Pos(350, 300), //new Pos(350, 400), 
                         new Pos(400, 300),
                         new Pos(450, 300),
      };
   
   private static Pos _home11 = new Pos(400, 700);
   private static Pos[] _pos11 = { 
      new Pos(50, 150), new Pos(450, 150),
      new Pos(100, 200),new Pos(400, 200), 
      new Pos(150, 250),new Pos(350, 250),
      new Pos(200, 300), new Pos(300, 300),
      new Pos(250, 400)
      };
   
   private static Pos _home12 = new Pos(400, 700);
   private static Pos[] _pos12 = { 
      new Pos(50, 150), new Pos(100, 150),new Pos(150, 150),new Pos(350, 150),new Pos(400, 150),new Pos(450, 150),
      new Pos(100, 300), new Pos(150, 300), new Pos(200, 300), new Pos(250, 300), new Pos(300, 300),new Pos(350, 300), new Pos(400, 300),
      };
   private static Pos _home13 = new Pos(200, 420);
   private static Pos[] _pos13 = { 
      new Pos(100, 300), new Pos(150, 300),new Pos(200, 300),new Pos(250, 300),new Pos(300, 300),
      new Pos(100, 350),                                                       new Pos(300, 350),
      new Pos(100, 400),
      new Pos(100, 450),                                                       new Pos(300, 450),
      new Pos(100, 500),                                                       new Pos(300, 500),
      new Pos(100, 550), new Pos(150, 550),new Pos(200, 550),new Pos(250, 550),new Pos(300, 550),
      };
   private static Pos _home14 = new Pos(400, 750);
   private static Pos[] _pos14 = { 
      new Pos(300, 650), new Pos(350,650), new Pos(400,650), new Pos(450,650),
      new Pos(300, 700),
      new Pos(300, 730),
      };
   private static Pos _home15 = new Pos(200, 420);
   private static Pos[] _pos15 = { 
      
      new Pos(65,150),new Pos(110, 150), new Pos(150, 150),new Pos(200, 150),new Pos(250, 150),new Pos(300, 150),new Pos(350, 150),new Pos(400, 150), new Pos(450, 150),
      new Pos(100, 300), new Pos(150, 300),new Pos(200, 300),new Pos(250, 300),new Pos(300, 300), 
      new Pos(100, 350),                                                       new Pos(300, 350),
      new Pos(100, 400),                                                       new Pos(300, 380),
      new Pos(100, 450),                                                       new Pos(300, 450),
      new Pos(100, 500),                                                       new Pos(300, 500),
      new Pos(100, 550), new Pos(150, 550),new Pos(200, 550),new Pos(250, 550),new Pos(300, 550),
      };
   private static ArrayList<PosData> mPosDataList;

   public static final int OBS_H = 30;
   public static final int OBS_W= 30;

   public AntMap() {
      mPosDataList = new ArrayList<PosData>();
      mPosDataList.add(new PosData(_home1, _pos1));
      mPosDataList.add(new PosData(_home2, _pos2));
      mPosDataList.add(new PosData(_home3, _pos3));
      mPosDataList.add(new PosData(_home4, _pos4));
      mPosDataList.add(new PosData(_home5, _pos5));
      mPosDataList.add(new PosData(_home6, _pos6));
      mPosDataList.add(new PosData(_home7, _pos7));
      mPosDataList.add(new PosData(_home8, _pos8));
      mPosDataList.add(new PosData(_home9, _pos9));
      mPosDataList.add(new PosData(_home10, _pos10));
      mPosDataList.add(new PosData(_home11, _pos11));
      mPosDataList.add(new PosData(_home12, _pos12));
      mPosDataList.add(new PosData(_home13, _pos13));
      mPosDataList.add(new PosData(_home14, _pos14));
      mPosDataList.add(new PosData(_home15, _pos15));
   }

   public int getMapCount() {
      if (mPosDataList != null) {
         return mPosDataList.size();
      }
      return 0;
   }

   public Pos getHome(int mapId) {

      if (mPosDataList != null) {
         PosData data = mPosDataList.get(mapId);
         if (data == null) {
            return null;
         }
         return data.getHomePos();
      }
      return null;
   }

   public ArrayList<Pos> getObs(int mapId) {
      if (mPosDataList != null) {
         PosData data = mPosDataList.get(mapId);
         if (data == null) {
            return null;
         }
         Utils.log(TAG, "Obstacles in map is " + data.getObsList());
         return data.getObsList();
      }
      Utils.log(TAG, "Obstacles in map is null");
      return null;
   }

   public int getObsW() {
      return OBS_W;
   }

   public int getObsH() {
      return OBS_H;
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

      public void setHomePos(Pos pos) {
         mHomePos = pos;
      }

      public void setObsPosList(Pos[] poses) {
         if (mObsPosList == null) {
            mObsPosList = new ArrayList<Pos>();
         }
         mObsPosList.clear();
         for (int i = 0; i < poses.length; i++) {
            mObsPosList.add(poses[i]);
         }
      }

   }

}
