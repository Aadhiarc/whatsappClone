package com.example.whatsappclone1;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FireStoreDataBASE  {

    public static final String USER_NICKNAME = "userNickname";
    public static final String USER_PROFILEPIC = "userProfilepic";
    public static final String USER_PHONENUMBER = "userPhonenumber";
    public static final String STATUS = "Status";
    FirebaseAuth auth;
    FirebaseFirestore fireStore;
    String userID;
    FirebaseStorage storage;
    String myUrl;

    void dataPut(String user_name,String user_dp,String user_mob){
       fireStore= FirebaseFirestore.getInstance();
       auth=FirebaseAuth.getInstance();
        userID =  auth.getCurrentUser().getUid();

       DocumentReference documentReference=fireStore.collection("AllUsers").document(auth.getCurrentUser().getPhoneNumber());
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

   public void dataPutCloudStorage(Uri image, Context context){
       storage=FirebaseStorage.getInstance();
        StorageReference storageReference=storage.getReference("profileImages/"+ UUID.randomUUID()+".png");
        UploadTask uploadTask=storageReference.putFile(image);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(context, "completed", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

  public void senImagesPutCloudStorage(Context context,Uri uri){
        storage=FirebaseStorage.getInstance();
        StorageReference storageReference=storage.getReference("SendImages/"+UUID.randomUUID()+".png");
        UploadTask uploadTask=storageReference.putFile(uri);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
            }
        });
         uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }else{
                    System.out.println(storageReference.getDownloadUrl()+"sasa");
                    return storageReference.getDownloadUrl();

                }
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri downloadUrl=task.getResult();
                    myUrl=downloadUrl.toString();


                }
            }
        });

    }


  public  void sentWhatsAppStatusPutCloudStorage(Context context,Uri uri){
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        StorageReference storageReference=storage.getReference("WhatsAppStatus/"+auth.getCurrentUser().getPhoneNumber()+"/"+"Videos/"+UUID.randomUUID()+".mp4");
        UploadTask uploadTask=storageReference.putFile(uri);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
            }
        });
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }else{
                    return storageReference.getDownloadUrl();

                }
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri downloadUrl=task.getResult();
                    myUrl=downloadUrl.toString();
                }
            }
        });

    }

    public  void sentWhatsAppStatusImagesPutCloudStorage(Context context,Uri uri){
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        StorageReference storageReference=storage.getReference("WhatsAppStatus/"+auth.getCurrentUser().getPhoneNumber()+"/"+"Images/"+UUID.randomUUID()+".png");
        UploadTask uploadTask=storageReference.putFile(uri);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
            }
        });
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }else{
                    return storageReference.getDownloadUrl();

                }
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri downloadUrl=task.getResult();
                    myUrl=downloadUrl.toString();
                }
            }
        });

    }





}
