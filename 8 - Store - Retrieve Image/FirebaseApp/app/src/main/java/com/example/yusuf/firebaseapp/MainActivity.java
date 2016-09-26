package com.example.yusuf.firebaseapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    // Don't forget to add firebase dependicies

    /*
     Giris yapmadan veri kaydetmek icin Firebase Dashboard'ındaki Storage kısmındaki
     Rules da gereken degisiklikleri yapıyoruz

     Don't change the Store Rules from Firebase Dashboard
     Rules should be;

     allow read, write: if true;

     Manifest Dosyasında izinleri istiyoruz
     Also add <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/> to manifest

     Picasso'yu da app altındaki gradle'a ekliyoruz
     Add picasso compile 'com.squareup.picasso:picasso:2.5.2' to app level gradle file
     */

    private Button btnSelectImage;
    private ImageView mImageView;

    private StorageReference mStorage;

    private static final int GALLERY_INTENT = 1 ;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSelectImage = (Button) findViewById(R.id.btn_image);
        mImageView = (ImageView) findViewById(R.id.imageView);

        mProgressDialog = new ProgressDialog(this);

        //Get instance
        mStorage = FirebaseStorage.getInstance().getReference();

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK){

            mProgressDialog.setMessage("Uploading...");
            mProgressDialog.show();

            Uri uri = data.getData();

            // "Photos" diye bir child olusturup sectigimiz resmin ismini veriyoruz
            // Create a folder name "Photos"
            StorageReference filePath = mStorage.child("Photos").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mProgressDialog.dismiss();

                    // Yükledigimiz resmi gösteriyoruz
                    // Show image
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    Picasso.with(MainActivity.this).load(downloadUri).fit().centerCrop().into(mImageView);

                    Toast.makeText(MainActivity.this,"Upload Done",Toast.LENGTH_SHORT).show();
                    Log.v("Test","image load");

                }
            });


        }
    }
}
