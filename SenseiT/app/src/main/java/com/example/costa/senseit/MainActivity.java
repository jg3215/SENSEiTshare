package com.example.costa.senseit;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //
    private TextView bluetoothstatus;
    public ConnectedThread mConnectedThread; // bluetooth background worker thread to send and receive data
    public Handler mHandler; // Our main handler that will receive callback notifications
    private BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path
    private BluetoothAdapter mBTAdapter;
    private Set<BluetoothDevice> mPairedDevices;

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier

    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    private final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status
    //

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothstatus = (TextView) findViewById(R.id.bluetoothstatus);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio

        //bluetooth stuff
        if (!mBTAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        bluetoothstatus.setText("Connecting to bluetooth...");

        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == MESSAGE_READ) {
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                        bluetoothstatus.setText("Receiving data");
                        writeToFile(readMessage);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                if (msg.what == CONNECTING_STATUS) {
                    if (msg.arg1 == 1)
                        bluetoothstatus.setText("Connected to Device");
                    else
                        bluetoothstatus.setText("Connection Failed");
                }
            }
        };

        final BluetoothDevice device = mBTAdapter.getRemoteDevice("AB:9D:AC:56:34:02");
        new Thread() {
            public void run() {
                boolean fail = false;
                try {
                    mBTSocket = createBluetoothSocket(device);
                } catch (IOException e) {
                    fail = true;
                    Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                }
                // Establish the Bluetooth socket connection.
                try {
                    mBTSocket.connect();
                } catch (IOException e) {
                    try {
                        fail = true;
                        mBTSocket.close();
                        mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                .sendToTarget();
                    } catch (IOException e2) {
                        //insert code to deal with this
                        Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                }
                if (fail == false) {
                    mConnectedThread = new MainActivity.ConnectedThread(mBTSocket);
                    mConnectedThread.start();

                    mHandler.obtainMessage(CONNECTING_STATUS, 1, -1)
                            .sendToTarget();
                }
            }
        }.start();

        //halithosis=(ImageButton) findViewById(R.id.halithosis);
        // Capture button clicks
        //halithosis.setOnClickListener(new OnClickListener() {
        // public void onClick(View arg0) {

        // Start heartactivity.class
        //Intent myIntent = new Intent(MainActivity.this,
        //   halithosis.class);
        //startActivity(myIntent);
        // }
        //  });
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }


    public void writeToFile(String data) {
        if(isExternalStorageWritable()==true && isExternalStorageReadOnly()==false) {
           // Toast.makeText(getBaseContext(), "ExternalStorageAvailableandWritable", Toast.LENGTH_SHORT).show();
            File directory = getExternalFilesDir("/Data/");
           // Toast.makeText(getBaseContext(), directory.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            File file = new File(directory,"heartraw.txt");

           if(directory.exists()) {
              //  Toast.makeText(getBaseContext(), "Directory exists!", Toast.LENGTH_SHORT).show();
            FileWriter outstream = null;
                try {
                    file.createNewFile();
                    //File tosave = new File(folderino.getAbsolutePath(),filename);
                    //tosave.createNewFile();
                    outstream = new FileWriter(file);
                   // BufferedWriter bufferwrite = new BufferedWriter(outstream);
                    outstream.write(data);
                    //outstream.flush();
                    outstream.close();
                    Toast.makeText(getBaseContext(), "Data written to device", Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    Toast.makeText(getBaseContext(), "File write failed", Toast.LENGTH_SHORT).show();
                    Log.e("Exception", "File write failed: " + e.toString());
                }
            }else{
                Toast.makeText(getBaseContext(), "Directory doesn't exist", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getBaseContext(), "ExternalStorageNOTAvailable", Toast.LENGTH_SHORT).show();
        }
    }

    public void heartrateclick (View v){
        Intent myIntent = new Intent(MainActivity.this,
                heartactivity.class);
        startActivity(myIntent);
    }

    public void lungcapacityclick (View v){
        Intent myIntent = new Intent(MainActivity.this,
                lungactivity.class);
        startActivity(myIntent);
    }

    public void o2click (View v){
        Intent myIntent = new Intent(MainActivity.this,
                o2activity.class);
        startActivity(myIntent);
    }

    public void no2click (View v){
        Intent myIntent = new Intent(MainActivity.this,
                no2activity.class);
        startActivity(myIntent);
    }
    public void viewdataclick (View v){

    }


   // @Override
   // public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.activity_main, menu);
       // return true;
   //

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
       // Check which request we're responding to
       if (requestCode == REQUEST_ENABLE_BT) {
           // Make sure the request was successful
           if (resultCode == RESULT_OK) {
               // The user picked a contact.
               // The Intent's data Uri identifies which contact was selected.
               //bluetoothstatus.setText("Enabled");
               Toast.makeText(getApplicationContext(), "Bluetooth turned on", Toast.LENGTH_SHORT).show();
           } else
               Toast.makeText(getBaseContext(), "Please Enable Bluetooth", Toast.LENGTH_SHORT).show();
       }
   }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connection with BT device using UUID
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    if (bytes != 0) {
                       //SystemClock.sleep(100);
                       mmInStream.read(buffer);
                    }
                    // Send the obtained bytes to the UI activity

                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String input) {
            byte[] bytes = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }
}


