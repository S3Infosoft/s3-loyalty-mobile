package com.icy.chatscreen;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;




class xyz {

    List<String> l1,l2,l3;

    public boolean greet(String st){
        l1.add("Hello");
        l1.add("Hey");
        l1.add("Hi");
        l1.add("Whats up");
        l1.add("Hello Nice To meet You");
        for (String v :l1) {
            if (st.toLowerCase().contains(v.toLowerCase())){
                return true;
            }
        }
    return  false;
    }

    public boolean lad(String st){
l2.add("How");
l2.add("what");
l2.add("why");
l2.add("where");
l2.add("which");
        for (String v:l2) {
            if(st.toLowerCase().contains(v.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public boolean che(String st){
        l3.add("hmm");
        l3.add("then");
        l3.add("ok");
        l3.add("oh");
        l3.add("nice");
        l3.add("good");
        l3.add("great");
        l3.add("well");
        l3.add("thank");
        for (String v:l3) {
            if(st.toLowerCase().contains(v.toLowerCase())){
                return true;
            }
        }
        return false;
    }



    public String xyz1(String st){

        Random r = new Random();


        l1=new ArrayList<String>();
        l2=new ArrayList<String>();
        l3=new ArrayList<String>();

            if ((Pattern.matches("(.*)change(.*)password(.*)", st.toLowerCase()))) {
//                FirebaseAuth.getInstance().sendPasswordResetEmail("varun.001raone@gmail.com");
                return ("You can Change your password by Going to Settings -> Change My Password  -> Enter Your New Password -> Done ");
            } else if (Pattern.matches("(.*)payment(.*)mode(.*)", st.toLowerCase())) {
                return ("At Present we support Payments with Credit cards and Debit cards / Net Banking ");
            } else if (Pattern.matches("(.*)cancel(.*)order(.*)", st.toLowerCase())) {
                return ("If You have ordered they will be transferred to you immediately , so they cannot be cancelled . If You have Booked some room for some day in future it may be cancelled but some nominal amount may be charged.");
            } else if (Pattern.matches("(.*)contact(.*)resort(.*)", st.toLowerCase())) {
                return ("You can get the phone number of the Resort as follows. First Select a Resort of your choice from Dashboard Book a Resort Deals -> Then Go to Contact details -> You can get their email and Phone Number");
            } else if (Pattern.matches("(.*)change(.*)mobile(.*)", st.toLowerCase()) || Pattern.matches("(.*)change(.*)phone(.*)", st.toLowerCase())) {
                return ("You can change your mobile number by Going to Settings -> Change My Mobile Number -> Enter New Mobile number -> Verify -> Done");
            } else if (Pattern.matches("(.*)complaint(.*)", st.toLowerCase())) {
                return ("You can send your complaints or Suggestions or requests or any other kind of Queries to our email address , we will definitely get back to you in 24 hours.\n Email -> s3infosot@gmail.com");
            } else if (Pattern.matches("(.*)change(.*)mail(.*)", st.toLowerCase())) {
//                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
//                user.updateEmail("varunsharma@gmail.com");
                return ("You can change your email id by going to Settings -> Change Email Address -> Verify -> Done");
            } else if (Pattern.matches("(.*)use(.*)points(.*)", st.toLowerCase()) || Pattern.matches("(.*)role(.*)points(.*)", st.toLowerCase())) {
                return ("You can use your Points to Buy Awesome Deals , You can purchase amazon gift cards , Vouchers , You can book hotels , just select some deal of your choice from the available deals and then click on Proceed to Pay and Confirm and Check Out . You are all done , You have succesfully used your points to buy awesome deals.");
            } else if (Pattern.matches("(.*)purchase(.*)points(.*)", st.toLowerCase())) {
                return ("You can purchase your points by Going to Your Account -> Purchase Points -> enter Number of points -> payment Mode -> Check Out -> You have succesfully Bought Points ");
            } else if(greet(st)) {
                return l1.get(r.nextInt(l1.size()));
            }else if(lad(st)){
                return ("Okay , I don't like to answer that question now");
            }else if(che(st)){
                return l3.get(r.nextInt(l3.size()));
            }
            else{
                return ("Sorry , I didn't get you. an you be more clear");
            }

        }

    }



















        /*        What are the payment mode available?--
Can I cancel my order?--
How can I change my password?--
How do I contact the resort--
How do I register a complaint for Deals?--
How do I register a complaint for App related issues?--
How do I change my registered mobile number?--
How do I change my registered Email address?--
Can I club points with my friends?
What is your escalation matrix?
Why do we need points for purchasing instead of money?
What is the role of points?
         */