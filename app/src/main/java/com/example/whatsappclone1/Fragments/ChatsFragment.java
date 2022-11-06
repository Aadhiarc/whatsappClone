package com.example.whatsappclone1.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone1.R;
import com.example.whatsappclone1.userModel.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class ChatsFragment extends Fragment {
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    ShapeableImageView userImageView;
    LinearLayoutManager linearLayoutManager;
    FirestoreRecyclerAdapter<UserModel, NoteViewHolder> ChatAdapter;
    RecyclerView ChatRecyclerView;


    public ChatsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chats, container, false);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        ChatRecyclerView = v.findViewById(R.id.RecyclerView);

        Query query = firestore.collection("AllUsers");
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
                               Toast.makeText(getContext(), "item is clicked", Toast.LENGTH_SHORT).show();
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
}

