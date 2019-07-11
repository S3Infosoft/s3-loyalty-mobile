package com.s3infosoft.loyaltyapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.s3infosoft.loyaltyapp.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    int images[];
    LayoutInflater layoutInflater;
    List<String> image_urls;


    public ViewPagerAdapter(Context context, int images[], List<String> image_urls) {
        this.context = context;
        this.images = images;
        this.image_urls = image_urls;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return image_urls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView;
        itemView = layoutInflater.inflate(R.layout.custom_layout, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Glide.with(context).load(image_urls.get(position)).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}