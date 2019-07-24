package com.icy.chatscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.s3infosoft.loyaltyapp.R;

import java.util.Map;

public class ChEmail extends AppCompatActivity {
    EditText et1,et2,pass;
    Button emailchange;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chemail);
        et1=findViewById(R.id.editTextem);
        et2=findViewById(R.id.editText2em);
        emailchange = findViewById(R.id.buttonem);
        pass=findViewById(R.id.passwordemailchange);

        emailchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                 final String pass1=et1.getText().toString();
                String pass2=et2.getText().toString();
                String pass3=pass.getText().toString();
                if(!pass1.equals(pass2)){
                    Snackbar.make(view,"The Email Addresses does not Match",Snackbar.LENGTH_LONG).show();
                }else{
                     FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    AuthCredential credential = EmailAuthProvider
                            .getCredential(user.getEmail(), pass3);
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(pass1).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().getSignInMethods().size()==1){
                                Toast.makeText(ChEmail.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                            }else{
                                final FirebaseUser user1=FirebaseAuth.getInstance().getCurrentUser();
                                user1.updateEmail(pass1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            final DocumentReference docRef = db.collection("UserDets").document(FirebaseAuth.getInstance().getUid());
                                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if(task.isSuccessful()){
                                                        DocumentSnapshot documentSnapshot = task.getResult();

                                                        Map<String,Object> sambro= documentSnapshot.getData();
                                                        sambro.put("emailadd",pass1);
                                                        docRef.set(sambro);
                                                    }
                                                }
                                            });

                                            Toast.makeText(ChEmail.this, "Check The Verification Email sent to your new Email Address", Toast.LENGTH_SHORT).show();
                                            user1.sendEmailVerification();
                                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").setValue(pass1);
                                            CountDownTimer c = new CountDownTimer(3000,1000) {
                                                @Override
                                                public void onTick(long millisUntilFinished) {

                                                }

                                                @Override
                                                public void onFinish() {
                                                    startActivity(new Intent(ChEmail.this,MainActivity.class));
                                                }
                                            }.start();

                                            FirebaseAuth.getInstance().signOut();
                                        }else{
                                            Snackbar.make(view,"Error Occured",Snackbar.LENGTH_LONG).show();

                                        }
                            }
                        });
                            }



                            }
                        }});
                    }
                                }
                            });
                }
                                }
                            });
                        }


            }





