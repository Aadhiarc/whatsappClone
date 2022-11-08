package com.example.whatsappclone1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class EditUserProfile extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore fireStore;
    String userID;
    ImageButton backButton;
    ShapeableImageView userProfileImage;
    TextView userName,phoneNumber;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        auth=FirebaseAuth.getInstance();
        fireStore=FirebaseFirestore.getInstance();
        userID=auth.getCurrentUser().getPhoneNumber();
        backButton=findViewById(R.id.Edit_back_button);
        userProfileImage=findViewById(R.id.Edit_camera_image_button);
        userName=findViewById(R.id.edit_user_name);
        phoneNumber=findViewById(R.id.edit_user_phone);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditUserProfile.this,UserProfile.class));
            }
        });
        DocumentReference documentReference=fireStore.collection("AllUsers").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userName.setText(value.getString("userNickname"));
                Picasso.get().load(value.getString("userProfilepic")).into(userProfileImage);
                phoneNumber.setText(value.getString("userPhonenumber"));
            }
        });
    }
}