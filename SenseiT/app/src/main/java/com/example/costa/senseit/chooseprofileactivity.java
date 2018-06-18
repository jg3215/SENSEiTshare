package com.example.costa.senseit;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Objects;

public class chooseprofileactivity extends AppCompatActivity {

    public static String profileChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseprofile);
        File directory = getExternalFilesDir("/Profiles/");
        File[] files = directory.listFiles();

        TextView text_user1 = (TextView) findViewById(R.id.text_user1);
        TextView text_user2 = (TextView) findViewById(R.id.text_user2);
        TextView text_user3 = (TextView) findViewById(R.id.text_user3);
        TextView text_user4 = (TextView) findViewById(R.id.text_user4);

        if (files.length == 0) {
            String u1 = "Add a User";
            String u2 = "Add a User";
            String u3 = "Add a User";
            String u4 = "Add a User";
            text_user1.setText(u1);
            text_user2.setText(u2);
            text_user3.setText(u3);
            text_user4.setText(u4);

        } else if (files.length == 1) {
            String u1 = files[0].getName();
            u1 = u1.replace(".txt", "");
            String u2 = "Add a User";
            String u3 = "Add a User";
            String u4 = "Add a User";
            text_user1.setText(u1);
            text_user2.setText(u2);
            text_user3.setText(u3);
            text_user4.setText(u4);
        } else if (files.length == 2) {
            String u1 = files[0].getName();
            u1 = u1.replace(".txt", "");
            String u2 = files[1].getName();
            u2 = u2.replace(".txt", "");
            String u3 = "Add a User";
            String u4 = "Add a User";
            text_user1.setText(u1);
            text_user2.setText(u2);
            text_user3.setText(u3);
            text_user4.setText(u4);
        } else if (files.length == 3) {
            String u1 = files[0].getName();
            String u2 = files[1].getName();
            String u3 = files[2].getName();
            u1 = u1.replace(".txt", "");
            u2 = u2.replace(".txt", "");
            u3 = u3.replace(".txt", "");
            String u4 = "Add a User";
            text_user1.setText(u1);
            text_user2.setText(u2);
            text_user3.setText(u3);
            text_user4.setText(u4);
        } else if (files.length == 4) {
            String u1 = files[0].getName();
            String u2 = files[1].getName();
            String u3 = files[2].getName();
            String u4 = files[3].getName();
            u1 = u1.replace(".txt", "");
            u2 = u2.replace(".txt", "");
            u3 = u3.replace(".txt", "");
            u4 = u4.replace(".txt", "");
            text_user1.setText(u1);
            text_user2.setText(u2);
            text_user3.setText(u3);
            text_user4.setText(u4);
        } else {
            String u1 = files[0].getName();
            String u2 = files[1].getName();
            String u3 = files[2].getName();
            String u4 = files[3].getName();
            u1 = u1.replace(".txt", "");
            u2 = u2.replace(".txt", "");
            u3 = u3.replace(".txt", "");
            u4 = u4.replace(".txt", "");
            text_user1.setText(u1);
            text_user2.setText(u2);
            text_user3.setText(u3);
            text_user4.setText(u4);
        }

    }

    public void user1button(View v) {
        TextView text_user1 = (TextView) findViewById(R.id.text_user1);
         if (!Objects.equals(text_user1.getText().toString(),"Add a User")) {
        Intent myIntent = new Intent(chooseprofileactivity.this,
                MainActivity.class);
        startActivity(myIntent);}
        else{
         Intent myIntent = new Intent(chooseprofileactivity.this,
                 createprofileactivity.class);
             startActivity(myIntent);}
    }

    public void user2button(View v) {
        TextView text_user2 = (TextView) findViewById(R.id.text_user2);
        if (!Objects.equals(text_user2.getText().toString(),"Add a User")) {
            profileChosen = text_user2.getText().toString();
            Intent myIntent = new Intent(chooseprofileactivity.this,
                MainActivity.class);
            startActivity(myIntent);
        }
        else
        {Intent myIntent = new Intent(chooseprofileactivity.this,
                createprofileactivity.class);
            startActivity(myIntent);}
    }

    public void user3button(View v) {
        TextView text_user3 = (TextView) findViewById(R.id.text_user3);
        if (!Objects.equals(text_user3.getText().toString(),"Add a User")) {
            profileChosen = text_user3.getText().toString();
        Intent myIntent = new Intent(chooseprofileactivity.this,
                MainActivity.class);
        startActivity(myIntent);}
        else
        {Intent myIntent = new Intent(chooseprofileactivity.this,
                createprofileactivity.class);
            startActivity(myIntent);}

    }
    public void user4button(View v) {
        TextView text_user4 = (TextView) findViewById(R.id.text_user4);
        if (!Objects.equals(text_user4.getText().toString(),"Add a User")) {
            profileChosen = text_user4.getText().toString();
        Intent myIntent = new Intent(chooseprofileactivity.this,
                MainActivity.class);
        startActivity(myIntent);}
        else
        {Intent myIntent = new Intent(chooseprofileactivity.this,
                createprofileactivity.class);
            startActivity(myIntent);}
    }

}