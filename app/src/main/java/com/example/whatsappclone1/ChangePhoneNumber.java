package com.example.whatsappclone1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangePhoneNumber extends AppCompatActivity {
       FirebaseAuth auth;
       EditText oldPhoneNumber,newPhoneNumber;
       Button button;
       FireStoreDataBASE fireStoreDataBASE;
       FirebaseFirestore firestore;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        auth=FirebaseAuth.getInstance();
        fireStoreDataBASE=new FireStoreDataBASE();
        firestore=FirebaseFirestore.getInstance();
        oldPhoneNumber=(EditText) findViewById(R.id.oldNumber);
        newPhoneNumber=(EditText) findViewById(R.id.newNumber);
        button=findViewById(R.id.ChangePhoneNumberConfirm);
        String countryCode="+91";
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneCountry=countryCode+oldPhoneNumber.getText().toString();
                String newPhoneNumberCountry=countryCode+newPhoneNumber.getText().toString();
                if(phoneCountry.equals(auth.getCurrentUser().getPhoneNumber())){

                }else {
                    Toast.makeText(ChangePhoneNumber.this, "phone number is not matching", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}