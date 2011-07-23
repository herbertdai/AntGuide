package com.howfun.android.HF2D;

import com.howfun.android.antguide.Utils;

import android.graphics.Rect;


public class HF2D {

   private static final String TAG = "HF2D";

   /*
    * Get next pos by angle and speed
    * @param[in, out]: mPos   current position, it will be updated. 
    * @param[in]: speed       speed per step
    * @param[in]:angle        angle to move
    */
   public static void getNextPos(Pos mPos, double speed, int angle) {
      
      double radians = 2 * 3.1415 * angle / 360;
      float xStep = (float) (mPos.x + speed * Math.cos(radians));
      float yStep = (float) (mPos.y + speed * Math.sin(radians));
      mPos.x = xStep;
      mPos.y = yStep;
      
   }
   
   /*
    * Check collision of rect and line
    * if collision, then set a new angle
    * @return true   collision
    */
   public static boolean checkCollision(AntSprite ant, LineSprite line) {
      if (ant == null || line == null) {
         Utils.log(TAG, "ant or line is null in checkCollision()");
         return false;
      }
      //Left two dot of rect and upper right dot
      float x1 = ant.mRect.left;
      float y1 = ant.mRect.top;
      float x2 = x1; 
      float y2 = ant.mRect.top + ant.mRect.height();
      float x3 = ant.mRect.left + ant.mRect.width();
      float y3 = y1;
      
      //Line's two dot
      float lax = line.getStart().x;
      float lay = line.getStart().y;
      float lbx = line.getEnd().x;
      float lby = line.getEnd().y;
         
      boolean isCollision = false;
      //check upper horizontal line
      float rx = (y1 - lay) * (lbx - lax) / (lby -lay) + lax;
      if (rx >= x1 && rx <= x3) {
         isCollision = true; 
      }
      //check lower horizontal line
      rx = (y2 - lay) * (lbx - lax) / (lby -lay) + lax;
      if (rx >= x1 && rx <= x3) {
         isCollision = true; 
      }
      
      //TODO set ant a new angle
      if (isCollision) {
         ant.setAngle(ant.getAngle() + 20);
      }
      
      
      return isCollision;
   }

   /*
    * Check collision of two rect 
    */
   public static boolean checkCollision(Rect r1, Rect r2) {
      return false;
   }

   /*
    * Calculate rect by center point
    * @param[in, out] mRect   rect to calc
    * @param[in]     mPos     center point
    */
   public static void calRectByPos(Rect mRect, Pos mPos, int width, int height) {
      mRect.left = (int) (mPos.x - width / 2); 
      mRect.right = mRect.left + width;
      mRect.top = (int) (mPos.y - height /2);
      mRect.bottom = mRect.top + height;
   }
}
