package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class EntrySession extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_session);

        String first_name = "User";
        String last_name = "";
        String user_name = "abcde5";
        String value = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("user_info");

            String[] user_info = value.split("--", 10);;
            first_name = user_info[0];
            last_name = user_info[1];
            user_name = user_info[2];
        }

        TextView entry_time = findViewById(R.id.textView97);
        TextView exit_time = findViewById(R.id.textView41);

        TextView regi_type = findViewById(R.id.textView102);
        TextView start_date = findViewById(R.id.textView100);
        TextView end_date = findViewById(R.id.textView104);
        TextView plate_no = findViewById(R.id.textView106);
        TextView vehicle_info = findViewById(R.id.textView108);

        new BackgroundWorkerEntryInfo(EntrySession.this,entry_time,exit_time).execute("http://192.168.137.1/codes/getEntryInfo.php",user_name);
        new BackgroundWorkerPermitInfo(EntrySession.this,regi_type,start_date,end_date,plate_no,vehicle_info).execute("http://192.168.137.1/codes/getPermitInfo.php",user_name);

        ImageView backButton = (ImageView)findViewById(R.id.imageView33);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

class BackgroundWorkerEntryInfo extends AsyncTask<String,Void,String> {
    Context context;
    TextView EntryTime;
    TextView ExitTime;

    BackgroundWorkerEntryInfo(Context ctx, TextView entry, TextView exit){
        this.context = ctx;
        this.EntryTime = entry;
        this.ExitTime = exit;
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
        if (result.equals("NA")) {
            EntryTime.setText("N/A");
            ExitTime.setText("N/A");
        } else {
            String[][] array = new Gson().fromJson(result, String[][].class);
            EntryTime.setText(array[0][0]);
            if (array[0][1].equals("0000-00-00 00:00:00")) {
                ExitTime.setText("N/A");
            }else {
                ExitTime.setText(array[0][1]);
            }
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}

class BackgroundWorkerPermitInfo extends AsyncTask<String,Void,String> {
    Context context;
    TextView RegiType;
    TextView StartDate;
    TextView EndDate;
    TextView PlateNum;
    TextView CarInfo;

    BackgroundWorkerPermitInfo(Context ctx, TextView regi, TextView start, TextView end, TextView num, TextView carinfo){
        this.context = ctx;
        this.RegiType = regi;
        this.StartDate = start;
        this.EndDate = end;
        this.PlateNum = num;
        this.CarInfo = carinfo;
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
        if (result.equals("NA")) {

        } else {
            String[][] array = new Gson().fromJson(result, String[][].class);
            RegiType.setText(array[0][0]);
            StartDate.setText(array[0][1]);
            EndDate.setText(array[0][2]);
            PlateNum.setText(array[0][3]);
            CarInfo.setText(array[0][4]);
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}