package com.example.whatsappclone1;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

public class LoadingDialogue {

   private Activity activity;
   private AlertDialog alertDialog;

    public LoadingDialogue(Activity activity) {
        this.activity = activity;
    }

    void loadingDialogue(){
    AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialogue_layout,null));
        builder.setCancelable(true);
        alertDialog=builder.create();
        alertDialog.show();
    }
    void dismiss(){
        alertDialog.dismiss();
    }

}
