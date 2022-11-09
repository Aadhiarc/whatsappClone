package com.example.whatsappclone1;

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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class Profileinfo extends AppCompatActivity {
    ImageView openCamera;
    CardView cardView;
    EditText userName;
    Bitmap profilePhoto;
    Button next;
    private Uri imagePath;
    Uri imageUri;
    int type_check;
    String status;
    FireStoreDataBASE fireStoreDataBASE;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileinfo);
        openCamera = findViewById(R.id.camera_image_button);
        cardView = findViewById(R.id.profile_cardview);
        userName = findViewById(R.id.profile_name_edit_text);
        next = findViewById(R.id.profileInfo_Btn);
        fireStoreDataBASE=new FireStoreDataBASE();
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
            type_check=1;
            imagePath=data.getData();
            //to store image in cloud storage
            fireStoreDataBASE.dataPutCloudStorage(imagePath,this);
            openCamera.setImageURI(imagePath);
        }else if(requestCode==100&&resultCode==RESULT_OK){
            type_check=2;
            profilePhoto=(Bitmap)data.getExtras().get("data");
            //to convert image quality
            WeakReference<Bitmap> result1=new WeakReference<>(Bitmap.createScaledBitmap(profilePhoto,profilePhoto.getHeight(),profilePhoto.getWidth(),false)
                    .copy(Bitmap.Config.RGB_565,true));
            Bitmap bm=result1.get();
            imageUri=saveImage(bm,Profileinfo.this);
            fireStoreDataBASE.dataPutCloudStorage(imageUri,this);
            openCamera.setImageURI(imageUri);
        }

    }

    private Uri saveImage(Bitmap image, Profileinfo context) {
        File imagesFolder=new File(context.getCacheDir(),"images");
        Uri uri=null;
        try{
            imagesFolder.mkdirs();
            File file=new File(imagesFolder,"captured_image.jpg");
            FileOutputStream stream=new FileOutputStream(file);
            //compress the image
            image.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(context.getApplicationContext(),"com.example.whatsappclone1"+".provider",file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  uri;
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
        String profile_camera_photo = String.valueOf(imageUri);
        String profile_gallery_photo= String.valueOf(imagePath);
        FireStoreDataBASE fireStoreDataBASE = new FireStoreDataBASE();
        // to put captured image and gallery image
        if(type_check==2){
            fireStoreDataBASE.dataPut(profile_name, profile_camera_photo, phone_number);
        }else if(type_check==1){
            fireStoreDataBASE.dataPut(profile_name, profile_gallery_photo, phone_number);
        }

    }
}