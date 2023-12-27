package com.y3project.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;

public class StaffOrderAdapter extends RecyclerView.Adapter<StaffOrderAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<StaffOrderModel> courseModelArrayList;

    // Constructor
    public StaffOrderAdapter(Context context, ArrayList<StaffOrderModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public StaffOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_order_card_layout, parent, false);
        return new StaffOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffOrderAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        StaffOrderModel model = courseModelArrayList.get(position);
        holder.orderNum.setText(Html.fromHtml("<font color='#007ca7'>#</font><font color='#000000'>"+model.getOrder_no()+"</font>"));
        holder.dishName.setText(model.getDish_name());
        holder.orderQuantity.setText("x"+model.getDish_quantity());
        holder.cstmerName.setText(model.getCstmer_name());
        holder.totalPaid.setText("RM "+model.getTotal_paid());
        holder.orderTime.setText(model.getOrder_time());
        if(model.getStatus().contains("ready")){
            holder.orderStatus.setText("COLLECTED");
            holder.orderStatusLogo.setImageResource(R.drawable.ic_baseline_unarchive_24);
            holder.orderStatus.setTextColor(Color.parseColor("#000000"));
            holder.orderStatusLogo.setColorFilter(Color.parseColor("#000000"));
        }

        String url = "";
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.orderStatus.getText().toString().equals("READY")){
                    holder.orderStatus.setText("COLLECTED");
                    holder.orderStatusLogo.setImageResource(R.drawable.ic_baseline_unarchive_24);
                    holder.orderStatus.setTextColor(Color.parseColor("#000000"));
                    holder.orderStatusLogo.setColorFilter(Color.parseColor("#000000"));
                    new BackgroundWorkerUpdateStatus(context).execute("http://192.168.137.1/codes/updateStatusStaff.php",model.getOrder_no(),"ready_un");
                } else if (holder.orderStatus.getText().toString().equals("COLLECTED")){
                    courseModelArrayList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    notifyItemRangeChanged(holder.getAdapterPosition(), courseModelArrayList.size());
                    new BackgroundWorkerUpdateStatus(context).execute("http://192.168.137.1/codes/updateStatusStaff.php",model.getOrder_no(),"done");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return courseModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView orderNum;
        private final TextView dishName;
        private final TextView orderQuantity;
        private final TextView cstmerName;
        private final TextView totalPaid;
        private final TextView orderTime;
        private final TextView orderStatus;
        private final ImageView orderStatusLogo;
        private final ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNum = itemView.findViewById(R.id.textView46);
            dishName = itemView.findViewById(R.id.textView47);
            orderQuantity = itemView.findViewById(R.id.textView50);
            cstmerName = itemView.findViewById(R.id.textView81);
            totalPaid = itemView.findViewById(R.id.textView82);
            orderTime = itemView.findViewById(R.id.textView84);
            constraintLayout = itemView.findViewById(R.id.haha);
            orderStatus = itemView.findViewById(R.id.textView72);
            orderStatusLogo = itemView.findViewById(R.id.imageView30);
        }
    }
}

class BackgroundWorkerUpdateStatus extends AsyncTask<String,Void,String> {
    Context context;

    BackgroundWorkerUpdateStatus(Context ctx){
        this.context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String login_url = (String)params[0];
        String orderid = (String)params[1];
        String status = (String)params[2];
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
            post_data = URLEncoder.encode("orderid", "UTF-8") + "=" + URLEncoder.encode(orderid, "UTF-8")+ "&"
                    + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8");

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
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

    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
