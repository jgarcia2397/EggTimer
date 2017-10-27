package com.josh2.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timer;
    TextView timerTextView;
    MediaPlayer mPlayer;
    Button button;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        mPlayer = MediaPlayer.create(this,R.raw.bells);

        timer = (SeekBar) findViewById(R.id.seekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        timer.setMax(600);
        timer.setProgress(30);
        timer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void start(View view) {
        if(counterIsActive == false) {
            counterIsActive = true;
            timer.setEnabled(false);
            button.setText("Stop");

            countDownTimer = new CountDownTimer(timer.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    mPlayer.start();
                }
            }.start();
        } else {
            resetTimer();
        }
    }

    public void updateTimer(int secondsLeft) {
        int minutes = (int) secondsLeft/60;
        int seconds = secondsLeft - minutes*60;
        timerTextView.setText((String.format("%02d", minutes)) + ":" + (String.format("%02d", seconds)));
    }

    public void resetTimer() {
        timerTextView.setText("00:30");
        timer.setProgress(30);
        countDownTimer.cancel();
        button.setText("Go!");
        timer.setEnabled(true);
        counterIsActive = false;
    }
}
