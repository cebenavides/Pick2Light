/********************************************
 PICK2LIGHT APP hola
 ********************************************/

package com.picking.pick2light;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        final Animation a = AnimationUtils.loadAnimation(this, R.anim.fadein);
        final TextView rText = (TextView) findViewById(R.id.animatedtext);
        final TextView rText2 = (TextView) findViewById(R.id.textView);
        rText.startAnimation(a);
        rText2.startAnimation(a);
        spinner.setVisibility(View.VISIBLE);


    }

    public void boton1(final View view){
        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    String valueIP =((EditText)findViewById(R.id.entradaIP)).getText().toString();
                    int port = 10099;
                    DBConnection.getInstance().init(valueIP, port);
                    DBConnection.getInstance().send("1");
                    if (DBConnection.getInstance().receive().equals("1")){
                        showToast("Autorizado");
                        System.out.println("Autorizado");
                        //DBConnection.getInstance().closeConnection();
                        Intent callData_user = new Intent(MainActivity.this,Data_user.class);
                        callData_user.putExtra("ip", valueIP);
                        callData_user.putExtra("port", port);
                        startActivity(callData_user);

                    }else{
                        DBConnection.getInstance().closeConnection();
                        showToast("No autorizado");
                        System.out.println("No autorizado");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
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
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        DBConnection.getInstance().closeConnection();

    }
}











