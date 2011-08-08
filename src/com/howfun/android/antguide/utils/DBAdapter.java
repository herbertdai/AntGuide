package com.howfun.android.antguide.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.howfun.android.antguide.entity.Score;

public class DBAdapter {
	public static final String TAG = "DBAdapter";

	private static final String DATABASE_NAME = "ant";
	private static final int DATABASE_VERSION = 1;

	private static final String TABLE_SCORE_BOARD = "score_board";
	private static final String TABLE_ANT_STORE = "ant_store";

	// key 4 score board
	private static final String KEY_ID = "_id";
	private static final String KEY_NAME = "name";
	private static final String KEY_SCORE = "score";

	private static final int ANT_STORE_ITEM_BUYED = 0;
	// key 4 ant store
	private static final String KEY_ANT_ID = "ant_id";
	private static final String KEY_ANT_BUYED = "ant_buyed"; // 0 for buyed

	private static final String CREATE_TABLE_SCORE_BOARD = "CREATE TABLE "
			+ TABLE_SCORE_BOARD + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME
			+ " TEXT NOT NULL," + KEY_SCORE + " INTEGER NOT NULL" + ");";

	private static final String CREATE_TABLE_ANT_STORE = "CREATE TABLE "
			+ TABLE_ANT_STORE + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ANT_ID
			+ " INTEGER NOT NULL," + KEY_ANT_BUYED + " INTEGER NOT NULL" + ");";

	private static final String DROP_TABLE_SCORE_BOARD = "DROP TABLE IF EXISTS "
			+ TABLE_SCORE_BOARD;
	private static final String DROP_TABLE_ANT_STORE = "DROP TABLE IF EXISTS "
			+ TABLE_ANT_STORE;

	private final Context mCtx;

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	public DBAdapter(Context context) {
		mCtx = context;
	}

	public DBAdapter open() {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public List<Score> getHighScores(int top) {
		List<Score> l = new ArrayList<Score>();
		if (top < 0) {
			return l;
		}
		Cursor cur = null;

		if (top == 0) { // query all
			cur = mDb.query(TABLE_SCORE_BOARD, null, null, null, null, null,
					KEY_SCORE + " DESC");
		} else {
			String sql = "select * from " + TABLE_SCORE_BOARD + " order by "
					+ KEY_SCORE + " DESC limit " + String.valueOf(top);
			cur = mDb.rawQuery(sql, null);
		}

		if (cur != null) {
			cur.moveToFirst();
		} else {
			return l;
		}
		if (cur.getCount() == 0) {
			cur.close();
			return l;
		}
		do {
			String name = cur.getString(cur.getColumnIndex(KEY_NAME));
			long score = cur.getLong(cur.getColumnIndex(KEY_SCORE));
			Score item = new Score(name, score);
			l.add(item);

		} while (cur.moveToNext());
		cur.close();

		return l;
	}

	public long getHighestScore() {
		List<Score> l = getHighScores(1);
		if (l == null || l.size() == 0)
			return -1; // no record in score board
		else
			return l.get(0).getScore();
	}

	public void addScore(Score score) {
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, score.getName());
		values.put(KEY_SCORE, score.getScore());
		mDb.insert(TABLE_SCORE_BOARD, null, values);

	}

	public int getAntStoreItemCount() {
		Cursor cur = null;
		int count = 0;
		cur = mDb.query(TABLE_ANT_STORE, null, null, null, null, null, null);
		if (cur != null) {
			count = cur.getCount();
			cur.close();
			cur = null;
		}
		return count;

	}

	public int antStoreItemBuyed(int antId) {
		int buyed = -1;
		Cursor cur = null;
		cur = mDb.query(TABLE_ANT_STORE, new String[] { KEY_ANT_BUYED },
				KEY_ANT_ID + "=" + antId, null, null, null, null);
		if (cur != null) {
			buyed = cur.getInt(cur.getColumnIndex(KEY_ANT_BUYED));
			cur.close();
			cur = null;
		}
		return buyed;
	}

	public void buyAntStoreItem(int antId) {
		String sql = "update " + TABLE_ANT_STORE + " set " + KEY_ANT_BUYED
				+ " = " + ANT_STORE_ITEM_BUYED + " where " + KEY_ANT_ID + " = "
				+ antId;
		mDb.execSQL(sql);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Utils.log(TAG, "create table " + TABLE_SCORE_BOARD);
			Utils.log(TAG, "create table " + TABLE_ANT_STORE);
			db.execSQL(CREATE_TABLE_SCORE_BOARD);
			db.execSQL(CREATE_TABLE_ANT_STORE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_TABLE_SCORE_BOARD);
			db.execSQL(DROP_TABLE_ANT_STORE);
			onCreate(db);
		}
	}

}
