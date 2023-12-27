package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CheckService extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_service);

        String value = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("user_info");
        }

        CardView mailRoom = (CardView)findViewById(R.id.parking_card);
        String finalValue = value;
        mailRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckService.this, ServiceStatus.class);
                String finalInfo = finalValue.concat("--mail_room");
                intent.putExtra("user_info", finalInfo);
                startActivity(intent);
            }
        });

        CardView financeOffice = (CardView)findViewById(R.id.financeCard);
        financeOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckService.this, ServiceStatus.class);
                String finalInfo = finalValue.concat("--finance_office");
                intent.putExtra("user_info", finalInfo);
                startActivity(intent);
            }
        });

        CardView securityOffice = (CardView)findViewById(R.id.regi_vehicle);
        securityOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckService.this, ServiceStatus.class);
                String finalInfo = finalValue.concat("--security_office");
                intent.putExtra("user_info", finalInfo);
                startActivity(intent);
            }
        });

        CardView saOffice = (CardView)findViewById(R.id.sa_card);
        saOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckService.this, ServiceStatus.class);
                String finalInfo = finalValue.concat("--sa_office");
                intent.putExtra("user_info", finalInfo);
                startActivity(intent);
            }
        });

        ImageView backButton = (ImageView)findViewById(R.id.imageView37);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}