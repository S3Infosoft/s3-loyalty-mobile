package com.s3infosoft.loyaltyapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.s3infosoft.loyaltyapp.R;
import com.s3infosoft.loyaltyapp.model.Hotel;
import com.s3infosoft.loyaltyapp.model.Order;
import com.s3infosoft.loyaltyapp.model.Product;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    private List<Order> orders;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv1, tv2;

        public MyViewHolder(View view)
        {
            super(view);
            tv1 = (TextView) view.findViewById(R.id.product_name);
            tv2 = (TextView) view.findViewById(R.id.product_desc);
        }
    }

    public OrderHistoryAdapter(Context context, List<Order> orders)
    {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv1.setText(orders.get(position).getDesc());
        holder.tv2.setText("Amount : "+orders.get(position).getPoints());
        //Glide.with(context).load(products.get(position).getLogo_url()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}