package com.example.costa.senseit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
public class no2button extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no2button);
        TextView mMessageWindow = (TextView) findViewById(R.id.messageWindow);
        StringBuilder stringBuilder = new StringBuilder();

        String someMessage = " Normal levels of nitric oxide in the breath is less than 25 ppb (parts per billion). Levels of\n" +
                "greater than 51 ppb is said to be raised. Higher than normal levels of nitric oxide in the\n" +
                "expired breath has been linked to inflammation in the airways, which may be a result of air\n" +
                "pollutants, such as exhaust fumes from cars. High levels of nitric oxide in the breath may be\n" +
                "linked to a breathing condition called asthma.\n ";


        mMessageWindow.setText(someMessage);
    }
}
