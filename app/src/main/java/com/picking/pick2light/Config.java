package com.picking.pick2light;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Config extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        readFromFile();
    }

    public void update(View view){
        writeToFile(((EditText)findViewById(R.id.editText)).getText().toString(), MODE_PRIVATE);
        writeToFile(((EditText)findViewById(R.id.editText2)).getText().toString(), MODE_APPEND);
        finish();
    }

    private void writeToFile(String data, int mode) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("config.txt", mode));
            outputStreamWriter.write(data+"\n");
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
                    if (counter == 0){ ((EditText)findViewById(R.id.editText)).setText(receiveString); }
                    if (counter == 1){ ((EditText)findViewById(R.id.editText2)).setText(receiveString); }
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
}
