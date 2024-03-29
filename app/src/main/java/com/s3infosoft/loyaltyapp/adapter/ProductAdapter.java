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
import com.s3infosoft.loyaltyapp.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<Product> products;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv1, tv2, tv3;
        public ImageView imageView;

        public MyViewHolder(View view)
        {
            super(view);
            tv1 = (TextView) view.findViewById(R.id.product_name);
            tv2 = (TextView) view.findViewById(R.id.product_desc);
            tv3 = (TextView) view.findViewById(R.id.product_points);
            imageView = (ImageView) view.findViewById(R.id.logo_url);
        }
    }

    public ProductAdapter(Context context, List<Product> products)
    {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv1.setText(products.get(position).getName());
        holder.tv2.setText(products.get(position).getDesc());
        holder.tv3.setText(""+products.get(position).getPoints()+" PTS");
        Glide.with(context).load(products.get(position).getLogo_url()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}

