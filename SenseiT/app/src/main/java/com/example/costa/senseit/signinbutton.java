package com.example.costa.senseit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class signinbutton extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signinbutton);
    }
    public void welcome(View v)
    {
        Intent intent = new Intent(getApplicationContext(), welcome.class);
        startActivity(intent);
    }
}
