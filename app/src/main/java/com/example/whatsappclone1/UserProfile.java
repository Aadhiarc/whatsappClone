package com.example.whatsappclone1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {
          FirebaseAuth auth;
          FirebaseFirestore fireStore;
          String userID;
          ImageButton backButton;
          ShapeableImageView userProfileImage;
          TextView userName;
          RelativeLayout userProfileLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
            auth= FirebaseAuth.getInstance();
            fireStore= FirebaseFirestore.getInstance();
            userID=auth.getCurrentUser().getUid();
            backButton=findViewById(R.id.Settings_back_button);
            userProfileImage=findViewById(R.id.Settings_camera_image_button);
            userName=findViewById(R.id.Settings_user_profile_name);
            userProfileLayout=findViewById(R.id.Settings_page_user_profile);
            DocumentReference documentReference=fireStore.collection("AllUsers").document(userID);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                     userName.setText(value.getString("userNickname"));
                    Picasso.get().load(value.getString("userProfilepic")).into(userProfileImage);
                }
            });
           backButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(UserProfile.this,Tabbed_layout.class));
               }
           });
           userProfileLayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(UserProfile.this,EditUserProfile.class));
               }
           });

    }
}