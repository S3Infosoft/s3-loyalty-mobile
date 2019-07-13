package com.s3infosoft.loyaltyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.s3infosoft.loyaltyapp.adapter.CartAdapter;
import com.s3infosoft.loyaltyapp.adapter.ProductAdapter;
import com.s3infosoft.loyaltyapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    List<Product> products = new ArrayList<>();
    RecyclerView recyclerView;
    CartAdapter productAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("products");

        /*databaseReference.addValueEventListener(new ValueEventListener() {
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

                    hotels.add(new Hotel(list.get("name").toString(), list.get("address").toString(), list.get("manager_email").toString(), list.get("email").toString(), new Metadata(), list.get("logo_url").toString(), list.get("manager_name").toString(), image_urls));
                    Log.v("#####", list.get("name")+" "+list.get("metadata").toString());
                    hotelsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(CartActivity.this, SpecialDealsActivity.class);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        products.add(new Product("Flipkart Gift Vouchers", "Spend for Any thing you want to Buy", "https://www.underconsideration.com/brandnew/archives/flipkart_logo_detail_icon.jpg", 10000));

        productAdapter = new CartAdapter(this, products);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(productAdapter);
    }
}
