package com.s3infosoft.loyaltyapp;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s3infosoft.loyaltyapp.adapter.ProductAdapter;
import com.s3infosoft.loyaltyapp.adapter.ReservationHistoryAdapter;
import com.s3infosoft.loyaltyapp.adapter.SpecialDealAdapter;
import com.s3infosoft.loyaltyapp.model.Product;
import com.s3infosoft.loyaltyapp.model.SpecialDeal;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Menu menu;
    List<Product> products = new ArrayList<>();
    List<SpecialDeal> specialDeals = new ArrayList<>();
    RecyclerView recyclerView, recyclerView1;
    ProductAdapter productAdapter;
    SpecialDealAdapter specialDealAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, usersReference, specialDealReference;
    int points;
    String userLevel;
    ImageView profile_img;
    TextView user_level;
    FirebaseUser firebaseUser;
    private FirebaseAnalytics mFirebaseAnalytics;
    private ProgressBar progressBar1, progressBar2;
    List<String> keys;

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

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setUserId(firebaseUser == null ? "null": firebaseUser.getUid());

        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);

        profile_img = (ImageView) findViewById(R.id.profile_img);

        if (firebaseUser == null)
        {
            Glide.with(this).load("https://swopstakes.com/wp-content/themes/uncode-child-ss/images/user-profile.png").circleCrop().into(profile_img);
        }
        else
        {
            Glide.with(this).load(firebaseUser.getPhotoUrl()).circleCrop().into(profile_img);
        }

        user_level = (TextView) findViewById(R.id.user_level);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        recyclerView1 = (RecyclerView) findViewById(R.id.recyler_view1);

        firebaseDatabase = FirebaseDatabase.getInstance();

        keys = new ArrayList<>();

        databaseReference = firebaseDatabase.getReference("/product");
        usersReference = firebaseDatabase.getReference("/users/uid");
        specialDealReference = firebaseDatabase.getReference("/special_deals");

        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                points = Integer.parseInt(hashMap.get("points").toString());
                userLevel = hashMap.get("level").toString();
                user_level.setText(userLevel);
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
                    keys.add(hotelSnapshot.getKey());
                    HashMap<String,Object> list = (HashMap<String, Object>) hotelSnapshot.getValue();

                    products.add(new Product(list.get("name").toString(), list.get("description").toString(), list.get("image_url").toString(), Integer.parseInt(list.get("required_points").toString())));
                    productAdapter.notifyDataSetChanged();
                    progressBar1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        specialDealReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot hotelSnapshot: dataSnapshot.getChildren())
                {
                    Log.v("#DDDD", dataSnapshot.getValue().toString());
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

                    specialDeals.add(new SpecialDeal(list.get("name").toString(), list.get("description").toString(), image_urls, 8000));
                    //Log.v("#####", list.get("name")+" "+list.get("metadata").toString());
                    specialDealAdapter.notifyDataSetChanged();
                    progressBar2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, specialDeals.get(position).getName());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, specialDeals.get(position).getName());
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Special Deals");
                bundle.putInt(FirebaseAnalytics.Param.VALUE, specialDeals.get(position).getPoints());
                bundle.putString(FirebaseAnalytics.Param.VIRTUAL_CURRENCY_NAME, "Points");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                mFirebaseAnalytics.setUserProperty("Choose Special Deals", specialDeals.get(position).getName());
                Intent i = new Intent(LandingActivity.this, RedeemActivity.class);
                i.putExtra("name", products.get(position).getName());
                i.putExtra("desc", products.get(position).getDesc());
                i.putExtra("logo_url", products.get(position).getLogo_url());
                i.putExtra("points", products.get(position).getPoints());
                i.putExtra("id", keys.get(position));
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerView1.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView1, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(LandingActivity.this, ReservationActivity.class);
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(productAdapter);

        specialDealAdapter = new SpecialDealAdapter(this, specialDeals);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(specialDealAdapter);
    }

    private void updatePoints(int points, Menu menu) {
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
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.landing, menu);
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                points = Integer.parseInt(hashMap.get("points").toString());
                userLevel = hashMap.get("level").toString();
                user_level.setText(userLevel);
                updatePoints(points, menu);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_points) {
            return true;
        }
        else if(id == R.id.action_cart) {
            Intent i = new Intent(this, CartActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            Intent i = new Intent(this, OrderHistoryActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_tools) {
            Intent i = new Intent(LandingActivity.this, PurchaseActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_reservation_history) {
            Intent i = new Intent(LandingActivity.this, ReservationHistoryActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
