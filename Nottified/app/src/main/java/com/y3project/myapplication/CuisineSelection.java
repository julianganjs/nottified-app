package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CuisineSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_selection);

        String value = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("user_info");
        }
        String finalValue = value;

        CardView chineseCuisine = (CardView)findViewById(R.id.cuisineRow1);
        chineseCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuisineSelection.this, DishesSelection.class);
                String finalInfo = finalValue.concat("--chinese");
                intent.putExtra("user_info", finalInfo);
                startActivity(intent);
            }
        });

        CardView westernCuisine = (CardView)findViewById(R.id.cuisineRow2);
        westernCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuisineSelection.this, DishesSelection.class);
                String finalInfo = finalValue.concat("--western");
                intent.putExtra("user_info", finalInfo);
                startActivity(intent);
            }
        });

        CardView arabicCuisine = (CardView)findViewById(R.id.cuisineRow3);
        arabicCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuisineSelection.this, DishesSelection.class);
                String finalInfo = finalValue.concat("--arabic");
                intent.putExtra("user_info", finalInfo);
                startActivity(intent);
            }
        });

        CardView nindianCuisine = (CardView)findViewById(R.id.cuisineRow4);
        nindianCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuisineSelection.this, DishesSelection.class);
                String finalInfo = finalValue.concat("--n_indian");
                intent.putExtra("user_info", finalInfo);
                startActivity(intent);
            }
        });

        CardView japaneseCuisine = (CardView)findViewById(R.id.cuisineRow6);
        japaneseCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuisineSelection.this, DishesSelection.class);
                String finalInfo = finalValue.concat("--japanese");
                intent.putExtra("user_info", finalInfo);
                startActivity(intent);
            }
        });

        CardView mamakCuisine = (CardView)findViewById(R.id.cuisineRow7);
        mamakCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuisineSelection.this, DishesSelection.class);
                String finalInfo = finalValue.concat("--mamak");
                intent.putExtra("user_info", finalInfo);
                startActivity(intent);
            }
        });

        CardView cranCuisine = (CardView)findViewById(R.id.cuisineRow8);
        cranCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuisineSelection.this, DishesSelection.class);
                String finalInfo = finalValue.concat("--cran");
                intent.putExtra("user_info", finalInfo);
                startActivity(intent);
            }
        });

        ImageView backButton = (ImageView)findViewById(R.id.imageView21);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}