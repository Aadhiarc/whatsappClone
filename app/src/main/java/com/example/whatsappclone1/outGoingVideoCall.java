package com.example.whatsappclone1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class outGoingVideoCall extends AppCompatActivity {
      ShapeableImageView receiverPic;
      TextView receiverName;
      FloatingActionButton declineBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_going_video_call);
        receiverPic=findViewById(R.id.outGoingVideoCallPersonImage);
        receiverName=findViewById(R.id.outGoingCallerName);
        declineBtn=findViewById(R.id.declineCallOutGoing);
        Intent intent=getIntent();
        String receiver_name=intent.getStringExtra("recieverName");
        String receiver_phone_number=intent.getStringExtra("receiverPhoneNumber");
        String receiver_profile_pic=intent.getStringExtra("reciverProfilePic");
        Picasso.get().load(receiver_profile_pic).into(receiverPic);
        receiverName.setText(receiver_name);

        
    }
}