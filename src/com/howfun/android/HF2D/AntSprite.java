package com.howfun.android.HF2D;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import com.howfun.android.HF2D.*;

public class AntSprite extends Sprite {
   private static final String TAG = "AntSprite";

   
   private Context mContext;
   private Paint mPaint;
   private int mAngle;   //Ant's running direction. Reversed on normal. 
   
   public AntSprite(Context c) {
      mContext = c;
      init();
   }

   public void init() {
      mPaint = new Paint();
      mPaint.setColor(Color.RED);
      
      mAngle = 30;
      mSpeed = 1;
      
      mPos = new Pos(10, 10);
      mType = TYPE_ANT;
      FPS = 50;
      
//      Drawable antDrawable3 = mContext.getResources().getDrawable(R.drawable.ant3);
//      mBitmap = new BitmapDrawable(antDrawable3);
   }
   public void init(int FPS, Bitmap[] bitmaps, Pos pos, Rect rect){
      
   }
   
   @Override
   protected boolean checkCollision(Sprite s) {
      //Check with Line or Hole
      
      return false;
   }
   
   private void setAngle(int angle) {
      mAngle = angle;
   }

   @Override
   protected Pos getNextPos() {
      HF2D.getNextPos(mPos, mSpeed, mAngle);
      return mPos;
   }

   
   @Override
   public void draw(Canvas canvas) {
      //TODO: use fps
      getNextPos();
      canvas.drawCircle(mPos.x, mPos.y, 50, mPaint);
      
   }

}
