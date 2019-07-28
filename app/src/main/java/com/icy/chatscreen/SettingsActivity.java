package com.icy.chatscreen;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.s3infosoft.loyaltyapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    List<String> l1;
ArrayAdapter<String> aaa;
public int sett;
FirebaseAuth mAuth;
FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingspage);


        mAuth = FirebaseAuth.getInstance();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setUserId(mAuth.getCurrentUser()==null?"null":mAuth.getCurrentUser().getUid());

        ListView lv= findViewById(R.id.listviewsettings);
    l1= new ArrayList<String>();

        SharedPreferences preferences=getApplicationContext().getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor=preferences.edit();
        sett=preferences.getInt("sett",0);


    //google
    if(sett==1) {
        l1.add("Change Phone Number");
        l1.add("Account Details");
        l1.add("Logout");
        l1.add("Chat With US");
        l1.add("Mail Us for Suggestions / Issues / Complaints");
        l1.add("Privacy Policy and Terms of Service");
    }
    //mobile
       else if(sett==2) {
            l1.add("Change Email Address");
//            l1.add("Change Phone Number");
            l1.add("Account Details");
            l1.add("Logout");
            l1.add("Chat With US");
            l1.add("Mail Us for Suggestions / Issues / Complaints");
            l1.add("Privacy Policy and Terms of Service");
        }

    else if(sett==3) {
        l1.add("Change Password");
        l1.add("Change Email Address");
        l1.add("Change Phone Number");
        l1.add("Account Details");
        l1.add("Logout");
        l1.add("Chat With US");
        l1.add("Mail Us for Suggestions / Issues / Complaints");
        l1.add("Privacy Policy and Terms of Service");
    }
     aaa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,l1);
     lv.setAdapter(aaa);

     lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(final AdapterView<?> adapterView, View view, int i, long l) {

             Bundle bundle = new Bundle();
             bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, l1.get(i).toString());
             mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

             if(sett==1) {
                 i = i + 3;
                 if (i == 1) {
//                 Intent iam = new Intent(SettingsActivity.this,Chpass.class);
//                 startActivity(iam);
                     try {
                         FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if (task.isSuccessful()) {
                                     Snackbar.make(adapterView, "Password Reset Link has been sent", Snackbar.LENGTH_LONG).show();
                                 } else {
                                     Snackbar.make(adapterView, "Some Error Occured", Snackbar.LENGTH_SHORT).show();
                                     ;
                                 }
                             }
                         });

                     } catch (Exception e) {
                         Toast.makeText(SettingsActivity.this, "You can Only Change password if you are registered", Toast.LENGTH_SHORT).show();
                     }

                 } else if (i == 2) {
                     Intent iam = new Intent(SettingsActivity.this, ChEmail.class);
                     startActivity(iam);
                 } else if (i == 3) {
                     Intent iam = new Intent(SettingsActivity.this, Chphone.class);
                     startActivity(iam);
                 } else if (i == 4) {
                     Intent iam = new Intent(SettingsActivity.this, AcctInfActivity.class);
                     startActivity(iam);
                 } else if (i == 5) {
                     FirebaseAuth.getInstance().signOut();
                     GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                             .requestEmail()
                             .build();
                     GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(SettingsActivity.this, gso);
                     mGoogleSignInClient.signOut();
                     Intent ij = new Intent(SettingsActivity.this, MainActivity.class);
                     startActivity(ij);
                     finish();

                 } else if (i == 6) {
                     Intent ij = new Intent(SettingsActivity.this, samplechatbot.class);
                     startActivity(ij);

                 } else if (i == 7) {
                     try {
                         Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "bloglover19920002@gmail.com"));
                         intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestions / Issues / Complaints");
                         intent.putExtra(Intent.EXTRA_TEXT, " --- S 3 I N F O S O F T --- ");
                         startActivity(intent);
                     } catch (ActivityNotFoundException e) {
                         Toast.makeText(SettingsActivity.this, "something went wrong try again later", Toast.LENGTH_SHORT).show();
                     }

                 } else if (i == 8) {
                 }
             }




             if(sett==2) {
                 i = i + 2;
                 if (i == 1) {
//                 Intent iam = new Intent(SettingsActivity.this,Chpass.class);
//                 startActivity(iam);
                     try {
                         FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if (task.isSuccessful()) {
                                     Snackbar.make(adapterView, "Password Reset Link has been sent", Snackbar.LENGTH_LONG).show();
                                 } else {
                                     Snackbar.make(adapterView, "Some Error Occured", Snackbar.LENGTH_SHORT).show();
                                     ;
                                 }
                             }
                         });

                     } catch (Exception e) {
                         Toast.makeText(SettingsActivity.this, "You can Only Change password if you are registered", Toast.LENGTH_SHORT).show();
                     }

                 } else if (i == 2) {
                     Intent iam = new Intent(SettingsActivity.this, ChEmail.class);
                     startActivity(iam);
                 } else if (i == 0) {
                     Intent iam = new Intent(SettingsActivity.this, Chphone.class);
                     startActivity(iam);
                 } else if (i == 3) {
                     Intent iam = new Intent(SettingsActivity.this, AcctInfActivity.class);
                     startActivity(iam);
                 } else if (i == 4) {
                     FirebaseAuth.getInstance().signOut();
                     GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                             .requestEmail()
                             .build();
                     GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(SettingsActivity.this, gso);
                     mGoogleSignInClient.signOut();
                     Intent ij = new Intent(SettingsActivity.this, MainActivity.class);
                     startActivity(ij);
                     finish();

                 } else if (i == 5) {
                     Intent ij = new Intent(SettingsActivity.this, samplechatbot.class);
                     startActivity(ij);

                 } else if (i == 6) {
                     try {
                         Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "bloglover19920002@gmail.com"));
                         intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestions / Issues / Complaints");
                         intent.putExtra(Intent.EXTRA_TEXT, " --- S 3 I N F O S O F T --- ");
                         startActivity(intent);
                     } catch (ActivityNotFoundException e) {
                         Toast.makeText(SettingsActivity.this, "something went wrong try again later", Toast.LENGTH_SHORT).show();
                     }

                 } else if (i == 7) {
                 }
             }



         if(sett==3) {
             i = i + 1;
             if (i == 1) {
//                 Intent iam = new Intent(SettingsActivity.this,Chpass.class);
//                 startActivity(iam);
                 try {
                     FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()) {
                                 Snackbar.make(adapterView, "Password Reset Link has been sent", Snackbar.LENGTH_LONG).show();
                             } else {
                                 Snackbar.make(adapterView, "Some Error Occured", Snackbar.LENGTH_SHORT).show();
                                 ;
                             }
                         }
                     });

                 } catch (Exception e) {
                     Toast.makeText(SettingsActivity.this, "You can Only Change password if you are registered", Toast.LENGTH_SHORT).show();
                 }

             } else if (i == 2) {
                 Intent iam = new Intent(SettingsActivity.this, ChEmail.class);
                 startActivity(iam);
             } else if (i == 3) {
                 Intent iam = new Intent(SettingsActivity.this, Chphone.class);
                 startActivity(iam);
             } else if (i == 4) {
                 Intent iam = new Intent(SettingsActivity.this, AcctInfActivity.class);
                 startActivity(iam);
             } else if (i == 5) {
                 FirebaseAuth.getInstance().signOut();
                 GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                         .requestEmail()
                         .build();
                 GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(SettingsActivity.this, gso);
                 mGoogleSignInClient.signOut();
                 Intent ij = new Intent(SettingsActivity.this, MainActivity.class);
                 startActivity(ij);
                 finish();

             } else if (i == 6) {
                 Intent ij = new Intent(SettingsActivity.this, samplechatbot.class);
                 startActivity(ij);

             } else if (i == 7) {
                 try {
                     Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "bloglover19920002@gmail.com"));
                     intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestions / Issues / Complaints");
                     intent.putExtra(Intent.EXTRA_TEXT, " --- S 3 I N F O S O F T --- ");
                     startActivity(intent);
                 } catch (ActivityNotFoundException e) {
                     Toast.makeText(SettingsActivity.this, "something went wrong try again later", Toast.LENGTH_SHORT).show();
                 }

             } else if (i == 8) {
             }
         }




















         }
     });



    }
}




















/*          FirebaseFirestore db = FirebaseFirestore.getInstance();

                 final DocumentReference docRef = db.collection("First").document("sample123").collection("Regsters").document(FirebaseAuth.getInstance().getUid());
//                 CollectionReference colref=db.collection("First").document("sample123").collection("Regsters");
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot documentSnapshot = task.getResult();

                                Map<String,Object> sambro= documentSnapshot.getData();
                                String st = String.valueOf(new Date(System.currentTimeMillis()));
                                sambro.put("Time","Now");
                                sambro.put(st,"Login Time");
                                docRef.set(sambro);
                                if(documentSnapshot.exists()){
                                    String dat = String.valueOf(documentSnapshot.getData());
                                    Toast.makeText(SettingsActivity.this, dat, Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(SettingsActivity.this, "No Document As such", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
       */