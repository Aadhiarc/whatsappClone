package com.example.whatsappclone1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
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
        openCamera = findViewById(R.id.camera_image_button);
        cardView = findViewById(R.id.profile_cardview);
        userName = findViewById(R.id.profile_name_edit_text);
        next = findViewById(R.id.profileInfo_Btn);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean pick = true;
                if (pick == true) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else pickImage();
                } else {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();

                    } else pickImage();
                }
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

    private void pickImage() {
        Intent open=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(open,100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        profilePhoto=(Bitmap)data.getExtras().get("data");
        System.out.println(profilePhoto);
        openCamera.setImageBitmap(profilePhoto);
    }

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    private boolean checkStoragePermission() {
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return res2;
    }

    private boolean checkCameraPermission() {
        boolean res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return res1 && res2;
    }

    void putData() {
        Intent intent = getIntent();
        String phone_number = intent.getStringExtra("phoneNumber");
        String profile_name = userName.getText().toString();
        String profile_photo = String.valueOf(profilePhoto);
        FireStoreDataBASE fireStoreDataBASE = new FireStoreDataBASE();
        fireStoreDataBASE.dataPut(profile_name, profile_photo, phone_number);
    }
}