package com.example.whatsappclone1;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
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
    FireStoreDataBASE fireStoreDataBASE;
    String userID;
    ImageButton backButton;
    RelativeLayout editUserName,editPhoneNumber;
    ImageView userProfileImage;
    TextView userName,phoneNumber;
    private Uri imagePath;
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
        editUserName=findViewById(R.id.editUserProfileUserNAME);
        editPhoneNumber=findViewById(R.id.editUserPhoneNumber);
        fireStoreDataBASE=new FireStoreDataBASE();
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
        editUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(EditUserProfile.this);
                dialog.setContentView(R.layout.customdialog_editusername);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                TextView Cancel=dialog.findViewById(R.id.editUserNameCancelBtn);
                TextView save=dialog.findViewById(R.id.editUserNameSaveBtn);
                EditText newName=dialog.findViewById(R.id.editUserName);
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DocumentReference documentReference=fireStore.collection("AllUsers").document(auth.getCurrentUser().getPhoneNumber());
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String usermob= documentSnapshot.getString("userPhonenumber");
                                String userpro=documentSnapshot.getString("userProfilepic");
                                 fireStoreDataBASE.dataPutNewName(newName.getText().toString(),userpro,usermob);
                                 startActivity(new Intent(EditUserProfile.this,UserProfile.class));
                            }
                        });
                        Toast.makeText(EditUserProfile.this, "edited", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //to change profile photo
       userProfileImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Dialog dialog=new Dialog(EditUserProfile.this);
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
                       startActivity(new Intent(EditUserProfile.this,UserProfile.class));
                   }
               });
               gallery.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent openGallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                       startActivityForResult(openGallery,138);
                       dialog.dismiss();
                       startActivity(new Intent(EditUserProfile.this,UserProfile.class));
                   }
               });
           }
       });
        //to change phoneNumber
        editPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(EditUserProfile.this,ChangePhoneNumber.class);
               startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==138&&resultCode==RESULT_OK)
        {
            imagePath=data.getData();
            System.out.println(imagePath+"taken");
            userProfileImage.setImageURI(imagePath);
            updatePic(imagePath);

        }else if(requestCode==100&&resultCode==RESULT_OK){
             imagePath=data.getData();
             userProfileImage.setImageURI(imagePath);
            updatePic(imagePath);
        }

    }

    void updatePic(Uri image){
        DocumentReference documentReference=fireStore.collection("AllUsers").document(auth.getCurrentUser().getPhoneNumber());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String usermob= documentSnapshot.getString("userPhonenumber");
                String userpro=documentSnapshot.getString("userProfilepic");
                String username=documentSnapshot.getString("userNickname");
                fireStoreDataBASE.dataPutNewName(username, String.valueOf(image),usermob);
                startActivity(new Intent(EditUserProfile.this,UserProfile.class));
            }
        });
    }
}