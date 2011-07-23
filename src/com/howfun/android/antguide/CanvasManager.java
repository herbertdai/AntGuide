package com.howfun.android.antguide;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CanvasManager {
   private ArrayList<Sprite> mSprites;
   
   private Bitmap mBg;
   
   private Context mContext;
   
   public CanvasManager(Context c) {
      mContext = c;
      
      mSprites = new ArrayList<Sprite>();
      
      initAllSprite();
   }
   
   private void initAllSprite() {
      AntSprite ant = new AntSprite(mContext);
      mSprites.add(ant);
      
      //TODO add more sprites
   }
   
   public ArrayList<Sprite> getSprites() {
      return mSprites;
   }
   
   /*
    * Check collision of ant with line, hole, wall
    * 
    */
   public Sprite[] checkCollision() {
      boolean isCollide = false;
      //TODO: get the ant and line and hole , check.
      if (isCollide) {
         //TODO: set ant direction, or go home. 
      }
      return null;
      
   }
   
   public void draw(Canvas canvas) {
      //TODO:  draw bg
      
      for (int i=0; i<mSprites.size(); i++) {
         mSprites.get(i).draw(canvas); 
      }
   }
}
