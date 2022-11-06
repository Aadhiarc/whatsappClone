package com.example.whatsappclone1;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class FireStoreDataBASE {

    public static final String USER_NICKNAME = "userNickname";
    public static final String USER_PROFILEPIC = "userProfilepic";
    public static final String USER_PHONENUMBER = "userPhonenumber";
    public static final String STATUS = "Status";
    FirebaseAuth auth;
    FirebaseFirestore fireStore;
    String userID;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    void dataPut(String user_name,String user_dp,String user_mob){
       fireStore= FirebaseFirestore.getInstance();
       auth=FirebaseAuth.getInstance();
        userID =  auth.getCurrentUser().getUid();
       DocumentReference documentReference=fireStore.collection("AllUsers").document(userID);
       Map<String,Object> user=new HashMap<>();
       user.put(USER_NICKNAME,user_name);
       user.put(USER_PROFILEPIC,user_dp);
       user.put(USER_PHONENUMBER,user_mob);
       user.put(STATUS,"online");
       documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override

           public void onSuccess(Void unused) {
               System.out.println("successfully");
           }
       });

    }
}
