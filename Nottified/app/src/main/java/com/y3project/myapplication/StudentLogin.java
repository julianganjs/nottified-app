package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class StudentLogin extends AppCompatActivity {

    EditText usernameLogin;
    EditText passwordLogin;
    Button login;

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        String text = "<font color='#717171'>Having trouble logging in? Please </font> <font color='#009bbd'>Click Here</font><font color='#717171'>.</font>";
        TextView tv=(TextView)findViewById(R.id.textView8);
        tv.setText(Html.fromHtml(text));

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.nottingham.ac.uk/dts/help/it-service-desk/it-service-desk.aspx");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        TextView reset_pass=(TextView)findViewById(R.id.textView6);
        reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://account.live.com/ResetPassword.aspx");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        usernameLogin = findViewById(R.id.editTextTextPersonName2);
        passwordLogin = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                String url = "http://192.168.137.1/codes/validateAccount.php";
                String type = "login";
                BackgroundWorker backgroundWorker = new BackgroundWorker(StudentLogin.this, sharedpreferences);
                backgroundWorker.execute(url,type,username,password);

            }
        });

    }

}



class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    SharedPreferences SP;
    BackgroundWorker(Context ctx, SharedPreferences SP){
        this.context = ctx;
        this.SP = SP;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = (String)params[0];
        String type = (String)params[1];
        String username = (String)params[2];
        String password = (String)params[3];
        String result = "";

        if(username.equals("") || password.equals("")){
            result = "EMPTY";
            return result;
        } else {
            try {

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter bufferedWriter = new OutputStreamWriter(outputStream, "UTF-8");

                String post_data = "";
                if (type.equals("login")) {
                    post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                            + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                }

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
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Error");
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("FAILED")) {
            alertDialog.setMessage("Invalid username/password.");
            alertDialog.show();
        } else if (result.equals("EMPTY")) {
            alertDialog.setMessage("Please enter a username or password.");
            alertDialog.show();
        } else {
            SharedPreferences.Editor editor = SP.edit();

            // below two lines will put values for
            // email and password in shared preferences.
            editor.putString("USER_INFO", result);
            editor.putString("USER_TYPE", "student");

            // to save our data with key and value.
            editor.apply();

            Intent intent = new Intent(context, MainPortal.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("user_info",result);
            context.startActivity(intent);
            ((Activity)context).finish();
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}