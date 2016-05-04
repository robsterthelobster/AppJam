package com.toothpicks.AppJam.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.toothpicks.AppJam.R;

public class BackgroundSoundService extends Service {
    private static final String TAG = null;
    MediaPlayer player;
    public IBinder onBind(Intent arg0) {

        return null;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.background1);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);
    }
    
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return 1;
    }

    public void onStart(Intent intent, int startId) {
    	player.start();
    }
    public IBinder onUnBind(Intent arg0) {
        return null;
    }

    public void onStop() {
    	player.stop();
    	player.release();
    }
    
    public void onPause() {
    	player.stop();
    	player.release();
    }
    
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {
    }
}