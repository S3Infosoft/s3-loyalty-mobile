package com.icy.chatscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.s3infosoft.loyaltyapp.R;

public class Chpass extends AppCompatActivity {

    EditText et1,et2;
    Button passchange;
    FirebaseAuth firebaseAuth;
    FirebaseAnalytics mFirebaseAnalytics;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chpass);

        firebaseAuth = FirebaseAuth.getInstance();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setUserId(firebaseAuth.getCurrentUser()==null?"null":firebaseAuth.getCurrentUser().getUid());

        et1=findViewById(R.id.editText);
    et2=findViewById(R.id.editText2);
    passchange = findViewById(R.id.button);

    passchange.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            String pass1=et1.getText().toString();
            String pass2=et2.getText().toString();
            if(!pass1.equals(pass2)){
                Snackbar.make(view,"The Passwords does not Match",Snackbar.LENGTH_LONG).show();
            }else{
                FirebaseUser user12= FirebaseAuth.getInstance().getCurrentUser();
                user12.updatePassword(pass1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            Bundle bundle = new Bundle();
                            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Change Password");
                            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


                            Snackbar.make(view,"Password has been Changed Succesfully",Snackbar.LENGTH_LONG).show();
                            CountDownTimer c = new CountDownTimer(3000,1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    startActivity(new Intent(Chpass.this,com.icy.chatscreen.MainActivity.class));
                                }
                            }.start();
                        }else{
                            Snackbar.make(view,"Some Error Ocuured , Try again Later"+"\n"+task.getException(),Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    });


    }
}