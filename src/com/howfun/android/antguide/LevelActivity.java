package com.howfun.android.antguide;

import com.howfun.android.antguide.game.AntMap;
import com.howfun.android.antguide.utils.Utils;
import com.howfun.android.antguide.view.CustomNumberView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LevelActivity extends Activity {

   private static final String TAG = "LevelActivity";
   private Gallery mGallery = null;
   private LevelGalleryAdapter mAdapter;
   
   private int mPassedLevel;
   private Context mContext;
   private boolean mClickable = false;

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.level_select);

      mContext = this;
      
      mPassedLevel = GamePref.getInstance(this).getLevelPref();
      Utils.log(TAG, "passed game level = "+ mPassedLevel);

      mGallery = (Gallery) findViewById(R.id.level_select_gallery);
      mAdapter = new LevelGalleryAdapter(this);
      mGallery.setAdapter(mAdapter);
      mGallery.setSpacing(30);

      mGallery.setOnItemClickListener(new OnItemClickListener() {

         @Override
         public void onItemClick(AdapterView<?> parent, View view,
               int position, long id) {

            if (getClickable(position)) {
               Intent intent = new Intent(LevelActivity.this,
                     AntGuideActivity.class);
               intent.putExtra(Utils.LEVEL_REF, position);
               startActivity(intent);
            }else {
               Toast.makeText(mContext, R.string.lock, Toast.LENGTH_LONG).show();
            }

         }
      });

   }
   
   public void onResume() {
      super.onResume();
      
      if (mAdapter != null) {
         Utils.log(TAG, "back to levell activity");
         mPassedLevel = GamePref.getInstance(this).getLevelPref();
         mAdapter.notifyDataSetChanged();
      }
      
   }

   public class LevelGalleryAdapter extends BaseAdapter {

      private Context mContext;

      public LevelGalleryAdapter(Context context) {
         mContext = context;
      }

      public int getCount() {
         return AntMap.MAP_COUNT;
      }

      public Object getItem(int position) {
         return position;
      }

      public long getItemId(int position) {
         return position;
      }

      public View getView(int position, View convertView, ViewGroup parent) {
         LayoutInflater inflater = LayoutInflater.from(mContext);
         View ant = inflater.inflate(R.layout.level_gallery_item, null);
         
         CustomNumberView text = (CustomNumberView) ant.findViewById(R.id.level_gallery_item_level);
         text.setNum(position + 1); 
         
         ImageView passView = (ImageView) ant
               .findViewById(R.id.level_gallery_item_watermark);
         //if passed:
         boolean isPassed = mPassedLevel >= position; 
         if (isPassed) {
            //set unlock icon
            passView.setImageResource(R.drawable.passed);
         } else {
            //if is next level of passed one, set unlock icon
            //else set lock icon,
            
            boolean nextLevel = (mPassedLevel + 1 == position );
            if (nextLevel) {
               passView.setImageResource(R.drawable.new_icon);
            } else {
               passView.setBackgroundResource(R.drawable.lock);
            }

         }
         
         return ant;
      }

   }

   //get clickable to enter the level
   private boolean getClickable(int position) {
      if (position <= mPassedLevel + 1) {
         return true;
      } else {
         return false;
      }
   }

}
