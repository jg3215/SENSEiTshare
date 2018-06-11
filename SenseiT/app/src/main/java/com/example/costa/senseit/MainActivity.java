package com.example.costa.senseit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.content.Intent;
import android.view.View;
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

                // Start heartactivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        heartactivity.class);
                startActivity(myIntent);
            }
        });

        lungcapacitybutton=(ImageButton) findViewById(R.id.lungcapacitybutton);
        // Capture button clicks
        lungcapacitybutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start heartactivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        lungactivity.class);
                startActivity(myIntent);
            }
        });
        o2button=(ImageButton) findViewById(R.id.o2button);
        // Capture button clicks
        o2button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start heartactivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        o2activity.class);
                startActivity(myIntent);
            }
        });
        //halithosis=(ImageButton) findViewById(R.id.halithosis);
        // Capture button clicks
        //halithosis.setOnClickListener(new OnClickListener() {
           // public void onClick(View arg0) {

                // Start heartactivity.class
                //Intent myIntent = new Intent(MainActivity.this,
                     //   halithosis.class);
                //startActivity(myIntent);
           // }
      //  });
        no2button=(ImageButton) findViewById(R.id.no2button);
        // Capture button clicks
        no2button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {

                // Start heartactivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        no2activity.class);
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


