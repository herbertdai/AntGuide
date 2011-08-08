package com.howfun.android.antguide;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;

import com.howfun.android.HF2D.AntSprite;
import com.howfun.android.HF2D.FoodSprite;
import com.howfun.android.HF2D.HF2D;
import com.howfun.android.HF2D.HomeSprite;
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
	private Paint mResultPaint;

	private Bitmap mBackgroundBmp;

	private AntSprite mAnt;
	private LineSprite mLine;
	private FoodSprite mFood;

	private SoundPool mSoundPool;
	private static final int SOUND_EFFECT_COLLISION = 0;
	private static final int SOUND_EFFECT_VICTORY = 1;
	private static final int SOUND_EFFECT_FOOD = 2;

	private static final String TAG = "CanvasManager";

	private int[] mSoundEffects = { R.raw.collision, R.raw.victory3,
			R.raw.food };
	private int[] mSoundIds;

	private boolean mIsAntLost;
	private boolean mIsAntHome;

	private float LOST_TEXT_SIZE = 50;
	private float HOME_SPACE = 100; // leave space around home

	private HomeSprite mHome;
	private Handler mHandler;

	public CanvasManager(Context c) {
		mContext = c;

		mSoundPool = new SoundPool(mSoundEffects.length,
				AudioManager.STREAM_MUSIC, 100);
		mSoundIds = new int[] {
				mSoundPool.load(mContext,
						mSoundEffects[SOUND_EFFECT_COLLISION], 1),
				mSoundPool.load(mContext, mSoundEffects[SOUND_EFFECT_VICTORY],
						1),
				mSoundPool.load(mContext, mSoundEffects[SOUND_EFFECT_FOOD],
						1) };

		mSprites = new ArrayList<Sprite>();

		mBmpPaint = new Paint();
		mBmpPaint.setColor(Color.YELLOW);
		mResultPaint = new Paint();
		mResultPaint.setTextSize(LOST_TEXT_SIZE);

		loadGrass();
		loadBackground();
		initAllSprite();
	}

	public void setHandler(Handler handler) {
		mHandler = handler;
	}

	public void initAllSprite() {

		if (mSprites == null) {
			mSprites = new ArrayList<Sprite>();
		}

		mSprites.clear();
		mIsAntHome = false;
		mIsAntLost = false;

		AntSprite ant = new AntSprite(mContext);
		mAnt = ant;
		mSprites.add(ant);

		LineSprite line = new LineSprite();
		mLine = line;
		mSprites.add(line);

		Pos homePos = HF2D.getNewPos(AntGuide.DEVICE_WIDTH,
				AntGuide.DEVICE_HEIGHT);
		mHome = new HomeSprite(mContext, homePos);
		mSprites.add(mHome);

		Pos foodPos = HF2D.getNewPos(AntGuide.DEVICE_WIDTH,
				AntGuide.DEVICE_HEIGHT);
		mFood = new FoodSprite(mContext, foodPos);
		mSprites.add(mFood);

		// TODO add more sprites
	}

	public ArrayList<Sprite> getSprites() {
		return mSprites;
	}

	/*
	 * Check collision of ant with line, hole, wall
	 */
	public void checkCollision() {
		checkLine();
		checkHome();
		checkOutOfScreen();
		checkFoods();
	}

	private void checkLine() {

		boolean isCollide = false;

		isCollide = HF2D.checkRectAndLineCollision(mAnt, mLine);

		if (isCollide) {
			mSoundPool
					.play(mSoundIds[SOUND_EFFECT_COLLISION], 13, 15, 1, 0, 1f);
		}

	}

	private void checkHome() {
		if (HF2D.checkCollsion(mAnt, mHome)) {
			antHome();
		}

	}

	private void checkFoods() {
		if (mFood != null) {
			if (HF2D.checkCollsion(mAnt, mFood)) {
				Utils.log(TAG, "Eat food!!!");
				mSoundPool
				.play(mSoundIds[SOUND_EFFECT_FOOD], 13, 15, 1, 0, 1f);
				
				mFood.setNewPos();
				mHandler.sendMessage(mHandler
						.obtainMessage(Utils.MSG_UPDATE_SCORE));

			}

		}
	}

	private void checkOutOfScreen() {
		if (HF2D.checkOutOfScreen(mAnt, AntGuide.DEVICE_WIDTH,
				AntGuide.DEVICE_HEIGHT)) {
			antLost();
		}
	}

	private void antLost() {
		mIsAntLost = true;
		mHandler.sendMessage(mHandler.obtainMessage(Utils.MSG_ANT_LOST));
	}

	private void antHome() {
		mIsAntHome = true;
		mSoundPool.play(mSoundIds[SOUND_EFFECT_VICTORY], 13, 15, 1, 0, 1f);
		mHandler.sendMessage(mHandler.obtainMessage(Utils.MSG_ANT_HOME));
	}

	private void drawResult(Canvas canvas) {
		if (mIsAntLost) {
			// canvas.drawText("Ant is Lost!!!", 20, 100, mResultPaint);
		} else if (mIsAntHome) {
			// canvas.drawText("Ant is home....", 200, 200, mResultPaint);

		}
	}

	public void draw(Canvas canvas) {

		checkCollision();

		drawBg(canvas);

		drawResult(canvas);

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

		canvas.drawBitmap(mBackgroundBmp, 0, 0, mBmpPaint);
		canvas.drawBitmap(mGrassBmp, 68, 133, mBmpPaint);
		canvas.drawBitmap(mGrassBmp, 12, 190, mBmpPaint);
		canvas.drawBitmap(mGrassBmp, 310, 123, mBmpPaint);
		canvas.drawBitmap(mGrassBmp, 120, 99, mBmpPaint);
		canvas.drawBitmap(mGrassBmp, 200, 521, mBmpPaint);
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

	private void loadBackground() {
		int width = AntGuide.DEVICE_WIDTH;
		int height = AntGuide.DEVICE_HEIGHT;

		Resources r = mContext.getResources();
		Drawable holeDrawable = r.getDrawable(R.drawable.background);
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		holeDrawable.setBounds(0, 0, width, height);
		holeDrawable.draw(canvas);
		mBackgroundBmp = bitmap;
	}

	public void setWhichAntAnim(int which) {
		if (mAnt != null) {
			mAnt.mWhichAntAnim = which;
		}
	}

	// clear memory
	public void clear() {
		Utils.recycleBitmap(mBackgroundBmp);
		Utils.recycleBitmap(mGrassBmp);
		if (mSprites != null) {
			for (int i = 0; i < mSprites.size(); i++) {
				mSprites.get(i).clear();
			}
		}
	}

}
