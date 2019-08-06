package com.s3infosoft.loyaltyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.s3infosoft.loyaltyapp.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationActivity extends AppCompatActivity {

    TextView hotel_name, hotel_address, points;
    ProgressBar progressBar;
    ViewPager viewPager;
    Button selectDateBtn;
    DatabaseReference usersReference, databaseReference, reservation_history;
    ViewPagerAdapter viewPagerAdapter;
    EditText noOfPerson;
    String date;
    AlertDialog.Builder builder;
    FirebaseUser firebaseUser;
    int userPoints;
    int required_points;
    String hotel_id;
    public LinearLayout sliderDotspanel;
    private int images[] = {R.drawable.common_full_open_on_phone, R.drawable.ic_launcher_background };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        hotel_name = (TextView) findViewById(R.id.hotel_name);
        hotel_address = (TextView) findViewById(R.id.hotel_address);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        points = (TextView) findViewById(R.id.points);
        selectDateBtn = (Button) findViewById(R.id.selectDateBtn);
        noOfPerson = (EditText) findViewById(R.id.noOfPerson);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        builder = new AlertDialog.Builder(this);

        date = "5/8/2019";

        Intent i = getIntent();

        required_points = i.getIntExtra("points", -1);
        hotel_id = i.getStringExtra("hotel_id");
        userPoints = i.getIntExtra("userPoints", -1);

        points.setText(""+required_points+" PTS");

        databaseReference = FirebaseDatabase.getInstance().getReference("/hotels/"+hotel_id);
        reservation_history = FirebaseDatabase.getInstance().getReference("/reservation_history/"+firebaseUser.getUid());
        usersReference = FirebaseDatabase.getInstance().getReference("/Users/"+firebaseUser.getUid());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                hotel_name.setText(hashMap.get("name").toString());
                hotel_address.setText(hashMap.get("address").toString());

                HashMap<String,Object> list = (HashMap<String,Object>) hashMap.get("images");

                List<String> image_urls = new ArrayList<String>();
                for (Map.Entry<String,Object> map : list.entrySet())
                {
                    HashMap<String,Object> m = (HashMap<String, Object>) map.getValue();
                    Log.v("$$$$$", m.get("image_url").toString());
                    image_urls.add(m.get("image_url").toString());
                }
                viewPagerAdapter = new ViewPagerAdapter(getApplicationContext(), images, image_urls);
                viewPager.setAdapter(viewPagerAdapter);
                progressBar.setVisibility(View.GONE);

                final int dotscount = viewPagerAdapter.getCount();
                final ImageView[] dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView(getApplicationContext());
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 8, 8, 8);

                    sliderDotspanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                        for(int i = 0; i< dotscount; i++){
                            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                        }

                        dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void bookNow(View view) {
        if (noOfPerson.getText().length() >= 1 && userPoints >= required_points)
        {
            Map<String, Object> reser = new HashMap<String, Object>();
            reser.put("hotel_name", hotel_name.getText().toString());
            reser.put("hotel_id", hotel_id);
            reser.put("amount", Integer.parseInt(noOfPerson.getText().toString())*required_points);
            reser.put("booking_date", date);
            final String key = reservation_history.push().getKey();
            reservation_history.child("/"+key).setValue(reser).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                            //Toast.makeText(ReservationActivity.this, ""+hashMap.get("points").toString(), Toast.LENGTH_SHORT).show();
                            usersReference.child("points").setValue(Integer.parseInt(hashMap.get("points").toString())-required_points);
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

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    /*Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, cartItem.getItem_id());
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, cartItem.getItem_name());
                    bundle.putInt(FirebaseAnalytics.Param.QUANTITY, cartItem.getQuantity());
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, bundle);*/
                }
            });
            //Toast.makeText(this, ""+date.toString(), Toast.LENGTH_SHORT).show();
        }
        else
        {
            builder.setMessage("Buy more points to book hotels")
                    .setCancelable(false)
                    .setPositiveButton("Buy Points", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(ReservationActivity.this, PurchaseActivity.class);
                            startActivity(i);
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("Insufficent Points");
            alert.show();
        }
    }

    public void selectDate(View view) {
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        date = ""+dayOfMonth+"/"+monthOfYear+"/"+year;

                        selectDateBtn.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
