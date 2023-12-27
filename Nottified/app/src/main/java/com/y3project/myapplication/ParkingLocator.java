package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.util.ArrayList;

public class ParkingLocator extends AppCompatActivity {

    double latitude;
    double longitude;

    public static String type = "";

    public static TextView Jno;
    public static TextView Bno;
    public static TextView Cno;
    public static TextView Jcap;
    public static TextView Bcap;
    public static TextView Ccap;
    public static ConstraintLayout Jlay;
    public static ConstraintLayout Blay;
    public static ConstraintLayout Clay;

    public static TextView getJno(){
        return Jno;
    }
    public static TextView getBno(){
        return Bno;
    }
    public static TextView getCno(){
        return Cno;
    }
    public static TextView getJcap(){
        return Jcap;
    }
    public static TextView getBcap(){
        return Bcap;
    }
    public static TextView getCcap(){
        return Ccap;
    }
    public static ConstraintLayout getJlay(){
        return Jlay;
    }
    public static ConstraintLayout getBlay(){
        return Blay;
    }
    public static ConstraintLayout getClay(){
        return Clay;
    }

    @Override
    public void onBackPressed() {
        stopService(new Intent(this, ParkingLocatorService.class));
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_locator);

        String first_name = "User";
        String last_name = "";
        String user_name = "abcde5";
        String role = "";
        String value = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("user_info");

            String[] user_info = value.split("--", 10);;
            first_name = user_info[0];
            last_name = user_info[1];
            user_name = user_info[2];
            if (user_info.length > 3){
                ParkingLocator.type="staff";
            } else {
                ParkingLocator.type="student";
            }
        }

        String url = "http://192.168.137.1/codes/getParkingCount.php";
        String result = "";

        TextView b_no = findViewById(R.id.textView58);
        TextView j_no = findViewById(R.id.textView582);
        TextView cafe_no = findViewById(R.id.textView5822);

        Bno = b_no;
        Jno = j_no;
        Cno = cafe_no;

        TextView b_capacity = findViewById(R.id.textView69);
        TextView j_capacity = findViewById(R.id.textView691);
        TextView cafe_capacity = findViewById(R.id.textView692);

        Bcap = b_capacity;
        Jcap = j_capacity;
        Ccap = cafe_capacity;

        ConstraintLayout b_card = findViewById(R.id.b_layout);
        ConstraintLayout j_card = findViewById(R.id.j_layout);
        ConstraintLayout cafe_card = findViewById(R.id.cafe_layout);

        Blay = b_card;
        Jlay = j_card;
        Clay = cafe_card;

        BackgroundWorkerParkingNum backgroundWorker = new BackgroundWorkerParkingNum(ParkingLocator.this, b_no, j_no, cafe_no, b_capacity, j_capacity, cafe_capacity, b_card, j_card, cafe_card);
        backgroundWorker.execute(url,ParkingLocator.type);

        ConstraintLayout zone1_btn = findViewById(R.id.b_layout);
        zone1_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Block B Parking
                latitude = 2.945929;
                longitude = 101.873416;
                openMaps(latitude, longitude);
            }
        });

        ConstraintLayout zone2_btn = findViewById(R.id.j_layout);
        zone2_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // J-Block Parking
                latitude = 2.946465;
                longitude = 101.876775;
                openMaps(latitude, longitude);
            }
        });

        ConstraintLayout zone3_btn = findViewById(R.id.cafe_layout);
        zone3_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Cafeteria Parking
                latitude = 2.943584;
                longitude = 101.876019;
                openMaps(latitude, longitude);
            }
        });

        SwipeRefreshLayout swipeLayout;

        //Getting SwipeContainerLayout
        swipeLayout = findViewById(R.id.swipe_container);;

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stopService(new Intent(ParkingLocator.this, ParkingLocatorService.class));
                recreate();
                swipeLayout.setRefreshing(false);
            }
        });

        ImageView backButton = (ImageView)findViewById(R.id.imageView14);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent serviceIntent = new Intent(this, ParkingLocatorService.class);
        this.startService(serviceIntent);
    }

    public void openMaps(double latitude, double longitude){

        Uri IntentUri = Uri.parse("google.navigation:q="+latitude+","+longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, IntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }


}

class BackgroundWorkerParkingNum extends AsyncTask<String,Void,String> {
    Context context;
    TextView B_no;
    TextView J_no;
    TextView Cafe_no;
    TextView B_capacity;
    TextView J_capacity;
    TextView Cafe_capacity;
    ConstraintLayout B_card;
    ConstraintLayout J_card;
    ConstraintLayout Cafe_card;

    BackgroundWorkerParkingNum(Context ctx, TextView B_no, TextView J_no, TextView Cafe_no, TextView B_capacity, TextView J_capacity, TextView Cafe_capacity, ConstraintLayout B_card, ConstraintLayout J_card,ConstraintLayout Cafe_card){
        this.context = ctx;
        this.B_no = B_no;
        this.J_no = J_no;
        this.Cafe_no = Cafe_no;
        this.B_capacity = B_capacity;
        this.J_capacity = J_capacity;
        this.Cafe_capacity = Cafe_capacity;
        this.B_card = B_card;
        this.J_card = J_card;
        this.Cafe_card = Cafe_card;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = (String)params[0];
        String type = (String)params[1];
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
            post_data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");

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

        } else {
            String[][] array = new Gson().fromJson(result, String[][].class);

            if(Integer.parseInt(array[0][0]) < 10) {
                J_card.setBackgroundResource(R.drawable.high_grad);
                J_capacity.setText("High Capacity");
            } else if (Integer.parseInt(array[0][0]) <= 25) {
                J_card.setBackgroundResource(R.drawable.medium_grad);
                J_capacity.setText("Medium Capacity");
            } else if (Integer.parseInt(array[0][0]) > 25) {
                J_card.setBackgroundResource(R.drawable.low_grad);
                J_capacity.setText("Low Capacity");
            }

            if(Integer.parseInt(array[0][1]) < 10) {
                B_card.setBackgroundResource(R.drawable.high_grad);
                B_capacity.setText("High Capacity");
            } else if (Integer.parseInt(array[0][1]) <= 25) {
                B_card.setBackgroundResource(R.drawable.medium_grad);
                B_capacity.setText("Medium Capacity");
            } else if (Integer.parseInt(array[0][1]) > 25) {
                B_card.setBackgroundResource(R.drawable.low_grad);
                B_capacity.setText("Low Capacity");
            }

            if(Integer.parseInt(array[0][2]) < 10) {
                Cafe_card.setBackgroundResource(R.drawable.high_grad);
                Cafe_capacity.setText("High Capacity");
            } else if (Integer.parseInt(array[0][2]) <= 25) {
                Cafe_card.setBackgroundResource(R.drawable.medium_grad);
                Cafe_capacity.setText("Medium Capacity");
            } else if (Integer.parseInt(array[0][2]) > 25) {
                Cafe_card.setBackgroundResource(R.drawable.low_grad);
                Cafe_capacity.setText("Low Capacity");
            }

            J_no.setText(array[0][0]);
            B_no.setText(array[0][1]);
            Cafe_no.setText(array[0][2]);
        }

    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

