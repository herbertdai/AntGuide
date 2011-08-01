package com.howfun.android.antguide;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
   public static final String TAG = "DBAdapter";

   private static final String DATABASE_NAME = "ant";
   private static final int DATABASE_VERSION = 1;

   private static final String TABLE_SCORE_BOARD = "score_board";

   private static final String KEY_ID = "_id";
   private static final String KEY_NAME = "name";
   private static final String KEY_SCORE = "score";

   private static final String CREATE_TABLE_SCORE_BOARD = "CREATE TABLE "
         + TABLE_SCORE_BOARD + "(" + KEY_ID
         + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT NOT NULL,"
         + KEY_SCORE + " INTEGER NOT NULL" + ");";

   private static final String DROP_TABLE_SCORE_BOARD = "DROP TABLE IF EXISTS "
         + TABLE_SCORE_BOARD;

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
         return -1;  //no record in score board
      else
         return l.get(0).getScore();
   }

   public void addScore(Score score) {
      ContentValues values = new ContentValues();
      values.put(KEY_NAME, score.getName());
      values.put(KEY_SCORE, score.getScore());
      mDb.insert(TABLE_SCORE_BOARD, null, values);

   }

   private static class DatabaseHelper extends SQLiteOpenHelper {
      public DatabaseHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);

      }

      @Override
      public void onCreate(SQLiteDatabase db) {
         Utils.log(TAG, "create table " + TABLE_SCORE_BOARD);
         db.execSQL(CREATE_TABLE_SCORE_BOARD);
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL(DROP_TABLE_SCORE_BOARD);
         onCreate(db);
      }
   }

}
