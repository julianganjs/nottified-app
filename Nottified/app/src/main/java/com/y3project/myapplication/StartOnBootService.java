package com.y3project.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
import java.util.List;

public class StartOnBootService extends Service {
    private Handler mHandler = new Handler();

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;
    String userinfo, usertype;
    String first_name = "User";
    String last_name = "";
    String user_name = "abcde5";

    String url1 = "http://192.168.137.1/codes/getAlert.php";

    String CHANNEL_ID = "my_channel_01";
    NotificationManager hello;
    NotificationManager hi;

    public StartOnBootService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= 26) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_MIN);

            hello = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            hello.createNotificationChannel(channel);

            NotificationChannel channel2 = new NotificationChannel("lulu",
                    "huhe",
                    NotificationManager.IMPORTANCE_HIGH);

            hi = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            hi.createNotificationChannel(channel2);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_baseline_home_24).build();

            startForeground(1337, notification);
        }

        sharedpreferences = getSharedPreferences(SHARED_PREFS, this.MODE_PRIVATE);
        userinfo = sharedpreferences.getString("USER_INFO", null);
        usertype = sharedpreferences.getString("USER_TYPE", null);

        String[] user_info = userinfo.split("--", 10);;
        first_name = user_info[0];
        last_name = user_info[1];
        user_name = user_info[2];
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mToastRunnable.run();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        mHandler.removeCallbacks(mToastRunnable);
        stopForeground(true);
        stopSelf();
        super.onDestroy ();
    }

    private Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            //Toast.makeText(StartOnBootService.this, packageName, Toast.LENGTH_SHORT).show();
            new BackgroundWorkerAlert(StartOnBootService.this,CHANNEL_ID,hi).execute(url1,user_name);
            mHandler.postDelayed(this, 5000);
        }
    };

}

class BackgroundWorkerAlert extends AsyncTask<String,Void,String> {
    Context context;
    String CHANNEL_ID;
    NotificationManager NM;

    BackgroundWorkerAlert(Context ctx, String CID, NotificationManager NM){
        this.context = ctx;
        this.CHANNEL_ID = CID;
        this.NM = NM;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = (String)params[0];
        String username = (String)params[1];
        String result = "";

        try {

            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter bufferedWriter = new OutputStreamWriter(outputStream, "UTF-8");

            String post_data = "";
            post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("NON")) {

        } else {
            String[][] array = new Gson().fromJson(result, String[][].class);
            String description = null;
            if (array[0][1].equals("chinese")) {
                description = "Order #"+array[0][0]+" is ready! Please head to Chinese Cuisine to collect it.";
            } else if (array[0][1].equals("western")) {
                description = "Order #"+array[0][0]+" is ready! Please head to Western Cuisine to collect it.";
            } else if (array[0][1].equals("arabic")) {
                description = "Order #"+array[0][0]+" is ready! Please head to Arabic Cuisine to collect it.";
            } else if (array[0][1].equals("n_indian")) {
                description = "Order #"+array[0][0]+" is ready! Please head to North Indian Cuisine to collect it.";
            } else if (array[0][1].equals("japanese")) {
                description = "Order #"+array[0][0]+" is ready! Please head to Japanese Cuisine to collect it.";
            } else if (array[0][1].equals("mamak")) {
                description = "Order #"+array[0][0]+" is ready! Please head to Mamak Cuisine to collect it.";
            } else if (array[0][1].equals("cran")) {
                description = "Order #"+array[0][0]+" is ready! Please head to Chicken Rice and Noodles Cuisine to collect it.";
            }

            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            Notification notification = new NotificationCompat.Builder(context, "lulu")
                    .setContentTitle("Your Food Is Ready!")
                    .setContentText(description)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_baseline_local_dining_24)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(description))
                    .setAutoCancel(true).build();

            NM.notify(Integer.parseInt(array[0][0]),notification);

            new BackgroundWorkerUpdateAlert(context).execute("http://192.168.137.1/codes/updateAlert.php",array[0][2],array[0][0],array[0][1]);

        }

    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}

class BackgroundWorkerUpdateAlert extends AsyncTask<String,Void,String> {
    Context context;

    BackgroundWorkerUpdateAlert(Context ctx){
        this.context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = (String)params[0];
        String username = (String)params[1];
        String orderid = (String)params[2];
        String cuisine = (String)params[3];
        String result = "";

        try {

            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter bufferedWriter = new OutputStreamWriter(outputStream, "UTF-8");

            String post_data = "";
            post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                    + URLEncoder.encode("orderid", "UTF-8") + "=" + URLEncoder.encode(orderid, "UTF-8")+ "&"
                    + URLEncoder.encode("cuisine", "UTF-8") + "=" + URLEncoder.encode(cuisine, "UTF-8");

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onPostExecute(String result) {

    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}