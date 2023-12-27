package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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


public class OrderConfirm extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);

        String first_name = "User";
        String last_name = "";
        String user_name = "abcde5";
        String cuisine = "";
        String dish_no = "";
        String dish_name = "";
        String order_quantity = "";
        String total_price = "";

        String value = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("user_info");

            String[] user_info = value.split("--", 10);;
            first_name = user_info[0];
            last_name = user_info[1];
            user_name = user_info[2];
            cuisine = user_info[3];
            dish_no = user_info[4];
            dish_name = user_info[5];
            order_quantity = user_info[6];
            total_price = user_info[7];
        }

        String url = "http://192.168.137.1/codes/getOrderID.php";

        TextView orderno = (TextView)findViewById(R.id.textView43);

        BackgroundWorkerGetOrderID backgroundWorker = new BackgroundWorkerGetOrderID(OrderConfirm.this, orderno);
        backgroundWorker.execute(url,user_name);

        String finalFirst_name = first_name;
        String finalLast_name = last_name;
        String finalUser_name = user_name;
        String finalCuisine = cuisine;

        Button samecuisine = (Button)findViewById(R.id.login4);

        samecuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderConfirm.this, CuisineSelection.class);
                intent.putExtra("user_info", finalFirst_name +"--"+ finalLast_name +"--"+ finalUser_name);
                startActivity(intent);
            }
        });

        Button differentcuisine = (Button)findViewById(R.id.login5);

        differentcuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderConfirm.this, OrderViewer.class);
                intent.putExtra("user_info", finalFirst_name +"--"+ finalLast_name +"--"+ finalUser_name);
                startActivity(intent);
            }
        });

        Button backHome = (Button)findViewById(R.id.login6);

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderConfirm.this, MainPortal.class);
                intent.putExtra("user_info", finalFirst_name +"--"+ finalLast_name +"--"+ finalUser_name);
                startActivity(intent);
            }
        });

    }
}

class BackgroundWorkerGetOrderID extends AsyncTask<String,Void,String> {
    Context context;
    TextView ordernum;

    BackgroundWorkerGetOrderID(Context ctx, TextView ordernum){
        this.context = ctx;
        this.ordernum = ordernum;
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
        String[][] array = new Gson().fromJson(result, String[][].class);
        ordernum.setText(Html.fromHtml("<font color='#007ca7'>#</font><font color='#000000'>"+array[0][0]+"</font>"));
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}