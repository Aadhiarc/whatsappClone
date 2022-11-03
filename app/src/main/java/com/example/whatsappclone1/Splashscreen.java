package com.example.whatsappclone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class Splashscreen extends AppCompatActivity {
        FirebaseAuth auth;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        auth=FirebaseAuth.getInstance();
        try{
            userID=auth.getCurrentUser().getUid();
            System.out.println(userID);
            if(userID!=null){
                Intent intent=new Intent(Splashscreen.this,Tabbed_layout.class);

                startActivity(new Intent(Splashscreen.this,Tabbed_layout.class));
            }
        }catch (Exception e){
            startActivity(new Intent(Splashscreen.this,FirstScreen.class));
        }

    }
}