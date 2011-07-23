package com.howfun.android.antguide;

import com.howfun.android.HF2D.Pos;

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

   private static final int ANT_HEIGHT = 32;
   private static final int ANT_WIDTH = 32;

   public int whichAnt = 0;
   private Paint mBmpPaint;

   private Bitmap mGrassBmp;
   private Bitmap mHoleBmp;
   private boolean firstDraw = true;
   UpdateThread updateThread;

   private CanvasManager mCanvasManager;

   private static final String TAG = "AntView";

   private Context mContext;

   public AntView(Context context) {

      super(context);

      mContext = context;

      getHolder().addCallback(this);

      mCanvasManager = new CanvasManager(mContext);

      circleRadius = 10;

      circlePaint = new Paint();

      circlePaint.setColor(Color.BLUE);

      xVel = 2;

      yVel = 2;
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

   @Override
   protected void onDraw(Canvas canvas) {

      canvas.drawColor(Color.WHITE);

      if (mShowBlockLine) {

         mCanvasManager.setNewLine(new Pos(touchDownX, touchDownY), new Pos(
               touchUpX, touchUpY));
         mShowBlockLine = false;
      }
      mCanvasManager.draw(canvas);

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