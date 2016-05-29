package com.picking.pick2light;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class Data_user extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_user);
    }
    public void crono(View view){
        Intent i= new Intent(this, Crono.class );
        startActivity(i);
    }
}
