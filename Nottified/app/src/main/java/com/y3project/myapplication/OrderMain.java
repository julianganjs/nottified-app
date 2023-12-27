package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class OrderMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_main);

        String value = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("user_info");
        }
        String finalValue = value;

        Button placeOrder = (Button)findViewById(R.id.place_order_btn);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderMain.this, CuisineSelection.class);
                intent.putExtra("user_info", finalValue);
                startActivity(intent);
            }
        });

        Button viewOrder = (Button)findViewById(R.id.view_order_btn);
        viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderMain.this, OrderViewer.class);
                intent.putExtra("user_info", finalValue);
                startActivity(intent);
            }
        });

        ImageView backButton = (ImageView)findViewById(R.id.imageView14);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}