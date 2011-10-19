package com.howfun.android.antguide.game;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;

import com.howfun.android.HF2D.AntSprite;
import com.howfun.android.HF2D.FoodSprite;
import com.howfun.android.HF2D.HF2D;
import com.howfun.android.HF2D.HomeSprite;
import com.howfun.android.HF2D.LineSprite;
import com.howfun.android.HF2D.Pos;
import com.howfun.android.HF2D.Sprite;
import com.howfun.android.antguide.AntGuideActivity;
import com.howfun.android.antguide.R;
import com.howfun.android.antguide.utils.Utils;

public class CanvasManager {

   private ArrayList<Sprite> mSprites;

   private AntMap mMap;

   private Context mContext;

   private Bitmap mObsBmp;

   private Paint mBmpPaint;
   private Paint mResultPaint;

   private Bitmap mBackgroundBmp;

   private AntSprite mAnt;
   private LineSprite mLine;
   private FoodSprite mFood;

   private int mMapLevel;

   private static final String TAG = "CanvasManager";

   private boolean mIsAntLost;
   private boolean mIsAntHome;

   private float LOST_TEXT_SIZE = 50;

   private HomeSprite mHome;
   private Handler mHandler;

   private ArrayList<Pos> mObstacleList;

   public CanvasManager(Context c) {
      mContext = c;

      mSprites = new ArrayList<Sprite>();

      mBmpPaint = new Paint();
      mBmpPaint.setColor(Color.YELLOW);
      mResultPaint = new Paint();
      mResultPaint.setTextSize(LOST_TEXT_SIZE);

      loadObstacle();
      loadBackground();
   }

   public void setHandler(Handler handler) {
      mHandler = handler;
      Utils.log(TAG, "set canvas manager handler");
   }

   public void initAllSprite() {
      Utils.log(TAG, "initAllSprite");

      mIsAntHome = false;
      mIsAntLost = false;

      if (mSprites == null) {
         mSprites = new ArrayList<Sprite>();
      }

      if (mAnt == null) {
         AntSprite ant = new AntSprite(mContext);
         mAnt = ant;
         mSprites.add(ant);
      } else {
         mAnt.reset();
      }

      if (mLine == null) {
         LineSprite line = new LineSprite();
         mLine = line;
         mSprites.add(line);
      }

      Pos homePos = null;
      if (mMap != null) {
         homePos = mMap.getHome(mMapLevel);
      }

      if (mHome == null) {
         mHome = new HomeSprite(mContext, homePos);
         mSprites.add(mHome);
      }

      Pos foodPos = HF2D.getNewPos(AntGuideActivity.DEVICE_WIDTH,
            AntGuideActivity.DEVICE_HEIGHT);
      if (mFood == null) {
         mFood = new FoodSprite(mContext, foodPos);
         mSprites.add(mFood);
      } else {
         mFood.setNewPos();
      }

      if (mMap != null) {
         mObstacleList = mMap.getObs(mMapLevel);
      }

      // TODO add more sprites
   }

   public ArrayList<Sprite> getSprites() {
      return mSprites;
   }

   /*
    * Check collision of ant with line, hole, wall
    */
   public void checkCollision() {
      checkLine();
      checkHome();
      checkOutOfScreen();
      checkFoods();
      checkObstacle();
   }

   private void checkObstacle() {
      if (mAnt == null) {
         return;
      }

      if (mObstacleList != null && mObstacleList.size() > 0) {
         Iterator<Pos> it = mObstacleList.iterator();
         while (it.hasNext()) {
            Pos pos = it.next();
            int obsW = mMap.getObsW();
            int obsH = mMap.getObsH();
            Rect obsRect = new Rect((int) pos.x, (int) pos.y, (int) pos.x
                  + obsW, (int) pos.y + obsH);

            if (HF2D.checkRectCollision(obsRect, mAnt.getRect())) {
               if (mHandler != null) {
                  mHandler.sendMessage(mHandler
                        .obtainMessage(Utils.MSG_ANT_TRAPPED));
               }
               break;
            }
         }
      }

   }

   private void checkLine() {

      boolean isCollide = false;

      if (mAnt != null && mLine != null) {
         isCollide = HF2D.checkRectAndLineCollision_mirror(mAnt, mLine);
      }

      if (isCollide) {
         if (mHandler != null) {
            mHandler.sendMessage(mHandler
                  .obtainMessage(Utils.MSG_ANT_COLLISION));
         }
      }

   }

