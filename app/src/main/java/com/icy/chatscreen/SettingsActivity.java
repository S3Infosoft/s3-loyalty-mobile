package com.icy.chatscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    List<String> l1;
ArrayAdapter<String> aaa;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingspage);
        ListView lv= findViewById(R.id.listviewsettings);
    l1= new ArrayList<String>();
    l1.add("Change Password");
    l1.add("Change Email Address");
    l1.add("Change Phone Number");
    l1.add("Account Details");
    l1.add("Logout");
    l1.add("Chat With US");
    l1.add("Privacy Policy and Terms of Service");
     aaa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,l1);
     lv.setAdapter(aaa);

     lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             i=i+1;
             if(i==1){
                 Intent iam = new Intent(SettingsActivity.this,Chpass.class);
                 startActivity(iam);
             }else if(i==2){
                 Intent iam = new Intent(SettingsActivity.this,ChEmail.class);
                 startActivity(iam);
             }else if(i==3){
                 Intent iam = new Intent(SettingsActivity.this,Chphone.class);
                 startActivity(iam);
             }else if(i==4){
                 Intent iam = new Intent(SettingsActivity.this,AcctDet.class);
                 startActivity(iam);
             }else if(i==5){
                 FirebaseAuth.getInstance().signOut();
                 Intent ij = new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(ij);
                finish();

             }else if(i==6){
                 Intent ij = new Intent(SettingsActivity.this,samplechatbot.class);
                 startActivity(ij);

             }
         }
     });



    }
}
