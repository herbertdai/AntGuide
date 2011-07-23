package com.howfun.android.antguide;

public class HF2D {

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

}
