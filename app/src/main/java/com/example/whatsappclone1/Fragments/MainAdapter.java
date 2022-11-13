package com.example.whatsappclone1.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone1.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
     Activity activity;
     ArrayList<ContactModel>arrayList;

    public MainAdapter(Activity activity, ArrayList<ContactModel> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
         ContactModel model=arrayList.get(position);

         holder.tvName.setText(model.getName());
         holder.tvNumber.setText(model.getNumber());
         holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(activity, "clicked", Toast.LENGTH_SHORT).show();

             }
         });
         holder.call.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Dialog dialog = new Dialog(activity);
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
                         Uri u = Uri.parse("tel:" +model.getNumber());
                         Intent i = new Intent(Intent.ACTION_DIAL, u);
                         activity.startActivity(i);
                     }
                 });
             }
         });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvNumber;
        LinearLayout relativeLayout;
        ImageButton call;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_name);
            tvNumber=itemView.findViewById(R.id.tv_number);
            relativeLayout=itemView.findViewById(R.id.contactRelativeLayout);
            call=itemView.findViewById(R.id.contactCallButton);
        }
    }
}
