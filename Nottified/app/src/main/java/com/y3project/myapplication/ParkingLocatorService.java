package com.y3project.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class ParkingLocatorService extends Service {
    public ParkingLocatorService() {
    }

    private Handler mHandler = new Handler();
    String url1 = "http://192.168.137.1/codes/getParkingCount.php";

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
            new BackgroundWorkerParkingNum(ParkingLocatorService.this,ParkingLocator.getBno(), ParkingLocator.getJno(), ParkingLocator.getCno(),ParkingLocator.getBcap(),ParkingLocator.getJcap(),ParkingLocator.getCcap(),ParkingLocator.getBlay(),ParkingLocator.getJlay(),ParkingLocator.getClay()).execute(url1,ParkingLocator.type);
            mHandler.postDelayed(this, 8000);
        }
    };
}