package com.icy.chatscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.s3infosoft.loyaltyapp.R;

import java.util.ArrayList;
import java.util.List;

public class samplechatbot extends AppCompatActivity {

    ListView listView;
    EditText editText;
    Button button;
    List<String> l1;
    ArrayAdapter<String> adapter1;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.recyv);
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
                editText.setText("");
                listView.setSelection(l1.size()-1);
            }
        });

        adapter1 = new ArrayAdapter<String>(this,R.layout.messagereceived,R.id.receivemsg,l1);
        l1.add(" S3Bot : Hello , I am Your Virtual Assistant , you can ask me the Queries related to this application.");
        listView.setAdapter(adapter1);




    }
}
