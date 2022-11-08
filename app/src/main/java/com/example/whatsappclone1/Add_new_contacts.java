package com.example.whatsappclone1;

import static com.example.whatsappclone1.FireStoreDataBASE.STATUS;
import static com.example.whatsappclone1.FireStoreDataBASE.USER_NICKNAME;
import static com.example.whatsappclone1.FireStoreDataBASE.USER_PHONENUMBER;
import static com.example.whatsappclone1.FireStoreDataBASE.USER_PROFILEPIC;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsappclone1.userModel.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Add_new_contacts extends AppCompatActivity {
      ImageButton close,done;
      TextInputEditText userNickName,userPhoneNumber;
      String emptyPerson;
      FirebaseFirestore firestore;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contacts);
        close=findViewById(R.id.Add_new_contact_close);
        done=findViewById(R.id.Add_new_contact_done);
        userNickName=findViewById(R.id.add_new_contact_nickName);
        userPhoneNumber=findViewById(R.id.add_new_contact_phone_number);
        firestore=FirebaseFirestore.getInstance();
        emptyPerson="https://i.pinimg.com/originals/6b/aa/98/6baa98cc1c3f4d76e989701746e322dd.png";
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Add_new_contacts.this,Contacts.class));
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel = new UserModel();
                FirebaseAuth auth=FirebaseAuth.getInstance();
                  System.out.println(userModel.getUserPhonenumber());
                DocumentReference documentReference = firestore.collection("AllUsers").document("+91"+userPhoneNumber.getText().toString());
                Map<String, Object> user = new HashMap<>();
                user.put(USER_NICKNAME, userNickName.getText().toString());
                user.put(USER_PROFILEPIC, emptyPerson);
                user.put(USER_PHONENUMBER, userPhoneNumber.getText().toString());
                user.put(STATUS, "online");
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
            }
        });
    }
}