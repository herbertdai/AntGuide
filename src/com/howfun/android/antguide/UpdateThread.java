package com.howfun.android.antguide;

import android.graphics.Canvas;

import android.view.SurfaceHolder;

public class UpdateThread extends Thread {

   private static final String TAG = "UpdateThread";

   private long time;

   private final int fps = 20;

   private boolean toRun = false;

   private AntView mAntView;

   private SurfaceHolder surfaceHolder;

   public UpdateThread(AntView antView) {

      mAntView = antView;

      surfaceHolder = mAntView.getHolder();

   }

   public void setRunning(boolean run) {

      toRun = run;

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


      }
   }
}
