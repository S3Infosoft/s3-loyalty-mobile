package com.s3infosoft.loyaltyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.s3infosoft.loyaltyapp.db.DatabaseHandler;
import com.s3infosoft.loyaltyapp.model.CartItem;

import java.util.HashMap;
import java.util.Map;

public class RedeemActivity extends AppCompatActivity {

    FirebaseUser user;
    AlertDialog.Builder builder;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int points = 0;
    TextView name, desc, pointsTextView;
    ImageView logo;
    int required_points = 0;
    DatabaseHandler databaseHandler;
    CartItem cartItem;
    FirebaseAnalytics mFirebaseAnalytics;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        getSupportActionBar().setTitle("Redeem Points");
        builder = new AlertDialog.Builder(this);

        databaseHandler = new DatabaseHandler(this);

        name = (TextView) findViewById(R.id.name);
        desc = (TextView) findViewById(R.id.desc);
        logo = (ImageView) findViewById(R.id.logo);
        pointsTextView = (TextView) findViewById(R.id.points);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        Intent i = getIntent();
        required_points = i.getIntExtra("points", -1);

        name.setText(i.getStringExtra("name"));
        desc.setText(i.getStringExtra("desc"));
        pointsTextView.setText(""+required_points+" PTS");

        cartItem = new CartItem(name.getText().toString(), i.getStringExtra("id"), desc.getText().toString(), i.getStringExtra("logo_url"), 1, required_points);

        Glide.with(this).load(i.getStringExtra("logo_url")).into(logo);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, i.getStringExtra("id"));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, i.getStringExtra("name"));
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "product");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setUserId(firebaseUser == null ? "null": firebaseUser.getUid());
        mFirebaseAnalytics.setUserProperty("Open Product", i.getStringExtra("name"));
    }

    public void buyClick(View view) {
        if (databaseHandler.addItem(cartItem))
        {
            Toast.makeText(this, "Item Added to Cart", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Already in a Cart", Toast.LENGTH_SHORT).show();
        }
        /*databaseReference = firebaseDatabase.getReference("/order_history/uid");
        final DatabaseReference usersReference = firebaseDatabase.getReference("/users/uid");
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                points = Integer.parseInt(hashMap.get("points").toString());
                if (points >= required_points)
                {
                    Map<String, Object> order = new HashMap<String, Object>();
                    order.put("desc","Flipkart Gift Vochers");
                    order.put("amount", required_points);
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
                        }
                    });
                }
                else
                {
                    builder.setMessage("You not have enough points")
                            .setCancelable(false)
                            .setPositiveButton("Buy Points", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(RedeemActivity.this, PurchaseActivity.class);
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
                    Toast.makeText(getApplicationContext(), "User is not Logged in", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }
}
