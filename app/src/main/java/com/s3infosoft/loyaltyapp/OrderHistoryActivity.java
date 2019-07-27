package com.s3infosoft.loyaltyapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s3infosoft.loyaltyapp.adapter.OrderHistoryAdapter;
import com.s3infosoft.loyaltyapp.adapter.ProductAdapter;
import com.s3infosoft.loyaltyapp.model.Hotel;
import com.s3infosoft.loyaltyapp.model.Order;
import com.s3infosoft.loyaltyapp.model.Product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderHistoryActivity extends AppCompatActivity {

    List<Order> orders = new ArrayList<>();
    RecyclerView recyclerView;
    OrderHistoryAdapter productAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    FirebaseAnalytics mFirebaseAnalytics;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/order_history/uid");

        databaseReference.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("#DDDD", dataSnapshot.getValue().toString());
                if(dataSnapshot.exists())
                {
                    for (DataSnapshot hotelSnapshot: dataSnapshot.getChildren())
                    {
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) hotelSnapshot.getValue();

                        orders.add(new Order(hashMap.get("desc").toString(),Integer.parseInt(hashMap.get("amount").toString())));
                        productAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Order History");
        bundle.putString(FirebaseAnalytics.Param.ITEM_VARIANT, "orders");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, bundle);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setUserId(firebaseUser == null ? "null": firebaseUser.getUid());
        mFirebaseAnalytics.setUserProperty("Order History", "my orders");

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //products.add(new Product("You Order For Flipkart Gift Voucher", "Points : 3000", "https://www.underconsideration.com/brandnew/archives/flipkart_logo_detail_icon.jpg"));
        //products.add(new Product("You Order For Starbucks Gift Voucher", "Points : 3000", "https://www.underconsideration.com/brandnew/archives/flipkart_logo_detail_icon.jpg"));

        productAdapter = new OrderHistoryAdapter(this, orders);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(productAdapter);
    }

}
