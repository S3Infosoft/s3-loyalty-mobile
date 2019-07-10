package com.s3infosoft.loyaltyapp;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s3infosoft.loyaltyapp.adapter.ProductAdapter;
import com.s3infosoft.loyaltyapp.model.Product;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Menu menu;
    List<Product> products = new ArrayList<>();
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, usersReference;
    int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("/product");
        usersReference = firebaseDatabase.getReference("/users/uid");
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                points = Integer.parseInt(hashMap.get("points").toString());
                updatePoints(points);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("#DDDD", dataSnapshot.getValue().toString());
                for (DataSnapshot hotelSnapshot: dataSnapshot.getChildren())
                {
                    HashMap<String,Object> list = (HashMap<String, Object>) hotelSnapshot.getValue();

                    products.add(new Product(list.get("name").toString(), list.get("description").toString(), list.get("image_url").toString(), Integer.parseInt(list.get("required_points").toString())));
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(LandingActivity.this, RedeemActivity.class);
                i.putExtra("name", products.get(position).getName());
                i.putExtra("desc", products.get(position).getDesc());
                i.putExtra("logo_url", products.get(position).getLogo_url());
                i.putExtra("points", products.get(position).getPoints());
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //products.add(new Product("Flipkart Gift Vouchers", "Spend for Any thing", "https://www.underconsideration.com/brandnew/archives/flipkart_logo_detail_icon.jpg", 5000));
        //products.add(new Product("Starbucks Gift Vouchers", "Spend for Any thing", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQT-YzeUzQGQtzPR1K88rKh6CfP_mVwNHMB4Y_T8wVSiYvnhgNQr_cQSfg", 5000));

        productAdapter = new ProductAdapter(this, products);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(productAdapter);
    }

    private void updatePoints(int points) {
        MenuItem menuItem = menu.findItem(R.id.action_points);
        menuItem.setTitle(""+points+" PTS");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_points) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_history) {
            Intent i = new Intent(this, OrderHistoryActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_tools) {
            Intent i = new Intent(LandingActivity.this, PurchaseActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
