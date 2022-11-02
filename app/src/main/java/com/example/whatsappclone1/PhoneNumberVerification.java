package com.example.whatsappclone1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneNumberVerification extends AppCompatActivity {
         EditText otp;
         Button otp_verify_btn;
         FirebaseAuth auth;
         String verificationId;
         TextView phonenumber,phonenumber2,wrong_num;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verification);
        otp=findViewById(R.id.Otp);
        otp_verify_btn=findViewById(R.id.verify_btn);
        phonenumber=findViewById(R.id.phone_number_header);
        phonenumber2=findViewById(R.id.phone_number_header_2);
        wrong_num=findViewById(R.id.wrong_number_button);
        String text="Wrong number ?";
        SpannableString ss= new SpannableString(text);
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(PhoneNumberVerification.this,Phonenumber_input.class));

            }
        };
        ss.setSpan(clickableSpan,0,14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        wrong_num.setText(ss);
        wrong_num.setMovementMethod(LinkMovementMethod.getInstance());
        auth=FirebaseAuth.getInstance();
        Intent intent=getIntent();
        String number=intent.getStringExtra("phone_number");
        phonenumber.setText("Verify +91"+number);
        phonenumber2.setText("Enter your otp.Message send to"+" "+number);
        wrong_num=findViewById(R.id.wrong_number_button);
        sendverificationcode(number);
        otp_verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(otp.getText().toString())){
                    Toast.makeText(PhoneNumberVerification.this, "Enter a valid  otp", Toast.LENGTH_SHORT).show();
                }else{
                    verifycode(otp.getText().toString());
                }

            }
        });
    }


    private void sendverificationcode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+91"+number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

   private PhoneAuthProvider.OnVerificationStateChangedCallbacks
    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
        {
            final String code=credential.getSmsCode();
            if(code!=null){
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(PhoneNumberVerification.this, "Verification failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s,token);
            verificationId=s;

        }
    };
    private void verifycode(String code) {
       PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,code);
       signinbycredentials(credential);
    }

    private void signinbycredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(PhoneNumberVerification.this, "Verification completed successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PhoneNumberVerification.this, Tabbed_layout.class));
                    }
                    }
                });


    }
}