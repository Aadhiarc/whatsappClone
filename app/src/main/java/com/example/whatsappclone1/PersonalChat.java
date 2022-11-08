package com.example.whatsappclone1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone1.userModel.MessageDb;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PersonalChat extends AppCompatActivity {

    EditText mGetMessage;
    ImageButton mSendMessageButton;
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
    ImageButton backButtonOfPersonalChat;

    RecyclerView mRecyclerView;
    String CurrentTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    MessagesAdapter messagesAdapter;
    ArrayList<MessageDb> messageDbArrayList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chat);


        mGetMessage=findViewById(R.id.getmessage);
        mSendMessageCardView=findViewById(R.id.Personal_profile_cardview);
        mSendMessageButton=findViewById(R.id.sendMessageBtn);
        mPersonalChatToolBar=findViewById(R.id.Personal_chat_toolbar);
        mTextViewOfSpecificUser=findViewById(R.id.specificUserName);
        mImageViewOfSpecificUser=findViewById(R.id.Personal_chat_camera_image_button);
        backButtonOfPersonalChat=findViewById(R.id.Personal_chat_back_button);
        messageDbArrayList=new ArrayList<>();
        mRecyclerView=findViewById(R.id.Recyclerviewodpersonalchat);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        messagesAdapter=new MessagesAdapter(PersonalChat.this,messageDbArrayList);
        mRecyclerView.setAdapter(messagesAdapter);

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
        Picasso.get().load(getIntent().getStringExtra("userProfilePIc")).into(mImageViewOfSpecificUser);
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
                    MessageDb messageDb=new MessageDb(enteredMessage,auth.getCurrentUser().getPhoneNumber(),date.getTime(),CurrentTime);
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



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
       startActivity(new Intent(PersonalChat.this,Tabbed_layout.class));
    }


    @Override
    public void onStart() {
        super.onStart();
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