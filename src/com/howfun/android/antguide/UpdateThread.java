package com.howfun.android.antguide;

import android.graphics.Canvas;
import android.graphics.Rect;

import android.view.SurfaceHolder;

public class UpdateThread extends Thread {

   private static final String TAG = "UpdateThread";

   private long time;
   private long animTime;

   private final int fps = 100;
   private final int animFps = 10;

   private boolean toRun = false;

   int whichAnt = 0;

   private AntView mAntView;

   private SurfaceHolder surfaceHolder;

   public UpdateThread(AntView antView) {

      mAntView = antView;

      surfaceHolder = mAntView.getHolder();

   }

   public void setRunning(boolean run) {

      toRun = run;

   }

   private int whichAnt() {
      int which = whichAnt++;
      if (whichAnt == 4) {
         whichAnt = 0;
      }
      return which;
   }

   public void run() {

      Canvas c;

      while (toRun) {

         long cTime = System.currentTimeMillis();

         if ((cTime - time) > (1000 / fps)) {
            c = null;

            try {

               c = surfaceHolder.lockCanvas(null);

               mAntView.updatePhysics();
               mAntView.onDraw(c);

            } finally {

               if (c != null) {

                  surfaceHolder.unlockCanvasAndPost(c);

               }

            }

            time = cTime;

         }

         // anim
         if ((cTime - animTime) > (1000 / animFps)) {
            mAntView.whichAnt = whichAnt();

            animTime = cTime;

         }

      }
   }
}
