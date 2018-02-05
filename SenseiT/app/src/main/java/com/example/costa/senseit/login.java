package com.example.costa.senseit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Menu;
import android.view.View.OnClickListener;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

    }
    public void loginbuttonclick(View v)
    {
        Intent intent = new Intent(getApplicationContext(), loginbutton.class);
        startActivity(intent);
    }

    public void signinbuttonclick(View v)
    {
        Intent intent = new Intent(getApplicationContext(), signinbutton.class);
        startActivity(intent);
    }

}
