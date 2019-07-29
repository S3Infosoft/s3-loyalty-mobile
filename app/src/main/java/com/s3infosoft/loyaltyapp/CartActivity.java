package com.s3infosoft.loyaltyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s3infosoft.loyaltyapp.adapter.CartAdapter;
import com.s3infosoft.loyaltyapp.adapter.ProductAdapter;
import com.s3infosoft.loyaltyapp.db.DatabaseHandler;
import com.s3infosoft.loyaltyapp.model.CartItem;
import com.s3infosoft.loyaltyapp.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    List<CartItem> cartItems = new ArrayList<>();
    RecyclerView recyclerView;
    CartAdapter productAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseHandler databaseHandler;
    FirebaseUser firebaseUser;
    FirebaseAnalytics mFirebaseAnalytics;
    AlertDialog.Builder builder;
    int points = 0;
    int required_points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setUserId(firebaseUser == null ? "null": firebaseUser.getUid());

        recyclerView = (RecyclerView) findViewById(R.id.recyler_view);

        databaseHandler = new DatabaseHandler(this);

        firebaseDatabase = FirebaseDatabase.getInstance();

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

        builder = new AlertDialog.Builder(this);
    }

    public void buyNow(View view)
    {
        databaseReference = firebaseDatabase.getReference("/order_history/uid");
        final DatabaseReference usersReference = firebaseDatabase.getReference("/users/uid");
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                points = Integer.parseInt(hashMap.get("points").toString());

                for (CartItem cartItem : databaseHandler.getAllCartItem())
                {
                    required_points = required_points + cartItem.getAmount();
                }

                if (points >= required_points)
                {
                    for (final CartItem cartItem : databaseHandler.getAllCartItem())
                    {
                        Map<String, Object> order = new HashMap<String, Object>();
                        order.put("desc",cartItem.getItem_desc());
                        order.put("amount", cartItem.getAmount());
                        final String key = databaseReference.push().getKey();
                        databaseReference.child("/"+key).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                usersReference.child("points").setValue(points-required_points);
                                builder.setMessage("Order Id - "+key)
                                        .setCancelable(false)
                                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                finish();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.setTitle("Order Successfully");
                                alert.show();
                                Bundle bundle = new Bundle();
                                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, cartItem.getItem_id());
                                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, cartItem.getItem_name());
                                bundle.putInt(FirebaseAnalytics.Param.QUANTITY, cartItem.getQuantity());
                                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, bundle);
                            }
                        });
                    }
                }
                else
                {
                    builder.setMessage("You not have enough points")
                            .setCancelable(false)
                            .setPositiveButton("Buy Points", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(CartActivity.this, PurchaseActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("Order Failed");
                    alert.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
