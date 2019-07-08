package com.s3infosoft.loyaltyapp;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class PurchaseActivity extends AppCompatActivity implements PaymentResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Checkout.preload(getApplicationContext());
    }

    public void startPayment() {
        Checkout c = new Checkout();
        int payment = 100;
        c.setImage(R.drawable.ic_launcher_foreground);
        final Activity a = this;
        try {
            JSONObject j = new JSONObject();
            j.put("name", "merchant");
            j.put("currency","INR");
            j.put("description", "Order #987949");
            j.put("amount", payment*100);
            c.open(PurchaseActivity.this,j);
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
    }

    public void doPay(View view) {
        startPayment();
    }
}
