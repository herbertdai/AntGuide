package com.howfun.android.antguide;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MusicReceiver extends BroadcastReceiver {

   @Override
   public void onReceive(Context context, Intent intent) {
      Intent i = new Intent("com.howfun.android.antguide.MusicService");
      context.startService(i);

   }

}
