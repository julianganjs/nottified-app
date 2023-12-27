package com.y3project.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class VehicleRegistration extends AppCompatActivity {

    private Spinner spinner;
    private static final String[] paths = {"Visitor", "Staff/Student"};

    public static String type = "";

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView start_dateView;
    private TextView end_dateView;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_registration);

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
            if (user_info.length > 3){
                VehicleRegistration.type="staff";
            } else {
                VehicleRegistration.type="student";
            }
        }

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(VehicleRegistration.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.parseColor("#000000")); //Change selected text color
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ((TextView) view).setTextAppearance(R.style.SpinnerFont);
                }
                ((TextView) view).setTextSize(15);
                ((TextView) view).setLetterSpacing((float)0.01);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        start_dateView = (TextView) findViewById(R.id.textView91);
        end_dateView = (TextView) findViewById(R.id.textView92);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        ImageView backButton = (ImageView)findViewById(R.id.imageView28);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        EditText username_input = findViewById(R.id.editTextTextPersonName4);
        EditText plate_input = findViewById(R.id.editTextTextPersonName5);
        EditText vehicle_info_input = findViewById(R.id.editTextTextPersonName6);

        Button login = findViewById(R.id.login7);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        String finalValue = value;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder1.setTitle("Vehicle Registration");
                builder1.setMessage(Html.fromHtml("Are you sure you want to submit? No changes can be made after this!"))
                        .setCancelable(true)
                        .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String regi_type = spinner.getSelectedItem().toString();
                                String start_date = start_dateView.getText().toString();
                                String end_date = end_dateView.getText().toString();
                                String username = username_input.getText().toString();
                                String plate_no = plate_input.getText().toString().replaceAll("\\s+","");
                                String vehicle_info = vehicle_info_input.getText().toString();

                                String url = "http://192.168.137.1/codes/registerVehicle.php";
                                BackgroundWorkerVehicleRegi backgroundWorker = new BackgroundWorkerVehicleRegi(VehicleRegistration.this, finalValue);
                                backgroundWorker.execute(url,plate_no,username,regi_type,start_date,end_date,vehicle_info);
                            }
                        });
                AlertDialog alert = builder1.create();
                alert.show();
            }
        });


    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        } else if (id == 888) {
            return new DatePickerDialog(this,
                    myDateListener2, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
        DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0,
                                  int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                // arg1 = year
                // arg2 = month
                // arg3 = day
                showDate(arg1, arg2+1, arg3);
            }
        };

    private void showDate(int year, int month, int day) {
        start_dateView.setText(Html.fromHtml("<font color='#000000'>"+year+"-"+month+"-"+day+"</font>"));
    }

    @SuppressWarnings("deprecation")
    public void setDate2(View view) {
        showDialog(888);
    }

    private DatePickerDialog.OnDateSetListener myDateListener2 = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate2(arg1, arg2+1, arg3);
                }
            };

    private void showDate2(int year, int month, int day) {
        end_dateView.setText(Html.fromHtml("<font color='#000000'>"+year+"-"+month+"-"+day+"</font>"));
    }


}

class BackgroundWorkerVehicleRegi extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    String finalValue;
    BackgroundWorkerVehicleRegi(Context ctx, String value){
        this.context = ctx;
        this.finalValue = value;
    }
    @Override
    protected String doInBackground(String... params) {
        String login_url = (String)params[0];
        String plate_no = (String)params[1];
        String username = (String)params[2];
        String permit_type = (String)params[3];
        String entry_date = (String)params[4];
        String expiry_date = (String)params[5];
        String vehicle_info = (String)params[6];
        String result = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = null;
        Date start = null;
        Date end = null;
        try {
            start = sdf.parse(entry_date);
            end = sdf.parse(expiry_date);
            today = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(plate_no.equals("") || username.equals("") || entry_date.equals("yyyy-mm-dd") || expiry_date.equals("yyyy-mm-dd") || vehicle_info.equals("")){
            result = "EMPTY";
            return result;
        } else if (start.after(end)){
            result = "DATE PROB";
            return result;
        } else if (start.before(today)){
            result = "DATE PROB 2";
            return result;
        } else if (!end.after(start)){
            result = "DATE PROB 3";
            return result;
        }else {
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter bufferedWriter = new OutputStreamWriter(outputStream, "UTF-8");

                String post_data = "";
                post_data = URLEncoder.encode("plate_no", "UTF-8") + "=" + URLEncoder.encode(plate_no, "UTF-8") + "&"
                        + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(permit_type, "UTF-8") + "&"
                        + URLEncoder.encode("entry_date", "UTF-8") + "=" + URLEncoder.encode(entry_date, "UTF-8") + "&"
                        + URLEncoder.encode("expiry_date", "UTF-8") + "=" + URLEncoder.encode(expiry_date, "UTF-8") + "&"
                        + URLEncoder.encode("info", "UTF-8") + "=" + URLEncoder.encode(vehicle_info, "UTF-8");

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
        if (result.equals("EMPTY")) {
            alertDialog.setMessage("Please fill up all fields in the form.");
            alertDialog.show();
        } else if (result.equals("DATE PROB")) {
            alertDialog.setMessage("Please ensure that the End Date is later than the Start Date.");
            alertDialog.show();
        } else if (result.equals("DATE PROB 2")) {
            alertDialog.setMessage("Please ensure that the Start Date is not before today's date.");
            alertDialog.show();
        } else if (result.equals("DATE PROB 3")) {
            alertDialog.setMessage("Please ensure that the End Date is later than the Start Date.");
            alertDialog.show();
        }else if (result.equals("REGISTERED")){
            Toast.makeText(context, "Vehicle Registered!", Toast.LENGTH_SHORT).show();
            Intent intent = null;
            if (VehicleRegistration.type.equals("student")) {
                intent = new Intent(context, MainPortal.class);
            } else if (VehicleRegistration.type.equals("staff")) {
                intent = new Intent(context, CuisineStaff.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("user_info", finalValue);
            context.startActivity(intent);
            ((Activity)context).finish();

        } else {
            //alertDialog.setMessage("Registration failed. Please check your internet connection.");
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}

class BackgroundWorkerCheckVehicle extends AsyncTask<String,Void,String> {
    Context context;
    String finalValue;
    BackgroundWorkerCheckVehicle(Context ctx, String value){
        this.context = ctx;
        this.finalValue = value;
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
        if (result.equals("EXISTS")) {
            Intent intent = new Intent(context, EntrySession.class);
            intent.putExtra("user_info", finalValue);
            context.startActivity(intent);

        } else {
            Intent intent = new Intent(context, VehicleRegistration.class);
            intent.putExtra("user_info", finalValue);
            context.startActivity(intent);
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}