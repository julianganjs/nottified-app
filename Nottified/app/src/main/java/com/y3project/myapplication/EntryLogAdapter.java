package com.y3project.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EntryLogAdapter extends RecyclerView.Adapter<EntryLogAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<EntryLogModel> courseModelArrayList;

    // Constructor
    public EntryLogAdapter(Context context, ArrayList<EntryLogModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public EntryLogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_log_card, parent, false);
        return new EntryLogAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryLogAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        EntryLogModel model = courseModelArrayList.get(position);
        holder.plateNum.setText(model.getPlate_no());
        holder.vehicleModel.setText(model.getVehicle_model());
        holder.durationAmount.setText(model.getDuration());
        holder.entryTime.setText(model.getEntry_time());
        holder.exitTime.setText(model.getExit_time());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return courseModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView plateNum;
        private final TextView vehicleModel;
        private final TextView durationAmount;
        private final TextView entryTime;
        private final TextView exitTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            plateNum = itemView.findViewById(R.id.textView46);
            vehicleModel = itemView.findViewById(R.id.textView111);
            durationAmount = itemView.findViewById(R.id.textView117);
            entryTime = itemView.findViewById(R.id.textView113);
            exitTime = itemView.findViewById(R.id.textView115);
        }
    }
}
