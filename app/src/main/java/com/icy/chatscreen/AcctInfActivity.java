package com.icy.chatscreen;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.InputType;
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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.s3infosoft.loyaltyapp.R;

import java.util.Map;

public class AcctInfActivity extends AppCompatActivity {


    TextView UerIdautocompletetextcityai,loyallevelai,pointsai,curai;
    EditText EmailAdresslnetai,Addressemailetai,Phonenumberfnetai;
Button savebutton,editbutton;
int sett;
    String userid;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_detailslt);

        Phonenumberfnetai = findViewById(R.id.fnetai);
        UerIdautocompletetextcityai = findViewById(R.id.autocompletetextcityai);
        EmailAdresslnetai = findViewById(R.id.lnetai);
        Addressemailetai = findViewById(R.id.emailetai);
        loyallevelai = findViewById(R.id.loyallevelai);
        pointsai = findViewById(R.id.pointsai);
        curai = findViewById(R.id.inrcurai);
        savebutton=findViewById(R.id.registerbuttonai);
        editbutton=findViewById(R.id.editbuttonai);


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        userid=user.getUid();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setUserId(firebaseAuth.getCurrentUser()==null?"null":firebaseAuth.getCurrentUser().getUid());

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailaddt= EmailAdresslnetai.getText().toString();
                 String phonenumt= Phonenumberfnetai.getText().toString();
                 String addressmt= Addressemailetai.getText().toString();
                if(emailaddt.isEmpty()){
                    emailaddt="##";
                }if(phonenumt.isEmpty()){
                    phonenumt="##";
                }if(addressmt.isEmpty()){
                    addressmt="##";
                }
                Toast.makeText(AcctInfActivity.this, "Updated", Toast.LENGTH_LONG).show();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final DocumentReference docRef = db.collection("UserDets").document(firebaseAuth.getUid());
                final String finalEmailaddt = emailaddt;
                final String finalPhonenumt = phonenumt;
                final String finalAddressmt = addressmt;
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();

                            Map<String,Object> sambro= documentSnapshot.getData();
                            sambro.put("emailadd", finalEmailaddt);
                            sambro.put("phonenum", finalPhonenumt);
                            sambro.put("address", finalAddressmt);
                            docRef.set(sambro);
                        }
                    }
                });
            }
        });


        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sett==1){
                    Addressemailetai.setInputType(InputType.TYPE_CLASS_TEXT);
                    Addressemailetai.setBackgroundResource(android.R.drawable.edit_text);
                    Phonenumberfnetai.setInputType(InputType.TYPE_CLASS_TEXT);
                    Phonenumberfnetai.setBackgroundResource(android.R.drawable.edit_text);

                }
                if(sett==2){

                    EmailAdresslnetai.setInputType(InputType.TYPE_CLASS_TEXT);
                    EmailAdresslnetai.setBackgroundResource(android.R.drawable.edit_text);
                    Addressemailetai.setInputType(InputType.TYPE_CLASS_TEXT);
                    Addressemailetai.setBackgroundResource(android.R.drawable.edit_text);

                }
                if(sett==3){
                    Addressemailetai.setInputType(InputType.TYPE_CLASS_TEXT);
                    Addressemailetai.setBackgroundResource(android.R.drawable.edit_text);
                    Phonenumberfnetai.setInputType(InputType.TYPE_CLASS_TEXT);
                    Phonenumberfnetai.setBackgroundResource(android.R.drawable.edit_text);

                }

            }
        });



        SharedPreferences preferences=getApplicationContext().getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor=preferences.edit();
        sett=preferences.getInt("sett",0);










        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("UserDets").document(userid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
           DocumentSnapshot documentSnapshot = task.getResult();


           String emailaddt= documentSnapshot.getString("emailadd");
           String phonenumt= documentSnapshot.getString("phonenum");
           String addressmt= documentSnapshot.getString("address");
if(!emailaddt.equals("##")){
    EmailAdresslnetai.setText(emailaddt);
    EmailAdresslnetai.setInputType(InputType.TYPE_NULL);
    EmailAdresslnetai.setBackground(null);

}
                if(!phonenumt.equals("##")){
                    Phonenumberfnetai.setText(phonenumt);
                    Phonenumberfnetai.setInputType(InputType.TYPE_NULL);
                    Phonenumberfnetai.setBackground(null);
                }
                if(!addressmt.equals("##")){
                    Addressemailetai.setText(addressmt);
                    Addressemailetai.setInputType(InputType.TYPE_NULL);
                    Addressemailetai.setBackground(null);
                }







           UerIdautocompletetextcityai.setText(encode(userid));
           loyallevelai.setText("Loyalty Level : "+documentSnapshot.getString("LoyalLevel"));
           String samplevel=documentSnapshot.getString("LoyalLevel");
           pointsai.setText("Points : "+documentSnapshot.getString("points"));
           int samppoi=Integer.parseInt(documentSnapshot.getString("points"));
           int point1 = 1;
           if(samplevel.equals("Bronze")){
                point1 = 1;
           }else if(samplevel.equals("Silver")){
                     point1 = 2;
                }else if(samplevel.equals("Gold")){
                     point1 = 3;
                }

           curai.setText("In Rupees : Rs."+samppoi*point1+"/-");
            }
        });










    }

    public String encode(String s){
        int[] arr = new int[s.length()];
        int i=0;
        for (char var : s.toCharArray()) {
            int a = var;
            int b= a+3;
            arr[i]=b;
            i++;
        }
        String comp="";
        for (int var : arr) {
            char b= (char)var;
            comp+=String.valueOf(b);
        }
        return comp;

    }
/*
    public String decode(String s){
        int[] arr = new int[s.length()];
        int i=0;
        for (char var : s.toCharArray()) {
            int a = var;
            int b= a-3;
            arr[i]=b;
            i++;
        }
        String comp="";
        for (int var : arr) {
            char b= (char)var;
            comp+=String.valueOf(b);
        }
        return comp;

    }
*/
}

