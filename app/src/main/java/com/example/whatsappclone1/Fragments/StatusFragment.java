package com.example.whatsappclone1.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.whatsappclone1.R;
import com.example.whatsappclone1.Tabbed_layout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class StatusFragment extends Fragment {
     FloatingActionButton camera,edit;
    Animation slide_down;
    Animation slide_up;
    public StatusFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_status, container, false);

        camera=view.findViewById(R.id.status_camera_floating_Btn);
        edit=view.findViewById(R.id.status_edit_floating_Btn);


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Load animation
         slide_down = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_down);

         slide_up = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_up);


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        edit.setAnimation(slide_up);
        edit.setVisibility(View.VISIBLE);
    }
}