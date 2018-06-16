package com.example.costa.senseit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
public class lungactivity extends AppCompatActivity {

    //public Handler LHandler = MainActivity.mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lungcapacity);
        //TextView mTitleWindow = (TextView) findViewById(R.id.titleWindow);
        TextView mMessageWindow = (TextView) findViewById(R.id.messageWindow);


        String someMessage = " Forced Vital Capacity (FVC) is the amount of air that you can push out of your lungs after" +
                "taking one deep breath. In adults, the approximate normal lung volume is 3.7 litres (L) for" +
                "women and 4.8 L for men. Everyone is different and repeated measurement allows you to compare yourself against previous measurements. ";


        mMessageWindow.setText(someMessage);
    }

    public void lungtoinstrclick (View v){
        Intent intent = new Intent(getApplicationContext(), lunginstructions.class);
        startActivity(intent);
    }
}