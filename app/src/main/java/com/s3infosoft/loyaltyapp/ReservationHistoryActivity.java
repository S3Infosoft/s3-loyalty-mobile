package com.s3infosoft.loyaltyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s3infosoft.loyaltyapp.adapter.OrderHistoryAdapter;
import com.s3infosoft.loyaltyapp.adapter.ReservationHistoryAdapter;
import com.s3infosoft.loyaltyapp.model.Order;
import com.s3infosoft.loyaltyapp.model.ReservationHistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ReservationHistoryActivity extends AppCompatActivity {

    List<ReservationHistory> orders = new ArrayList<>();
    RecyclerView recyclerView;
    ReservationHistoryAdapter productAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_history);

        getSupportActionBar().setTitle("Reservation History");

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/reservation_history/"+firebaseUser.getUid());

        databaseReference.orderByChild("booking_date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("#DDDD", dataSnapshot.getValue().toString());
                if(dataSnapshot.exists())
                {
                    for (DataSnapshot hotelSnapshot: dataSnapshot.getChildren())
                    {
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) hotelSnapshot.getValue();

                        orders.add(new ReservationHistory(hashMap.get("hotel_name").toString()+" on "+hashMap.get("booking_date").toString(), hashMap.get("hotel_id").toString(), Integer.parseInt(hashMap.get("amount").toString()), hashMap.get("booking_date").toString()));
                        Collections.reverse(orders);
                        productAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                }
                else
                {
                    Toast.makeText(ReservationHistoryActivity.this, "No Reservation Found", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        productAdapter = new ReservationHistoryAdapter(this, orders);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(productAdapter);
    }
}
