package com.s3infosoft.loyaltyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.s3infosoft.loyaltyapp.adapter.CartAdapter;
import com.s3infosoft.loyaltyapp.adapter.ProductAdapter;
import com.s3infosoft.loyaltyapp.db.DatabaseHandler;
import com.s3infosoft.loyaltyapp.model.CartItem;
import com.s3infosoft.loyaltyapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    List<CartItem> cartItems = new ArrayList<>();
    RecyclerView recyclerView;
    CartAdapter productAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseHandler databaseHandler;
    FirebaseUser firebaseUser;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);

        databaseHandler = new DatabaseHandler(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("products");

        cartItems = databaseHandler.getAllCartItem();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, cartItems.get(viewHolder.getAdapterPosition()).getItem_id());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, cartItems.get(viewHolder.getAdapterPosition()).getItem_name());
                bundle.putInt(FirebaseAnalytics.Param.QUANTITY, cartItems.get(viewHolder.getAdapterPosition()).getQuantity());
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.REMOVE_FROM_CART, bundle);
                mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
                mFirebaseAnalytics.setUserId(firebaseUser == null ? "null": firebaseUser.getUid());
                databaseHandler.removeItem(cartItems.get(viewHolder.getAdapterPosition()).getItem_id());
                cartItems.remove(viewHolder.getAdapterPosition());
                productAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        productAdapter = new CartAdapter(this, cartItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(productAdapter);
    }
}
