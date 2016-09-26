package com.example.yusuf.firebaseapp;

import android.app.ProgressDialog;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    // Don't forget to add firebase dependencies

    /*
     Giris yapmadan veri kaydetmek icin Firebase Dashboard'ındaki Storage kısmındaki
     Rules da gereken degisiklikleri yapıyoruz

     Don't change the Store Rules from Firebase Dashboard
     Rules should be;

     allow read, write: if true;

     Manifest Dosyasında izinleri istiyoruz
     Add permissions to Manifest

    <uses-permission android:name="android.permission.RECORD_AUDIO"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     */


    private Button btnRecord;
    private TextView tvInfo;

    private MediaRecorder mRecorder;
    private String mFileName= null;

    private ProgressDialog mProgressDailog;

    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRecord = (Button) findViewById(R.id.btnRecord);
        tvInfo = (TextView) findViewById(R.id.tvInfo);

        mProgressDailog = new ProgressDialog(this);

        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/recorded_audio.3gp";

        //Get instance
        mStorage = FirebaseStorage.getInstance().getReference();

        btnRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    startRecording();
                    tvInfo.setText("Recording Started");

                } else if (event.getAction() == MotionEvent.ACTION_UP){

                    stopRecording();
                    tvInfo.setText("Recording Stopped");

                }

                return false;
            }
        });


    }


    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("Record", "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        
        uploadAudio();
    }

    private void uploadAudio() {

        mProgressDailog.setMessage("Uploading Audio");
        mProgressDailog.show();

        StorageReference filePath = mStorage.child("Audio").child("new_audio.3gp");
        Uri uri = Uri.fromFile(new File(mFileName));
        filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mProgressDailog.dismiss();
                tvInfo.setText("Upload Finished");
            }
        });
    }
}
