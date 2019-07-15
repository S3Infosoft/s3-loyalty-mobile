package com.icy.chatscreen;

import android.content.Intent;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.s3infosoft.loyaltyapp.LandingActivity;
import com.s3infosoft.loyaltyapp.R;

import java.util.concurrent.TimeUnit;

public class phonenumberlogin extends AppCompatActivity {
    EditText phonenumber, otpnumber;
    Button getotp, signinwithphone;
    FirebaseAuth firebaseAuth;
String samcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonenumberlogin);

        phonenumber = findViewById(R.id.mobilenumbergoes);
        otpnumber = findViewById(R.id.otpgoes);
        getotp = findViewById(R.id.getotpbutton);
        signinwithphone = findViewById(R.id.phonenumbersigninraata);
firebaseAuth=FirebaseAuth.getInstance();

        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobnum = phonenumber.getText().toString();
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
        String userenteredotp=otpnumber.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(samcode, userenteredotp);
        signInWithPhoneAuthCredential(credential);

    }

    private void sendotp(String mobnum) {
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
        }

        @Override
        public void onCodeSent(String verificationId,
                               PhoneAuthProvider.ForceResendingToken token) {
            //super.onCodeSent(verificationId,token);
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
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(phonenumberlogin.this, "You have signed in succesfully", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();
                            Intent i = new Intent(phonenumberlogin.this, LandingActivity.class);
                            startActivity(i);
                            finish();

                            // ...
                        } else {
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
}
