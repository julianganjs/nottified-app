package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

public class OrderViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_viewer);

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

        RecyclerView courseRV = findViewById(R.id.idRVCourse);

        String url = "http://192.168.137.1/codes/getOrderList.php";
        String result = "";

        ArrayList<OrderListModel> courseModelArrayList = new ArrayList<OrderListModel>();
        ImageView missingOrderImage = (ImageView)findViewById(R.id.imageView25);
        TextView missingOrderText = (TextView)findViewById(R.id.textView51);

        BackgroundWorkerOrderViewer backgroundWorker = new BackgroundWorkerOrderViewer(OrderViewer.this,courseModelArrayList,courseRV,missingOrderImage,missingOrderText);
        backgroundWorker.execute(url,user_name);

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

class BackgroundWorkerOrderViewer extends AsyncTask<String,Void,String> {
    Context context;
    ArrayList<OrderListModel> courseModelArrayList;
    RecyclerView courseRV;
    ImageView missingImage;
    TextView missingText;

    BackgroundWorkerOrderViewer(Context ctx, ArrayList<OrderListModel> cMAL, RecyclerView cRV, ImageView missingImage, TextView missingText){
        this.context = ctx;
        this.courseModelArrayList = cMAL;
        this.courseRV = cRV;
        this.missingImage = missingImage;
        this.missingText = missingText;
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
        if (result.equals("EMPTY")) {
            missingImage.setImageResource(R.drawable.chef_confused);
            missingText.setText("Oops! Looks like you don't have any orders yet for today.");
        } else {
            String[][] array = new Gson().fromJson(result, String[][].class);
            for (int i = 0; i < array.length; i++) {

                if (array[i][3].equals("chinese")) {
                    if (array[i][4].equals("received")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Chinese Cuisine", array[i][0], "x" + array[i][2], "PREPARING ...", "#fd5602"));
                    } else if (array[i][4].contains("ready")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Chinese Cuisine", array[i][0], "x" + array[i][2], "READY TO COLLECT!", "#03dac5"));
                    } else if (array[i][4].equals("done")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Chinese Cuisine", array[i][0], "x" + array[i][2], "COLLECTED", "#000000"));
                    }
                } else if (array[i][3].equals("western")) {
                    if (array[i][4].equals("received")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Western Cuisine", array[i][0], "x" + array[i][2], "PREPARING ...", "#fd5602"));
                    } else if (array[i][4].contains("ready")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Western Cuisine", array[i][0], "x" + array[i][2], "READY TO COLLECT!", "#03dac5"));
                    } else if (array[i][4].equals("done")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Western Cuisine", array[i][0], "x" + array[i][2], "COLLECTED", "#000000"));
                    }
                } else if (array[i][3].equals("arabic")) {
                    if (array[i][4].equals("received")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Arabic Cuisine", array[i][0], "x" + array[i][2], "PREPARING ...", "#fd5602"));
                    } else if (array[i][4].contains("ready")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Arabic Cuisine", array[i][0], "x" + array[i][2], "READY TO COLLECT!", "#03dac5"));
                    } else if (array[i][4].equals("done")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Arabic Cuisine", array[i][0], "x" + array[i][2], "COLLECTED", "#000000"));
                    }
                } else if (array[i][3].equals("n_indian")) {
                    if (array[i][4].equals("received")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from North Indian Cuisine", array[i][0], "x" + array[i][2], "PREPARING ...", "#fd5602"));
                    } else if (array[i][4].contains("ready")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from North Indian Cuisine", array[i][0], "x" + array[i][2], "READY TO COLLECT!", "#03dac5"));
                    } else if (array[i][4].equals("done")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from North Indian Cuisine", array[i][0], "x" + array[i][2], "COLLECTED", "#000000"));
                    }
                } else if (array[i][3].equals("japanese")) {
                    if (array[i][4].equals("received")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Japanese Cuisine", array[i][0], "x" + array[i][2], "PREPARING ...", "#fd5602"));
                    } else if (array[i][4].contains("ready")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Japanese Cuisine", array[i][0], "x" + array[i][2], "READY TO COLLECT!", "#03dac5"));
                    } else if (array[i][4].equals("done")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Japanese Cuisine", array[i][0], "x" + array[i][2], "COLLECTED", "#000000"));
                    }
                } else if (array[i][3].equals("mamak")) {
                    if (array[i][4].equals("received")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Mamak Cuisine", array[i][0], "x" + array[i][2], "PREPARING ...", "#fd5602"));
                    } else if (array[i][4].contains("ready")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Mamak Cuisine", array[i][0], "x" + array[i][2], "READY TO COLLECT!", "#03dac5"));
                    } else if (array[i][4].equals("done")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Mamak Cuisine", array[i][0], "x" + array[i][2], "COLLECTED", "#000000"));
                    }
                } else if (array[i][3].equals("cran")) {
                    if (array[i][4].equals("received")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Chicken Rice and Noodles Cuisine", array[i][0], "x" + array[i][2], "PREPARING ...", "#fd5602"));
                    } else if (array[i][4].contains("ready")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Chicken Rice and Noodles Cuisine", array[i][0], "x" + array[i][2], "READY TO COLLECT!", "#03dac5"));
                    } else if (array[i][4].equals("done")) {
                        courseModelArrayList.add(new OrderListModel(array[i][1], "from Chicken Rice and Noodles Cuisine", array[i][0], "x" + array[i][2], "COLLECTED", "#000000"));
                    }
                }
            }
        }

        // we are initializing our adapter class and passing our arraylist to it.
        OrderListAdapter courseAdapter = new OrderListAdapter(context, courseModelArrayList);

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
