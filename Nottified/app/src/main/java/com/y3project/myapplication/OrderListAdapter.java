package com.y3project.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<OrderListModel> courseModelArrayList;

    // Constructor
    public OrderListAdapter(Context context, ArrayList<OrderListModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlist_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        OrderListModel model = courseModelArrayList.get(position);
        holder.orderNo.setText(Html.fromHtml("<font color='#007ca7'>#</font><font color='#000000'>"+model.getOrder_no()+"</font>"));
        holder.dishName.setText(model.getOrder_name());
        holder.cuisineName.setText(model.getCuisine_name());
        holder.quantityNo.setText(model.getQuantity_no());
        holder.status.setText(model.getStatus());
        holder.layout.setBackgroundColor(Color.parseColor(model.getColor()));
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return courseModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView orderNo;
        private final TextView dishName;
        private final TextView cuisineName;
        private final TextView quantityNo;
        private final TextView status;
        private final ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNo = itemView.findViewById(R.id.textView46);
            dishName = itemView.findViewById(R.id.textView47);
            cuisineName = itemView.findViewById(R.id.textView48);
            quantityNo = itemView.findViewById(R.id.textView50);
            status = itemView.findViewById(R.id.textView49);
            layout = itemView.findViewById(R.id.layout2);

        }
    }
}
