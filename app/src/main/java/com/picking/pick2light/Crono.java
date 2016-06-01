package com.picking.pick2light;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class Crono extends AppCompatActivity implements View.OnClickListener {
    Button startChrono;
    Button stopChrono;
    Chronometer Chrono;
    long time=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crono2);
        startChrono=(Button)findViewById(R.id.start);
        stopChrono=(Button)findViewById(R.id.stop);
        Chrono=(Chronometer)findViewById(R.id.chronometer);
        startChrono.setOnClickListener(this);
        stopChrono.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start:
                Chrono.setBase(SystemClock.elapsedRealtime() + time);
                Chrono.start();
                break;
            case R.id.stop:
                time = Chrono.getBase() + SystemClock.elapsedRealtime();
                Chrono.stop();
                break;


        }
    }
}
