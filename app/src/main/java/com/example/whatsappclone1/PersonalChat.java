package com.example.whatsappclone1;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone1.userModel.MessageDb;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PersonalChat extends AppCompatActivity {

    EditText mGetMessage;
    FloatingActionButton mSendMessageButton,mCameraBtn,mGalleryBtn,mLocationBtn;
    Animation fab_up,fab_down;
    boolean isOpen =false;
    int type_check;
    private Uri imagePath;
    CardView mSendMessageCardView;
    androidx.appcompat.widget.Toolbar mPersonalChatToolBar;
    ImageView mImageViewOfSpecificUser;
    TextView mTextViewOfSpecificUser;
    String enteredMessage;
    Intent intent;
    String mRecieverName,mSenderName,mReceiverPhoneNumber,mSenderPhoneNumber;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    String senderRoom,recieverRoom;
    ImageButton backButtonOfPersonalChat,videoCall,voiceCall;
    RecyclerView mRecyclerView;
    String CurrentTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    MessagesAdapter messagesAdapter;
    ArrayList<MessageDb> messageDbArrayList;
    FireStoreDataBASE fireStoreDataBASE;
    Bitmap cameraPhoto;
    Uri imageUri;
    String mUserProfilePic;
    PendingIntent pendingIntent;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chat);
        mGetMessage=findViewById(R.id.getmessage);
        mSendMessageCardView=findViewById(R.id.Personal_profile_cardview);
        mSendMessageButton=findViewById(R.id.sendMessageBtn);
        mCameraBtn=findViewById(R.id.CameraBtn_personal_chat);
        mGalleryBtn=findViewById(R.id.GalleryBtn_personal_chat);
        mLocationBtn=findViewById(R.id.LocationBtn_personal_chat);
        fab_up= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_up);
        fab_down=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_down);
        mPersonalChatToolBar=findViewById(R.id.Personal_chat_toolbar);
        mTextViewOfSpecificUser=findViewById(R.id.specificUserName);
        mImageViewOfSpecificUser=findViewById(R.id.Personal_chat_camera_image_button);
        backButtonOfPersonalChat=findViewById(R.id.Personal_chat_back_button);
        videoCall=findViewById(R.id.videoCallBtn);
        voiceCall=findViewById(R.id.CallBtn);
        messageDbArrayList=new ArrayList<>();
        mRecyclerView=findViewById(R.id.Recyclerviewodpersonalchat);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        messagesAdapter=new MessagesAdapter(this,messageDbArrayList);

        fireStoreDataBASE=new FireStoreDataBASE();
        intent=getIntent();
        mPersonalChatToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PersonalChat.this, "ToolBar", Toast.LENGTH_SHORT).show();
            }

        });



        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("hh:mm a");
        mSenderPhoneNumber=auth.getCurrentUser().getPhoneNumber();
        mReceiverPhoneNumber=getIntent().getStringExtra("ReceiverMobileNumber");
        mRecieverName=getIntent().getStringExtra("userNickname");
        senderRoom=mSenderPhoneNumber+mReceiverPhoneNumber;
        recieverRoom=mReceiverPhoneNumber+mSenderPhoneNumber;

    DatabaseReference databaseReference=firebaseDatabase.getReference().child("Chats").child(senderRoom).child("Messages");
   messagesAdapter=new MessagesAdapter(PersonalChat.this,messageDbArrayList);
   databaseReference.addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull DataSnapshot snapshot) {
           messageDbArrayList.clear();
           for(DataSnapshot snapshot1:snapshot.getChildren()){
               MessageDb messageDb=snapshot1.getValue(MessageDb.class);
               messageDbArrayList.add(messageDb);
           }
           messagesAdapter.notifyDataSetChanged();
       }


       @Override
       public void onCancelled(@NonNull DatabaseError error) {

       }
   });
        backButtonOfPersonalChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalChat.this,Tabbed_layout.class));
            }
        });
        mTextViewOfSpecificUser.setText(mRecieverName);
        mUserProfilePic=getIntent().getStringExtra("userProfilePIc");
        Picasso.get().load(mUserProfilePic).into(mImageViewOfSpecificUser);
        mSendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredMessage=mGetMessage.getText().toString();
                if(enteredMessage.isEmpty()){
                    Toast.makeText(PersonalChat.this, "Message is empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    Date date=new Date();
                    CurrentTime=simpleDateFormat.format(calendar.getTime());
                    NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Intent i =new Intent(PersonalChat.this,MessageNotification.class);
                    i.putExtra("sendMessage",enteredMessage);
                    pendingIntent= PendingIntent.getBroadcast(PersonalChat.this,0,i,0);

                    MessageDb messageDb=new MessageDb(enteredMessage,auth.getCurrentUser().getPhoneNumber(),date.getTime(),CurrentTime, fireStoreDataBASE.myUrl);
                    firebaseDatabase=FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference().child("Chats")
                            .child(senderRoom)
                            .child("Messages")
                            .push().setValue(messageDb).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    firebaseDatabase.getReference().child("Chats")
                                            .child(recieverRoom)
                                            .child("Messages")
                                            .push()
                                            .setValue(messageDb).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                }
                                            });
                                }
                            });
                    mGetMessage.setText(null);
                }
            }
        });

         mSendMessageButton.setOnLongClickListener(new View.OnLongClickListener() {
             @Override
             public boolean onLongClick(View view) {
                 if(isOpen){
                     mCameraBtn.setVisibility(View.INVISIBLE);
                     mGalleryBtn.setVisibility(View.INVISIBLE);
                     mLocationBtn.setVisibility(View.INVISIBLE);
                     mCameraBtn.startAnimation(fab_down);
                     mGalleryBtn.startAnimation(fab_down);
                     mLocationBtn.startAnimation(fab_down);
                     isOpen=false;
                 }
                 else{
                     mCameraBtn.startAnimation(fab_up);
                     mGalleryBtn.startAnimation(fab_up);
                     mLocationBtn.startAnimation(fab_up);
                     mCameraBtn.setVisibility(View.VISIBLE);
                     mGalleryBtn.setVisibility(View.VISIBLE);
                     mLocationBtn.setVisibility(View.VISIBLE);

                     isOpen=true;
                 }
                  return true;
             }
         });
         //for opening gallery button
        mGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(openGallery,138);
            }
        });
        //for opening camera button
        mCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open,100);
            }
        });
         //video call button
        videoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(PersonalChat.this);
                dialog.setContentView(R.layout.dialogbox_videocall);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                TextView Cancel=dialog.findViewById(R.id.cancelBtnVideo);
                TextView Call=dialog.findViewById(R.id.callBtnVideo);
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1=new Intent(PersonalChat.this,outGoingVideoCall.class);
                        intent1.putExtra("recieverName",mRecieverName);
                        intent1.putExtra("receiverPhoneNumber",mReceiverPhoneNumber);
                        intent1.putExtra("reciverProfilePic",mUserProfilePic);
                        startActivity(intent1);
                    }
                });
            }
        });
        voiceCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(PersonalChat.this);
                dialog.setContentView(R.layout.dialogbox_call);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                TextView Cancel=dialog.findViewById(R.id.cancelBtnVideo);
                TextView Call=dialog.findViewById(R.id.callBtnVideo);
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println(mReceiverPhoneNumber);
                        Uri u = Uri.parse("tel:" + mReceiverPhoneNumber);
                        Intent i = new Intent(Intent.ACTION_DIAL, u);
                        startActivity(i);
                    }
                });
            }
        });
        //for location sharing
        mLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i =new Intent(PersonalChat.this,mapsFragment.class);
               startActivity(i);
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
            fireStoreDataBASE.senImagesPutCloudStorage(this, imagePath);
            mSendMessageButton.performClick();

        }else if(requestCode==100&&resultCode==RESULT_OK){
            type_check=2;
            cameraPhoto=(Bitmap)data.getExtras().get("data");
            //to convert image quality
            WeakReference<Bitmap> result1=new WeakReference<>(Bitmap.createScaledBitmap(cameraPhoto,800,800,false)
                    .copy(Bitmap.Config.RGB_565,true));
            Bitmap bm=result1.get();
           imageUri=saveImage(bm,this);
            fireStoreDataBASE.senImagesPutCloudStorage(this,imageUri);
            mSendMessageButton.performClick();

        }

    }
    private Uri saveImage(Bitmap image, Context context) {
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
       startActivity(new Intent(PersonalChat.this,Tabbed_layout.class));
    }


    @Override
    public void onStart() {
        super.onStart();

        mRecyclerView.setAdapter(messagesAdapter);
        messagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(messagesAdapter!=null){
            messagesAdapter.notifyDataSetChanged();
        }
    }

}