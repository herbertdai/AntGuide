package com.howfun.android.antguide;

import android.os.Handler;
import android.os.Message;

public class TimeManager {
   private static final int SECOND = 60;
   private int time;

   private int minute;
   private int second;

   private boolean toRun;
   private boolean flag;

   private Handler handler;

   private Thread thread = new Thread() {
      public void run() {
         while (toRun) {
            if (flag) {
               try {
                  Message msg = new Message();
                  msg.what = Utils.MSG_TIME_UPDATED;
                  msg.obj = timeStr();
                  handler.sendMessage(msg);
                  Thread.sleep(1000);
                  add();
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }
         }
      }
   };

   public TimeManager(Handler h) {
      handler = h;
      toRun = true;
      time = 0;
      minute = 0;
      second = 0;
      thread.start();
   }

   public void start() {
      flag = true;
      reset();
   }

   public void resume() {
      flag = true;
   }

   public void pause() {
      flag = false;
   }

   public void stop() {
      flag = false;
   }

   public int getMinute() {

      return minute;
   }

   public int getSecond() {
      return second;
   }

   private void reset() {
      time = 0;
      minute = 0;
      second = 0;
   }

   /**
    * add one second
    */
   private void add() {
      time++;
      minute = time / SECOND;
      second = time % SECOND;
   }

   private String timeStr() {
      String timeStr = "";
      String min = "";
      String sec = "";
      if (minute < 10) {
         min = "0" + String.valueOf(minute);
      } else {
         min = String.valueOf(minute);
      }

      if (second < 10) {
         sec = "0" + String.valueOf(second);
      } else {
         sec = String.valueOf(second);
      }
      timeStr = min + sec;
      return timeStr;
   }
}
