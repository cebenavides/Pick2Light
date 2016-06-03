/********************************************
 PICK2LIGHT APP hola
 ********************************************/

package com.picking.pick2light;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private ProgressBar spinner;
    String valueIP;
    int port;

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
        readFromFile();
        //writeToFile(((EditText)findViewById(R.id.entradaIP)).getText().toString());
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile() {
        int counter = 0;
        String ret = "";

        try {
            InputStream inputStream = openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    if (counter == 0){ valueIP = receiveString; }
                    if (counter == 1){ port = Integer.parseInt(receiveString); }
                    counter++;
                    //((EditText)findViewById(R.id.entradaIP)).setText(receiveString);
                    //stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public void settings(final View view){
        //readFromFile();
        System.out.println("ESTOY AQUI");
        Intent intent = new Intent(this, Config.class);
        startActivity(intent);
    }

    public void boton1(final View view){
        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    //String valueIP =((EditText)findViewById(R.id.entradaIP)).getText().toString();
                    //int port = 10099;
                    readFromFile();
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
        //DBConnection.getInstance().closeConnection();

    }
}











