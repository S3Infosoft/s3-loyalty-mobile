package com.icy.chatscreen;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.s3infosoft.loyaltyapp.R;

public class Chuser extends AppCompatActivity {

    Button changeu;
    EditText newun;
    TextView cunu;

    String userid;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chuser);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userid=user.getUid();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setUserId(userid);



        changeu=findViewById(R.id.changebut);
        newun=findViewById(R.id.newusername);
        cunu=findViewById(R.id.currentuser);

        changeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Change Username");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                String uentnewu = newun.getText().toString();
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("firstname").setValue(uentnewu).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Snackbar.make(view,"Username changed Succesfully",Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showdata(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void showdata(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds:dataSnapshot.getChildren()) {
            GetInfoFIre g= new GetInfoFIre();
            g.setCity(ds.child(userid).getValue(GetInfoFIre.class).getCity());
            g.setEmail(ds.child(userid).getValue(GetInfoFIre.class).getEmail());
            g.setFirstname(ds.child(userid).getValue(GetInfoFIre.class).getFirstname());
            g.setLastname(ds.child(userid).getValue(GetInfoFIre.class).getLastname());
            g.setPhonenum(ds.child(userid).getValue(GetInfoFIre.class).getPhonenum());
            Toast.makeText(this,"Your Current Username is  "+ g.getFirstname() , Toast.LENGTH_SHORT).show();
            cunu.setText("Your Current Username "+g.getFirstname());
        }

    }


}
