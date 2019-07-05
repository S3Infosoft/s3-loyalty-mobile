package com.icy.chatscreen;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Chphone extends AppCompatActivity {
    EditText et1,et2;
    Button phonechange;

    String userid;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener authStateListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chphone);

        et1=findViewById(R.id.editTextpn);
        et2=findViewById(R.id.editText2pn);
        phonechange = findViewById(R.id.buttonpn);


        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        userid=firebaseUser.getUid();
        databaseReference=firebaseDatabase.getReference();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showdata(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        phonechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String pass1=et1.getText().toString();
                String pass2=et2.getText().toString();
                if(!pass1.equals(pass2)){
                    Snackbar.make(view,"The Phone Numbers does not Match",Snackbar.LENGTH_LONG).show();
                }else{
                    FirebaseDatabase.getInstance().getReference("Users").child(userid).child("phonenum").setValue(pass1);
                }
            }
        });




    }

    private void showdata(DataSnapshot dataSnapshot) {
        for (DataSnapshot ds:dataSnapshot.getChildren()) {
            GetInfoFIre gs= new GetInfoFIre();
            gs.setPhonenum(ds.child(userid).getValue(GetInfoFIre.class).getPhonenum());
            Toast.makeText(this, "Your Current Phone Number is "+gs.getPhonenum(), Toast.LENGTH_SHORT).show();

        }
    }
}