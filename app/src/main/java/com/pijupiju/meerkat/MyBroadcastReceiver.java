package com.pijupiju.meerkat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = MyBroadcastReceiver.class.getSimpleName();
    MediaPlayer player;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        Toast.makeText(context, "Straighten the neck, kid! :)", Toast.LENGTH_LONG).show();
        List<Integer> sounds = new ArrayList<>();
        sounds.add(R.raw.neck_01);
        sounds.add(R.raw.neck_02);
        sounds.add(R.raw.neck_03);
        sounds.add(R.raw.neck_04);
        sounds.add(R.raw.neck_05);
        sounds.add(R.raw.neck_06);
        sounds.add(R.raw.neck_07);
        sounds.add(R.raw.neck_08);
        sounds.add(R.raw.neck_09);

        player = MediaPlayer.create(context, sounds.get(new Random().nextInt(sounds.size())));
        player.start();
        player.setLooping(false);
    }
}
