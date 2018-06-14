package com.example.costa.senseit;

/**
 * Created by artur on 6/12/2018.
 */
import java.io.*;
import java.util.ArrayList;

public class bpmo2calculation {

    private int bpm;
    private int spo2;

    public bpmo2calculation(){
    }

    public void mainer(String fileName) {
        //Read Raw Data Values from file
        ArrayList<Integer> IRvalues = Readfromfile("IR", fileName);
        ArrayList<Integer> REDvalues = Readfromfile("RED", fileName);

        //Perform DC filtering  - removing of the DC component
        ArrayList<Integer> IRwithoutDC = DCremoval(IRvalues);
        ArrayList<Integer> REDwithoutDC = DCremoval(REDvalues);

        //Part to Calculate SPO2
        spo2 = SPO2(IRwithoutDC,REDwithoutDC);

        //Moving Average Filtering of IR data
        ArrayList<Integer> IRnoDCMAF = MAF(IRwithoutDC,15);

        //LowPass filtering (fc = 4kHz) of IR data
        ArrayList<Integer> IRfiltered = LowPass(IRnoDCMAF);

        //BPM Calculation
        bpm = BPM(IRfiltered);
    }

    public String getbpm(){
        return Integer.toString(bpm);
    }

    public String getspo2(){
        return Integer.toString(spo2);
    }

    public ArrayList<Integer> Readfromfile(String Sensor, String fileName){
        //Arraylists into which data from text file will be stored
        ArrayList<Integer> IRvalues = new ArrayList<Integer>();
        ArrayList<Integer> REDvalues = new ArrayList<Integer>();
        //READING THE NUMBERS FROM THE TEXT FILE INTO ARRAYS
        String line = null; // This will reference one line at a time

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                //IR and Red LED values are stored one after the other in one column so each line is a number and have read them in turn
                IRvalues.add(Integer.parseInt(line));
                REDvalues.add(Integer.parseInt(bufferedReader.readLine()));
            }
            bufferedReader.close(); //Closes file.
        }
        catch(FileNotFoundException ex) {
            //System.out.println("Unable to open file '" + fileName + "'");


        }
        catch(IOException ex) {
           // System.out.println(
                //    "Error reading file '" + fileName + "'");
        }

        if(Sensor == "IR"){
            return IRvalues;
        }
        else{
            return REDvalues;
        }
    }

    public ArrayList<Integer> DCremoval(ArrayList<Integer> y){
        //REMOVING DC COMPONENT FROM SIGNAL
        ArrayList<Integer> ynoDC = new ArrayList<Integer>();
        double prev_w = 0;
        for (int i=0;i<y.size();i++){
            ynoDC.add((int)((double)y.get(i)+0.95*prev_w-prev_w));
            prev_w =(double)y.get(i)+0.95*prev_w;
        }

        for (int i=0;i<300;i++){
            ynoDC.remove(0);
            if(i%2 == 1){
                ynoDC.remove(ynoDC.size()-1);
            }
        }
        return ynoDC;
    }

    public int SPO2(ArrayList<Integer> y1, ArrayList<Integer> y2){
        double R = 0;
        int SPO2 = 0;
        double IR_RMS = 0;
        double RED_RMS = 0;
        for(int i=0;i<y1.size();i++){
            IR_RMS = IR_RMS + (double)(y1.get(i)*y1.get(i));
            RED_RMS = RED_RMS + (double)(y2.get(i)*y2.get(i));
        }
        IR_RMS = Math.sqrt(IR_RMS/y1.size());
        RED_RMS = Math.sqrt(RED_RMS/y2.size());
        R = (Math.log(RED_RMS)*650*0.000000001)/(Math.log(IR_RMS)*950*0.000000001);
        SPO2 = (int)((110-20*R)+0.5);
        return SPO2;
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

    public ArrayList<Integer> LowPass(ArrayList<Integer> y){
        //LowPass Filtering
        double prev_v = 0;
        ArrayList<Integer> yfiltered = new ArrayList<Integer>();

        for (int i=0;i<y.size();i++){
            yfiltered.add((int)(1.367287359973195227e-1 *(double)y.get(i) + 0.72654252800536101020 * prev_v + prev_v));
            prev_v = 1.367287359973195227e-1 *(double)y.get(i) + 0.72654252800536101020 * prev_v;
        }
        return yfiltered;
    }

    //States for calculating BPM - must know if were currently above or below threshold
    public enum currentPulseDetectorState{
        BELOWTHRESHOLD, ABOVETHRESHOLD
    }

    public int BPM(ArrayList<Integer> y){
        ArrayList<Integer> Indexes = new ArrayList<Integer>();  //Indexes in REDfiltered where sensor value goes above threshold
        currentPulseDetectorState state = currentPulseDetectorState.BELOWTHRESHOLD;

        int maxvalue = 0;
        int minvalue = 0;
        for(int i=0;i<y.size();i++){
            if(y.get(i) > maxvalue){
                maxvalue = y.get(i);
            }
            if(y.get(i)<minvalue){
                minvalue = y.get(i);
            }
        }
        int PULSE_THRESHOLD = (maxvalue+minvalue)/2;
        if(y.get(0) > PULSE_THRESHOLD){
            state = currentPulseDetectorState.ABOVETHRESHOLD;
        }
        else{
            state = currentPulseDetectorState.BELOWTHRESHOLD;
        }

        for (int index = 0; index < y.size(); index++){
            switch(state)
            {
                case BELOWTHRESHOLD:
                    if(y.get(index) >= PULSE_THRESHOLD) {
                        state = currentPulseDetectorState.ABOVETHRESHOLD;
                        Indexes.add(index);
                    } break;
                case ABOVETHRESHOLD:
                    if(y.get(index)<PULSE_THRESHOLD)
                    {
                        state = currentPulseDetectorState.BELOWTHRESHOLD;
                    } break;
            }
        }

        ArrayList<Integer> BeatPeriods = new ArrayList<Integer>();
        for (int k=1; k<Indexes.size();k++){
            BeatPeriods.add(Indexes.get(k) - Indexes.get(k-1));
        }
        double meanbeatperiod = 0;
        for (int i=0;i<BeatPeriods.size();i++){
            meanbeatperiod = meanbeatperiod + BeatPeriods.get(i);
        }
        meanbeatperiod = meanbeatperiod / BeatPeriods.size(); //MeanBeatperiod in number of samples. Sample every 0.01 seconds.
        int BPM = (int)(60/(meanbeatperiod*0.01)+0.5);
        return BPM;
    }
}