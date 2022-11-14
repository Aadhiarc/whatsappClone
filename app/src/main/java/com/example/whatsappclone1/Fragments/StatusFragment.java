package com.example.whatsappclone1.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.whatsappclone1.FireStoreDataBASE;
import com.example.whatsappclone1.OpenCamera;
import com.example.whatsappclone1.R;
import com.example.whatsappclone1.VideoTrimmer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;


public class StatusFragment extends Fragment {
     FloatingActionButton camera,edit;
    ShapeableImageView userImageView;
    RelativeLayout relativeLayout;
    FireStoreDataBASE fireStoreDataBASE;
    Bitmap statusImage;
    Uri sentImageUri;
    public StatusFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_status, container, false);
        camera=view.findViewById(R.id.status_camera_floating_Btn);
        edit=view.findViewById(R.id.status_edit_floating_Btn);
        relativeLayout=view.findViewById(R.id.updateStatus);
             fireStoreDataBASE=new FireStoreDataBASE();
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNewActivity();
            }

        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           moveToCamera();
            }
        });
        setupOnBackPressed();
        return view;
    }

    private void setupOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(isEnabled()){
                    setEnabled(false);
                    requireActivity().onBackPressed();
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void moveToNewActivity () {
        Intent i = new Intent(getActivity(), VideoTrimmer.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);

    }
    private void moveToCamera () {
        Intent i = new Intent(getActivity(), OpenCamera.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);

    }

}
