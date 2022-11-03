package com.example.whatsappclone1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Profileinfo extends AppCompatActivity {
     ImageButton openCamera;
     CardView cardView;
     EditText userName;
    Bitmap profilePhoto;
    Button next;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileinfo);
        openCamera=findViewById(R.id.camera_image_button);
        cardView=findViewById(R.id.profile_cardview);
        userName=findViewById(R.id.profile_name_edit_text);
        next=findViewById(R.id.profileInfo_Btn);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open,100);

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putData();
                Toast.makeText(Profileinfo.this, "DATA STORED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Profileinfo.this,Tabbed_layout.class));
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         profilePhoto=(Bitmap)data.getExtras().get("data");
        System.out.println(profilePhoto);
     openCamera.setImageBitmap(profilePhoto);
    }
    void putData(){
        Intent intent=getIntent();
        String phone_number=intent.getStringExtra("phoneNumber");
        String profile_name=userName.getText().toString();
        String profile_photo= String.valueOf(profilePhoto);
        FireStoreDataBASE fireStoreDataBASE=new FireStoreDataBASE();
        fireStoreDataBASE.dataPut(profile_name,profile_photo,phone_number);
    }
}