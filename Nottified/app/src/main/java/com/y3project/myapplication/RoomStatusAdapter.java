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

import java.util.ArrayList;

public class RoomStatusAdapter extends RecyclerView.Adapter<RoomStatusAdapter.ViewHolder>{
    private final Context context;
    private final ArrayList<RoomStatusModel> courseModelArrayList;
    private final String userInfo;

    // Constructor
    public RoomStatusAdapter(Context context, ArrayList<RoomStatusModel> courseModelArrayList, String userInfo) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
        this.userInfo = userInfo;
    }

    @NonNull
    @Override
    public RoomStatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_status_card, parent, false);
        return new RoomStatusAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomStatusAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        RoomStatusModel model = courseModelArrayList.get(position);
        holder.roomNum.setText(model.getRoom_no());
        holder.acLayout.setBackgroundResource(model.getAc_bg());
        holder.lightsLayout.setBackgroundResource(model.getLights_bg());

        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,FacilityMain.class);

                String finalInfo = userInfo.concat("--" + model.getRoom_no());
                intent.putExtra("user_info",finalInfo);
                context.startActivity(intent);
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

        private final TextView roomNum;
        private final ConstraintLayout acLayout;
        private final ConstraintLayout lightsLayout;
        private final ConstraintLayout main;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomNum = itemView.findViewById(R.id.textView118);
            acLayout = itemView.findViewById(R.id.aclay);
            lightsLayout = itemView.findViewById(R.id.lightslay);
            main = itemView.findViewById(R.id.idIVConstraintLayout);
        }
    }
}
