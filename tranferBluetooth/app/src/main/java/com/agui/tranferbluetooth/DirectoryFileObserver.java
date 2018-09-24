package com.agui.tranferbluetooth;

import android.content.Intent;
import android.os.FileObserver;
import android.util.Log;

import java.io.File;

/**
 * Created by aguil on 9/23/2018.
 */

public class DirectoryFileObserver extends FileObserver {
    String absolutePath = "/storage/emulated/0/Download";
    File check;
    String newPath;
    long mod;
    MainActivity resend;

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
//            case FileObserver.CREATE:
//                Log.e("FO:", "CREATE: " + path);
//                check = new File(path);
//                mod = check.lastModified();
//                newPath = path;
//
//                break;
            case FileObserver.MODIFY:
                Log.e("FO:", "MODIFY");
                resend.resendFile(path);
                resend.textView_FileName.setText("File Modified");

                break;
        }

    }
}
