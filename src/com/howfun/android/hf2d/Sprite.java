package com.howfun.android.hf2d;

import com.howfun.android.hf2d.Pos;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

public abstract class Sprite {
   
   protected int FPS;
   protected Rect mRect;
   protected BitmapDrawable[] mBitmaps;
   protected Pos mPos;
   protected int mType;
   protected float mSpeed; //Move n pixels per step  
   protected boolean mIsVisible;
   
   protected static final int TYPE_ANT = 0;
   protected static final int TYPE_LINE = 1;
   protected static final int TYPE_HOLE = 2;
   
   
   protected abstract Pos getNextPos();
   
   protected abstract boolean checkCollision(Sprite s);
   
   public abstract void draw(Canvas canvas);
   
   public abstract void clear();
}
