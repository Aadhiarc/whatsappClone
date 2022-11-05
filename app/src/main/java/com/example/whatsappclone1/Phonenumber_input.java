package com.example.whatsappclone1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class Phonenumber_input extends AppCompatActivity {
       EditText phone_input;
       Button next;
    Spinner spinner_country,spinner_code;
    LoadingDialogue dialogue;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonenumber_input);
        //for country drop down list
        spinner_country=findViewById(R.id.spinner_country);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.country_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_country.setAdapter(adapter);
        //for country code drop down list
        spinner_code=findViewById(R.id.phone_spinner);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.country_code, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_code.setAdapter(adapter1);
       phone_input=findViewById(R.id.phone);
        String phoneNumber=phone_input.getText().toString();
        next=findViewById(R.id.otp_button);
      phone_input.getText().toString();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(!TextUtils.isEmpty(phone_input.getText().toString())){
                        //custom dialog for loading
                        dialogue=new LoadingDialogue(Phonenumber_input.this);
                        dialogue.loadingDialogue();
                        //custom dialog for edit alert box
                        Dialog dialog = new Dialog(Phonenumber_input.this);
                        dialog.setContentView(R.layout.custom_dialoguebox2);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        MaterialButton editBtn=dialog.findViewById(R.id.edit_button_dialogue);
                        MaterialButton okBtn=dialog.findViewById(R.id.Ok_button_dialogue);
                        TextView numberTextView=dialog.findViewById(R.id.dialogue_phoneNumber);
                        numberTextView.setText("+91"+" "+phone_input.getText().toString());
                        editBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                phone_input.setText("");
                                dialog.dismiss();
                                dialogue.dismiss();
                            }
                        });
                        okBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(Phonenumber_input.this,PhoneNumberVerification.class);
                                intent.putExtra("phone_number",phone_input.getText().toString());
                                startActivity(intent);
                            }
                        });
                        dialog.show();
                    }else{
                        Toast.makeText(Phonenumber_input.this, "invalid Phone number", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(Phonenumber_input.this, "invalid Phone number", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}