package com.example.aguilar.bluetoothapp;

import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



/**
 * Created by aguil on 9/23/2018.
 */

//public class ObserveFile extends FileObserver {
//    File absolutePath;
//    File check = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "helloWorld.txt");
//
//    BluetoothConnectionService mBluetoothConnection;
//
//    public ObserveFile(File path, BluetoothConnectionService mBluetoothConnection) {
//        super(String.valueOf(path), FileObserver.MODIFY);
//        this.absolutePath = path;
//        this.mBluetoothConnection = mBluetoothConnection;
//    }
//
//    @Override
//    public void onEvent(int event, String absolutePath) {
//
//            if(FileObserver.MODIFY == 2) {
//
//                Log.e("FO:", "MODIFY");
//
//                FileInputStream inStream = null;
//                StringBuilder inMessage = new StringBuilder();
//                int inChar;
//                try {
//                    inStream = new FileInputStream(check);
//                    while ((inChar = inStream.read()) != -1) {
//                        inMessage.append((char) inChar);
//                    }
//                    mBluetoothConnection.write(inMessage.toString().getBytes());
//
//                    DataCommunication.incomingMessages.setText("File modified by another app");
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    //inStream.flush();
//                    try {
//                        assert inStream != null;
//
//                        inStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//    }
//}
