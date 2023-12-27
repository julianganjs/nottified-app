package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class StaffOrderList extends AppCompatActivity {

    public static String max_id = "";
    public static String cuisine = "";
    public static String value = "";

    private static Context mContext;

    public static Activity mAct;

    public static Context getAppContext(){
        return mContext;
    }

    @Override
    public void onBackPressed() {
        stopService(new Intent(this, CuisineStaffService.class));
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_order_list);
        mContext = this.getApplicationContext();
        mAct = this;

        String first_name = "User";
        String last_name = "";
        String user_name = "abcde5";
        String role = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("user_info");

            String[] user_info = value.split("--", 10);;
            first_name = user_info[0];
            last_name = user_info[1];
            user_name = user_info[2];
            role = user_info[3];
        }

        if (role.contains("chinese")) {
            cuisine = "chinese";
        } else if (role.contains("western")) {
            cuisine = "western";
        } else if (role.contains("arabic")) {
            cuisine = "arabic";
        } else if (role.contains("n_indian")) {
            cuisine = "n_indian";
        } else if (role.contains("japanese")) {
            cuisine = "japanese";
        } else if (role.contains("mamak")) {
            cuisine = "mamak";
        } else if (role.contains("cran")) {
            cuisine = "cran";
        }

        RecyclerView courseRV = findViewById(R.id.idRVCourse);

        String url = "http://192.168.137.1/codes/getStaffOrderList.php";
        String result = "";

        ArrayList<StaffOrderModel> courseModelArrayList = new ArrayList<StaffOrderModel>();

        BackgroundWorkerStaffOrderList backgroundWorker = new BackgroundWorkerStaffOrderList(StaffOrderList.this,courseModelArrayList,courseRV);
        backgroundWorker.execute(url,cuisine);

        ImageView backButton = (ImageView)findViewById(R.id.imageView23);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SwipeRefreshLayout swipeLayout;

        //Getting SwipeContainerLayout
        swipeLayout = findViewById(R.id.swipe_container);;

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stopService(new Intent(StaffOrderList.this, CuisineStaffService.class));
                recreate();
                swipeLayout.setRefreshing(false);
            }
        });

        Intent serviceIntent = new Intent(this, CuisineStaffService.class);
        this.startService(serviceIntent);
    }
}

class BackgroundWorkerStaffOrderList extends AsyncTask<String,Void,String> {
    Context context;
    ArrayList<StaffOrderModel> courseModelArrayList;
    RecyclerView courseRV;

    BackgroundWorkerStaffOrderList(Context ctx, ArrayList<StaffOrderModel> cMAL, RecyclerView cRV){
        this.context = ctx;
        this.courseModelArrayList = cMAL;
        this.courseRV = cRV;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = (String)params[0];
        String cuisine = (String)params[1];
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
            post_data = URLEncoder.encode("cuisine", "UTF-8") + "=" + URLEncoder.encode(cuisine, "UTF-8");

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
        if (result.equals("EMPTY")) {
            StaffOrderList.max_id = "0";
        } else {
            String[][] array = new Gson().fromJson(result, String[][].class);
            StaffOrderList.max_id = array[array.length-1][0];
            for (int i = 0; i < array.length; i++) {
                courseModelArrayList.add(new StaffOrderModel(array[i][0],array[i][4],array[i][5],array[i][2]+" "+array[i][3],array[i][6],array[i][9],array[i][8]));
            }
        }
        // we are initializing our adapter class and passing our arraylist to it.
        StaffOrderAdapter courseAdapter = new StaffOrderAdapter(context, courseModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(courseAdapter);
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

class BackgroundWorkerUpdateStaff extends AsyncTask<String,Void,String> {
    Context context;

    BackgroundWorkerUpdateStaff(Context ctx){
        this.context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = (String)params[0];
        String cuisine = (String)params[1];
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
            post_data = URLEncoder.encode("cuisine", "UTF-8") + "=" + URLEncoder.encode(cuisine, "UTF-8");

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
        if (result.equals("EMPTY")) {
            StaffOrderList.max_id = "0";
        } else {
            String[][] array = new Gson().fromJson(result, String[][].class);
            if(Integer.parseInt(array[0][0]) > Integer.parseInt(StaffOrderList.max_id))
            {
                context.stopService(new Intent(context, CuisineStaffService.class));
                Intent intent = new Intent(StaffOrderList.getAppContext(), StaffOrderList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("user_info",StaffOrderList.value);
                StaffOrderList.getAppContext().startActivity(intent);
                StaffOrderList.mAct.finish();
            }
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

