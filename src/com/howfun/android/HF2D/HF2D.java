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
   public static void getNextPos(Pos mPos, float speed, float angle) {
      
      double radians = Math.toRadians(angle); 
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
      float oldAngle = ant.getAngle();
      
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
      boolean isUpperCollision = false;
      //check upper horizontal line
      float rx = (y1 - lay) * (lbx - lax) / (lby -lay) + lax;
      if (rx >= x1 && rx <= x3) {
         isCollision = true; 
         isUpperCollision = true;
         
      }
      //check lower horizontal line
      rx = (y2 - lay) * (lbx - lax) / (lby -lay) + lax;
      if (rx >= x1 && rx <= x3) {
         isCollision = true; 
      }
      
      if (isCollision) {
         //set line two point a new sequence: smaller up, bigger down
         if (lay > lby) {
            //swap
            float temp = lax;
            lax = lbx;
            lbx = temp;
            
            temp = lay;
            lay = lby;
            lby = temp;
         }
         
         double lineAngle = Math.atan2((lby - lay),  (lbx - lax));
         float degree = (float) Math.toDegrees(lineAngle);
         float randomDegree = (float) (Math.random() * 180);
         float angle = degree + randomDegree;
         
         if (lax < lbx) { //'\' line
            if (!isUpperCollision) {
               angle += 180;
            }
         } else {
            if (isUpperCollision) {
               angle += 180;
            }
         }
         
         
         //Check horizon and vertical line
         int YUZHI = 6;
         //vertical
         if (Math.abs(lax - lbx) <=YUZHI) {
            Utils.log(TAG ,"vertical line");
            
            angle = 90 + randomDegree;
            
            //verify origin direction: if old and new on same PI, change to another PI 
            if (oldAngle > 90 && oldAngle < 270) {
               Utils.log(TAG, "change PI");
               angle = angle + 180; 
            }
         }
         //horizontal
         Utils.log(TAG, "y differs: " + (lay - lby));
         
         if (Math.abs(lay - lby) <= YUZHI) {
            Utils.log(TAG, "hori line");
            angle = 0 + randomDegree;
            if (oldAngle > 0 && oldAngle < 180) {
               Utils.log(TAG, "change PI");
               angle = angle + 180;
            }
         }
         
         ant.setAngle(angle );
         
      }
      
      
      return isCollision;
   }
   
   /*
    * check if ant is out of screen
    */
   public static boolean checkOutOfScreen(AntSprite ant) {
      
      if ((ant.mPos.x < -AntSprite.ANT_WIDTH) || (ant.mPos.y > 800)) {
         return true; 
      }
      return false;
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
