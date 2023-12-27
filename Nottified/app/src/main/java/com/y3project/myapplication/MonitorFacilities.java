package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

public class MonitorFacilities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_facilities);

        String first_name = "User";
        String last_name = "";
        String user_name = "abcde5";
        String cuisine = "";
        String value = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("user_info");

            String[] user_info = value.split("--", 10);;
            first_name = user_info[0];
            last_name = user_info[1];
            user_name = user_info[2];
        }

        RecyclerView courseRV = findViewById(R.id.idRVCourse);

        String url = "http://192.168.137.1/codes/getRoomList.php";
        String result = "";

        ArrayList<RoomStatusModel> courseModelArrayList = new ArrayList<RoomStatusModel>();

        BackgroundWorkerFaciStatus backgroundWorker = new BackgroundWorkerFaciStatus(MonitorFacilities.this,courseModelArrayList,courseRV,value);
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

class BackgroundWorkerFaciStatus extends AsyncTask<String,Void,String> {
    Context context;
    ArrayList<RoomStatusModel> courseModelArrayList;
    RecyclerView courseRV;
    String userInfo;

    BackgroundWorkerFaciStatus(Context ctx, ArrayList<RoomStatusModel> cMAL, RecyclerView cRV, String userInfo){
        this.context = ctx;
        this.courseModelArrayList = cMAL;
        this.courseRV = cRV;
        this.userInfo = userInfo;
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

        if (result.equals("FAILED")) {
        }else {

            String[][] array = new Gson().fromJson(result, String[][].class);
            for (int i = 0; i < array.length; i++) {

                if (array[i][2].equals("1")) {
                    if (array[i][1].equals("1")) {
                        courseModelArrayList.add(new RoomStatusModel(array[i][0], R.drawable.price_btn_bg,R.drawable.price_btn_bg));
                    } else if (array[i][1].equals("0")) {
                        courseModelArrayList.add(new RoomStatusModel(array[i][0], R.drawable.price_btn_bg,R.drawable.high_grad));
                    }
                } else if (array[i][2].equals("0")) {
                    if (array[i][1].equals("1")) {
                        courseModelArrayList.add(new RoomStatusModel(array[i][0], R.drawable.high_grad,R.drawable.price_btn_bg));
                    } else if (array[i][1].equals("0")) {
                        courseModelArrayList.add(new RoomStatusModel(array[i][0],  R.drawable.high_grad,R.drawable.high_grad));
                    }
                }
            }

            // we are initializing our adapter class and passing our arraylist to it.
            RoomStatusAdapter courseAdapter = new RoomStatusAdapter(context, courseModelArrayList, userInfo);

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