package com.picking.pick2light;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    String tiempo;
    String msg = "start";
    TextView[] titulo = new TextView[7];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        timerValue = (TextView) findViewById(R.id.timerValue);
        titulo[0] = (TextView) findViewById(R.id.est1);
        titulo[1] = (TextView) findViewById(R.id.est2);
        titulo[2] = (TextView) findViewById(R.id.est3);
        titulo[3] = (TextView) findViewById(R.id.est4);
        titulo[4] = (TextView) findViewById(R.id.est5);
        titulo[5] = (TextView) findViewById(R.id.est6);
        titulo[6] = (TextView) findViewById(R.id.est7);
        Thread t = new Thread(){
            @Override
            public void run() {
                while (true) {
                    try {
                        msg = DBConnection.getInstance().receive();
                        if (msg.equals("ended")) {
                            //DBConnection.getInstance().send("h");
                            finish();
                        }

                        if (msg.equals("start")) {
                            startTime = SystemClock.uptimeMillis();
                            timeInMilliseconds = 0L;
                            timeSwapBuff = 0L;
                            updatedTime = 0L;
                            customHandler.postDelayed(updateTimerThread, 0);
                        }

                        if(msg.equals("reset")){
                            customHandler.removeCallbacks(updateTimerThread);
                            updateName2(timerValue, "0:00:000");
                            counter = 1;
                            updateName2(titulo[0],"Estación 1");
                            updateName2(titulo[1],"Estación 2");
                            updateName2(titulo[2],"Estación 3");
                            updateName2(titulo[3],"Estación 4");
                            updateName2(titulo[4],"Estación 5");
                            updateName2(titulo[5],"Estación 6");
                            updateName2(titulo[6],"Estación 7");
                        }

                        if (msg.equals("stop")) {
                            updateName(counter, tiempo);
                            System.out.println("El contador esta en "+counter);
                            counter++;
                            if (counter >= 8) { counter = 1; }
                            startTime = SystemClock.uptimeMillis();
                            timeInMilliseconds = 0L;
                            timeSwapBuff = 0L;
                            updatedTime = 0L;
                            //timeSwapBuff += timeInMilliseconds;
                            //customHandler.removeCallbacks(updateTimerThread);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
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
            updateName2(timerValue, tiempo);
            customHandler.postDelayed(this, 0);
        }

    };

    public void updateName(final int counter,final String strink)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                if (counter < 7) {
                    titulo[counter - 1].setText(strink);
                    titulo[counter].setTextColor(Color.parseColor("#ffea00"));
                    titulo[counter - 1].setTextColor(Color.parseColor("#ffffff"));
                }else if (counter == 7) {
                    titulo[counter - 1].setText(strink);
                    titulo[counter - 1].setTextColor(Color.parseColor("#ffffff"));
                }
            }
        });
    }

    public void updateName2(final TextView text,final String strink)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                text.setText(strink);
            }
        });
    }


}