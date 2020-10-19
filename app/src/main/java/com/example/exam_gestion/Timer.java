package com.example.exam_gestion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Timer extends AppCompatActivity  {

    private long START_TIME_IN_MILLIS = 600000;
    private ProgressBar progressBarCircle;
    private TextView editTextMinute;
    private TextView  mTextViewCountDown;
    private ImageView  mButtonReset;
    private ImageView   mButtonStartPause;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    private long mEndTime;
    private final static String default_notification_channel_id = "default";
    int count ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_activity);
        Intent  examtime = getIntent();
        String st =examtime.getStringExtra("time");
        Toast.makeText(this,st,Toast.LENGTH_SHORT).show();
        progressBarCircle = (ProgressBar) findViewById(R.id.progressBarCircle);
        editTextMinute = (TextView) findViewById(R.id.textViewTime);
        mTextViewCountDown = (TextView) findViewById(R.id.textViewTime);
        mButtonReset = (ImageView) findViewById(R.id.imageViewReset);
        mButtonStartPause= (ImageView) findViewById(R.id.imageViewStartStop);
        mTextViewCountDown.setText(st);
        START_TIME_IN_MILLIS = Integer.valueOf(st)*60000;
        editTextMinute.setText(st);
        onStart();


        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {

                    mButtonStartPause.setImageResource(R.mipmap.start);
                    setProgressBarValues();
                } else {
                    startTimer();
                    mButtonStartPause.setImageResource(R.mipmap.stop);
                    setProgressBarValues();
                }
            }
        });


        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void startTimer() {

        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                count++;
                if (count==30)
                notifyme();
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();
                notifyme();
                resetTimer();
                setProgressBarValues();
            }
        }.start();
        mTimerRunning = true;
        updateButtons();
    }
    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        updateButtons();
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateButtons() {
        if (mTimerRunning) {
            mButtonReset.setVisibility(View.INVISIBLE);
        } else {

            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }
            if (mTimeLeftInMillis < START_TIME_IN_MILLIS) {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        setProgressBarValues();
        editor.apply();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        setProgressBarValues();
        updateCountDownText();
        updateButtons();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if ( mTimeLeftInMillis==START_TIME_IN_MILLIS/2){
                notifyme();
            }
            if (mTimeLeftInMillis < 0) {
                notifyme ();
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateButtons();
            } else {
                startTimer();
            }
        }
    }
    private void setProgressBarValues() {

        progressBarCircle.setMax((int) START_TIME_IN_MILLIS/ 1000);
        progressBarCircle.setProgress((int) START_TIME_IN_MILLIS / 1000);
    }


    public void notifyme (){
        Uri alarmSound =
                RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION );
        MediaPlayer mp = MediaPlayer. create (getApplicationContext(), alarmSound);
        mp.start();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, default_notification_channel_id )
                        .setSmallIcon(R.drawable. ic_launcher_foreground )
                        .setContentTitle( "notification" )
                        .setContentText( "exam ends" ) ;
        NotificationManager mNotificationManager = (NotificationManager)
                getSystemService(Context. NOTIFICATION_SERVICE );
        mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                mBuilder.build());
    }


}