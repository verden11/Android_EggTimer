package com.verden1.eggtimer;

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
    SeekBar timerSeekBar;
    TextView timerTextView;
    boolean counterIsActive = false;
    Button controllerButton;
    CountDownTimer countDownTimer;

    public void updateTimer(int secondLeft) {
        int minutes = secondLeft / 60;
        int seconds = secondLeft % 60;

        String secondsString = Integer.toString(seconds);

        if (secondsString.length() == 1) {
            secondsString = "0" + secondsString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondsString);
    }

    public void controlTimer(View view) {
        if (!counterIsActive) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("STOP");


            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("0:00");
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mplayer.start();
                    timerSeekBar.setEnabled(true);
                }
            }.start();

        } else {
            timerTextView.setText("0:30");
            timerSeekBar.setProgress(30);
            countDownTimer.cancel();
            controllerButton.setText("Go!");
            timerSeekBar.setEnabled(true);
            counterIsActive = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controllerButton = (Button) findViewById(R.id.controllerButton);
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timeTextView);
        timerSeekBar.setMax(60);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
}
