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
import com.s3infosoft.loyaltyapp.model.SpecialDeal;

import java.util.List;

public class SpecialDealAdapter extends RecyclerView.Adapter<SpecialDealAdapter.MyViewHolder> {

    private List<SpecialDeal> specialDeals;
    Context context;
    private int images[] = {R.drawable.common_full_open_on_phone, R.drawable.ic_launcher_background };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv1, tv2;
        public ViewPager viewPager;
        public LinearLayout sliderDotspanel;

        public MyViewHolder(View view)
        {
            super(view);
            viewPager = (ViewPager) view.findViewById(R.id.viewPager);
            sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);
            tv1 = (TextView) view.findViewById(R.id.hotel_name);
            tv2 = (TextView) view.findViewById(R.id.hotel_address);
        }
    }

    public SpecialDealAdapter(Context context, List<SpecialDeal> specialDeals)
    {
        this.context = context;
        this.specialDeals = specialDeals;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.special_deals_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(context, images, specialDeals.get(position).getImages_urls());
        holder.viewPager.setAdapter(viewPagerAdapter);

        final int dotscount = viewPagerAdapter.getCount();
        final ImageView[] dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(context);
            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 8, 8, 8);

            holder.sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

        holder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        holder.tv1.setText(specialDeals.get(position).getName());
        holder.tv2.setText(specialDeals.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return specialDeals.size();
    }
}
