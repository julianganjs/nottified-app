package com.y3project.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ParkingFinder extends AppCompatActivity {

    double latitude;
    double longitude;
    int total_parking = 100;
    String zone1_empty = "10";
    String zone2_empty = "20";
    String zone3_empty = "30";
    int total_empty_parking = Integer.valueOf(zone1_empty) + Integer.valueOf(zone2_empty) + Integer.valueOf(zone3_empty);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_finder);

        TextView zone1_space = findViewById(R.id.zone1empty);
        zone1_space.setText(zone1_empty);

        TextView zone2_space = findViewById(R.id.zone2empty);
        zone2_space.setText(zone2_empty);

        TextView zone3_space = findViewById(R.id.zone3empty);
        zone3_space.setText(zone3_empty);

        ProgressBar EmptyParking = findViewById(R.id.empty_parking);
        EmptyParking.setMax(100);
        EmptyParking.setProgress(50);


        ImageButton zone1_btn = findViewById(R.id.zone1parking);
        zone1_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Block B Parking
                latitude = 2.945929;
                longitude = 101.873416;
                openMaps(latitude, longitude);
            }
        });

        ImageButton zone2_btn = findViewById(R.id.zone2parking);
        zone2_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // J-Block Parking
                latitude = 2.946465;
                longitude = 101.876775;
                openMaps(latitude, longitude);
            }
        });

        ImageButton zone3_btn = findViewById(R.id.zone3parking);
        zone3_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Cafeteria Parking
                latitude = 2.943584;
                longitude = 101.876019;
                openMaps(latitude, longitude);
            }
        });

    }

    public void openMaps(double latitude, double longitude){
        //Search for a location
        //Uri IntentUri = Uri.parse("geo:2.9450,101.8740?z=18&q=Engineering Building Block D (Purple Building)");

        //Navigation Function
        Uri IntentUri = Uri.parse("google.navigation:q="+latitude+","+longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, IntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}