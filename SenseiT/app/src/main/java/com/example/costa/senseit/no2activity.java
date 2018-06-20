package com.example.costa.senseit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class no2activity extends AppCompatActivity {

    public static int NOconc = 0;
    public static double LUNGvolume = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no2);

        Intent mIntent = new Intent(this, BluetoothService.class);
        startService(mIntent);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("ConnectedBluetooth"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver2,
                new IntentFilter("ReceivingData"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver3,
                new IntentFilter("RawDataWrittenToFile"));

        TextView mMessageWindow = (TextView) findViewById(R.id.messageWindow);

        String someMessage = " Normal levels of nitric oxide in the breath is less than 25 ppb (parts per billion). Levels of\n" +
                "greater than 51 ppb is said to be raised. Higher than normal levels of nitric oxide in the\n" +
                "expired breath has been linked to inflammation in the airways, which may be a result of air\n" +
                "pollutants, such as exhaust fumes from cars. High levels of nitric oxide in the breath may be\n" +
                "linked to a breathing condition called asthma.\n ";

        mMessageWindow.setText(someMessage);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView textView3 = (TextView) findViewById(R.id.textView3);
            String instr = "Place finger on sensor";
            textView3.setText(instr);
        }
    };
    private BroadcastReceiver mMessageReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView textView3 = (TextView) findViewById(R.id.textView3);
            String recvd = "Receiving Data";
            textView3.setText(recvd);
        }
    };
    private BroadcastReceiver mMessageReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView textView3 = (TextView) findViewById(R.id.textView3);
           // if(lungactivity.NOconc == 0) {
                processData("rawdata.txt");
                File directory = getExternalFilesDir("/Profiles/");
                // Toast.makeText(getBaseContext(), directory.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                File file = new File(directory, "Artur.txt");
                try {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
                    FileWriter outstream = new FileWriter(file, true);
                    Date currentTime = Calendar.getInstance().getTime();
                    outstream.write(df.format(currentTime) + " - NOconc: " + Integer.toString(NOconc) + " LungVol: " + Double.toString(LUNGvolume) + "\n");
                    outstream.close();
                    String text = Integer.toString(NOconc) + " ppm";
                    textView3.setText(text);
                } catch (IOException e) {
                    Toast.makeText(getBaseContext(), "File write failed", Toast.LENGTH_SHORT).show();
                }
          /*  }
            else{
                String text = Integer.toString(lungactivity.NOconc)+" ppm";
                textView3.setText(text);
            } */
        }
    };

    public void processData(String fileName) {
        File directory = getExternalFilesDir("/Data/");
        if (directory.exists()) {
            String filepath = directory.getAbsolutePath() + "/"+ fileName;
            ArrayList<Integer> NOvalues = Readfromfile("NO", filepath);
            ArrayList<Integer> LUNGvalues = Readfromfile("Lung", filepath);
            if(!NOvalues.isEmpty()) {
                NOconc = getMax(NOvalues)-first2sAverage(NOvalues);

                ArrayList<Integer> LUNGvaluesMAF = MAF(LUNGvalues, 15);
                LUNGvolume = TrapzIntegration(LUNGvaluesMAF);
            }
            else{
                Toast.makeText(getBaseContext(), "File isn't there!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

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

    public int getMax(ArrayList<Integer> list){
        int max = Integer.MIN_VALUE;
        for(int i=0; i<list.size(); i++){
            if(list.get(i) > max){
                max = list.get(i);
            }
        }
        return max;
    }

    public int first2sAverage(ArrayList<Integer> data){
        int average = 0;
        for (int i=0;i<200;i++){
            average = average + data.get(i);
        }
        average = average/200;
        return average;
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

    public double TrapzIntegration(ArrayList<Integer> data){
        Collections.sort(data);
        int a = 1;
        int N = data.size();
        int h = 1; // Stepsize
        double sum = 0.5*(data.get(0)+data.get(data.size()-1));
        for (int i=1;i<N;i++){
            int x = a + h*i;
            sum = sum + data.get(x);
        }
        return sum*h;
    }
}
