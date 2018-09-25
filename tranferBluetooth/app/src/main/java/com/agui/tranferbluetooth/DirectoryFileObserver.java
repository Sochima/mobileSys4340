package com.agui.tranferbluetooth;


import android.content.ContentValues;


import android.content.Intent;
import android.os.FileObserver;
import android.util.Log;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import static android.content.Intent.*;


/**
 * Created by aguil on 9/23/2018.
 */

public class DirectoryFileObserver extends FileObserver {
    String absolutePath = "/storage/emulated/0/Download";
    File check;
    String newPath;
    long mod;
    MainActivity resend = new MainActivity();

    public DirectoryFileObserver(String path, MainActivity main) {
        super(path, FileObserver.ALL_EVENTS);
        resend = main;
        absolutePath = path;
    }

    public String getPath() {
        return newPath;
    }

    @Override
    public void onEvent(int event, String path) {

        switch (event) {
            case FileObserver.CREATE:
                Log.e("FO:", "CREATE: " + path);

                break;
            case FileObserver.MODIFY:
                Log.e("FO:", "MODIFY");
                //Uri uri = Uri.parse(uriString);
                resend.textView_FileName.setText("File Modified");
//                ContentValues val = new ContentValues();
//                val.put(BluetoothShare.URI,"content://storage/emulated/0/Documents");
//                val.put(BluetoothShare.DESTINATION, "B8:1D:AA:02:DO:AD");
//                val.put(BluetoothShare.DIRECTION, BluetoothShare.DIRECTION_OUTBOUND);
                try {
                    resend.onActivityResult(300, 1, Intent.getIntent(absolutePath));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


        }

    }


}
