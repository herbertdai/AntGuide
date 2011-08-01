package com.howfun.android.antguide;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ScoreBoardActivity extends Activity {

   private TextView noScoreRecord;
   private ListView scoreBoard;

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.score_board);
      noScoreRecord = (TextView) findViewById(R.id.no_score_record);
      scoreBoard = (ListView) findViewById(R.id.score_board);
      List<Score> l = new ArrayList<Score>();
      DBAdapter db = new DBAdapter(this);
      db.open();
      l = db.getHighScores(Utils.TOP_SCORE_COUNT);
      db.close();
      if (l == null || l.size() == 0) {
         noScoreRecord.setVisibility(View.VISIBLE);
      } else {
         ScoreAdapter adapter = new ScoreAdapter(this, R.layout.score_item, l);
         scoreBoard.setAdapter(adapter);
      }
   }

   class ScoreAdapter extends ArrayAdapter<Score> {
      private LayoutInflater mInflater;
      private Context mContext;
      private int mResource;

      public ScoreAdapter(Context context, int resource, List<Score> objects) {
         super(context, resource, objects);
         mContext = context;
         mResource = resource;
         mInflater = (LayoutInflater) context
               .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder holder;

         Score item = getItem(position);

         if (convertView == null) {
            convertView = mInflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.score = (TextView) convertView.findViewById(R.id.score);
            convertView.setTag(holder);
         } else {
            holder = (ViewHolder) convertView.getTag();
         }

         holder.name.setText(item.getName());
         holder.score.setText(String.valueOf(item.getScore()));

         return convertView;
      }

      private class ViewHolder {
         TextView name;
         TextView score;
      }

   }

}
