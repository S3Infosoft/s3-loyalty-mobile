package com.s3infosoft.loyaltyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s3infosoft.loyaltyapp.adapter.SpecialDealAdapter;
import com.s3infosoft.loyaltyapp.model.SpecialDeal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecialDealsActivity extends AppCompatActivity {

    List<SpecialDeal> specialDeals = new ArrayList<>();
    RecyclerView recyclerView;
    SpecialDealAdapter specialDealAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_deals);

        getSupportActionBar().setTitle("Special Deals");

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("special_deals");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("#DDDD", dataSnapshot.getValue().toString());
                for (DataSnapshot hotelSnapshot: dataSnapshot.getChildren())
                {
                    Log.v("#EEEE", hotelSnapshot.getValue().toString());
                    List<String> image_urls = new ArrayList<String>();
                    HashMap<String,Object> list = (HashMap<String, Object>) hotelSnapshot.getValue();
                    Log.v("#####", list.get("images").toString());

                    HashMap<String,Object> hashMap = (HashMap<String,Object>) list.get("images");

                    Log.v("$$$$$", hashMap.toString());

                    for (Map.Entry<String,Object> map : hashMap.entrySet())
                    {
                        HashMap<String,Object> m = (HashMap<String, Object>) map.getValue();
                        Log.v("$$$$$", m.get("image_url").toString());
                        image_urls.add(m.get("image_url").toString());
                    }

                    specialDeals.add(new SpecialDeal(list.get("name").toString(), list.get("description").toString(), image_urls, list.get("hotel_id").toString(), 8000));
                    //Log.v("#####", list.get("name")+" "+list.get("metadata").toString());
                    specialDealAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(SpecialDealsActivity.this, RedeemActivity.class);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        specialDealAdapter = new SpecialDealAdapter(this, specialDeals);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(specialDealAdapter);
    }
}
