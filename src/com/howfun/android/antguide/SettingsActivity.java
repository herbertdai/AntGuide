package com.howfun.android.antguide;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.howfun.android.antguide.utils.Utils;

public class SettingsActivity extends Activity {
   ImageView mBackMusicSwitcher;
   ImageView mSoundEffectSwitcher;

   SharedPreferences mSettings;

   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.settings);
      mBackMusicSwitcher = (ImageView) findViewById(R.id.back_music_switcher);
      mSoundEffectSwitcher = (ImageView) findViewById(R.id.sound_effect_switcher);
      mSettings = getSharedPreferences(Utils.PREF_SETTINGS, 0);
      boolean backMusicOff = mSettings.getBoolean(
            Utils.PREF_SETTINGS_BACK_MUSIC_OFF, false);
      boolean soundEffectOff = mSettings.getBoolean(
            Utils.PREF_SETTINGS_SOUND_EFFECT_OFF, false);

      setBackMusicRes(backMusicOff);
      setSoundEffectRes(soundEffectOff);

      mBackMusicSwitcher.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
            boolean flag = isBackMusicOff();
            mSettings.edit().putBoolean(Utils.PREF_SETTINGS_BACK_MUSIC_OFF,
                  !flag).commit();
            setBackMusicRes(!flag);
         }
      });

      mSoundEffectSwitcher.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(View v) {
            boolean flag = isSoundEffectOff();
            mSettings.edit().putBoolean(Utils.PREF_SETTINGS_SOUND_EFFECT_OFF,
                  !flag).commit();
            setSoundEffectRes(!flag);

         }
      });

   }

   private boolean isBackMusicOff() {
      return mSettings.getBoolean(Utils.PREF_SETTINGS_BACK_MUSIC_OFF, false);
   }

   private boolean isSoundEffectOff() {
      return mSettings.getBoolean(Utils.PREF_SETTINGS_SOUND_EFFECT_OFF, false);
   }

   private void setBackMusicRes(boolean off) {
      if (off) {
         mBackMusicSwitcher.setBackgroundResource(R.drawable.sound_off);
      } else {
         mBackMusicSwitcher.setBackgroundResource(R.drawable.sound_on);
      }
   }

   private void setSoundEffectRes(boolean off) {
      if (off) {
         mSoundEffectSwitcher.setBackgroundResource(R.drawable.sound_off);
      } else {
         mSoundEffectSwitcher.setBackgroundResource(R.drawable.sound_on);
      }
   }

}
