package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DecimalFormat;

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

public class OrderPlacing extends AppCompatActivity {
    DecimalFormat df=new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placing);

        final int[] order_quantity = {1};
        final double[] total_price = {0.00};
        final double[] actual_price = {0.00};


        String first_name = "User";
        String last_name = "";
        String user_name = "abcde5";
        String cuisine = "";
        String dish_no = "";

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
        }

        TextView Title=(TextView)findViewById(R.id.textView35);
        TextView Description=(TextView)findViewById(R.id.textView34);
        Button Button_Price=(Button)findViewById(R.id.price_button);
        ImageView top_img=(ImageView)findViewById(R.id.imageView10);
        ConstraintLayout clayout = (ConstraintLayout)findViewById(R.id.CLayout);

        String url = "http://192.168.137.1/codes/dishDisplay.php";
        BackgroundWorkerOrderPlacing backgroundWorker = new BackgroundWorkerOrderPlacing(OrderPlacing.this, Title, Description, Button_Price, top_img, total_price, actual_price, clayout);
        backgroundWorker.execute(url,dish_no);

        TextView quantity_no=(TextView)findViewById(R.id.textView36);
        Button plusButton = (Button)findViewById(R.id.minus_button);
        Button minusButton = (Button)findViewById(R.id.plus_button);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_quantity[0] = order_quantity[0] +1;
                total_price[0] = total_price[0] + actual_price[0];
                quantity_no.setText(Html.fromHtml(String.valueOf(order_quantity[0])));
                Button_Price.setText("RM "+ String.valueOf(df.format(total_price[0])));
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(order_quantity[0] != 1) {
                    order_quantity[0] = order_quantity[0] - 1;
                    total_price[0] = total_price[0] - actual_price[0];
                    quantity_no.setText(Html.fromHtml(String.valueOf(order_quantity[0])));
                    Button_Price.setText("RM "+ String.valueOf(df.format(total_price[0])));
                }
            }
        });

        ImageView backButton = (ImageView)findViewById(R.id.imageView13);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        String finalValue = value;
        Button_Price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder1.setTitle("Order Confirmation");
                builder1.setMessage(Html.fromHtml("The total price of your order is <b>RM "+String.valueOf(df.format(total_price[0]))+"</b>. Would you like to head to the payment screen (QR Pay Only)?"))
                        .setCancelable(true)
                        .setPositiveButton("Take me there!", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(OrderPlacing.this, PaymentScreen.class);
                                String finalInfo = finalValue.concat("--"+Title.getText().toString()+"--"+String.valueOf(order_quantity[0])+"--"+String.valueOf(df.format(total_price[0])));
                                intent.putExtra("user_info", finalInfo);
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = builder1.create();
                alert.show();
            }
        });
    }
}

class BackgroundWorkerOrderPlacing extends AsyncTask<String,Void,String> {
    Context context;
    TextView title;
    TextView description;
    Button button_price;
    ImageView img;
    ConstraintLayout cLayout;
    final double[] priceTotal;
    final double[] actualPrice;

    BackgroundWorkerOrderPlacing(Context ctx, TextView title, TextView description, Button button_price, ImageView img, final double[] priceTotal, final double[] actualPrice, ConstraintLayout clayout){
        this.context = ctx;
        this.title = title;
        this.description = description;
        this.button_price = button_price;
        this.img = img;
        this.priceTotal = priceTotal;
        this.actualPrice = actualPrice;
        this.cLayout = clayout;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = (String)params[0];
        String id = (String)params[1];
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
            post_data = URLEncoder.encode("dish_id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

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
        title.setText(array[0][1]);
        button_price.setText("RM "+array[0][2]);

        priceTotal[0] = priceTotal[0] + Double.parseDouble(array[0][2]);
        actualPrice[0] = Double.parseDouble(array[0][2]);

        if (array[0][4].equals("chinese")){
            description.setText("Would you like to order this dish from Chinese cuisine?");
            img.setImageResource(R.drawable.chinese_food_lead);
            cLayout.setBackgroundResource(R.drawable.chinese_op_bg);
        } else if (array[0][4].equals("western")) {
            description.setText("Would you like to order this dish from Western cuisine?");
            img.setImageResource(R.drawable.western);
            cLayout.setBackgroundResource(R.drawable.red_op_bg);
        } else if (array[0][4].equals("arabic")) {
            description.setText("Would you like to order this dish from Arabic cuisine?");
            img.setImageResource(R.drawable.arabic);
            cLayout.setBackgroundResource(R.drawable.blue_op_bg);
        } else if (array[0][4].equals("n_indian")) {
            description.setText("Would you like to order this dish from North Indian cuisine?");
            img.setImageResource(R.drawable.northindian);
            cLayout.setBackgroundResource(R.drawable.yellow_op_bg);
        } else if (array[0][4].equals("japanese")) {
            description.setText("Would you like to order this dish from Japanese cuisine?");
            img.setImageResource(R.drawable.japanese);
            cLayout.setBackgroundResource(R.drawable.red_op_bg);
        } else if (array[0][4].equals("mamak")) {
            description.setText("Would you like to order this dish from Mamak cuisine?");
            img.setImageResource(R.drawable.mamak);
            cLayout.setBackgroundResource(R.drawable.blue_op_bg);
        } else if (array[0][4].equals("cran")) {
            description.setText("Would you like to order this dish from Chicken Rice and Noodles cuisine?");
            img.setImageResource(R.drawable.chickenrice);
            cLayout.setBackgroundResource(R.drawable.chinese_op_bg);
        }

    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}