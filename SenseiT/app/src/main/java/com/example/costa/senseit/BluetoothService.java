package com.example.costa.senseit;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.UUID;


public class BluetoothService extends Service {

    public ConnectedThread mConnectedThread; // bluetooth background worker thread to send and receive data
    private BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path
    private BluetoothAdapter mBTAdapter;

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier

    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names

    public BluetoothService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Connect();
        return START_STICKY;// Keeps the service running
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //bluetooth stuff
       /* if (!mBTAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } */

        mBTAdapter = BluetoothAdapter.getDefaultAdapter();

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
                        Toast.makeText(getBaseContext(), "Connection to device failed", Toast.LENGTH_SHORT).show();
                    } catch (IOException e2) {
                        //insert code to deal with this
                        Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                }
                if (fail == false) {
                    mConnectedThread = new BluetoothService.ConnectedThread(mBTSocket);
                    mConnectedThread.start();
                 //   Toast.makeText(getApplicationContext(), "Connected to Bluetooth", Toast.LENGTH_SHORT).show();
                    sendMessage();
                }
            }
        }.start();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void sendMessage() {
        Intent intent = new Intent("ConnectedBluetooth");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendMessage2() {
        Intent intent = new Intent("ReceivingData");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendMessage3() {
        Intent intent = new Intent("RawDataWrittenToFile");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendMessage4() {
        Intent intent = new Intent("FingerPlacedOnSensor");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

 /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_ENABLE_BT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                Toast.makeText(getApplicationContext(), "Bluetooth turned on", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getBaseContext(), "Please Enable Bluetooth", Toast.LENGTH_SHORT).show();
        }
    } */
 public boolean isInteger( String input )
 {
     try
     {
         Integer.parseInt( input );
         return true;
     }
     catch( Exception e)
     {
         return false;
     }
 }

    public BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connection with BT device using UUID
    }

    public class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private BufferedWriter outstream;
        private boolean ReceivingStarted= false;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
                File directory = getExternalFilesDir("/Data/");
                File file = new File(directory, "rawdata.txt");
                file.delete();
                file.createNewFile();
                outstream = new BufferedWriter(new FileWriter(file, true));
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    if (bytes != 0) {
                        final String strReceived = new String(buffer, 0, bytes);
                        if(!Objects.equals(strReceived,"#")){
                            if(Objects.equals(strReceived,"S")){
                                sendMessage4();
                            }else{
                                if(!ReceivingStarted){
                                    sendMessage2();
                                    ReceivingStarted = true;
                                }
                                outstream.write(strReceived);
                            }
                        }else {
                            outstream.close();
                            sendMessage3();
                            //onDestroy();
                            //cancel();
                            break;
                        }
                    }
                } catch (IOException e) {
                }
            }
        }

     /*   // Call this from the main activity to send data to the remote device
        public void write(String input) {
            byte[] bytes = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
            }
        }

        // Call this from the main activity to shutdown the connection
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        } */
    }

}
