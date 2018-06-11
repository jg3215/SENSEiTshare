package com.example.costa.senseit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.content.Intent;
import android.view.View;
import android.view.Menu;
import android.view.View.OnClickListener;

//kakajunn testing
public class MainActivity extends AppCompatActivity {
ImageButton heartratebutton;
ImageButton o2button;
    ImageButton no2button;
    ImageButton alibutton;
    ImageButton lungcapacitybutton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heartratebutton=(ImageButton) findViewById(R.id.heartratebutton);
        // Capture button clicks
        heartratebutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        NewActivity.class);
                startActivity(myIntent);
            }
        });

        lungcapacitybutton=(ImageButton) findViewById(R.id.lungcapacitybutton);
        // Capture button clicks
        lungcapacitybutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        lungbutton.class);
                startActivity(myIntent);
            }
        });
        o2button=(ImageButton) findViewById(R.id.o2button);
        // Capture button clicks
        o2button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        o2button.class);
                startActivity(myIntent);
            }
        });
        //alibutton=(ImageButton) findViewById(R.id.alibutton);
        // Capture button clicks
        //alibutton.setOnClickListener(new OnClickListener() {
           // public void onClick(View arg0) {

                // Start NewActivity.class
                //Intent myIntent = new Intent(MainActivity.this,
                     //   alibutton.class);
                //startActivity(myIntent);
           // }
      //  });
        no2button=(ImageButton) findViewById(R.id.no2button);
        // Capture button clicks
        no2button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        no2button.class);
                startActivity(myIntent);
            }
        });
    }

   // @Override
   // public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.activity_main, menu);
       // return true;
   // }
}


