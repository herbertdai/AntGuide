package com.howfun.android.antguide;

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
   
   protected static final int TYPE_ANT = 0;
   protected static final int TYPE_LINE = 1;
   protected static final int TYPE_HOLE = 2;
   
   
   protected abstract Pos getNextPos();
   
   protected abstract boolean checkCollision(Sprite s);
   
   protected abstract void draw(Canvas canvas);
}
