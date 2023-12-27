package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
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

import pl.droidsonroids.gif.GifImageView;

public class FacilityMain extends AppCompatActivity {

    public static String room_no = "";
    public static String ac_stat = "0";
    public static String light_stat = "0";

    public static TextView ACSTAT;
    public static TextView LSTAT;
    public static ImageView L;
    public static GifImageView AC;

    public static TextView getACSTAT(){
        return ACSTAT;
    }
    public static TextView getLSTAT(){
        return LSTAT;
    }
    public static ImageView getL(){
        return L;
    }
    public static GifImageView getAC(){
        return AC;
    }

    @Override
    public void onBackPressed() {
        stopService(new Intent(this, FacilityMainService.class));
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_main);

        String first_name = "User";
        String last_name = "";
        String user_name = "abcde5";
        String value = "";

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("user_info");

            String[] user_info = value.split("--", 10);
            ;
            first_name = user_info[0];
            last_name = user_info[1];
            user_name = user_info[2];
            if (user_info.length > 4) {
                FacilityMain.room_no = user_info[4];
            } else {
                FacilityMain.room_no = user_info[3];
            }

        }

        TextView roomNum = findViewById(R.id.textView125);
        roomNum.setText(room_no);

        TextView ac_stat = findViewById(R.id.idTVCourseRating2);
        TextView light_stat = findViewById(R.id.idTVCourseRating3);

        ImageView demoImage = (ImageView) findViewById(R.id.imageView40);
        GifImageView gif = findViewById(R.id.gifImageView2);

        ImageView backButton = (ImageView)findViewById(R.id.imageView39);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //animate(demoImage,R.drawable.light_on);
                //animate2in(gif);
            }
        });

        ACSTAT = ac_stat;
        LSTAT = light_stat;
        L = demoImage;
        AC = gif;

        String url = "http://192.168.137.1/codes/getRoomStatus.php";
        String result = "";

        BackgroundWorkerRoomStatus backgroundWorker = new BackgroundWorkerRoomStatus(FacilityMain.this,ac_stat ,light_stat,demoImage,gif);
        backgroundWorker.execute(url,FacilityMain.room_no);

        Intent serviceIntent = new Intent(this, FacilityMainService.class);
        this.startService(serviceIntent);
    }

    public static void animate(final ImageView imageView, final int images) {

        //imageView <-- The View which displays the images
        //images[] <-- Holds R references to the images to display
        //imageIndex <-- index of the first image to show in images[]
        //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.

        int fadeInDuration = 1000; // Configure time values here

        imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);


        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        imageView.setImageResource(images);
        imageView.setVisibility(View.VISIBLE);
    }

    public static void animate2out(final GifImageView gif) {

        //imageView <-- The View which displays the images
        //images[] <-- Holds R references to the images to display
        //imageIndex <-- index of the first image to show in images[]
        //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.

        int fadeInDuration = 800; // Configure time values here

        gif.setVisibility(View.VISIBLE);    //Visible or invisible by default - this will apply when the animation ends

        Animation fadeIn = new AlphaAnimation(1, 0);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);


        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.setRepeatCount(1);
        gif.setAnimation(animation);

        gif.setVisibility(View.INVISIBLE);
    }

    public static void animate2in(final GifImageView gif) {

        //imageView <-- The View which displays the images
        //images[] <-- Holds R references to the images to display
        //imageIndex <-- index of the first image to show in images[]
        //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.

        int fadeInDuration = 800; // Configure time values here

        gif.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);


        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.setRepeatCount(1);
        gif.setAnimation(animation);

        gif.setVisibility(View.VISIBLE);
    }

}

class BackgroundWorkerRoomStatus extends AsyncTask<String,Void,String> {
    Context context;
    TextView ac_status;
    TextView light_status;
    ImageView light;
    GifImageView ac;

