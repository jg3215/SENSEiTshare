package com.example.costa.senseit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class welcomeactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

    }
    public void chooseprofileclick(View v)
    {
        Intent intent = new Intent(getApplicationContext(), chooseprofileactivity.class);
        startActivity(intent);
    }

    public void createnewprofileclick(View v)
    {
        Intent intent = new Intent(getApplicationContext(), createprofileactivity.class);
        startActivity(intent);
    }

}
