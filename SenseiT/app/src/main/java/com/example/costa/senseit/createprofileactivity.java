package com.example.costa.senseit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
        String user_name = NewUserName.getText().toString();
        if(Objects.equals(user_name, "")){
            Toast.makeText(getBaseContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
        }
        else {
            chooseprofileactivity.profileChosen = user_name;
            File directory = getExternalFilesDir("/Profiles/");
            File file_user = new File(directory, user_name + ".txt");
            try {
                file_user.createNewFile();
            } catch (IOException e) {
            }
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}
