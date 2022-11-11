package com.example.whatsappclone1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.gowtham.library.utils.LogMessage;
import com.gowtham.library.utils.TrimType;
import com.gowtham.library.utils.TrimVideo;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class VideoTrimmer extends AppCompatActivity {
    VideoView views;
    Button button;
    ActivityResultLauncher<Intent> startForResult;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_trimmer);
        views=findViewById(R.id.videosView);
        button=findViewById(R.id.selectVideoBtn);
        forResult();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkPermission();
            }
        });
       }
    private void checkPermission(){
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

//                        intent.setType("video/*");
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
                       startForResult.launch(intent);



                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }



    void trimVideo(Uri videoUri){
        TrimVideo.activity(String.valueOf(videoUri))
                .setHideSeekBar(true)
                .setTrimType(TrimType.FIXED_DURATION)
                .setFixedDuration(30)
                .start(this,startForResult);

    }

    void forResult(){
        startForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK &&
                            result.getData() != null) {
                       Intent empty= result.getData();
                          trimVideo(empty.getData());
                        Uri uri = Uri.parse(TrimVideo.getTrimmedVideoPath(result.getData()));
                        showVideo(uri);

                    } else
                        LogMessage.v("videoTrimResultLauncher data is null");
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    void showVideo(Uri trimmedVideo){
        views.setVideoURI(trimmedVideo);
        views.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        views.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
    }

}