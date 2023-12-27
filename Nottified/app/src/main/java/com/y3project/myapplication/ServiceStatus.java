package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.google.gson.Gson;

import org.w3c.dom.Text;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ServiceStatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_status);

        String first_name = "User";
        String last_name = "";
        String user_name = "abcde5";
        String location = "";
        String value = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("user_info");

            String[] user_info = value.split("--", 10);;
            first_name = user_info[0];
            last_name = user_info[1];
            user_name = user_info[2];
            if (user_info.length > 4){
                location= user_info[4];
            } else {
                location= user_info[3];
            }

        }

        HalfGauge gauge = findViewById(R.id.halfGauge);
        Range range1 = new Range();
        range1.setColor(Color.parseColor("#62f40c"));
        range1.setFrom(0);
        range1.setTo(7);

        Range range2 = new Range();
        range2.setColor(Color.parseColor("#ff6d1f"));
        range2.setFrom(7);
        range2.setTo(14);

        Range range3 = new Range();
        range3.setColor(Color.parseColor("#fe2712"));
        range3.setFrom(14);
        range3.setTo(21);

        gauge.addRange(range1);
        gauge.addRange(range2);
        gauge.addRange(range3);

        gauge.setMinValue(0);
        gauge.setMaxValue(21);
        gauge.setMinValueTextColor(Color.parseColor("#FFFFFF"));
        gauge.setMaxValueTextColor(Color.parseColor("#FFFFFF"));
        gauge.setValueColor(Color.parseColor("#FFFFFF"));

        String url = "http://192.168.137.1/codes/getServiceStatus.php";
        String result = "";

        TextView pplNum = findViewById(R.id.textView55);
        TextView waitTime = findViewById(R.id.textView41);
        CardView closed = findViewById(R.id.closed_card);
        CardView ori = findViewById(R.id.cardView4);


        BackgroundWorkerServiceStatus backgroundWorker = new BackgroundWorkerServiceStatus(ServiceStatus.this, pplNum, waitTime, gauge, closed, ori);
        backgroundWorker.execute(url,location);

        TextView locName = findViewById(R.id.textView120);
        TextView closeText = findViewById(R.id.textView121);
        if (location.contains("mail")){
            locName.setText("Mail Room");
            closeText.setText("Mail Room");
        } else if (location.contains("finance")){
            locName.setText("Finance Office");
            closeText.setText("Finance Office");
        } else if (location.contains("security")){
            locName.setText("Security Office");
            closeText.setText("Security Office");
        } else if (location.contains("sa")){
            locName.setText("SA Office");
            closeText.setText("SA Office");
        }

        ImageView backButton = (ImageView)findViewById(R.id.imageView35);
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

class BackgroundWorkerServiceStatus extends AsyncTask<String,Void,String> {
    Context context;
    TextView peopleNum;
    TextView waitTime;
    HalfGauge gauge2;
    CardView close;
    CardView original;

    BackgroundWorkerServiceStatus(Context ctx, TextView pN, TextView wT, HalfGauge gg, CardView close, CardView original){
        this.context = ctx;
        this.peopleNum = pN;
        this.waitTime = wT;
        this.gauge2 = gg;
        this.close = close;
        this.original = original;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = (String)params[0];
        String location = (String)params[1];
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
            post_data = URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8");

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
        String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        Date currentTime = null;
        Date opening = null;
        Date closing = null;
        try {
            currentTime = new SimpleDateFormat("HH:mm:ss").parse(time);
            opening = new SimpleDateFormat("HH:mm:ss").parse(array[0][1]);
            closing = new SimpleDateFormat("HH:mm:ss").parse(array[0][2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (currentTime.before(opening) || currentTime.after(closing)){
            close.setVisibility(View.VISIBLE);
            original.setVisibility(View.GONE);
            //Toast.makeText(context, new SimpleDateFormat("HH:mm:ss").format(currentTime), Toast.LENGTH_SHORT).show();
        } else {
            gauge2.setValue(Integer.valueOf(array[0][0]));
            if(Integer.valueOf(array[0][0])<5){
                peopleNum.setText(Html.fromHtml("&lt; 5"));
                waitTime.setText("Around 1 - 5 minutes.");
            } else if (Integer.valueOf(array[0][0])<10){
                peopleNum.setText("5 - 10");
                waitTime.setText("Around 5 - 10 minutes.");
            } else if (Integer.valueOf(array[0][0])<15){
                peopleNum.setText("10 - 15");
                waitTime.setText("Around 10 - 20 minutes.");
            } else if (Integer.valueOf(array[0][0])<20){
                peopleNum.setText("15 - 20");
                waitTime.setText("Around 20 minutes.");
            } else if (Integer.valueOf(array[0][0])>=20){
                peopleNum.setText(Html.fromHtml("&gt; 20"));
                waitTime.setText("More than 30 minutes.");
            }
        }

    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}