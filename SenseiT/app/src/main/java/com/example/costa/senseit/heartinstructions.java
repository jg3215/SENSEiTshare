package com.example.costa.senseit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class heartinstructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heartinstruct);
    }

    public void heartratestart(View v)
    {
        Intent intent = new Intent(getApplicationContext(), heartactivity.class);
        startActivity(intent);
    }
}
