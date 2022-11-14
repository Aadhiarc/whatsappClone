package com.example.whatsappclone1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Splashscreen extends AppCompatActivity {
        FirebaseAuth auth;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        auth=FirebaseAuth.getInstance();
        Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                        startActivity(new Intent(Splashscreen.this,Tabbed_layout.class));
                    }else if(FirebaseAuth.getInstance().getCurrentUser()==null){
                        startActivity(new Intent(Splashscreen.this,FirstScreen.class));
                    }
                }catch (Exception e){

                }
            }
        },3000);


    }
}