package com.example.whatsappclone1.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone1.Contacts;
import com.example.whatsappclone1.PersonalChat;
import com.example.whatsappclone1.R;
import com.example.whatsappclone1.userModel.UserModel;
import com.example.whatsappclone1.userModel.WhatsAppStatusModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class ChatsFragment extends Fragment {
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    ShapeableImageView userImageView;
    LinearLayoutManager linearLayoutManager;
    FirestoreRecyclerAdapter<UserModel, NoteViewHolder> ChatAdapter;
    RecyclerView ChatRecyclerView;
     FloatingActionButton Contact;
     String userId;
     UserModel userModel;
    SharedPreferences sharedPreferences;
    public ChatsFragment() {

    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chats, container, false);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        ChatRecyclerView = v.findViewById(R.id.RecyclerView);
        Contact=v.findViewById(R.id.chats_floating_action_button);
        userModel=new UserModel();

        sharedPreferences= getActivity().getSharedPreferences("user_phone_number", MODE_PRIVATE);
        String userMobileNumber = sharedPreferences.getString("userPhoneNumber", "");
        Query query = firestore.collection("AllUsers").whereNotEqualTo("userPhonenumber", userMobileNumber);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                System.out.println(queryDocumentSnapshots.size());
            }
        });
        FirestoreRecyclerOptions<UserModel> allusers = new FirestoreRecyclerOptions.Builder<UserModel>().setQuery(query, UserModel.class).build();
        ChatAdapter =new FirestoreRecyclerAdapter<UserModel, NoteViewHolder>(allusers) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull UserModel model) {
                       holder.userName.setText(model.getUserNickname());
                       String uri=model.getUserProfilepic();
                       Picasso.get().load(uri).into(userImageView);

//                       if(model.getStatus().equals("online")){
//                           holder.userStatus.setText(model.getStatus());
//                           holder.userStatus.setTextColor(Color.GREEN);
//                       }else{
//                           holder.userStatus.setText(model.getStatus());
//                       }

                       holder.itemView.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Intent intent=new Intent(getActivity(), PersonalChat.class);
                               intent.putExtra("userNickname",model.getUserNickname());
                               intent.putExtra("userProfilePIc",model.getUserProfilepic());
                               intent.putExtra("ReceiverMobileNumber","+91"+model.getUserPhonenumber());
                               intent.putExtra("userUid",auth.getUid());
                               startActivity(intent);
                           }
                       });
            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.customchatlayout,parent,false);
               return new NoteViewHolder(view);
            }
        };

         ChatRecyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        ChatRecyclerView.setLayoutManager(linearLayoutManager);
        ChatRecyclerView.setAdapter(ChatAdapter);
            Contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveToNewActivity();
                }
            });
      return v;


    }

    public class NoteViewHolder extends RecyclerView.ViewHolder
    {
           TextView userName;
           TextView userStatus;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.nameofuser);
            userStatus=itemView.findViewById(R.id.statusofuser);
            userImageView=itemView.findViewById(R.id.Custom_chat_profile_image_view);


        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ChatAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(ChatAdapter!=null){
            ChatAdapter.stopListening();
        }
    }
    private void moveToNewActivity () {

        Intent i = new Intent(getActivity(), Contacts.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);

    }
}

