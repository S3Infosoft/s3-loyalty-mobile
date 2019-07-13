package com.s3infosoft.loyaltyapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PurchaseActivity extends AppCompatActivity implements PaymentResultListener {
    EditText amount;
    FirebaseDatabase db;
    DatabaseReference orderReference, userReference;
    int points;
    AlertDialog.Builder builder;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        amount = (EditText) findViewById(R.id.amount);

        db = FirebaseDatabase.getInstance();
        orderReference = db.getReference("/order_history/uid");
        userReference = db.getReference("/users/uid");

        builder = new AlertDialog.Builder(this);
        fetchPoints();

        Checkout.preload(getApplicationContext());
    }

    public void fetchPoints()
    {
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                points = Integer.parseInt(hashMap.get("points").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void startPayment() {
        Checkout c = new Checkout();
        c.setImage(R.drawable.ic_launcher_foreground);
        final Activity a = this;
        try {
            JSONObject j = new JSONObject();
            j.put("name", "merchant");
            j.put("currency","INR");
            j.put("description", "Order #987949");
            j.put("amount", Integer.parseInt(amount.getText().toString())*100);
            c.open(PurchaseActivity.this,j);
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        fetchPoints();
        Map<String, Object> order = new HashMap<String, Object>();
        order.put("desc","Amount Added");
        order.put("amount", Integer.parseInt(amount.getText().toString()));
        final String key = orderReference.push().getKey();
        orderReference.child("/"+key).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                userReference.child("points").setValue(points+Integer.parseInt(amount.getText().toString()));
                builder.setMessage("Order Id - "+key)
                        .setCancelable(false)
                        .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Purchase Successfully");
                alert.show();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
    }

    public void buyClick(View view) {
        startPayment();
    }
}
