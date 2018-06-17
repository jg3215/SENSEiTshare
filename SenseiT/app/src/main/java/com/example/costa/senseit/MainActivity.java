package com.example.costa.senseit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void heartrateclick (View v){
        Intent myIntent = new Intent(MainActivity.this,
                heartinstructions.class);
        startActivity(myIntent);
    }

    public void lungcapacityclick (View v){
        Intent myIntent = new Intent(MainActivity.this,
                lunginstructions.class);
        startActivity(myIntent);
    }

    public void o2click (View v){
        Intent myIntent = new Intent(MainActivity.this,
                o2activity.class);
        startActivity(myIntent);
    }

    public void no2click (View v){
        Intent myIntent = new Intent(MainActivity.this,
                no2activity.class);
        startActivity(myIntent);
    }
    public void viewdataclick (View v){

    }
}


