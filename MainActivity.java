package com.example.exercise;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity.class";
    private Button goButton;
    private SeekBar mySeekBar;
    private TextView timeTextView;
    private long time_for_timer;
    private MediaPlayer myMediaPlayer;
    CountDownTimer myCountDownTimer;
    Boolean CounterIsActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myMediaPlayer = MediaPlayer.create(this, R.raw.airhorn);
        mySeekBar = findViewById(R.id.mySeekBar);
        mySeekBar.setMax(600);
        mySeekBar.setProgress(30);
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                String time_for_TextView = TimeFormat(i);
                Log.d("time for text view is", time_for_TextView);
                timeTextView.setText(time_for_TextView);
                time_for_timer = (long) i * 1000;
                Log.d("time for timer is", String.valueOf(time_for_timer));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        goButton = findViewById(R.id.goButton);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mySeekBar.getProgress() == 0) {
                    Toast.makeText(MainActivity.this, "Please choose a valid time value", Toast.LENGTH_SHORT).show();
                }
                else {
                    goButton.setClickable(false);
                    mySeekBar.setEnabled(false);
                    TimerController((long) time_for_timer);
                }

            }
        });
        timeTextView = findViewById(R.id.timerTextView);
        timeTextView.setText(TimeFormat(30));
    }

    private void TimerController(long time) {
        long interval = 1000;
        myCountDownTimer = new CountDownTimer(time, interval) {
            @Override
            public void onTick(long l) {
                final String new_time = TimeFormat((int) l / 1000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeTextView.setText(new_time);
                    }
                });
            }
            @Override
            public void onFinish() {
                reset();
            }
        }.start();
    }

    private String TimeFormat(int seconds) {
        int minutes = seconds / 60;
        int remaining_seconds = seconds - (minutes * 60);
        String time_min = String.valueOf(minutes);
        String time_sec = String.valueOf(remaining_seconds);
        if (minutes < 10) {
            time_min = "0" + time_min;
        }
        if (remaining_seconds < 10) {
            time_sec = "0" + time_sec;
        }
        String full_time = time_min + ":" + time_sec;
        return full_time;
    }

    private void reset() {
        mySeekBar.setProgress(0);
        timeTextView.setText(TimeFormat(0));
        goButton.setClickable(true);
        mySeekBar.setEnabled(true);
        myMediaPlayer.start();
        myCountDownTimer.cancel();
    }
}