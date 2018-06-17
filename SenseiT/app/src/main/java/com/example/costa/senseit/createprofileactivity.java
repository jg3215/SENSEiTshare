package com.example.costa.senseit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;

public class createprofileactivity extends AppCompatActivity {
    EditText NewUserName;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createprofile);
        NewUserName = (EditText) findViewById(R.id.editText2);


        }
    public void crprclick(View v)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        String user_name = NewUserName.getText().toString();

        File directory = getExternalFilesDir("/Profiles/");
        File file_user = new File(directory,user_name+".txt");
        try {
            file_user.createNewFile();

        } catch (IOException e){}


        startActivity(intent);
    }
}
