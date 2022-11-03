package com.example.whatsappclone1.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsappclone1.R;


public class CameraFragment extends Fragment {



    public CameraFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}