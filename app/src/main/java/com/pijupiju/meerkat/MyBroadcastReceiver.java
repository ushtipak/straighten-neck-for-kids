package com.pijupiju.meerkat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = MyBroadcastReceiver.class.getSimpleName();
    MediaPlayer player;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show();
        player = MediaPlayer.create(context, R.raw.trumpet);
        player.start();
        player.setLooping(false);
    }
}
