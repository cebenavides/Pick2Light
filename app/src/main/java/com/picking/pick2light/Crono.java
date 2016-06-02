package com.picking.pick2light;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import java.net.Socket;

public class Crono extends AppCompatActivity implements View.OnClickListener {
    Button startChrono;
    Button stopChrono;
    Button resetChrono;
    Chronometer Chrono;
    TextView titulo;
    long time=0;
    int est = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crono2);
        startChrono=(Button)findViewById(R.id.start);
        stopChrono=(Button)findViewById(R.id.stop);
        resetChrono=(Button)findViewById(R.id.reset);
        Chrono=(Chronometer)findViewById(R.id.chronometer);
        startChrono.setOnClickListener(this);
        stopChrono.setOnClickListener(this);
        resetChrono.setOnClickListener(this);
        titulo = (TextView)findViewById(R.id.est8);
        Bundle bundle = getIntent().getExtras();

        Thread t = new Thread(){
            @Override
            public void run() {
                while (true) {
                    try {
                        //DBConnection.getInstance().send("2");
                        String message = DBConnection.getInstance().receive();
                        System.out.println(message);
                        showToast("Estación: " + message);

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                titulo.setText(toast);
            }
        });
    }

    @Override
    public void onClick(View v) {
        TextView campo;
        long tiempo;
        int minutos, segundos;
        String string_min, string_seg;
        switch (v.getId()) {
            case R.id.start:
                Chrono.setBase(SystemClock.elapsedRealtime() + time);
                Chrono.start();
                break;
            case R.id.stop:
                time = Chrono.getBase() + SystemClock.elapsedRealtime();
                Chrono.stop();
                break;
            case R.id.reset:

                //time = Chrono.getBase() + SystemClock.elapsedRealtime();
                switch (est) {
                    case 1:
                        campo = (TextView)findViewById(R.id.est1);
                        break;
                    case 2:
                        campo = (TextView)findViewById(R.id.est2);
                        break;
                    case 3:
                        campo = (TextView)findViewById(R.id.est3);
                        break;
                    case 4:
                        campo = (TextView)findViewById(R.id.est4);
                        break;
                    case 5:
                        campo = (TextView)findViewById(R.id.est5);
                        break;
                    case 6:
                        campo = (TextView)findViewById(R.id.est6);
                        break;
                    case 7:
                        campo = (TextView)findViewById(R.id.est7);
                        break;
                    default:
                        campo = null;
                        break;
                }
                tiempo = Math.abs((Chrono.getBase()-SystemClock.elapsedRealtime())/1000);
                minutos = (int)(tiempo/60);
                segundos = (int)(tiempo-minutos*60);
                string_min = String.valueOf(minutos);
                string_seg = String.valueOf(segundos);
                if (minutos < 10) {string_min = "0"+string_min;}
                if (segundos < 10) {string_seg = "0"+string_seg;}
                campo.setText(string_min+":"+string_seg);
                Chrono.setBase(SystemClock.elapsedRealtime());
                time = 0;
                est++;
                if (est > 7){ est = 1; }
                break;


        }
    }
    public void inicio(View view) {
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }
}
