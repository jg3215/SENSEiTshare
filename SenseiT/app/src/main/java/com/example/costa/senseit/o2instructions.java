package com.example.costa.senseit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by artur on 6/15/2018.
 */

public class o2instructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.o2instruct);
    }

    public void o2start(View v)
    {
        Intent intent = new Intent(getApplicationContext(), o2activity.class);
        startActivity(intent);
    }
}
