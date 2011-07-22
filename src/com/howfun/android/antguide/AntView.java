package com.howfun.android.antguide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AntView extends SurfaceView implements SurfaceHolder.Callback {

   private int xPos;

   private int yPos;

   private int xVel;

   private int yVel;

   private int width;

   private int border;

   private int circleRadius;

   private Paint circlePaint;

   private float touchDownX = 10;

   private float touchDownY = 10;

   private float touchUpX = 200;

   private float touchUpY = 300;

   private boolean mShowBlockLine;

   private static final int ANT_HEIGHT = 100;
   private static final int ANT_WIDTH = 100;

   public int whichAnt = 0;
   private Paint mBmpPaint;
   private Bitmap mAntBmpArray[];
   UpdateThread updateThread;

   private static final String TAG = "AntView";

   public AntView(Context context) {

      super(context);

      getHolder().addCallback(this);

      circleRadius = 10;

      circlePaint = new Paint();

      circlePaint.setColor(Color.BLUE);

      xVel = 2;

      yVel = 2;
      loadAntBmp();
   }

   private void loadAntBmp() {
      mAntBmpArray = new Bitmap[4];
      Resources r = this.getContext().getResources();
      Drawable antDrawable0 = r.getDrawable(R.drawable.ant0);
      Drawable antDrawable1 = r.getDrawable(R.drawable.ant1);
      Drawable antDrawable2 = r.getDrawable(R.drawable.ant2);
      Drawable antDrawable3 = r.getDrawable(R.drawable.ant3);

      Bitmap bitmap = Bitmap.createBitmap(ANT_WIDTH, ANT_HEIGHT,
            Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);
      antDrawable0.setBounds(0, 0, ANT_WIDTH, ANT_HEIGHT);
      antDrawable0.draw(canvas);
      mAntBmpArray[0] = bitmap;

      bitmap = Bitmap.createBitmap(ANT_WIDTH, ANT_HEIGHT,
            Bitmap.Config.ARGB_8888);
      canvas = new Canvas(bitmap);
      antDrawable1.setBounds(0, 0, ANT_WIDTH, ANT_HEIGHT);
      antDrawable1.draw(canvas);
      mAntBmpArray[1] = bitmap;

      bitmap = Bitmap.createBitmap(ANT_WIDTH, ANT_HEIGHT,
            Bitmap.Config.ARGB_8888);
      canvas = new Canvas(bitmap);
      antDrawable2.setBounds(0, 0, ANT_WIDTH, ANT_HEIGHT);
      antDrawable2.draw(canvas);
      mAntBmpArray[2] = bitmap;

      bitmap = Bitmap.createBitmap(ANT_WIDTH, ANT_HEIGHT,
            Bitmap.Config.ARGB_8888);
      canvas = new Canvas(bitmap);
      antDrawable3.setBounds(0, 0, ANT_WIDTH, ANT_HEIGHT);
      antDrawable3.draw(canvas);
      mAntBmpArray[3] = bitmap;
   }

   public void setDownPos(float x, float y) {
      touchDownX = x;
      touchDownY = y;
   }

   public void setUpPos(float x, float y) {
      touchUpX = x;
      touchUpY = y;
   }

   public void showBlockLine() {
      mShowBlockLine = true;

   }

   float oldTouchDownX, oldTouchDownY, oldTouchUpX, oldTouchUpY;

   @Override
   protected void onDraw(Canvas canvas) {

      canvas.drawColor(Color.WHITE);

      canvas.drawCircle(xPos, yPos, circleRadius, circlePaint);
      canvas.drawBitmap(mAntBmpArray[whichAnt], 12, xPos, mBmpPaint);

      if (mShowBlockLine) {

         canvas.drawLine(touchDownX, touchDownY, touchUpX, touchUpY,
               circlePaint);

         oldTouchDownX = touchDownX;
         oldTouchDownY = touchDownY;
         oldTouchUpX = touchUpX;
         oldTouchUpY = touchUpY;
         mShowBlockLine = false;
      } else {
         canvas.drawLine(oldTouchDownX, oldTouchDownY, oldTouchUpX,
               oldTouchUpY, circlePaint);

      }

   }

   public void updatePhysics() {

      xPos += xVel;

      yPos += yVel;

      if (yPos - circleRadius < 0 || yPos + circleRadius > border) {

         if (yPos - circleRadius < 0) {

            yPos = circleRadius;

         } else {

            yPos = border - circleRadius;

         }

         yVel *= -1;

      }

      if (xPos - circleRadius < 0 || xPos + circleRadius > width) {

         if (xPos - circleRadius < 0) {

            xPos = circleRadius;

         } else {

            xPos = width - circleRadius;

         }

         xVel *= -1;

      }

   }

   public void surfaceCreated(SurfaceHolder holder) {

      Rect surfaceFrame = holder.getSurfaceFrame();

      width = surfaceFrame.width();

      border = surfaceFrame.height();

      xPos = width / 2;

      yPos = circleRadius;

      updateThread = new UpdateThread(this);

      updateThread.setRunning(true);

      updateThread.start();

   }

   public void surfaceChanged(SurfaceHolder holder, int format, int width,
         int border) {

   }

   public void surfaceDestroyed(SurfaceHolder holder) {

      boolean retry = true;

      updateThread.setRunning(false);

      while (retry) {

         try {

            updateThread.join();

            retry = false;

         } catch (InterruptedException e) {

         }

      }

   }

}