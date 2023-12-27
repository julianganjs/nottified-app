package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CuisineStaff extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_staff);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, this.MODE_PRIVATE);

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
            role = user_info[3];
        }

        TextView tv=(TextView)findViewById(R.id.textView68);
        tv.setText(Html.fromHtml("Hello, "+first_name));

        ConstraintLayout logout = (ConstraintLayout)findViewById(R.id.haha);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder1.setTitle("Logout Confirmation");
                builder1.setMessage(Html.fromHtml("Are you sure you want to logout?"))
                        .setCancelable(true)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.apply();

                                Intent intent = new Intent(CuisineStaff.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });
                AlertDialog alert = builder1.create();
                alert.show();
            }
        });

        ConstraintLayout cuisineCard = (ConstraintLayout)findViewById(R.id.goCuisine);
        String finalValue = value;
        cuisineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuisineStaff.this, StaffOrderList.class);
                intent.putExtra("user_info", finalValue);
                startActivity(intent);
            }
        });

        ConstraintLayout parkingCard = (ConstraintLayout)findViewById(R.id.goCuisine3);
        parkingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuisineStaff.this, ParkingLocator.class);
                intent.putExtra("user_info", finalValue);
                startActivity(intent);
            }
        });

        ConstraintLayout permitCard = (ConstraintLayout)findViewById(R.id.goCuisine32);
        String finalUser_name = user_name;
        permitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundWorkerCheckVehicle(CuisineStaff.this,finalValue).execute("http://192.168.137.1/codes/checkVehicle.php", finalUser_name);
            }
        });

        ConstraintLayout entryLogCard = (ConstraintLayout)findViewById(R.id.goCuisine321);
        entryLogCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuisineStaff.this, VehicleEntryLogs.class);
                startActivity(intent);
            }
        });

        ConstraintLayout serviceCard = (ConstraintLayout)findViewById(R.id.goCuisine39);
        serviceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuisineStaff.this, CheckService.class);
                intent.putExtra("user_info", finalValue);
                startActivity(intent);
            }
        });

        ConstraintLayout facilityCard = (ConstraintLayout)findViewById(R.id.goCuisine399);
        facilityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuisineStaff.this, MonitorFacilities.class);
                intent.putExtra("user_info", finalValue);
                startActivity(intent);
            }
        });

        TextView cuisineName = (TextView)findViewById(R.id.textView74);
        TextView staffWord = (TextView)findViewById(R.id.textView75);
        TextView swipe = (TextView)findViewById(R.id.textView71);
        ImageView cuisineImage = (ImageView)findViewById(R.id.imageView32);
        CardView cuisineService = (CardView)findViewById(R.id.cardView12);
        CardView permitService = (CardView)findViewById(R.id.cardView1232);
        CardView entryLogs = (CardView)findViewById(R.id.cardView12321);
        //Toast.makeText(CuisineStaff.this, role, Toast.LENGTH_SHORT).show();
        if (role.contains("cuisine")) {
            entryLogs.setVisibility(View.GONE);
            if (role.contains("chinese")) {
                cuisineName.setText("Chinese");
                cuisineImage.setImageResource(R.drawable.chinese_food_lead);
            } else if (role.contains("western")) {
                cuisineName.setText("Western");
                cuisineImage.setImageResource(R.drawable.western);
            } else if (role.contains("arabic")) {
                cuisineName.setText("Arabic");
                cuisineImage.setImageResource(R.drawable.arabic);
            } else if (role.contains("n_indian")) {
                cuisineName.setText("North Indian");
                cuisineImage.setImageResource(R.drawable.northindian);
            } else if (role.contains("japanese")) {
                cuisineName.setText("Japanese");
                cuisineImage.setImageResource(R.drawable.japanese);
            } else if (role.contains("mamak")) {
                cuisineName.setText("Mamak");
                cuisineImage.setImageResource(R.drawable.mamak);
            } else if (role.contains("cran")) {
                cuisineName.setText("Chicken Rice");
                cuisineImage.setImageResource(R.drawable.chickenrice);
            }
        } else if (role.contains("office")){
            cuisineName.setText("Available");
            staffWord.setText("Services");
            cuisineService.setVisibility(View.GONE);
            entryLogs.setVisibility(View.GONE);
        } else if (role.contains("security")){
            cuisineName.setText("Available");
            staffWord.setText("Services");
            permitService.setVisibility(View.GONE);
            cuisineService.setVisibility(View.GONE);
        }
    }
}