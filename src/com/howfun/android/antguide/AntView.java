package com.howfun.android.antguide;

import android.content.Context;

import android.graphics.Canvas;

import android.graphics.Color;

import android.graphics.Paint;

import android.graphics.Rect;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

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

   }

   public void setDownPos(float x, float y){
      touchDownX = x;
      touchDownY = y;
   }
   
   public void setUpPos(float x, float y){
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

      if (mShowBlockLine) {
         
         canvas.drawLine(touchDownX, touchDownY, touchUpX, touchUpY, circlePaint);
         
         oldTouchDownX = touchDownX;
         oldTouchDownY = touchDownY;
         oldTouchUpX = touchUpX;
         oldTouchUpY = touchUpY;
         mShowBlockLine = false;
      } else {
         canvas.drawLine(oldTouchDownX, oldTouchDownY, oldTouchUpX, oldTouchUpY, circlePaint);
         
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