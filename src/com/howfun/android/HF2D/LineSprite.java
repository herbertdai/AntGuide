package com.howfun.android.HF2D;

import com.howfun.android.HF2D.Pos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class LineSprite extends Sprite {

   private Pos mStartPos;
   private Pos mEndPos;
   private Paint mPaint;
   
   public LineSprite() {
      mPaint = new Paint();
      mPaint.setColor(Color.RED);
      mStartPos = new Pos(0,0);
      mEndPos = new Pos(0,0);
      
   }

   public void setPos(Pos start, Pos end) {
      mStartPos = start;
      mEndPos = end;
   }

   @Override
   protected boolean checkCollision(Sprite s) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void draw(Canvas canvas) {
      canvas.drawLine(mStartPos.x, mStartPos.y, mEndPos.x, mEndPos.y, mPaint);

   }

   @Override
   protected Pos getNextPos() {
      return mPos;
   }

}
