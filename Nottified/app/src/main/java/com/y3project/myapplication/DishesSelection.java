package com.y3project.myapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
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

public class DishesSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            cuisine = user_info[3];
        }

        setContentView(R.layout.activity_dishes_selection);
        RecyclerView courseRV = findViewById(R.id.idRVCourse);

        String url = "http://192.168.137.1/codes/cuisineDishes.php";
        String result = "";

        ArrayList<CourseModel> courseModelArrayList = new ArrayList<CourseModel>();

        BackgroundWorkerDish backgroundWorker = new BackgroundWorkerDish(DishesSelection.this,courseModelArrayList,courseRV,value);
        backgroundWorker.execute(url,cuisine);

        ImageView backButton = (ImageView)findViewById(R.id.imageView23);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

class BackgroundWorkerDish extends AsyncTask<String,Void,String> {
    Context context;
    ArrayList<CourseModel> courseModelArrayList;
    RecyclerView courseRV;
    String userInfo;

    BackgroundWorkerDish(Context ctx, ArrayList<CourseModel> cMAL, RecyclerView cRV, String userInfo){
        this.context = ctx;
        this.courseModelArrayList = cMAL;
        this.courseRV = cRV;
        this.userInfo = userInfo;
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

        String[][] array = new Gson().fromJson(result, String[][].class);
        for (int i = 0; i < array.length; i++) {

            if (array[i][3].equals("extra")){
                courseModelArrayList.add(new CourseModel(array[i][1],"RM "+array[i][2], R.drawable.extra, array[i][0]));
            } else if (array[i][3].equals("drink")) {
                courseModelArrayList.add(new CourseModel(array[i][1],"RM "+array[i][2], R.drawable.drink, array[i][0]));
            } else {
                courseModelArrayList.add(new CourseModel(array[i][1],"RM "+array[i][2], R.drawable.dish, array[i][0]));
            }
        }

        // we are initializing our adapter class and passing our arraylist to it.
        CourseAdapter courseAdapter = new CourseAdapter(context, courseModelArrayList, userInfo);

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
