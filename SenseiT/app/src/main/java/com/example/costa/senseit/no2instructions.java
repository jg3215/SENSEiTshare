package com.example.costa.senseit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by artur on 6/15/2018.
 */

public class no2instructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no2instruct);
    }

    public void no2start(View v)
    {
        Intent intent = new Intent(getApplicationContext(), no2activity.class);
        startActivity(intent);
    }
}
