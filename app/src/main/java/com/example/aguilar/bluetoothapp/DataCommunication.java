/* William Caballero-Ramos, 1000885642
 * Devony Powell, 1001237272
 * CSE 4340 FALL 2018
 * Lab 1 - An Introduction to Bluetooth and Consistency
 */

package com.example.aguilar.bluetoothapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.os.Environment;
import android.os.FileObserver;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.IntentFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.io.IOException;
import android.widget.Toast;

public class DataCommunication extends AppCompatActivity {
    private static final String TAG = "DataCommunication";

    // Deals with file creation and tracking
    /* TODO: change the names of the following variables */
    public static boolean dontWatch = false;
    File file;
    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    FileOutputStream fileOutputStream;
    String filename = "helloWorld.txt";
    private FileObserver observe;

    Button btnSend;
    EditText etSend;

    public static TextView incomingMessages;
    StringBuilder messages;

    BluetoothConnectionService mBluetoothConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_view);
        file = new File(path, filename);

        // TODO
        observe = new FileObserver(file.getPath()) {

            @Override
            public void onEvent(int event, String path) {

                switch (event) {

                    case FileObserver.MODIFY:

                        Log.e("FO:", "MODIFY");

                        FileInputStream inStream = null;
                        StringBuilder inMessage = new StringBuilder();
                        int inChar;
                        try {
                            inStream = new FileInputStream(file);
                            while ((inChar = inStream.read()) != -1) {
                                inMessage.append((char) inChar);
                            }
                            mBluetoothConnection.write(inMessage.toString().getBytes());

                            //DataCommunication.incomingMessages.setText("File modified by another app");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                assert inStream != null;

                                inStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }

            }

    };
            observe.startWatching();

        mBluetoothConnection = BluetoothConnectionService.getInstance();
        btnSend = (Button) findViewById(R.id.btnSend);
        etSend = (EditText) findViewById(R.id.editText);

        incomingMessages = (TextView) findViewById(R.id.incomingMessage);
        messages = new StringBuilder();

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("incomingMessage"));

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] bytes = etSend.getText().toString().getBytes(Charset.defaultCharset());
                Log.d(TAG, "The btn send: " + etSend.getText().toString() + "\n\n");

                try {
                    fileModify(etSend.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mBluetoothConnection.write(bytes);
//                messages.append("Sent: " + etSend.getText().toString() + "\n");

                incomingMessages.setText("File Modified");
                etSend.setText("");

                Context context = getApplicationContext();
                CharSequence text = "Sent";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    // Display received messages
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("theMessage");

            try {
                fileModify(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            messages.append(text);

            incomingMessages.setText("File Received");

        }
    };

    // Save text to file
    private void fileModify(String text) throws IOException {
        text += "\n";
        try {
            File filePath;
            filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            if (!filePath.exists()) {
                if (filePath.mkdir()) ; //directory is created;
            }

            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(text.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fileOutputStream.flush();
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onReceive: STATE OFF");
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
            Log.d(TAG, "mReceiver unregistered");

        } catch (Exception e) {
            Log.d(TAG, "mReceiver not registered");
        }
    }
}