    BackgroundWorkerRoomStatus(Context ctx, TextView acstat, TextView lightstat, ImageView l, GifImageView ac){
        this.context = ctx;
        this.ac_status = acstat;
        this.light_status = lightstat;
        this.light = l;
        this.ac = ac;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = (String)params[0];
        String room_no = (String)params[1];
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
            post_data = URLEncoder.encode("room_no", "UTF-8") + "=" + URLEncoder.encode(room_no, "UTF-8");

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
            light.setImageResource(R.drawable.light_off);
            ac.setVisibility(View.INVISIBLE);
            ac_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.off_circle, 0, 0, 0);
            light_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.off_circle, 0, 0, 0);
        } else {
            String[][] array = new Gson().fromJson(result, String[][].class);

            FacilityMain.ac_stat = array[0][0];
            FacilityMain.light_stat = array[0][1];

            if (array[0][0].equals("1")) {
                if (array[0][1].equals("1")) {
                    light.setImageResource(R.drawable.light_on);
                    ac.setVisibility(View.VISIBLE);
                    ac_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.on_circle, 0, 0, 0);
                    light_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.on_circle, 0, 0, 0);
                } else if (array[0][1].equals("0")) {
                    light.setImageResource(R.drawable.light_off);
                    ac.setVisibility(View.VISIBLE);
                    ac_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.on_circle, 0, 0, 0);
                    light_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.off_circle, 0, 0, 0);
                }
            } else if (array[0][0].equals("0")) {
                if (array[0][1].equals("1")) {
                    light.setImageResource(R.drawable.light_on);
                    ac.setVisibility(View.INVISIBLE);
                    ac_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.off_circle, 0, 0, 0);
                    light_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.on_circle, 0, 0, 0);
                } else if (array[0][1].equals("0")) {
                    light.setImageResource(R.drawable.light_off);
                    ac.setVisibility(View.INVISIBLE);
                    ac_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.off_circle, 0, 0, 0);
                    light_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.off_circle, 0, 0, 0);
                }
            }

        }

    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

class BackgroundWorkerUpdateRoomStatus extends AsyncTask<String,Void,String> {
    Context context;
    TextView ac_status;
    TextView light_status;
    ImageView light;
    GifImageView ac;

    BackgroundWorkerUpdateRoomStatus(Context ctx, TextView acstat, TextView lightstat, ImageView l, GifImageView ac){
        this.context = ctx;
        this.ac_status = acstat;
        this.light_status = lightstat;
        this.light = l;
        this.ac = ac;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = (String)params[0];
        String room_no = (String)params[1];
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
            post_data = URLEncoder.encode("room_no", "UTF-8") + "=" + URLEncoder.encode(room_no, "UTF-8");

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
            light.setImageResource(R.drawable.light_off);
            ac.setVisibility(View.INVISIBLE);
            ac_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.off_circle, 0, 0, 0);
            light_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.off_circle, 0, 0, 0);
            FacilityMain.ac_stat = "0";
            FacilityMain.light_stat = "0";
        } else {
            String[][] array = new Gson().fromJson(result, String[][].class);

            if (!array[0][0].equals(FacilityMain.ac_stat)){
                if (array[0][0].equals("1")) {
                    FacilityMain.animate2in(ac);
                    ac_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.on_circle, 0, 0, 0);
                } else if (array[0][0].equals("0")) {
                    FacilityMain.animate2out(ac);
                    ac_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.off_circle, 0, 0, 0);
                }
                FacilityMain.ac_stat = array[0][0];
            }

            if (!array[0][1].equals(FacilityMain.light_stat)){
                if (array[0][1].equals("1")) {
                    FacilityMain.animate(light,R.drawable.light_on);
                    light_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.on_circle, 0, 0, 0);
                } else if (array[0][1].equals("0")) {
                    FacilityMain.animate(light,R.drawable.light_off);
                    light_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.off_circle, 0, 0, 0);
                }
                FacilityMain.light_stat = array[0][1];
            }

        }

    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}