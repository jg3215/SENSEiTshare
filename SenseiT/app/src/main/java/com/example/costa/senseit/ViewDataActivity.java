package com.example.costa.senseit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ViewDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdata);

        String line = null; // This will reference one line at a time
        StringBuilder text2print =  new StringBuilder();
        File directory = getExternalFilesDir("/Profiles/");
        // Toast.makeText(getBaseContext(), directory.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        String filepath = directory.getAbsolutePath() + "/" + chooseprofileactivity.profileChosen + ".txt";

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(filepath);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null){
                text2print.append(line);
                text2print.append("\n");
            }
            bufferedReader.close(); //Closes file.
        }
        catch(FileNotFoundException e) {
            Toast.makeText(getBaseContext(), "File Not Found!", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e) {
            Toast.makeText(getBaseContext(), "File read failed!", Toast.LENGTH_SHORT).show();
        }


        TextView output= (TextView) findViewById(R.id.textView14);
            output.setText((CharSequence) text2print);
    }
}
