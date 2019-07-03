package com.s3infosoft.loyaltyapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.s3infosoft.loyaltyapp.R;
import com.s3infosoft.loyaltyapp.model.Hotel;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<String> hotels;
    List<String> image_urls;
    Context context;
    private int images[] = {R.drawable.common_full_open_on_phone, R.drawable.ic_launcher_background };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv1, tv2;
        public ImageView imageView;
        public RelativeLayout relativeLayout;
        public LinearLayout linearLayout;

        public MyViewHolder(View view)
        {
            super(view);
            tv1 = (TextView) view.findViewById(R.id.hotel_name);
            imageView = (ImageView) view.findViewById(R.id.logo_url);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
        }
    }

    public NewsAdapter(Context context, List<String> hotels)
    {
        this.context = context;
        this.hotels = hotels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GradientDrawable gradientDrawable = (GradientDrawable) holder.relativeLayout.getBackground().getCurrent();
        if (position == 0 || position%2==0)
        {
            gradientDrawable.setColor(Color.parseColor("#D81B60"));
            holder.linearLayout.setBackgroundColor(Color.parseColor("#425EDB"));
        }
        else
        {
            gradientDrawable.setColor(Color.parseColor("#425EDB"));
            holder.linearLayout.setBackgroundColor(Color.parseColor("#D81B60"));
        }
        holder.tv1.setText(hotels.get(position).toString());
        Glide.with(context).load("https://akm-img-a-in.tosshub.com/indiatoday/images/story/201907/gopal_chawla_sidhu_photo-170x96.jpeg").circleCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }
}

