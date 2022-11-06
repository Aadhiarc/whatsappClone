package com.example.whatsappclone1;

import androidx.annotation.NonNull;
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
            if(FirebaseAuth.getInstance().getCurrentUser()!=null){

                startActivity(new Intent(Splashscreen.this,Tabbed_layout.class));
            }else if(FirebaseAuth.getInstance().getCurrentUser()==null){
                startActivity(new Intent(Splashscreen.this,FirstScreen.class));
            }
        }catch (Exception e){

        }

    }
}