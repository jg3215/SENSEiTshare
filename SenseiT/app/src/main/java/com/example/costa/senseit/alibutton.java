package com.example.costa.senseit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class alibutton extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alibutton);
        TextView mMessageWindow = (TextView) findViewById(R.id.messageWindow);
        StringBuilder stringBuilder = new StringBuilder();

        String someMessage = " It is another word for bad breath. Bad breath is a very common condition, estimated\n" +
                "to affect 25% of people worldwide. It can be improved with good dental hygiene and \n"+
                "use of an antibacterial or anti-odour mouthwash as well as product such as breath mints and gums. \n" +
                " If you are worried about bad breath, you can seek advice from your dentist or doctor.\n ";


        mMessageWindow.setText(someMessage);
    }
}