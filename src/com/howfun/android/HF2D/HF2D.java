package com.howfun.android.HF2D;

import com.howfun.android.antguide.Utils;

import android.graphics.Rect;

public class HF2D {

   private static final String TAG = "HF2D";
   private static final int YUZHI = 6;
   private static final double SPACE = 150;

   /*
    * Get next pos by angle and speed
    * 
    * @param[in, out]: mPos current position, it will be updated.
    * 
    * @param[in]: speed speed per step
    * 
    * @param[in]:angle angle to move
    */
   public static void getNextPos(Pos mPos, float speed, float angle) {

      double radians = Math.toRadians(angle);
      float xStep = (float) (mPos.x + speed * Math.cos(radians));
      float yStep = (float) (mPos.y + speed * Math.sin(radians));
      mPos.x = xStep;
      mPos.y = yStep;

   }

   /*
    * Check collision of rect and line if collision, then set a new angle
    * 
    * @return true collision
    */
   public static boolean checkRectAndLineCollision(AntSprite ant, LineSprite line) {
      
      if (ant.checkIsCoolDown()) {
         return false;
      }

      if (ant == null || line == null) {
         Utils.log(TAG, "ant or line is null in checkCollision()");
         return false;
      }
      float oldAngle = ant.getAngle();
      float randomDegree = (float) (Math.random() * 180);

      // Left two dot of rect and upper right dot
      float x1 = ant.mRect.left;
      float y1 = ant.mRect.top;
      float x2 = x1;
      float y2 = ant.mRect.top + ant.mRect.height();
      float x3 = ant.mRect.left + ant.mRect.width();
      float y3 = y1;

      // Line's two dot
      float lax = line.getStart().x;
      float lay = line.getStart().y;
      float lbx = line.getEnd().x;
      float lby = line.getEnd().y;

      boolean isCollision = false;
      boolean isUpperCollision = false;

      // check upper horizontal side
      float rx = (y1 - lay) * (lbx - lax) / (lby - lay) + lax;

      float a = 0, b = 0;
      if (lax < lbx) {
         a = lax;
         b = lbx;
      } else {
         b = lax;
         a = lbx;
      }
      if (rx >= x1 && rx <= x3 && rx >= a && rx <= b) {
         isCollision = true;
         isUpperCollision = true;

      }
      // check lower horizontal side
      rx = (y2 - lay) * (lbx - lax) / (lby - lay) + lax;
      if (rx >= x1 && rx <= x3 && (rx >= a && rx <= b)) {
         isCollision = true;
      }

      if (isCollision) {
         // set line two point a new sequence: smaller up, bigger down
         if (lay > lby) {
            // swap
            float temp = lax;
            lax = lbx;
            lbx = temp;

            temp = lay;
            lay = lby;
            lby = temp;
         }

         double lineAngle = Math.atan2((lby - lay), (lbx - lax));
         float degree = (float) Math.toDegrees(lineAngle);
         float angle = degree + randomDegree;

         if (lax < lbx) { // '\' line
            if (!isUpperCollision) {
               angle += 180;
            }
         } else {
            if (isUpperCollision) {
               angle += 180;
            }
         }

         // Check vertical line
         if (Math.abs(lax - lbx) <= YUZHI) {
            Utils.log(TAG, "vertical line");

            angle = 90 + randomDegree;

            // verify origin direction: if old and new on same PI, change to
            // another PI
            if (oldAngle > 90 && oldAngle < 270) {
               Utils.log(TAG, "change PI");
               angle = angle + 180;
            }
         }

         ant.setAngle(angle);

      }

      // fix bug: horizontal line can't be checked
      if (!isCollision) {
         float angle = 0;
         float ry = (x2 - lax) * (lby - lay) / (lbx - lax) + lay;
         if (lay < lby) {
            a = lay;
            b = lby;
         } else {
            a = lby;
            b = lay;
         }
         if ((ry >= y1) && (ry <= y2) && (ry >= a) && (ry <= b)) {
            isCollision = true;
         }
         if (isCollision) {

            Utils.log(TAG, "hori line");
            angle = 0 + randomDegree;
            if (oldAngle > 0 && oldAngle < 180) {
               Utils.log(TAG, "change PI");
               angle = angle + 180;
            }
            ant.setAngle(angle);
         }
      }

      if (isCollision) {
         ant.startCoolDown();
      }
      return isCollision;
   }

   /*
    * check if ant is out of screen
    */
   public static boolean checkOutOfScreen(AntSprite ant, int screenWidth,
         int screenHeight) {

      if ((ant.mPos.x < -AntSprite.ANT_WIDTH) || (ant.mPos.x > screenWidth)
            || (ant.mPos.y < -AntSprite.ANT_HEIGHT)
            || (ant.mPos.y > screenHeight)) {
         return true;
      }
      return false;
   }

   public static boolean checkCollsion(Sprite sprite1, Sprite sprite2) {
      if (sprite1 == null || sprite2 == null) {
         return false;
      }
      return checkRectCollision(sprite1.mRect, sprite1.mPos, sprite2.mRect, sprite2.mPos);
   }
   /*
    * Check collision of two rect
    */
   public static boolean checkRectCollision(Rect r1, Pos pos1, Rect r2, Pos pos2) {
      if (
            (Math.abs(pos1.x - pos2.x) < (r1.width() + r2.width()) / 2)
            && (Math.abs(pos1.y - pos2.y) <= (r1.height() + r2.height()) / 2 )
            ) {
         Utils.log(TAG, "pos1=" + pos1.x + ", " + pos1.y + "   pos2=" + pos2.x + ", " + pos2.y);
         return true;
      }
      return false;
   }

   /*
    * Calculate rect by center point
    * 
    * @param[in, out] mRect rect to calc
    * 
    * @param[in] mPos center point
    */
   public static void calRectByPos(Rect mRect, Pos mPos, int width, int height) {
      mRect.left = (int) (mPos.x - width / 2);
      mRect.right = mRect.left + width;
      mRect.top = (int) (mPos.y - height / 2);
      mRect.bottom = mRect.top + height;
   }
   
   /*
    * Get a random position in the given rectangle
    */
   public static Pos getNewPos(int width, int height) {
      
      int x = (int) (Math.random()* (width - SPACE) + SPACE / 2);
      int y = (int) (Math.random() * (height - SPACE) + SPACE / 2);
      Pos pos = new Pos(x,y);
      return pos;
      
   }
}
