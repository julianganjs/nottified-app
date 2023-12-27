package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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

public class VehicleEntryLogs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_entry_logs);

        RecyclerView courseRV = findViewById(R.id.idRVCourse);

        String url = "http://192.168.137.1/codes/getEntryLogs.php";
        String result = "";

        ArrayList<EntryLogModel> courseModelArrayList = new ArrayList<EntryLogModel>();

        BackgroundWorkerEntryLogs backgroundWorker = new BackgroundWorkerEntryLogs(VehicleEntryLogs.this,courseModelArrayList,courseRV);
        backgroundWorker.execute(url);

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
                recreate();
                swipeLayout.setRefreshing(false);
            }
        });
    }
}

class BackgroundWorkerEntryLogs extends AsyncTask<String,Void,String> {
    Context context;
    ArrayList<EntryLogModel> courseModelArrayList;
    RecyclerView courseRV;

    BackgroundWorkerEntryLogs(Context ctx, ArrayList<EntryLogModel> cMAL, RecyclerView cRV){
        this.context = ctx;
        this.courseModelArrayList = cMAL;
        this.courseRV = cRV;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = (String)params[0];
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

        if (result.equals("EMPTY")) {}
        else{

            String[][] array = new Gson().fromJson(result, String[][].class);
            for (int i = 0; i < array.length; i++) {

                courseModelArrayList.add(new EntryLogModel(array[i][0],array[i][1],array[i][2],array[i][3],array[i][4]));
            }

            // we are initializing our adapter class and passing our arraylist to it.
            EntryLogAdapter courseAdapter = new EntryLogAdapter(context, courseModelArrayList);

            // below line is for setting a layout manager for our recycler view.
            // here we are creating vertical list so we will provide orientation as vertical
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

            // in below two lines we are setting layoutmanager and adapter to our recycler view.
            courseRV.setLayoutManager(linearLayoutManager);
            courseRV.setAdapter(courseAdapter);
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}