package com.howfun.android.antguide;

import android.graphics.Canvas;
import android.graphics.Rect;

import android.view.SurfaceHolder;

public class UpdateThread extends Thread {

   private static final String TAG = "UpdateThread";

   private long time;
   private long animTime;

   private final int fps = 100; // frames per second
   private final int animFps = 10;

   private boolean toRun = false;

   private boolean flag = true;

   int whichAntAnim = 0;

   private AntView mAntView;

   private SurfaceHolder surfaceHolder;

   public UpdateThread(AntView antView) {

      mAntView = antView;

      surfaceHolder = mAntView.getHolder();

   }

   public void setRunning(boolean run) {

      toRun = run;

   }

   /**
    * start drawing the canvas
    */
   public void startUpdate() {
      flag = true;
   }

   /**
    * stop drawing the canvas
    */
   public void stopUpdate() {
      flag = false;
   }

   private int whichAntAnim() {
      int which = whichAntAnim++;
      if (whichAntAnim == 4) {
         whichAntAnim = 0;
      }
      return which;
   }

   public void run() {

      Canvas c;

      while (toRun) {

         long cTime = System.currentTimeMillis();

         if ((cTime - time) > (1000 / fps)) {
            c = null;

            if (flag) {
               try {

                  c = surfaceHolder.lockCanvas(null);

                  mAntView.onDraw(c);

               } finally {

                  if (c != null) {

                     surfaceHolder.unlockCanvasAndPost(c);

                  }

               }
            }

            time = cTime;

         }

         // update ant anim
         if ((cTime - animTime) > (1000 / animFps)) {
            if(flag){
               mAntView.setWhichAntAnim(whichAntAnim());
            }

            animTime = cTime;

         }

      }
   }
}
