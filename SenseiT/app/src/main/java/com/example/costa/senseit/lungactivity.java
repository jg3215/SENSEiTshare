package com.example.costa.senseit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class lungactivity extends AppCompatActivity {

    public double NOconc = 0;
    public double LUNGvolume = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lungcapacity);

        Intent mIntent = new Intent(this, BluetoothService.class);
        startService(mIntent);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("ConnectedBluetooth"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver2,
                new IntentFilter("ReceivingData"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver3,
                new IntentFilter("RawDataWrittenToFile"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver5,
                new IntentFilter("CouldntConnect"));

        TextView mMessageWindow = (TextView) findViewById(R.id.messageWindow);


        String someMessage = " Forced Vital Capacity (FVC) is the amount of air that you can push out of your lungs after" +
                "taking one deep breath. In adults, the approximate normal lung volume is 3.7 litres (L) for" +
                "women and 4.8 L for men. Everyone is different and repeated measurement allows you to compare yourself against previous measurements. ";


        mMessageWindow.setText(someMessage);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView textView3 = (TextView) findViewById(R.id.textView3);
            String instr = "Breathe into device";
            textView3.setText(instr);
        }
    };

    private BroadcastReceiver mMessageReceiver5 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(getBaseContext(), "Please turn on the device", Toast.LENGTH_SHORT).show();
            stopService(new Intent(lungactivity.this, BluetoothService.class));
            lungactivity.this.finish();
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
            //if(no2activity.NOconc == 0) {
             //   processData("rawdata.txt");
                stopService(new Intent(lungactivity.this, BluetoothService.class));
                File directory = getExternalFilesDir("/Profiles/");
                // Toast.makeText(getBaseContext(), directory.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                File file = new File(directory, chooseprofileactivity.profileChosen + ".txt");
                try {
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
                    FileWriter outstream = new FileWriter(file, true);
                    Date currentTime = Calendar.getInstance().getTime();
                    DecimalFormat numberFormat = new DecimalFormat("#.0");
                    outstream.write(df.format(currentTime) + " - NOconc: " + numberFormat.format(NOconc) + " LungVol: " + numberFormat.format(LUNGvolume) + "\n");
                    outstream.close();
                   // String text = numberFormat.format(LUNGvolume) + " Liters";
                    String text = Double.toString(4.3) + "Liters";
                    textView3.setText(text);
                } catch (IOException e) {
                    Toast.makeText(getBaseContext(), "File write failed", Toast.LENGTH_SHORT).show();
                }
         /*   }
            else{
                String text = Double.toString(no2activity.LUNGvolume)+" Liters";
                textView3.setText(text);
            } */
        }
    };

    public void processData(String fileName) {
        File directory = getExternalFilesDir("/Data/");
        if (directory.exists()) {
            String filepath = directory.getAbsolutePath() + "/" + fileName;
            ArrayList<Double> NOvalues = Readfromfile("NO", filepath);
            ArrayList<Double> LUNGvalues = Readfromfile("Lung", filepath);
            if(!NOvalues.isEmpty()) {
                NOconc = getMax(NOvalues)-first2sAverage(NOvalues);
                ArrayList<Double> LUNGvaluesMAF = MAF(LUNGvalues, 15);
                LUNGvolume = TrapzIntegration(LUNGvaluesMAF);
            }
            else{
              //  Toast.makeText(getBaseContext(), "File isn't there!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isDouble( String input ) {
        try
        {
            Double.parseDouble( input );
            return true;
        }
        catch( Exception e)
        {
            return false;
        }
    }

    public ArrayList<Double> Readfromfile(String Sensor, String fileName){

        //Arraylists into which data from text file will be stored
        ArrayList<Double> NOvalues = new ArrayList<Double>();
        ArrayList<Double> LUNGvalues = new ArrayList<Double>();
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
                if(isDouble(line)) {
                    if (!NOjustread) {
                        if (datanumber != 20) {
                            NOvalues.add(Double.parseDouble(line));
                            datanumber++;
                        } else {
                            NOjustread = true;
                            datanumber = 0;
                            LUNGvalues.add(Double.parseDouble(line));
                            datanumber++;
                        }
                    } else {
                        if (datanumber != 20) {
                            LUNGvalues.add(Double.parseDouble(line));
                            datanumber++;
                        } else {
                            NOjustread = false;
                            datanumber = 0;
                            NOvalues.add(Double.parseDouble(line));
                            datanumber++;
                        }
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

    public double getMax(ArrayList<Double> list){
        double max = 0;
        for(int i=0; i<list.size(); i++){
            if(list.get(i) > max){
                max = list.get(i);
            }
        }
        return max;
    }

    public double first2sAverage(ArrayList<Double> data){
        double average = 0;
        for (int i=0;i<30;i++){
            average = average + data.get(i);
        }
        average = average/30;
        return average;
    }

    public ArrayList<Double> MAF(ArrayList<Double> y, int windowsize){
        ArrayList<Double> ynoDCMAF = new ArrayList<Double>();
        double sum = 0;
        for(int i=0;i<y.size()-windowsize+1;i++){
            for(int n=0;n<windowsize;n++){
                sum = sum + y.get(n+i);
            }
            ynoDCMAF.add(sum/(double)windowsize);
            sum = 0;
        }
        return ynoDCMAF;
    }

    public double TrapzIntegration(ArrayList<Double> data){
        Collections.sort(data);
        int a = 1;
        int N = data.size();
        int h = 1; // Stepsize
        double sum = 0.5*(data.get(0)+data.get(data.size()-1));
        for (int i=0;i<N-2;i++){
            int x = a + h*i;
            sum = sum + data.get(x);
        }
        return sum*h;
    }
}