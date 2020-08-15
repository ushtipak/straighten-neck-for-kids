package com.pijupiju.meerkat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.RangeSlider;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView tvActiveRange;
    private int min;
    private int max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        Log.d(TAG, "initViews");
        RangeSlider rangeSlider = findViewById(R.id.rangeSlider);
        rangeSlider.addOnSliderTouchListener(onSliderTouchListener);
        tvActiveRange = findViewById(R.id.tvActiveRange);
        Button btnStart = findViewById(R.id.btnStart);
        Button btnStop = findViewById(R.id.btnStop);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleReminders();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableReminders();
            }
        });
        updateRange(rangeSlider);
    }

    private void disableReminders() {
        Log.d(TAG, "disableReminders");
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        for (int i = 0; i < 50; i++) {
            try {
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), i, intent, PendingIntent.FLAG_NO_CREATE);
                Log.d(TAG, "Removing pendingIntent: " + pendingIntent);
                alarmManager.cancel(pendingIntent);
            } catch (Exception e) {
                Log.w(TAG, "pendingIntent not removed " + e.getLocalizedMessage());
            }
        }
        Toast.makeText(this, "Removed all reminders", Toast.LENGTH_LONG).show();
    }

    private final RangeSlider.OnSliderTouchListener onSliderTouchListener =
            new RangeSlider.OnSliderTouchListener() {
                @Override
                public void onStartTrackingTouch(@NotNull RangeSlider slider) {
                    Log.d(TAG, "onStartTrackingTouch");
                }

                @Override
                public void onStopTrackingTouch(@NotNull RangeSlider slider) {
                    Log.d(TAG, "onStopTrackingTouch");
                    updateRange(slider);
                }
            };

    private void updateRange(RangeSlider slider) {
        Log.d(TAG, "updateRange");
        min = Math.round(slider.getValues().get(0));
        max = Math.round(slider.getValues().get(1));
        String range = min + "–" + max;
        tvActiveRange.setText(range);
    }

    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        disableReminders();
        super.onDestroy();
    }

    private void scheduleReminders() {
        Log.d(TAG, "scheduleReminders");

        int middle = (int) (max - min) / 2 + min;
        Log.d(TAG, "max: " + max);
        Log.d(TAG, "min: " + min);
        Log.d(TAG, "middle: " + middle);

        int reminders = 180 / middle;
        Log.d(TAG, "reminders: " + reminders);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        long scheduleTime = System.currentTimeMillis();
        Random random = new Random();

        for (int i = 0; i < reminders; i++) {
            int randomInterval = random.nextInt((max - min) + 1) + min;
            Log.d(TAG, "randomInterval: " + randomInterval);
            scheduleTime += 1000 * 60 * randomInterval;
            Log.d(TAG, "scheduled (" + i + "): " + scheduleTime);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), i, intent, 0);
            alarmManager.set(AlarmManager.RTC_WAKEUP, scheduleTime, pendingIntent);
        }
        Toast.makeText(this, "Set " + reminders + " reminders", Toast.LENGTH_LONG).show();
    }

}
