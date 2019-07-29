package com.icy.chatscreen;


import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

public class userregdetails implements Serializable {
 public String firstname;
 public String lastname;
 public String phonenum;
 public String city;
 public String date;
 public String email;
 public String anniversary;

    public void userregdetails(){}

 public  userregdetails(String firstname,
         String lastname,
         String phonenum,
         String city,
         String date,
         String email,
         String anniversary){
     this.firstname=firstname;
     this.lastname=lastname;
     this.phonenum=phonenum;
     this.city=city;
     this.date=date;
     this.anniversary=anniversary;
     this.email=email;

 }

}
