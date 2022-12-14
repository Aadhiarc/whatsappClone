package com.example.whatsappclone1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone1.userModel.MessageDb;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<MessageDb> messageDbArrayList;
    int ITEM_SEND=1;
    int ITEM_RECIEVE=2;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;

    public MessagesAdapter(Context context, ArrayList<MessageDb> messageDbArrayList) {
        this.context = context;
        this.messageDbArrayList = messageDbArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SEND){
            View view= LayoutInflater.from(context).inflate(R.layout.senderchatlayout,parent,false);
            return  new SenderViewHolder(view);
        }else {
            View view= LayoutInflater.from(context).inflate(R.layout.recieverlayout,parent,false);
            return  new RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageDb messageDb=messageDbArrayList.get(position);
        if(holder.getClass()==SenderViewHolder.class){
            SenderViewHolder senderViewHolder=(SenderViewHolder) holder;
            String msg=messageDb.getMessage();
            senderViewHolder.textViewMessage.setText(msg);
            senderViewHolder.timeOfMessage.setText(messageDb.getCurrenttime());
            Picasso.get().load(messageDb.getSentImages()).into(senderViewHolder.sentImage);
           senderViewHolder.location.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

               }
           });
        }else{
            RecieverViewHolder ViewHolder=(RecieverViewHolder) holder;
            String rMsg=messageDb.getMessage();
            System.out.println(rMsg+"ggggg");
            ViewHolder.textViewMessage.setText(rMsg);
            ViewHolder.timeOfMessage.setText(messageDb.getCurrenttime());
            Picasso.get().load(messageDb.getSentImages()).into(ViewHolder.recievedImage);
            ViewHolder.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

    }

    @Override
    public int getItemViewType(int position) {
        MessageDb messageDb=messageDbArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().equals(messageDb.getSenderPhoneNumber())){
            return ITEM_SEND;
        }
        else {
            return ITEM_RECIEVE;
        }

    }

    @Override
    public int getItemCount() {
        return messageDbArrayList.size();
    }



    class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessage;
        TextView timeOfMessage;
        ImageView sentImage;
        View location;
        public SenderViewHolder(@NonNull View itemView) {

            super(itemView);
            textViewMessage=itemView.findViewById(R.id.senderMessage);
            timeOfMessage=itemView.findViewById(R.id.timeofmeassage);
            sentImage=itemView.findViewById(R.id.senderChatSentImages);
            location=itemView.findViewById(R.id.google_maps_sender);
        }
    }
    class RecieverViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMessage;
        TextView timeOfMessage;
        ImageView recievedImage;
        View location;
        public RecieverViewHolder(@NonNull View itemView) {

            super(itemView);
            textViewMessage=itemView.findViewById(R.id.ReciveveMessage);
            timeOfMessage=itemView.findViewById(R.id.Recivevetimeofmeassage);
            recievedImage=itemView.findViewById(R.id.recieverChatSentImages);
            location=itemView.findViewById(R.id.google_maps);
        }
    }



}
