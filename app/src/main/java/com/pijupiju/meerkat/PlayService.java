package com.pijupiju.meerkat;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class PlayService extends Service {
    private static final String TAG = PlayService.class.getSimpleName();
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    public void onCreate() {
        Log.d(TAG, "onCreate");
        player = MediaPlayer.create(this, R.raw.trumpet);
        player.setLooping(false);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        player.start();
        return Service.START_NOT_STICKY;
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        player.stop();
        player.release();
        stopSelf();
        super.onDestroy();
    }

}