package com.picking.pick2light;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Station extends Activity {

    private Button startButton;
    private Button pauseButton;

    private TextView timerValue;

    private long startTime = 0L;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int counter = 1;
    TextView titulo;
    String tiempo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        timerValue = (TextView) findViewById(R.id.timerValue);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);

            }
        });

        pauseButton = (Button) findViewById(R.id.pauseButton);

        pauseButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                switch (counter) {
                    case 1:
                        titulo = (TextView) findViewById(R.id.est1);
                        break;
                    case 2:
                        titulo = (TextView) findViewById(R.id.est2);
                        break;
                    case 3:
                        titulo = (TextView) findViewById(R.id.est3);
                        break;
                    case 4:
                        titulo = (TextView) findViewById(R.id.est4);
                        break;
                    case 5:
                        titulo = (TextView) findViewById(R.id.est5);
                        break;
                    case 6:
                        titulo = (TextView) findViewById(R.id.est6);
                        break;
                    case 7:
                        titulo = (TextView) findViewById(R.id.est7);
                        break;
                }
                titulo.setText(tiempo);
                counter++;
                startTime = SystemClock.uptimeMillis();
                timeInMilliseconds = 0L;
                timeSwapBuff = 0L;
                updatedTime = 0L;
                //timeSwapBuff += timeInMilliseconds;
                //customHandler.removeCallbacks(updateTimerThread);

            }
        });

    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            tiempo = "" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds);
            timerValue.setText(tiempo);
            customHandler.postDelayed(this, 0);
        }

    };

}