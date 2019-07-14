package com.icy.chatscreen;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Chpass extends AppCompatActivity {

    EditText et1,et2;
    Button passchange;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chpass);

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
                            Snackbar.make(view,"Password has been Changed Succesfully",Snackbar.LENGTH_LONG).show();
                        }else{
                            Snackbar.make(view,"Some Error Ocuured , Try again Later",Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    });


    }
}