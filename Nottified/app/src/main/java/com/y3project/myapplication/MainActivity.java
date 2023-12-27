package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Button;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String userinfo, usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, this.MODE_PRIVATE);
        userinfo = sharedpreferences.getString("USER_INFO", null);
        usertype = sharedpreferences.getString("USER_TYPE", null);
    }

    public void sendToStudent(View view) {
        Intent intent = new Intent(MainActivity.this, StudentLogin.class);
        startActivity(intent);
    }

    public void sendToStaff(View view) {
        Intent intent = new Intent(MainActivity.this, StaffLogin.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (userinfo != null && usertype != null) {
            if(usertype.equals("student")) {
                Intent intent = new Intent(MainActivity.this, MainPortal.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("user_info", userinfo);
                startActivity(intent);
            } else if (usertype.contains("staff")) {
                Intent intent = new Intent(MainActivity.this, CuisineStaff.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("user_info", userinfo);
                startActivity(intent);
            }
        }
    }
}