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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.s3infosoft.loyaltyapp.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Chphone extends AppCompatActivity {
    EditText et1,et2,otpenter;
    Button phonechange,getotpbutt;
    String thisistoverify,thisislolab;
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

        getotpbutt = findViewById(R.id.getoptchange2);
        otpenter = findViewById(R.id.editTextotp2pn);
        et1=findViewById(R.id.editTextpn);
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

        getotpbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass1=et1.getText().toString();

                    SendOtp(pass1);


                }
        });

        phonechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String pass1=et1.getText().toString();

String entredotp= otpenter.getText().toString();
//PhoneAuthCredential credential = PhoneAuthProvider.getCredential(thisistoverify,entredotp);
//String justtry =credential.getSmsCode();
//Toast.makeText(Chphone.this, String.valueOf(justtry), Toast.LENGTH_SHORT).show();


if(entredotp.equals(thisislolab)){
                    FirebaseDatabase.getInstance().getReference("Users").child(userid).child("phonenum").setValue(pass1);

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final DocumentReference docRef = db.collection("UserDets").document(firebaseAuth.getUid());
    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if(task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();
                Map<String,Object> sambro= documentSnapshot.getData();
                sambro.put("phonenum",pass1);
                docRef.set(sambro);
            }
        }
    });


}else{

    Toast.makeText(Chphone.this, "You Entered Wrong Sorry", Toast.LENGTH_SHORT).show();

}



            }
        });




    }

    private void SendOtp(String pass1) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                pass1,
                60,
                TimeUnit.SECONDS,
                this,
                mCallBacks
        );
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                thisislolab = phoneAuthCredential.getSmsCode();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            e.printStackTrace();

            Toast.makeText(Chphone.this, "SomeThing went wrong", Toast.LENGTH_SHORT).show();
            Toast.makeText(Chphone.this, String.valueOf(e.getStackTrace()+"\n"+e.getMessage()), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            thisistoverify = s;
            Toast.makeText(Chphone.this, "Code Sent Sucesfully", Toast.LENGTH_SHORT).show();
        }
    };

    private void showdata(DataSnapshot dataSnapshot) {
        DataSnapshot ds = dataSnapshot.child("Users");
        GetInfoFIre gs= new GetInfoFIre();
        gs.setPhonenum(ds.child(userid).getValue(GetInfoFIre.class).getPhonenum());
        Toast.makeText(this, "Your Current Phone Number is "+gs.getPhonenum(), Toast.LENGTH_SHORT).show();

    }
}