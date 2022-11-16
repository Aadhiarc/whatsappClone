package com.example.whatsappclone1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class OpenCamera extends AppCompatActivity {
    FireStoreDataBASE fireStoreDataBASE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_camera);
        Intent open=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(open,100);
        fireStoreDataBASE=new FireStoreDataBASE();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap= (Bitmap) data.getExtras().get("data");
        Uri uri=getImageUri(OpenCamera.this,bitmap);
       fireStoreDataBASE.sentWhatsAppStatusImagesPutCloudStorage(OpenCamera.this,uri);
       startActivity(new Intent(OpenCamera.this,Tabbed_layout.class));
        fireStoreDataBASE.retrieveData(this);
    }
    //convert bitmap into uri
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}