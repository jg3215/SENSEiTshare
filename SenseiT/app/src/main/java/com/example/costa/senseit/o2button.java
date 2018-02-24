package com.example.costa.senseit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class o2button extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.o2button); TextView mMessageWindow = (TextView) findViewById(R.id.messageWindow);


        StringBuilder stringBuilder = new StringBuilder();

        String someMessage = " Sp0 2 stands for the peripheral capillary blood oxygen saturation and estimates how much\n" +
                "oxygen there is in your blood. The normal oxygen saturation in an adult is between 95% and\n" +
                "100%. Smoking and some breathing conditions can cause you to have a slightly lower than\n" +
                "average blood oxygen saturation.\n ";


        mMessageWindow.setText(someMessage);
    }
    public void bluetoothclick(View v)
    {
        Intent intent = new Intent(getApplicationContext(),findDevice.class);
        startActivity(intent);
    }
}
