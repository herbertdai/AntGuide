package com.howfun.android.HF2D;

import com.howfun.android.antguide.R;
import com.howfun.android.antguide.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class FoodSprite extends Sprite {
   
   
   private static final int FOOD_W = 40;
   private static final int FOOD_H = 40;
   private static final int FOOD_RANGE_Y = 800;
   private static final int FOOD_RANGE_X = 480;
   
	private Bitmap mBmp;
   private Context mContext;
   private Paint mPaint;
	
   public FoodSprite(Context context, Pos pos) {
      mContext = context;
      mPos = pos;
      mRect = new Rect();
      HF2D.calRectByPos(mRect, mPos, FOOD_W, FOOD_H);
      
      loadFood();
      mPaint = new Paint();
      
   }

   @Override
   protected boolean checkCollision(Sprite s) {
      // TODO Auto-generated method stub
      return false;
   }

   @Override
   public void draw(Canvas canvas) {
      if (canvas == null) {
         return;
      }
      canvas.drawBitmap(mBmp, mRect.left, mRect.top, mPaint);
   }

   @Override
   protected Pos getNextPos() {
      return mPos;
   }

	private void loadFood() {
		Resources r = mContext.getResources();
		Drawable holeDrawable = r.getDrawable(R.drawable.play);
		Bitmap bitmap = Bitmap.createBitmap(FOOD_W, FOOD_H,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
      holeDrawable.setBounds(0, 0, FOOD_W, FOOD_H);
		holeDrawable.draw(canvas);
		mBmp = bitmap;
		//mBmp = ((BitmapDrawable)holeDrawable).getBitmap();
	}

   @Override
   public void clear() {
      Utils.recycleBitmap(mBmp);
      mBmp = null;
   }

   public void setNewPos() {
      mPos = HF2D.getNewPos(FOOD_RANGE_X, FOOD_RANGE_Y);
      HF2D.calRectByPos(mRect, mPos, FOOD_W, FOOD_H);
   }
}
