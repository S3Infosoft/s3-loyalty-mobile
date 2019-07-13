package com.icy.chatscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.s3infosoft.loyaltyapp.LandingActivity;
import com.s3infosoft.loyaltyapp.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//GSO
private FirebaseAuth mAuth;
SignInButton gsignin;
GoogleSignInClient mGoogleSignInClient;
//GSO
int RC_SIGN_IN=9001;
TextView signinwithphone;
Button registernew;
    EditText emailet,passet;
ImageButton imgsign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactlogin);


        registernew = findViewById(R.id.Register);
        signinwithphone=findViewById(R.id.phonelogin);
        emailet= findViewById(R.id.usernameet);
        passet = findViewById(R.id.passwordet);
        imgsign = findViewById(R.id.imagesignin);
        imgsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String em= emailet.getText().toString();
                String pass=passet.getText().toString();
                if(em.isEmpty()){
                    Snackbar.make(view,"Email Cannot be empty",Snackbar.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, "Email Cannot be Empty", Toast.LENGTH_SHORT).show();
                    emailet.requestFocus();
                }else if(pass.isEmpty()){
                    Snackbar.make(view,"Password Cannot be empty",Snackbar.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, "Password Cannot be Empty", Toast.LENGTH_SHORT).show();
                    passet.requestFocus();
                }else {
                    mAuth.signInWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                if(mAuth.getCurrentUser().isEmailVerified()){
                                Toast.makeText(MainActivity.this, "Sign In Success", Toast.LENGTH_SHORT).show();

                                FirebaseFirestore db = FirebaseFirestore.getInstance();

                            Snackbar.make(view,"Sign In Succesful",Snackbar.LENGTH_LONG).show();
                            Intent i = new Intent(MainActivity.this,LandingActivity.class);
                            startActivity(i);
                            finish();
                            }
                            else {
                                    mAuth.getCurrentUser().sendEmailVerification();
                                    Snackbar.make(view,"Please Verify Your Email to continue",Snackbar.LENGTH_LONG).show();
                            }
                        }else{
                                Toast.makeText(MainActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();

                            }
                    }});

                }
            }
        });







        gsignin = (SignInButton) findViewById(R.id.googlesign);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);mAuth=FirebaseAuth.getInstance();
        gsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });





                signinwithphone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(MainActivity.this,phonenumberlogin.class);
                        startActivity(i);
                        finish();

                    }
                });

                registernew.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {

FirebaseFirestore db = FirebaseFirestore.getInstance();
Map<String,Object> s3inf = new HashMap<>();
s3inf.put("Sign In","Time value ");
db.collection("First").document("sample123").collection("Regsters").add(s3inf);
                        Intent i = new Intent(MainActivity.this,ListItem.class);
                        startActivity(i);
                        finish();

                    }
                });






    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Si
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Sign in Success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                            if(acct!=null){

                                    String personName = acct.getDisplayName();
                                    String personGivenName = acct.getGivenName();
                                    String personFamilyName = acct.getFamilyName();
                                    String personEmail = acct.getEmail();
                                    String personId = acct.getId();
                                String model = Build.MODEL;
                                String Manuf = Build.MANUFACTURER;
                                String Brand = Build.BRAND;
                                String Device = Build.DEVICE;
                                String hard= Build.HARDWARE;
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                Map<String,Object> userdet=new HashMap<>();
                                userdet.put("personEmail",personEmail);
                                userdet.put("personname",personName);
                                userdet.put("personGivenName",personGivenName);
                                userdet.put("personID",personId);
                                userdet.put("MODEL",model);
                                userdet.put("Manufacturer",Manuf);
                                userdet.put("Brand",Brand);
                                userdet.put("Device",Device);
                                userdet.put("Hardware",hard);
                                long timein=System.currentTimeMillis();
                                Date ds = new Date(timein);
                                String s3un=personName+ds;
                                db.collection("First").document(s3un).set(userdet).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, "Relogin", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            Intent i = new Intent(MainActivity.this, LandingActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // If sign in fails, display a message to th
                            Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }














    @Override
    public void onStart()
    {
        super.onStart();
       // String timetemp = String.valueOf(Timestamp.now());
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            if(currentUser.isEmailVerified()) {
                Intent i = new Intent(MainActivity.this, LandingActivity.class);
                startActivity(i);
                finish();
            }
//            FirebaseAuth.getInstance().signOut();
        }
    }


}
