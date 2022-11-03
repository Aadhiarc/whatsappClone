package com.example.whatsappclone1;

import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class FireStoreDataBASE {
    FirebaseAuth auth;
    FirebaseFirestore fireStore;
    String userID;
   void dataPut(String user_name,String user_dp,String user_mob){
       fireStore= FirebaseFirestore.getInstance();
       auth=FirebaseAuth.getInstance();
        userID =  auth.getCurrentUser().getUid();
       DocumentReference documentReference=fireStore.collection("AllUsers").document(userID);
       Map<String,Object> user=new HashMap<>();
       user.put("userNickname",user_name);
       user.put("userProfilepic",user_dp);
       user.put("userPhonenumber",user_mob);
       documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void unused) {
               System.out.println("successfully");
           }
       });

    }
}
