package com.howfun.android.antguide.view;

import com.howfun.android.antguide.R;
import com.howfun.android.antguide.utils.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CustomNumberView extends LinearLayout{

   private static final String TAG = "CustomNumberView";

   private Context mContext;
   
   private static final int[] nums = { R.drawable.num_0, R.drawable.num_1,
         R.drawable.num_2, R.drawable.num_3, R.drawable.num_4,
         R.drawable.num_5, R.drawable.num_6, R.drawable.num_7,
         R.drawable.num_8, R.drawable.num_9 };
   
   private ImageView num0;
   private ImageView num1; 
   public CustomNumberView(Context context, AttributeSet attrs) {
      super(context, attrs);
      mContext = context;
      num0 = new ImageView(mContext);
      num1 = new ImageView(mContext);
      this.addView(num0);
      this.addView(num1);
   }
   
   /*
    * Set number from 0~99
    */
   public void setNum(int num) {
      if (num > 99 || num < 0) {
         Utils.log(TAG, "num is too big!");
         return;
      }
      int high = num / 10;
      int low = num % 10;
      if (high != 0) {
         num0.setImageResource(nums[high]); 
      }
      num1.setImageResource(nums[low]); 
   }

}
