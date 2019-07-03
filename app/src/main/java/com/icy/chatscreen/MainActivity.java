package com.icy.chatscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
/*
    ListView listView;
    EditText editText;
    Button button;
    List<String> l1;
    ArrayAdapter<String> adapter1;
*/

//GSO
FirebaseAuth mAuth;
SignInButton gsi;
GoogleSignInClient mGoogleSignInClient;
//GSO

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
                            if(mAuth.getCurrentUser().isEmailVerified()){
                                if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Sign In Success", Toast.LENGTH_SHORT).show();
                            Snackbar.make(view,"Sign In Succesful",Snackbar.LENGTH_LONG).show();
                            Intent i = new Intent(MainActivity.this,samplechatbot.class);
                            startActivity(i);
                            finish();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                                Snackbar.make(view,"Please Verify Your Email to continue",Snackbar.LENGTH_LONG).show();
                            }
                    }});

                }
            }
        });






        mAuth=FirebaseAuth.getInstance();
        gsi = findViewById(R.id.gsi);
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        gsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });














     /*   listView = findViewById(R.id.recyv);
        l1= new ArrayList<String>();
        editText = findViewById(R.id.message);
        button=findViewById(R.id.sendbut);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg= editText.getText().toString();
                l1.add("You :  "+msg);
                xyz x = new xyz();
                String ans=x.xyz1(msg);
                l1.add("S3Bot :  "+ans);
                listView.setAdapter(adapter1);
                editText.setText("");
            }
        });

        adapter1 = new ArrayAdapter<String>(this,R.layout.messagereceived,R.id.receivemsg,l1);
        l1.add(" S3Bot : Hello , I am Your Virtual Assistant , you can ask me the Queries related to this application.");
        listView.setAdapter(adapter1);
*/

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
                        Intent i = new Intent(MainActivity.this,ListItem.class);
                        startActivity(i);
                        finish();

                    }
                });






    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 9001);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 9001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                // ...
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
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Sign In Success", Toast.LENGTH_SHORT).show();;
                            Intent i = new Intent(MainActivity.this,samplechatbot.class);
                            startActivity(i);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }















    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            FirebaseAuth.getInstance().signOut();
        }
    }


}
