package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
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
import java.util.ArrayList;

public class PaymentScreen extends AppCompatActivity {
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);

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

        TextView description=(TextView)findViewById(R.id.textView38);
        description.setText(Html.fromHtml("Screenshot the QR code, or scan it using another device!<br>Just reminding, your total bill is <b>RM "+total_price+"</b>."));

        TextView QR_ori=(TextView)findViewById(R.id.textView67);
        if (cuisine.equals("chinese")){
            QR_ori.setText("SAMPLE\nCHINESE QR CODE");
        } else if (cuisine.equals("western")) {
            QR_ori.setText("SAMPLE\nWESTERN QR CODE");
        } else if (cuisine.equals("arabic")) {
            QR_ori.setText("SAMPLE\nARABIC QR CODE");
        } else if (cuisine.equals("n_indian")) {
            QR_ori.setText("SAMPLE\nN INDIAN QR CODE");
        } else if (cuisine.equals("japanese")) {
            QR_ori.setText("SAMPLE\nJAPANESE QR CODE");
        } else if (cuisine.equals("mamak")) {
            QR_ori.setText("SAMPLE\nMAMAK QR CODE");
        } else if (cuisine.equals("cran")) {
            QR_ori.setText("SAMPLE\nCR AND NOODLES QR CODE");
        }

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        Button paid_button=(Button)findViewById(R.id.login2);
        String finalUser_name = user_name;
        String finalDish_name = dish_name;
        String finalOrder_quantity = order_quantity;
        String finalTotal_price = total_price;
        String finalCuisine = cuisine;
        String finalValue = value;
        String finalFirst_name = first_name;
        String finalLast_name = last_name;
        paid_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder1.setTitle("Payment Confirmation");
                builder1.setMessage(Html.fromHtml("Are you sure you've paid for your food? There's no going back..."))
                        .setCancelable(true)
                        .setPositiveButton("Yeap!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String url = "http://192.168.137.1/codes/placeOrder.php";

                                BackgroundWorkerPlaceOrder backgroundWorker = new BackgroundWorkerPlaceOrder(PaymentScreen.this, finalValue);
                                backgroundWorker.execute(url, finalUser_name, finalDish_name, finalOrder_quantity, finalTotal_price, finalCuisine, finalFirst_name, finalLast_name);

                            }
                        });
                AlertDialog alert = builder1.create();
                alert.show();
            }
        });


        ImageView backButton = (ImageView)findViewById(R.id.imageView20);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

class BackgroundWorkerPlaceOrder extends AsyncTask<String,Void,String> {
    Context context;
    String Value;

    BackgroundWorkerPlaceOrder(Context ctx, String Value){
        this.context = ctx;
        this.Value = Value;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = (String)params[0];
        String username = (String)params[1];
        String dishname = (String)params[2];
        String quantity = (String)params[3];
        String totalprice = (String)params[4];
        String cuisine = (String)params[5];
        String firstname = (String)params[6];
        String lastname = (String)params[7];

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
            post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                    + URLEncoder.encode("dish_name", "UTF-8") + "=" + URLEncoder.encode(dishname, "UTF-8") + "&"
                    + URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(quantity, "UTF-8") + "&"
                    + URLEncoder.encode("total_price", "UTF-8") + "=" + URLEncoder.encode(totalprice, "UTF-8") + "&"
                    + URLEncoder.encode("cuisine", "UTF-8") + "=" + URLEncoder.encode(cuisine, "UTF-8") + "&"
                    + URLEncoder.encode("firstname", "UTF-8") + "=" + URLEncoder.encode(firstname, "UTF-8") + "&"
                    + URLEncoder.encode("lastname", "UTF-8") + "=" + URLEncoder.encode(lastname, "UTF-8");

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
        if(result.equals("FAILED")) {

        } else if (result.equals("Order Received.")) {
            Intent intent = new Intent(context, OrderConfirm.class);
            intent.putExtra("user_info",Value);
            context.startActivity(intent);
            ((Activity)context).finish();
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}