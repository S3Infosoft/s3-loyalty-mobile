package com.icy.chatscreen;

import android.graphics.Color;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AcctDet extends AppCompatActivity {


    TextView alldet;

    String userid;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userid=user.getUid();

        alldet=findViewById(R.id.textview);


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
            Toast.makeText(this,"Your User Details Appear Here ", Toast.LENGTH_SHORT).show();
            alldet.setTextColor(Color.BLACK);
            alldet.setText("\n\n\n\n\t\tFirst Name  "+g.getFirstname()+"  \n"+
                    "\t\tLast Name  "+g.getLastname()+"  \n"+
                            "\t\tCity  "+g.getCity()+"  \n"+
                            "\t\tPhone Number  "+g.getPhonenum()+"  \n"+
                            "\t\tEmail Address "+g.getEmail()+"  \n"

                    );

        }

    }


}
