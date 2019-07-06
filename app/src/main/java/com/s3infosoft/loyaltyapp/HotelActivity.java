package com.s3infosoft.loyaltyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class HotelActivity extends AppCompatActivity {

    FirebaseUser user;
    AlertDialog.Builder builder;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        getSupportActionBar().setTitle("Redeem Points");
        builder = new AlertDialog.Builder(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void buyClick(View view) {
        databaseReference = firebaseDatabase.getReference("/users/a1");
        if (user == null) {
            Toast.makeText(this, "User is Logged in", Toast.LENGTH_SHORT).show();
            //Suppose user has 80000 points
            final int points = 80000;
            final int required_points = 10000;
            if (points <= required_points)
            {
                Map<String, Object> order = new HashMap<String, Object>();
                order.put("amount", required_points);
                final String key = databaseReference.push().getKey();
                databaseReference.child("/orders/"+key).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        databaseReference.child("points").setValue(points-required_points);
                        builder.setMessage("Order Id - "+key)
                                .setCancelable(false)
                                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.setTitle("Order Successfull");
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
                                finish();
                                Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Order Failed");
                alert.show();
                Toast.makeText(this, "User is not Logged in", Toast.LENGTH_SHORT).show();
            }

        } else {
            builder.setMessage("Please Log in to Continue")
                    .setCancelable(false)
                    .setPositiveButton("Log in", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("User not Logged in");
            alert.show();
            Toast.makeText(this, "User is not Logged in", Toast.LENGTH_SHORT).show();
        }
    }
}
