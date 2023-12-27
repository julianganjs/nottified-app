package com.y3project.myapplication;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.y3project.myapplication.databinding.ActivityMainPortalBinding;

import java.util.Iterator;
import java.util.List;

public class MainPortal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainPortalBinding binding;

    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainPortalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMainPortal.toolbar);
        getSupportActionBar().hide();

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_portal);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView mDrawerList = (NavigationView)findViewById(R.id.nav_view);

        mDrawerList.setNavigationItemSelectedListener(this);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, this.MODE_PRIVATE);

        ImageView imgFavorite = (ImageView) findViewById(R.id.imageView2);
        imgFavorite.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        });

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

        View headerView = mDrawerList.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.navTitle);
        navUsername.setText(first_name+" "+last_name);
        TextView navEmail = (TextView) headerView.findViewById(R.id.navEmail);
        navEmail.setText(user_name+"@nottingham.edu.my");

        TextView tv=(TextView)findViewById(R.id.textView2);
        tv.setText(Html.fromHtml("Hello, "+first_name+"!"));

        CardView cuisineCard = (CardView)findViewById(R.id.cardRow1);
        String finalValue = value;
        cuisineCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPortal.this, OrderMain.class);
                intent.putExtra("user_info", finalValue);
                startActivity(intent);
            }
        });

        CardView parkingCard = (CardView)findViewById(R.id.parking_card);
        parkingCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPortal.this, ParkingLocator.class);
                intent.putExtra("user_info", finalValue);
                startActivity(intent);
            }
        });

        CardView vehicleRegiCard = (CardView)findViewById(R.id.regi_vehicle);
        String finalUser_name = user_name;
        vehicleRegiCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundWorkerCheckVehicle(MainPortal.this,finalValue).execute("http://192.168.137.1/codes/checkVehicle.php", finalUser_name);
            }
        });

        CardView serviceCard = (CardView)findViewById(R.id.service_card);
        serviceCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPortal.this, CheckService.class);
                intent.putExtra("user_info", finalValue);
                startActivity(intent);
            }
        });

        CardView faciCard = (CardView)findViewById(R.id.faci_card);
        faciCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPortal.this, MonitorFacilities.class);
                intent.putExtra("user_info", finalValue);
                startActivity(intent);
            }
        });

        if (isMyServiceRunning(StartOnBootService.class)) {

        } else {
            Intent serviceIntent = new Intent(this, StartOnBootService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.startForegroundService(serviceIntent);
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_portal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_portal);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            builder1.setTitle("Logout Confirmation");
            builder1.setMessage(Html.fromHtml("Are you sure you want to logout?"))
                    .setCancelable(true)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            stopService(new Intent(MainPortal.this, StartOnBootService.class));

                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.clear();
                            editor.apply();

                            Intent intent = new Intent(MainPortal.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });
            AlertDialog alert = builder1.create();
            alert.show();
        }
        return false;
    }
}