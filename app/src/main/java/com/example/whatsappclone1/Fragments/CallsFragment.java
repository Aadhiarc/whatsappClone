package com.example.whatsappclone1.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclone1.R;

import java.util.ArrayList;


public class CallsFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList<ContactModel>arrayList=new ArrayList<ContactModel>();
    MainAdapter adapter;
    RelativeLayout relativeLayout;

    public CallsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_calls, container, false);
        recyclerView=view.findViewById(R.id.contactRecyclerView);
        checkPermission();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new MainAdapter(getActivity(),arrayList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS},100);
        }else{
            getContactList();
        }
    }

    private void getContactList() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sort= ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        Cursor cursor = getActivity().getContentResolver().query(uri,null,null,null,sort);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Uri uriPhone=ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String selection=ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" =?";
                Cursor phoneCursor =getActivity().getContentResolver().query(uriPhone,null,selection,new String[]{id},null);
                if(phoneCursor.moveToNext()){
                    @SuppressLint("Range") String Phone_number =phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    ContactModel model=new ContactModel();
                    model.setName(name);
                    model.setNumber(Phone_number);
                    arrayList.add(model);
                    phoneCursor.close();
                }

            }
        }
        cursor.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            getContactList();
        }else{
            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}