package com.example.whatsappclone1.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.whatsappclone1.R;
import com.example.whatsappclone1.VideoTrimmer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;



public class StatusFragment extends Fragment {
     FloatingActionButton camera,edit;
    ShapeableImageView userImageView;
    RelativeLayout relativeLayout;


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
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToNewActivity();
            }

        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }






//    void forResults(){
//        startForResult = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent uri0 = result.getData();
//                        Uri uri = uri0.getData();
//                        System.out.println(uri);
////                        trimVideo(uri);
////                        showVideo(uri);
//
//                    } else
//                        LogMessage.v("videoTrimResultLauncher data is null");
//                });
//    }



    private void moveToNewActivity () {

        Intent i = new Intent(getActivity(), VideoTrimmer.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);

    }
    }
