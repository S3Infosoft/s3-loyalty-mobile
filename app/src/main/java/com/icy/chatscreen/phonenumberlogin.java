package com.icy.chatscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.s3infosoft.loyaltyapp.LandingActivity;
import com.s3infosoft.loyaltyapp.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class phonenumberlogin extends AppCompatActivity {
    EditText phonenumber, otpnumber;
    Button getotp, signinwithphone;
    FirebaseAuth firebaseAuth;
    String samcode, mobnum;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonenumberlogin);


        firebaseAuth = FirebaseAuth.getInstance();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setUserId(firebaseAuth.getCurrentUser()==null?"null":firebaseAuth.getCurrentUser().getUid());

        phonenumber = findViewById(R.id.mobilenumbergoes);
        otpnumber = findViewById(R.id.otpgoes);
        getotp = findViewById(R.id.getotpbutton);
        signinwithphone = findViewById(R.id.phonenumbersigninraata);

        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobnum = phonenumber.getText().toString();
                if (mobnum.isEmpty() || mobnum.length() < 10) {
                    phonenumber.setError("Please Enter 10 Digit Mobile Number");
                    Snackbar.make(view, "Please Enter 10 digit mobile number", Snackbar.LENGTH_LONG).show();
                } else {
                    sendotp(mobnum);
                }
            }
        });
        signinwithphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyPhone();
            }
        });


    }

    private void verifyPhone() {
        String userenteredotp = otpnumber.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(samcode, userenteredotp);
        signInWithPhoneAuthCredential(credential);

    }

    private void sendotp(String mobnum) {
        mobnum = "+91"+mobnum;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobnum,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacksPhoneAuthActivity.java

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential credential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Toast.makeText(phonenumberlogin.this, "verification failed", Toast.LENGTH_SHORT).show();
            Toast.makeText(phonenumberlogin.this, e.getStackTrace() + "\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String verificationId,
                               PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(verificationId, token);
            samcode = verificationId;
            Toast.makeText(phonenumberlogin.this, "Code Sent Succesfully", Toast.LENGTH_SHORT).show();

        }

    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.METHOD, "phone");
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);

                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(phonenumberlogin.this, "You have signed in succesfully", Toast.LENGTH_SHORT).show();
                            storedata();
                            Intent i = new Intent(phonenumberlogin.this, LandingActivity.class);
                            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref",0);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("sett",2);
                            editor.commit();startActivity(i);
                            finish();

                            FirebaseUser user = task.getResult().getUser();
                          /*  FirebaseAuth.getInstance().createUserWithEmailAndPassword(mobnum + "@gmail.com", "1111111@").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(phonenumberlogin.this, "Success", Toast.LENGTH_SHORT).show();



                                        // ...
                                    }
                                    */
                        }else {
                                        // Sign in failed, display a message and update the UI
                                        Toast.makeText(phonenumberlogin.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                            // The verification code entered was invalid
                                            Toast.makeText(phonenumberlogin.this, "Invalid Code Entered", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }






    public void storedata(){
        String model = Build.MODEL;
        String Manuf = Build.MANUFACTURER;
        String Brand = Build.BRAND;
        String Device = Build.DEVICE;
        String hard= Build.HARDWARE;
        Map<String,Object> userdet=new HashMap<>();
        userdet.put("MODEL",model);
        userdet.put("Manufacturer",Manuf);
        userdet.put("Brand",Brand);
        userdet.put("Device",Device);
        userdet.put("Hardware",hard);
        long timein=System.currentTimeMillis();
        Date ds = new Date(timein);
        userdet.put("userid",FirebaseAuth.getInstance().getUid());
        userdet.put("Type","Phone");
        userdet.put("LoyalLevel","Bronze");
        userdet.put("points","100");
        userdet.put("address","##");
        userdet.put("emailadd","##");
        userdet.put("phonenum",mobnum);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("UserDets").document(firebaseAuth.getUid()).set(userdet).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(phonenumberlogin.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(phonenumberlogin.this, "Issue", Toast.LENGTH_SHORT).show();
            }
        });



        final DocumentReference docRef = db.collection("Authentication Logs").document(FirebaseAuth.getInstance().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        final Map<String, Object> sambro = documentSnapshot.getData();
                        String st = String.valueOf(new Date(System.currentTimeMillis()));
                        sambro.put(st, "Login Time");
                        sambro.put("type", "phone");
                        sambro.put("phone_number", mobnum);
/*
                                                   db.collection("UserDets").document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                           if(task.isSuccessful()){
                                                               DocumentSnapshot documentSnapshot1=task.getResult();
                                                               sambro.put("usernamedet",documentSnapshot1.getString("username"));

                                                           }
                                                       }
                                                   });
*/                                                               docRef.set(sambro);

                    } else {
                        final Map<String, Object> sambro = new HashMap<>();
                        String st = String.valueOf(new Date(System.currentTimeMillis()));
                        sambro.put(st, "Login Time");
                        sambro.put("type", "phone");
                        sambro.put("phone_number", mobnum);
                                                 /*  db.collection("UserDets").document(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                           if(task.isSuccessful()){
                                                               DocumentSnapshot documentSnapshot1=task.getResult();
                                                               sambro.put("usernamedet",documentSnapshot1.getString("username"));
                                                               docRef.set(sambro);

                                                           }
                                                       }
                                                   });
*/                                                               docRef.set(sambro);


                    }


                } else {
                    Toast.makeText(phonenumberlogin.this, "Issue ", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}


