package com.example.costa.senseit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class lungactivity extends AppCompatActivity {

    //public Handler LHandler = MainActivity.mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lungcapacity);
        //TextView mTitleWindow = (TextView) findViewById(R.id.titleWindow);
        TextView mMessageWindow = (TextView) findViewById(R.id.messageWindow);


        String someMessage = " Forced Vital Capacity (FVC) is the amount of air that you can push out of your lungs after" +
                "taking one deep breath. In adults, the approximate normal lung volume is 3.7 litres (L) for" +
                "women and 4.8 L for men. Everyone is different and repeated measurement allows you to compare yourself against previous measurements. ";


        mMessageWindow.setText(someMessage);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            File directory = getExternalFilesDir("/Data/");
            File file = new File(directory,"rawdata.txt");
            //processdata("rawdata.txt");
            if (heartactivity.bpm != 0) { //Means data was calculated
                File directory2 = getExternalFilesDir("/Profiles/");
                // Toast.makeText(getBaseContext(), directory.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                File file2 = new File(directory, "Artur.txt");
                try {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    FileWriter outstream = new FileWriter(file, true);
                    Date currentTime = Calendar.getInstance().getTime();
                    outstream.write(df.format(currentTime) + " - BPM: " + Integer.toString(heartactivity.bpm) + " SPO2: " + Integer.toString(heartactivity.spo2) + "\n");
                    outstream.close();
                    TextView textView3 = (TextView) findViewById(R.id.textView3);
                    textView3.setText("BPM: "+Integer.toString(heartactivity.bpm));
                } catch (IOException e) {
                    Toast.makeText(getBaseContext(), "File write failed", Toast.LENGTH_SHORT).show();
                    Log.e("Exception", "File write failed: " + e.toString());
                }
            }
        }
    };

    public ArrayList<Integer> Readfromfile(String Sensor, String fileName){

        //Arraylists into which data from text file will be stored
        ArrayList<Integer> NOvalues = new ArrayList<Integer>();
        ArrayList<Integer> LUNGvalues = new ArrayList<Integer>();
        //READING THE NUMBERS FROM THE TEXT FILE INTO ARRAYS
        String line = null; // This will reference one line at a time
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int datanumber = 0;
            boolean NOjustread = false;
            while((line = bufferedReader.readLine()) != null) {
                if(!NOjustread){
                    if(datanumber!=20){
                        NOvalues.add(Integer.parseInt(line));
                        datanumber++;
                    }
                    else{
                        NOjustread = true;
                        datanumber = 0;
                        LUNGvalues.add(Integer.parseInt(line));
                        datanumber++;
                    }
                }
                else{
                    if(datanumber!=20){
                        LUNGvalues.add(Integer.parseInt(line));
                        datanumber++;
                    }
                    else{
                        NOjustread = false;
                        datanumber = 0;
                        NOvalues.add(Integer.parseInt(line));
                        datanumber++;
                    }
                }
            }
            bufferedReader.close(); //Closes file.
        }
        catch(FileNotFoundException e) {
            Toast.makeText(getBaseContext(), "File Not Found!", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e) {
           Toast.makeText(getBaseContext(), "File read failed!", Toast.LENGTH_SHORT).show();
        }

        if(Objects.equals(Sensor, "NO")){
            return NOvalues;
        }
        else{
            return LUNGvalues;
        }
    }

    public ArrayList<Integer> MAF(ArrayList<Integer> y, int windowsize){
        ArrayList<Integer> ynoDCMAF = new ArrayList<Integer>();
        double sum = 0;
        for(int i=0;i<y.size()-windowsize+1;i++){
            for(int n=0;n<windowsize;n++){
                sum = sum + y.get(n+i);
            }
            ynoDCMAF.add((int)sum/windowsize);
            sum = 0;
        }
        return ynoDCMAF;
    }
}