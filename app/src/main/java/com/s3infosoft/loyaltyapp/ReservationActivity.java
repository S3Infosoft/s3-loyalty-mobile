package com.s3infosoft.loyaltyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s3infosoft.loyaltyapp.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationActivity extends AppCompatActivity {

    TextView hotel_name, hotel_address;
    ProgressBar progressBar;
    ViewPager viewPager;
    DatabaseReference databaseReference;
    ViewPagerAdapter viewPagerAdapter;
    public LinearLayout sliderDotspanel;
    private int images[] = {R.drawable.common_full_open_on_phone, R.drawable.ic_launcher_background };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        hotel_name = (TextView) findViewById(R.id.hotel_name);
        hotel_address = (TextView) findViewById(R.id.hotel_address);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        Intent i = getIntent();

        databaseReference = FirebaseDatabase.getInstance().getReference("/hotels/"+i.getStringExtra("hotel_id"));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                hotel_name.setText(hashMap.get("name").toString());
                hotel_address.setText(hashMap.get("address").toString());

                HashMap<String,Object> list = (HashMap<String,Object>) hashMap.get("images");

                List<String> image_urls = new ArrayList<String>();
                for (Map.Entry<String,Object> map : list.entrySet())
                {
                    HashMap<String,Object> m = (HashMap<String, Object>) map.getValue();
                    Log.v("$$$$$", m.get("image_url").toString());
                    image_urls.add(m.get("image_url").toString());
                }
                viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(), images, image_urls);
                viewPager.setAdapter(viewPagerAdapter);
                progressBar.setVisibility(View.GONE);

                final int dotscount = viewPagerAdapter.getCount();
                final ImageView[] dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView(getApplicationContext());
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 8, 8, 8);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                        for(int i = 0; i< dotscount; i++){
                            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                        }

                        dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void bookNow(View view) {

    }
}
