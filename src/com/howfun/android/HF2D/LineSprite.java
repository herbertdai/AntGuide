package com.howfun.android.HF2D;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class LineSprite extends Sprite {

   private Pos mStartPos;
   private Pos mEndPos;
   private Paint mPaint;

   public LineSprite() {

      mPaint = new Paint();
      mPaint.setColor(Color.rgb(64, 64, 64));
      mPaint.setAntiAlias(true);

      mPaint.setStyle(Paint.Style.STROKE);

      mPaint.setStrokeWidth(3);

      mStartPos = new Pos(0, 0);
      mEndPos = new Pos(0, 0);

   }

   public Pos getStart() {
      return mStartPos;
   }

   public Pos getEnd() {
      return mEndPos;
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

   @Override
   public void clear() {
      // Utils.recycleBitmap(bitmap)
   }

}
