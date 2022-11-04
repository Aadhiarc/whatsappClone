package com.example.whatsappclone1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class Profileinfo extends AppCompatActivity {
    ImageView openCamera;
    CardView cardView;
    EditText userName;
    Bitmap profilePhoto;
    Button next;
    private Uri imagePath;

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
        Dialog dialog = new Dialog(Profileinfo.this);
        dialog.setContentView(R.layout.cameraallerydialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
       ShapeableImageView camera= dialog.findViewById(R.id.camera_button);
        ShapeableImageView gallery=dialog.findViewById(R.id.gallery_button);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open,100);
                dialog.dismiss();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(openGallery,138);
                dialog.dismiss();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==138&&resultCode==RESULT_OK)
        {
            imagePath=data.getData();
            openCamera.setImageURI(imagePath);
        }else if(requestCode==100&&resultCode==RESULT_OK){
            profilePhoto=(Bitmap)data.getExtras().get("data");
            openCamera.setImageBitmap(profilePhoto);
        }

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