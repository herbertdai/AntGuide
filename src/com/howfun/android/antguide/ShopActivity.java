package com.howfun.android.antguide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.AdapterView.OnItemClickListener;

public class ShopActivity extends Activity {

   private Gallery mGallery = null;

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
      setContentView(R.layout.shop);

      mGallery = (Gallery) findViewById(R.id.shop_gallery);
      ShopGalleryAdapter adapter = new ShopGalleryAdapter(this);
      mGallery.setAdapter(adapter);

      mGallery.setOnItemClickListener(new OnItemClickListener() {

         @Override
         public void onItemClick(AdapterView<?> parent, View view,
               int position, long id) {
            
            //TODO judge if the shop can be open
            Intent intent = new Intent(ShopActivity.this,AntGuideActivity.class);
            startActivity(intent);

         }
      });

   }

   public class ShopGalleryAdapter extends BaseAdapter {

      private Context mContext;

      public ShopGalleryAdapter(Context context) {
         mContext = context;
      }

      public int getCount() {
         return 10;
      }

      public Object getItem(int position) {
         return position;
      }

      public long getItemId(int position) {
         return position;
      }

      public View getView(int position, View convertView, ViewGroup parent) {
         LayoutInflater inflater = LayoutInflater.from(mContext);
         View ant = inflater.inflate(R.layout.shop_gallery_item, null);
         return ant;
      }

   }

}
