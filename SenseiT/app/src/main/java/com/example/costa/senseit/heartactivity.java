package com.example.costa.senseit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class heartactivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heartrate);

        TextView mMessageWindow = (TextView) findViewById(R.id.messageWindow);
        StringBuilder stringBuilder = new StringBuilder();

        String someMessage = " The average normal resting heart rate for adults is between 60 and 100 beats per minute\n" +
                "(bpm). Heart rate increases with exercise and normal heart rate varies between individuals.\n ";


        mMessageWindow.setText(someMessage);
    }
    public void connectblueheartclick(View v)
    {
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        File directory = getExternalFilesDir("/Data/");
        if(directory.exists()) {
            Toast.makeText(getBaseContext(), "Directory exists!", Toast.LENGTH_SHORT).show();
            bpmo2calculation Heart = new bpmo2calculation();
            String file = directory.getAbsolutePath()+"heart.txt";
            Heart.mainer(file);
            String tesss = "BPM: "+Heart.getbpm()+" SPO2: "+Heart.getspo2();
            textView3.setText(tesss);
        }
        else{
            Toast.makeText(getBaseContext(), "Directory doesnt exist!", Toast.LENGTH_SHORT).show();
        }
        //Intent intent = new Intent(getApplicationContext(),connectblueheart.class);
        //startActivity(intent);



    }
}
