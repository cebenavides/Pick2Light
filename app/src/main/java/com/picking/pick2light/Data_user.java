package com.picking.pick2light;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class Data_user extends AppCompatActivity {
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_user);



        Thread t = new Thread(){
            @Override
            public void run() {
                while (true) {
                    try {
                        msg = DBConnection.getInstance().receive();
                        if (msg.equals("seguir")) {
                            dialogo();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();

    }
    public void crono(View view){
        DBConnection.getInstance().send("now");
        Intent i= new Intent(this, Station.class );
        startActivity(i);
    }

    public void dialogo()
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                new AlertDialog.Builder(Data_user.this)
                        .setTitle("Importante")
                        .setMessage("¿Desea seguir?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //DBConnection.getInstance().send("S");

                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DBConnection.getInstance().send("h");
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

}