   private void checkHome() {
      if (HF2D.checkCollsion(mAnt, mHome)) {
         antHome();
      }

   }

   private void checkFoods() {
      if (mFood != null) {
         if (HF2D.checkCollsion(mAnt, mFood)) {
            Utils.log(TAG, "Eat food!!!");
            mFood.setNewPos();
            mHandler.sendMessage(mHandler.obtainMessage(Utils.MSG_ANT_FOOD));

         }

      }
   }

   private void checkOutOfScreen() {
      if (HF2D.checkOutOfScreen(mAnt, AntGuideActivity.DEVICE_WIDTH,
            AntGuideActivity.DEVICE_HEIGHT)) {
         antLost();
      }
   }

   private void antLost() {
      mIsAntLost = true;
      mHandler.sendMessage(mHandler.obtainMessage(Utils.MSG_ANT_LOST));
   }

   private void antHome() {
      mIsAntHome = true;
      mHandler.sendMessage(mHandler.obtainMessage(Utils.MSG_ANT_HOME));
   }

   private void drawObs(Canvas canvas) {
      // TODO: draw
      Iterator<Pos> it = mObstacleList.iterator();
      while (it.hasNext()) {
         Pos pos = it.next();
         canvas.drawBitmap(mObsBmp, pos.x, pos.y, mBmpPaint);
      }
      if (mObstacleList == null) {
         return;
      }
   }

   public void draw(Canvas canvas) {

      checkCollision();

      drawBg(canvas);

      drawObs(canvas);

      for (int i = 0; i < mSprites.size(); i++) {
         mSprites.get(i).draw(canvas);
      }
   }

   public void setNewLine(Pos start, Pos end) {

      if (mLine != null) {
         mLine.setPos(start, end);
      }

   }

   private void drawBg(Canvas canvas) {
      canvas.drawBitmap(mBackgroundBmp, 0, 0, mBmpPaint);
   }

   private void loadObstacle() {
      Resources r = mContext.getResources();
      Drawable grassDrawable = r.getDrawable(R.drawable.trap);
      Bitmap bitmap = Bitmap.createBitmap(AntMap.OBS_W, AntMap.OBS_H,
            Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);
      grassDrawable.setBounds(0, 0, AntMap.OBS_W, AntMap.OBS_H);
      grassDrawable.draw(canvas);
      mObsBmp = bitmap;
      Utils.log(TAG, "loadGrass bmp = " + mObsBmp);
   }

   private void loadBackground() {
      int width = AntGuideActivity.DEVICE_WIDTH;
      int height = AntGuideActivity.DEVICE_HEIGHT;

      Resources r = mContext.getResources();
      Drawable holeDrawable = r.getDrawable(R.drawable.background);
      Bitmap bitmap = Bitmap.createBitmap(width, height,
            Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);
      holeDrawable.setBounds(0, 0, width, height);
      holeDrawable.draw(canvas);
      mBackgroundBmp = bitmap;
   }

   public void setWhichAntAnim(int which) {
      if (mAnt != null) {
         mAnt.mWhichAntAnim = which;
      }
   }

   // clear memory
   public void clear() {
      Utils.recycleBitmap(mBackgroundBmp);
      Utils.recycleBitmap(mObsBmp);
      if (mSprites != null) {
         for (int i = 0; i < mSprites.size(); i++) {
            mSprites.get(i).clear();
         }
      }
   }

   public void restoreGame(GameStatus gameStatus) {
      if (mAnt != null) {
         mAnt.setPos(gameStatus.getAntPos());
         mAnt.setAngle(gameStatus.getAntAngle());
         Utils.log(TAG, "set angle = " + gameStatus.getAntAngle());
      }
   }

   public void getGameStatus(GameStatus gameStatus) {
      if (mAnt != null) {
         gameStatus.setAntPos(mAnt.getPos());
         gameStatus.setAntAngle(mAnt.getAngle());
      }

   }

   public void setMap(AntMap map) {
      mMap = map;
   }

   public void setMapLevel(int level) {
      mMapLevel = level;
      if (mMap != null) {
         mObstacleList = mMap.getObs(mMapLevel);
         Utils.log(TAG, "setMapLevel() obstacle list size = " + mObstacleList.size());
      }
      if (mHome != null) {
         Pos pos = mMap.getHome(mMapLevel);
         mHome.setPos(pos);
      }
   }

   public void reset() {
      mAnt.reset();

   }
}
