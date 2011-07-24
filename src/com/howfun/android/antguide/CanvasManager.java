package com.howfun.android.antguide;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;

import com.howfun.android.HF2D.AntSprite;
import com.howfun.android.HF2D.HF2D;
import com.howfun.android.HF2D.LineSprite;
import com.howfun.android.HF2D.Pos;
import com.howfun.android.HF2D.Sprite;

public class CanvasManager {
	private static final int GRASS_WIDTH = 50;

	private static final int HOLE_WIDTH = 50;

	private static final int HOLE_HIGHT = 40;

	private static final int GRASS_HEIGHT = 50;

	private ArrayList<Sprite> mSprites;

	private Bitmap mBg;

	private Context mContext;

	private Bitmap mGrassBmp;

	private Paint mBmpPaint;

	private Bitmap mHoleBmp;

	private AntSprite mAnt;
	private LineSprite mLine;

	private SoundPool mSoundPool;
	private static final int SOUND_EFFECT_COLLISION = 0;
	private static final int SOUND_EFFECT_VICTORY = 1;
	private int[] mSoundEffects = { R.raw.collision, R.raw.victory };
	private int[] mSoundIds;

	public CanvasManager(Context c) {
		mContext = c;

		mSoundPool = new SoundPool(mSoundEffects.length,
				AudioManager.STREAM_MUSIC, 100);
		mSoundIds = new int[] {
				mSoundPool.load(mContext,
						mSoundEffects[SOUND_EFFECT_COLLISION], 1),
				mSoundPool.load(mContext, mSoundEffects[SOUND_EFFECT_VICTORY],
						1) };

		// mSoundPool.play(mSoundIds[SOUND_EFFECT_COLLISION], 13, 15, 1, 0, 1f);
		mSprites = new ArrayList<Sprite>();

		loadGrass();
		loadHole();
		initAllSprite();
	}

	private void initAllSprite() {
		AntSprite ant = new AntSprite(mContext);
		mAnt = ant;
		mSprites.add(ant);

		LineSprite line = new LineSprite();
		mLine = line;
		mSprites.add(line);

		// TODO add more sprites
	}

	public ArrayList<Sprite> getSprites() {
		return mSprites;
	}

	/*
	 * Check collision of ant with line, hole, wall
	 */
	public void checkCollision() {
		boolean isCollide = false;
		// TODO: get the ant and line and hole , check.
		isCollide = HF2D.checkCollision(mAnt, mLine);

		if (isCollide) {
			// TODO: set ant direction, or go home.
			mSoundPool
					.play(mSoundIds[SOUND_EFFECT_COLLISION], 13, 15, 1, 0, 1f);
		}

	}

	public void draw(Canvas canvas) {

		checkCollision();

		drawBg(canvas);

		for (int i = 0; i < mSprites.size(); i++) {
			mSprites.get(i).draw(canvas);
		}
	}

	public void setNewLine(Pos start, Pos end) {

		if (mLine != null) {
			mLine.setPos(start, end);
		}

	}

	private void drawBg(Canvas canvas) {

		canvas.drawBitmap(mGrassBmp, 68, 133, mBmpPaint);
		canvas.drawBitmap(mGrassBmp, 12, 190, mBmpPaint);
		canvas.drawBitmap(mGrassBmp, 310, 123, mBmpPaint);
		canvas.drawBitmap(mGrassBmp, 120, 99, mBmpPaint);
		canvas.drawBitmap(mGrassBmp, 200, 521, mBmpPaint);
		canvas.drawBitmap(mHoleBmp, 300, 700, mBmpPaint);
	}

	private void loadGrass() {
		Resources r = mContext.getResources();
		Drawable grassDrawable = r.getDrawable(R.drawable.grass);
		Bitmap bitmap = Bitmap.createBitmap(GRASS_WIDTH, GRASS_HEIGHT,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		grassDrawable.setBounds(0, 0, 32, 32);
		grassDrawable.draw(canvas);
		mGrassBmp = bitmap;
	}

	private void loadHole() {
		Resources r = mContext.getResources();
		Drawable holeDrawable = r.getDrawable(R.drawable.hole);
		Bitmap bitmap = Bitmap.createBitmap(HOLE_WIDTH, HOLE_HIGHT,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		holeDrawable.setBounds(0, 0, 32, 32);
		holeDrawable.draw(canvas);
		mHoleBmp = bitmap;
	}

	public void setWhichAntAnim(int which) {
		if (mAnt != null) {
			mAnt.mWhichAntAnim = which;
		}
	}

}
