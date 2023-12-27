package com.y3project.myapplication;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class CuisineStaffService extends Service {
    private Handler mHandler = new Handler();
    String url1 = "http://192.168.137.1/codes/getMaxId.php";

    public CuisineStaffService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mToastRunnable.run();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        mHandler.removeCallbacks(mToastRunnable);
        stopSelf();
        super.onDestroy ();
    }

    private Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            //Toast.makeText(StartOnBootService.this, packageName, Toast.LENGTH_SHORT).show();
            new BackgroundWorkerUpdateStaff(CuisineStaffService.this).execute(url1,StaffOrderList.cuisine);
            mHandler.postDelayed(this, 8000);
        }
    };

}