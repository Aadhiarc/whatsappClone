package com.example.whatsappclone1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Contacts extends AppCompatActivity {
     ImageButton back;
     RelativeLayout relativeLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        back=findViewById(R.id.Add_new_contact_back_button);
        relativeLayout=findViewById(R.id.add_new_contact_relative_layout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               startActivity(new Intent(Contacts.this,Add_new_contacts.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Contacts.this,Tabbed_layout.class));
            }
        });
    }
}